package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {
    @Select("SELECT * FROM FILES, USERS " +
            "WHERE FILES.userId = USERS.userId and USERS.username=#{username}")
    List<File> getFiles(String username);

    @Select("SELECT * FROM FILES WHERE fileid=#{fileId}")
    File getFile(Integer fileId);

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, filedata, userid) " +
            "VALUES (#{filename}, #{contentType}, #{fileSize}, #{fileData}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "fileid")
    int insertFile(File fileIn);

    @Delete("DELETE FROM FILES WHERE fileid=#{fileId}")
    void deleteFile(Integer fileId);
}
