package org.burgas.commitservice.mapper;

import org.burgas.commitservice.dto.CommitResponse;
import org.burgas.commitservice.entity.Commit;
import org.burgas.commitservice.repository.ChooseRepository;
import org.burgas.commitservice.repository.TokenRepositoryCommitRepository;
import org.burgas.databaseserver.entity.Token;
import org.springframework.stereotype.Component;

@Component
public class CommitMapper {

    private final ChooseRepository chooseRepository;
    private final TokenRepositoryCommitRepository tokenRepositoryCommitRepository;
    private final ChooseMapper chooseMapper;

    public CommitMapper(
            ChooseRepository chooseRepository, TokenRepositoryCommitRepository tokenRepositoryCommitRepository,
            ChooseMapper chooseMapper
    ) {
        this.chooseRepository = chooseRepository;
        this.tokenRepositoryCommitRepository = tokenRepositoryCommitRepository;
        this.chooseMapper = chooseMapper;
    }

    public CommitResponse toCommitResponse(Commit commit) {
        return CommitResponse.builder()
                .id(commit.getId())
                .identityId(commit.getIdentityId())
                .token(tokenRepositoryCommitRepository.findById(commit.getTokenId()).orElseGet(Token::new))
                .price(commit.getPrice())
                .closed(commit.getClosed())
                .chooseResponses(
                        chooseRepository
                                .findChoosesByCommitId(commit.getId())
                                .stream()
                                .map(chooseMapper::toChooseResponse)
                                .toList()
                )
                .build();
    }
}
