package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.File;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService {
    private FileMapper _fileMapper;

    public FileService(FileMapper _fileMapper) {
        this._fileMapper = _fileMapper;
    }

    public List<File> getFiles(String username) {
        return _fileMapper.getFiles(username);
    }

    public Boolean uploadFile(File fileIn) {
        int result = _fileMapper.insertFile(fileIn);
        return result > 0;
    }
}
