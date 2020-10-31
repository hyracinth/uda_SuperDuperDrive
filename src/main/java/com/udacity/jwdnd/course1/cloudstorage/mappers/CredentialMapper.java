package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {
    @Insert("INSERT INTO CREDENTIALS (url, username, key, password, userid) " +
            "VALUES (#{url}, #{username}, #{key}, #{password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    int insertCredential(Credential credIn);

    @Select("SELECT * FROM CREDENTIALS, USERS " +
            "WHERE CREDENTIALS.userId = USERS.userId and USERS.username=#{username}")
    List<Credential> getCredentials(String username);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialid=#{credId}")
    void deleteCredential(Integer credId);

    @Select("SELECT * FROM CREDENTIALS WHERE credentialid=#{credId}")
    Credential getCredential(Integer credId);

    @Update("UPDATE CREDENTIALS " +
            "SET url=#{url}, username=#{username}, password=#{password}, key=#{key}" +
            "WHERE credentialid=#{credentialId}")
    int updateCredential(Credential cred);
}