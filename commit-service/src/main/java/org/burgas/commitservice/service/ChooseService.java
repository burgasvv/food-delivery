package org.burgas.commitservice.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.burgas.commitservice.dto.ChooseRequest;
import org.burgas.commitservice.dto.ChooseResponse;
import org.burgas.commitservice.dto.CommitResponse;
import org.burgas.commitservice.entity.Choose;
import org.burgas.commitservice.entity.Commit;
import org.burgas.commitservice.exception.ChooseNotFoundException;
import org.burgas.commitservice.exception.CommitNotFoundException;
import org.burgas.commitservice.exception.CookieNotFoundException;
import org.burgas.commitservice.exception.TokenNotFoundException;
import org.burgas.commitservice.mapper.ChooseMapper;
import org.burgas.commitservice.mapper.CommitMapper;
import org.burgas.commitservice.repository.ChooseRepository;
import org.burgas.commitservice.repository.CommitRepository;
import org.burgas.commitservice.repository.TokenRepositoryCommitRepository;
import org.burgas.databaseserver.entity.Token;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

import static org.burgas.commitservice.entity.ChooseMessage.*;
import static org.burgas.commitservice.entity.CommitMessage.COMMIT_NOT_FOUND;
import static org.burgas.commitservice.entity.CommitMessage.TOKEN_NOT_FOUND;

@Service
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class ChooseService {

    private final ChooseRepository chooseRepository;
    private final ChooseMapper chooseMapper;
    private final CommitRepository commitRepository;
    private final TokenRepositoryCommitRepository tokenRepositoryCommitRepository;
    private final CommitMapper commitMapper;

    public ChooseService(
            ChooseRepository chooseRepository,
            ChooseMapper chooseMapper, CommitRepository commitRepository,
            TokenRepositoryCommitRepository tokenRepositoryCommitRepository, CommitMapper commitMapper
    ) {
        this.chooseRepository = chooseRepository;
        this.chooseMapper = chooseMapper;
        this.commitRepository = commitRepository;
        this.tokenRepositoryCommitRepository = tokenRepositoryCommitRepository;
        this.commitMapper = commitMapper;
    }

    public ChooseResponse findById(Long chooseId) {
        return chooseRepository.findById(chooseId)
                .map(chooseMapper::toChooseResponse)
                .orElseGet(ChooseResponse::new);
    }

    @Transactional(
            isolation = Isolation.SERIALIZABLE,
            propagation = Propagation.REQUIRED,
            rollbackFor = Exception.class
    )
    public ChooseResponse createOrUpdate(ChooseRequest chooseRequest, String authentication, Cookie cookie) {
        return chooseMapper.toChooseResponse(
                chooseRepository.save(chooseMapper.toChoose(chooseRequest, authentication, cookie))
        );
    }

    @Transactional(
            isolation = Isolation.SERIALIZABLE,
            propagation = Propagation.REQUIRED,
            rollbackFor = Exception.class
    )
    public String changeChooseAmount(Long chooseId, HttpServletRequest request, Boolean more) {
        Cookie commitCookie = Arrays.stream(request.getCookies())
                .filter(cookie -> "commitCookie".equals(cookie.getName()))
                .findFirst()
                .orElse(null);

        CommitResponse sessionCommit = (CommitResponse) request.getSession().getAttribute("commitCookie");
        if (sessionCommit != null && !sessionCommit.getClosed()) {
            Choose choose = chooseRepository.findById(chooseId).orElse(null);
            Token token = tokenRepositoryCommitRepository
                    .findTokenByValue(sessionCommit.getToken().getValue())
                    .orElse(null);

            return changeChooseMain(more, choose, token, request);
        }

        if (commitCookie != null) {
            Choose choose = chooseRepository.findById(chooseId).orElse(null);
            Token token = tokenRepositoryCommitRepository
                    .findTokenByValue(commitCookie.getValue())
                    .orElse(null);

            return changeChooseMain(more, choose, token, request);

        } else
            throw new CookieNotFoundException(COOKIE_NOT_FOUND.getMessage());
    }

    private String changeChooseMain(Boolean more, Choose choose, Token token, HttpServletRequest request) {
        if (choose != null && token != null) {
            Commit commit = commitRepository.findCommitByTokenId(token.getId()).orElse(null);

            if (commit != null) {
                ChooseResponse chooseResponse = chooseMapper.toChooseResponse(choose);
                CommitResponse commitResponse = commitMapper.toCommitResponse(commit);

                Long foundedChooseId = commitResponse.getChooseResponses()
                        .stream()
                        .filter(cr -> chooseResponse.getId().equals(cr.getId()))
                        .findFirst()
                        .map(ChooseResponse::getId)
                        .orElse(null);

                if (choose.getId().equals(foundedChooseId)) {

                    Long priceForOne = choose.getPrice() / choose.getAmount();

                    if (more) {
                        choose.setAmount(choose.getAmount() + 1);
                        choose.setPrice(choose.getPrice() + priceForOne);
                        chooseRepository.save(choose);

                        commit.setPrice(commit.getPrice() + priceForOne);
                        commitResponse = commitMapper.toCommitResponse(
                                commitRepository.save(commit)
                        );
                        request.getSession().setAttribute("commitCookie", commitResponse);

                        return CHOOSE_INCREMENTED.getMessage();

                    } else {
                        choose.setAmount(choose.getAmount() - 1);
                        choose.setPrice(choose.getPrice() - priceForOne);
                        chooseRepository.save(choose);

                        commit.setPrice(commit.getPrice() - priceForOne);

                        if (commit.getPrice() < 1)
                            throw new RuntimeException(CHOOSELESS.getMessage());

                        commitResponse = commitMapper.toCommitResponse(
                                commitRepository.save(commit)
                        );
                        request.getSession().setAttribute("commitCookie", commitResponse);

                        return CHOOSE_DECREMENTED.getMessage();
                    }

                } else
                    throw new ChooseNotFoundException(CHOOSE_NOT_FOUND.getMessage());

            } else
                throw new CommitNotFoundException(COMMIT_NOT_FOUND.getMessage());

        } else
            throw new TokenNotFoundException(TOKEN_NOT_FOUND.getMessage());
    }
}
