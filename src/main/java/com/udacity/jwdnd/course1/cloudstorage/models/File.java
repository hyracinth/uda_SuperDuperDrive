package com.udacity.jwdnd.course1.cloudstorage.models;

import lombok.Getter;

@Getter
public class File {
    private final Integer fileId;
    private final String filename;
    private final String contentType;
    private final String fileSize;
    private final Integer userId;
    private final byte[] fileData;

    public File(Integer fileId, String filename, String contentType, String fileSize, Integer userId, byte[] fileData) {
        this.fileId = fileId;
        this.filename = filename;
        this.contentType = contentType;
        this.fileSize = fileSize;
        this.userId = userId;
        this.fileData = fileData;
    }
}
