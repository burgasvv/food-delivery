package org.burgas.commitservice.mapper;

import org.burgas.commitservice.dto.CommitResponse;
import org.burgas.commitservice.entity.Commit;
import org.burgas.commitservice.handler.RestClientHandler;
import org.burgas.commitservice.repository.ChooseRepository;
import org.springframework.stereotype.Component;

@Component
public class CommitMapper {

    private final ChooseRepository chooseRepository;
    private final ChooseMapper chooseMapper;
    private final RestClientHandler restClientHandler;

    public CommitMapper(
            ChooseRepository chooseRepository,
            ChooseMapper chooseMapper, RestClientHandler restClientHandler
    ) {
        this.chooseRepository = chooseRepository;
        this.chooseMapper = chooseMapper;
        this.restClientHandler = restClientHandler;
    }

    public CommitResponse toCommitResponse(Commit commit) {
        return CommitResponse.builder()
                .id(commit.getId())
                .identityId(commit.getIdentityId())
                .token(restClientHandler.getTokenById(commit.getTokenId()).getBody())
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
