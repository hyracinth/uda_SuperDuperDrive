package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.File;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * This service handles logic regarding File object
 */
@Service
public class FileService {
    private Logger logger = LoggerFactory.getLogger(FileService.class);
    private FileMapper _fileMapper;

    public FileService(FileMapper _fileMapper) {
        this._fileMapper = _fileMapper;
    }

    /**
     * This method returns a list of files of the provided username
     * @param username username to search for files
     * @return a list of files of username
     */
    public List<File> getFiles(String username) {
        return _fileMapper.getFiles(username);
    }

    /**
     * This method returns a file
     * @param fileId id to search for files
     * @return a file of fileId
     */
    public File getFile(Integer fileId) {
        try {
            return _fileMapper.getFile(fileId);
        }
        catch(Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    /**
     * This method uploads a file to the database
     * @param fileIn file to be uploaded
     * @return true/false on success uploaded
     */
    public Boolean uploadFile(File fileIn) {
        try {
            int result = _fileMapper.insertFile(fileIn);
            return result > 0;
        }
        catch(Exception e) {
            logger.error(e.getMessage());
        }
        return false;
    }

    /**
     * This method deletes a file from the database
     * @param fileId file to be deleted
     * @return true/false on success delete
     */
    public Boolean deleteFile(Integer fileId) {
        try {
            _fileMapper.deleteFile(fileId);
            return true;
        } catch(Exception e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    /**
     * This method checks to see if a file exists for a username
     * @param filename filename to be checked
     * @param username username to be checked
     * @return true / false on file exists
     */
    public Boolean doesFileExist(String filename, String username) {
        try {
            List<File> listFiles = getFiles(username);
            for (File currFile : listFiles) {
                if (currFile.getFilename().equals(filename)) {
                    return true;
                }
            }
        }
        catch(Exception e) {
            logger.error(e.getMessage());
        }
        return false;
    }
}
