package com.udacity.jwdnd.course1.cloudstorage.models;

import lombok.Getter;

@Getter
public class File {
    private Integer fileId;
    private String filename;
    private String contentType;
    private String fileSize;
    private Integer userId;
    private byte[] fileData;

    public File(Integer fileId, String filename, String contentType, String fileSize, Integer userId, byte[] fileData) {
        this.fileId = fileId;
        this.filename = filename;
        this.contentType = contentType;
        this.fileSize = fileSize;
        this.userId = userId;
        this.fileData = fileData;
    }
}
