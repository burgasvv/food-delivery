package org.burgas.commitservice.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.burgas.commitservice.dto.CommitResponse;
import org.burgas.commitservice.entity.Commit;
import org.burgas.commitservice.exception.*;
import org.burgas.commitservice.handler.RestClientHandlerCommitService;
import org.burgas.commitservice.mapper.CommitMapper;
import org.burgas.commitservice.repository.ChooseIngredientRepository;
import org.burgas.commitservice.repository.ChooseRepository;
import org.burgas.commitservice.repository.CommitRepository;
import org.burgas.commitservice.repository.TokenRepositoryCommitRepository;
import org.burgas.databaseserver.entity.Token;
import org.burgas.identityservice.dto.IdentityPrincipal;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Objects;

import static org.burgas.commitservice.entity.CommitMessage.*;

@Service
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class CommitService {

    private final CommitRepository commitRepository;
    private final CommitMapper commitMapper;
    private final RestClientHandlerCommitService restClientHandlerCommitService;
    private final TokenRepositoryCommitRepository tokenRepositoryCommitRepository;
    private final ChooseIngredientRepository chooseIngredientRepository;
    private final ChooseRepository chooseRepository;

    public CommitService(
            CommitRepository commitRepository, CommitMapper commitMapper,
            RestClientHandlerCommitService restClientHandlerCommitService,
            TokenRepositoryCommitRepository tokenRepositoryCommitRepository,
            ChooseIngredientRepository chooseIngredientRepository, ChooseRepository chooseRepository
    ) {
        this.commitRepository = commitRepository;
        this.commitMapper = commitMapper;
        this.restClientHandlerCommitService = restClientHandlerCommitService;
        this.tokenRepositoryCommitRepository = tokenRepositoryCommitRepository;
        this.chooseIngredientRepository = chooseIngredientRepository;
        this.chooseRepository = chooseRepository;
    }

    public CommitResponse findCommit(
            HttpServletRequest request, String authentication
    ) {
        HttpSession session = request.getSession();
        CommitResponse commitResponse = (CommitResponse) session.getAttribute("commitCookie");

        if (commitResponse != null && !commitResponse.getClosed())
            return commitResponse;

        Cookie[] cookies = request.getCookies();
        if (cookies == null)
            cookies = new Cookie[0];

        Cookie commitCookie = Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals("commitCookie"))
                .findFirst()
                .orElse(null);

        IdentityPrincipal identityPrincipal = restClientHandlerCommitService
                .getIdentityPrincipal(authentication).getBody();

        if (commitCookie != null && !Objects.requireNonNull(identityPrincipal).getAuthenticated())
            return getCommitResponseByCookie(commitCookie);

        else if (commitCookie != null)

            if (Objects.requireNonNull(identityPrincipal).getAuthenticated())
                return getCommitResponseByIdentity(identityPrincipal);
            else
                return getCommitResponseByCookie(commitCookie);

        else
            if (Objects.requireNonNull(identityPrincipal).getAuthenticated())
                return getCommitResponseByIdentity(identityPrincipal);
            else
                return CommitResponse.builder().build();

    }

    private CommitResponse getCommitResponseByIdentity(IdentityPrincipal identityPrincipal) {
        return commitRepository.findCommitByIdentityId(identityPrincipal.getId())
                .map(commitMapper::toCommitResponse)
                .orElseGet(CommitResponse::new);
    }

    private @NotNull CommitResponse getCommitResponseByCookie(Cookie commitCookie) {
        Token token = tokenRepositoryCommitRepository
                .findTokenByValue(commitCookie.getValue())
                .orElseGet(Token::new);

        return commitRepository.findCommitByTokenIdAndClosed(token.getId(), false)
                .map(commitMapper::toCommitResponse)
                .orElseGet(CommitResponse::new);
    }

    @Transactional(
            isolation = Isolation.SERIALIZABLE,
            propagation = Propagation.REQUIRED,
            rollbackFor = Exception.class
    )
    public String closeCommit(
            HttpServletRequest request, String authentication
    ) {
        Cookie commitCookie = Arrays.stream(request.getCookies())
                .filter(cookie -> "commitCookie".equals(cookie.getName()))
                .findFirst()
                .orElse(null);

        IdentityPrincipal identityPrincipal = restClientHandlerCommitService
                .getIdentityPrincipal(authentication).getBody();

        CommitResponse commitResponse = (CommitResponse) request.getSession().getAttribute("commitCookie");

        if (commitResponse != null && !commitResponse.getClosed()) {
            Commit commit = commitRepository.findById(commitResponse.getId())
                    .orElse(null);

            if (commit != null) {
                return closeCommitMain(commit, COMMIT_CLOSED_BY_TOKEN.getMessage(), request);

            } else
                throw new NoUserCommitsException(NO_USER_COMMITS.getMessage());
        }

        if (commitCookie != null) {
            Token token = tokenRepositoryCommitRepository.findTokenByValue(commitCookie.getValue())
                    .orElse(null);

            if (token != null) {
                Commit commit = commitRepository.findCommitByTokenId(token.getId())
                        .orElse(null);

                if (commit != null) {
                    return closeCommitMain(commit, COMMIT_CLOSED_BY_TOKEN.getMessage(), request);

                } else
                    throw new NoUserCommitsException(NO_USER_COMMITS.getMessage());

            } else
                throw new TokenNotFoundException(TOKEN_NOT_FOUND.getMessage());

        } else if (identityPrincipal != null) {

            if (identityPrincipal.getAuthenticated()) {
                Commit commit = commitRepository.findCommitByIdentityId(identityPrincipal.getId())
                        .orElse(null);

                if (commit != null) {
                    return closeCommitMain(commit, COMMIT_CLOSED_BY_USER_ID.getMessage(), request);

                } else
                    throw new NoUserCommitsException(NO_USER_COMMITS.getMessage());

            } else
                throw new NotAuthorizedException(NOT_AUTHORIZED.getMessage());

        } else
            throw new CommitIsNotClosedException(COMMIT_IS_NOT_CLOSED.getMessage());
    }

    private String closeCommitMain(Commit commit, String commitClosedString, HttpServletRequest request) {
        if (!commit.getClosed()) {
            commit.setClosed(true);
            CommitResponse commitResponse = commitMapper.toCommitResponse(
                    commitRepository.save(commit)
            );
            request.getSession().setAttribute("commitCookie", commitResponse);
            return commitClosedString;

        } else
            throw new CommitAlreadyClosedException(COMMIT_ALREADY_CLOSED.getMessage());
    }

    @Transactional(
            isolation = Isolation.SERIALIZABLE,
            propagation = Propagation.REQUIRED,
            rollbackFor = Exception.class
    )
    public String deleteCommit(HttpServletRequest request, String authentication) {
        CommitResponse commitResponse = (CommitResponse) request.getSession().getAttribute("commitCookie");
        IdentityPrincipal identityPrincipal = restClientHandlerCommitService.getIdentityPrincipal(authentication).getBody();

        if (commitResponse != null && !commitResponse.getClosed())
            return deleteCommitMain(commitResponse, COMMIT_DELETED_BY_SESSION.getMessage());

        else
            if (identityPrincipal != null && identityPrincipal.getAuthenticated()) {
                commitResponse = commitRepository.findCommitByIdentityId(identityPrincipal.getId())
                        .map(commitMapper::toCommitResponse)
                        .orElse(null);

                if (commitResponse != null && !commitResponse.getClosed())
                    return deleteCommitMain(commitResponse, COMMIT_DELETED_BY_REPOSITORY.getMessage());

                else
                    throw new CommitNotFoundException(COMMIT_NOT_FOUND.getMessage());

            } else
                throw new NotAuthorizedException(NOT_AUTHORIZED.getMessage());
    }

    private String deleteCommitMain(CommitResponse commitResponse, String message) {
        commitResponse.getChooseResponses().forEach(
                chooseResponse -> {
                    chooseIngredientRepository.deleteChooseIngredientsByChooseId(chooseResponse.getId());
                    chooseRepository.deleteById(chooseResponse.getId());
                }
        );

        commitRepository.deleteById(commitResponse.getId());
        return message;
    }
}
