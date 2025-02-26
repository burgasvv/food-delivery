package org.burgas.mediaservice.entity;

public enum MediaMessage {

    MULTIPART_FILE_EMPTY("Предоставленный файл пуст"),
    MEDIA_FILE_UPLOADED("Файл с идентификатором %s успешно загружен"),
    MEDIA_FILE_EDITED("Файл с идентификатором %s успешно изменен"),
    WRONG_MEDIATYPE("В данном случае это неверный тип файла"),
    MEDIA_NOT_FOUND("Данные о медиа файле не найдены"),
    MEDIA_FILE_DELETED("Медиа файл с идентификатором %s успешно удален"),
    MULTIPART_EMPTY_IN_UPDATE("Multipart file is empty in edit"),
    MULTIPART_EMPTY_IN_CREATE("Multipart file is empty in insert");

    private final String message;

    MediaMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
