package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {
    @Select("SELECT * FROM NOTES, USERS " +
            "WHERE NOTES.userId = USERS.userId and USERS.username=#{username}")
    List<Note> getNotes(String username);

    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid) " +
            "VALUES(#{noteTitle}, #{noteDescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    int insertNote(Note note);

    @Delete("DELETE FROM NOTES WHERE noteid=#{noteId}")
    void deleteNote(Integer noteId);

    @Update("UPDATE NOTES " +
            "SET notetitle=#{noteTitle}, notedescription=#{noteDescription} " +
            "WHERE noteid=#{noteId}")
    int updateNote(Note note);
}
