package org.burgas.mediaservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.burgas.mediaservice.entity.Media;
import org.burgas.mediaservice.exception.WrongMediatypeException;
import org.burgas.mediaservice.service.MediaService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.Objects;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.burgas.mediaservice.entity.MediaMessage.WRONG_MEDIATYPE;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.*;

@Controller
@RequestMapping("/media")
@Tag(
        name = "MediaController",
        description = "Контроллер для обработки медиа файлов и данных"
)
public class MediaController {

    private final MediaService mediaService;

    public MediaController(MediaService mediaService) {
        this.mediaService = mediaService;
    }

    @GetMapping(value = "/by-id")
    @Operation(summary = "Получение медиа файла")
    public @ResponseBody ResponseEntity<Resource> getMediaFile(@RequestParam Long mediaId) {
        Media media = mediaService.findById(mediaId);
        return ResponseEntity
                .status(OK)
                .contentType(valueOf(media.getContentType()))
                .body(
                        new InputStreamResource(
                                new ByteArrayInputStream(media.getData())
                        )
                );
    }


    @Operation(summary = "Загрузка медиа файла")
    @PostMapping(value = "/upload", consumes = MULTIPART_FORM_DATA_VALUE)
    public @ResponseBody ResponseEntity<String> uploadMediaFile(
            @RequestPart MultipartFile file, @RequestPart String mediaType, @RequestPart(required = false) String mediaId
    ) {
        if (
                Objects.requireNonNull(file.getContentType())
                        .split("/")[0]
                        .equalsIgnoreCase(mediaType)
        ) {
            return ResponseEntity
                    .status(OK)
                    .contentType(TEXT_PLAIN)
                    .body(mediaService.uploadMediaFile(mediaId == null || mediaId.isBlank() ? 0L : Long.parseLong(mediaId), file));
        } else
            throw new WrongMediatypeException(WRONG_MEDIATYPE.getMessage());
    }

    @Operation(summary = "Удаление медиа файла")
    @DeleteMapping(value = "/delete")
    public @ResponseBody ResponseEntity<String> deleteMediaFile(@RequestParam Long mediaId) {
        return ResponseEntity
                .status(OK)
                .contentType(new MediaType(TEXT_PLAIN, UTF_8))
                .body(mediaService.deleteMediaFile(mediaId));
    }
}
