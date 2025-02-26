package org.burgas.mediaservice.service;

import org.burgas.mediaservice.entity.Media;
import org.burgas.mediaservice.exception.MediaNotFoundException;
import org.burgas.mediaservice.repository.MediaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

import static org.burgas.mediaservice.entity.MediaMessage.*;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class MediaService {

    private final MediaRepository mediaRepository;

    public MediaService(MediaRepository mediaRepository) {
        this.mediaRepository = mediaRepository;
    }

    public Media findById(Long mediaId) {
        return mediaRepository.findById(mediaId)
                .orElseGet(Media::new);
    }

    @Transactional(
            isolation = Isolation.SERIALIZABLE,
            propagation = Propagation.REQUIRED,
            rollbackFor = Exception.class
    )
    public String uploadMediaFile(Long mediaId, MultipartFile multipartFile) {
        return mediaRepository.findById(mediaId)
                .map(
                        media -> Optional.ofNullable(multipartFile)
                                .filter(file -> !file.isEmpty())
                                .map(
                                        file -> {
                                            try {
                                                media.setId(mediaId);
                                                media.setName(file.getOriginalFilename());
                                                media.setContentType(file.getContentType());
                                                media.setData(file.getBytes());
                                                Media saved = mediaRepository.save(media);

                                                return String.format(MEDIA_FILE_EDITED.getMessage(), saved.getId());

                                            } catch (IOException e) {
                                                throw new RuntimeException(e);
                                            }
                                        }
                                )
                                .orElseThrow(
                                        () -> new RuntimeException(
                                                String.format(MULTIPART_EMPTY_IN_UPDATE.getMessage())
                                        )
                                )
                )
                .orElseGet(
                        () -> Optional.of(multipartFile)
                                .filter(file -> !file.isEmpty())
                                .map(
                                        file -> {
                                            try {
                                                Media saved = mediaRepository.save(
                                                        Media.builder()
                                                                .name(file.getOriginalFilename())
                                                                .contentType(file.getContentType())
                                                                .data(file.getBytes())
                                                                .build()
                                                );

                                                return String.format(MEDIA_FILE_UPLOADED.getMessage(), saved.getId());

                                            } catch (IOException e) {
                                                throw new RuntimeException(e);
                                            }
                                        }
                                )
                                .orElseThrow(
                                        () -> new RuntimeException(
                                                String.format(MULTIPART_EMPTY_IN_CREATE.getMessage())
                                        )
                                )
                );
    }

    @Transactional(
            isolation = Isolation.READ_COMMITTED,
            propagation = Propagation.REQUIRED,
            rollbackFor = Exception.class
    )
    public String deleteMediaFile(Long mediaId) {
        return mediaRepository.findById(mediaId)
                .map(
                        media -> {
                            mediaRepository.deleteById(media.getId());
                            return String.format(MEDIA_FILE_DELETED.getMessage(), media.getId());
                        }
                )
                .orElseThrow(() -> new MediaNotFoundException(MEDIA_NOT_FOUND.getMessage()));
    }
}
