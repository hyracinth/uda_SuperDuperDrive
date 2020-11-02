package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * This service handles logic regarding Note object
 */
@Service
public class NoteService {
    private final Logger logger = LoggerFactory.getLogger(NoteService.class);
    private final NoteMapper _noteMapper;

    public NoteService(NoteMapper _noteMapper) {
        this._noteMapper = _noteMapper;
    }

    /**
     * This method returns a list of notes of the provided username
     *
     * @param username username to search for notes
     * @return a list of notes of username
     */
    public List<Note> getNotes(String username) {
        return _noteMapper.getNotes(username);
    }

    /**
     * The method handles creating and updating notes
     * If the note exist, it will update, else it will insert
     *
     * @param note note to be updated or inserted
     * @return returns true if inserted / updated
     */
    public Boolean createUpdateNote(Note note) {
        try {
            int result;
            if (note.getNoteId() != null) {
                result = _noteMapper.updateNote(note);
            } else {
                result = _noteMapper.insertNote(note);
            }
            return result > 0;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return false;
    }

    /**
     * This method deletes a note given an noteId
     *
     * @param noteId note to be deleted
     * @return returns true if delete successful
     */
    public Boolean deleteNote(Integer noteId) {
        try {
            _noteMapper.deleteNote(noteId);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
    }

}
