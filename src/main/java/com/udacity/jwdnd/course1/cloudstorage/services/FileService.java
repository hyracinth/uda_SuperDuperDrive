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

    public File getFile(Integer fileId) {
        return _fileMapper.getFile(fileId);
    }

    public Boolean uploadFile(File fileIn) {
        int result = _fileMapper.insertFile(fileIn);
        return result > 0;
    }

    public Boolean deleteFile(Integer fileId) {
        try {
            _fileMapper.deleteFile(fileId);
            return true;
        } catch(Exception e) {
            //TODO Log?
            System.out.println("Deleted failed");
            return false;
        }
    }
}
