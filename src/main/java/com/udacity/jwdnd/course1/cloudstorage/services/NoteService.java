package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    private NoteMapper _noteMapper;

    public NoteService(NoteMapper _noteMapper) {
        this._noteMapper = _noteMapper;
    }

    public List<Note> getNotes(String username) {
        return _noteMapper.getNotes(username);
    }

    public Boolean createUpdateNote(Note note) {
        int result;
        if (note.getNoteId() != null) {
            result = _noteMapper.updateNote(note);
        }
        else {
            result = _noteMapper.insertNote(note);
        }
        System.out.println(result);
        return result > 0;
    }

    public Boolean deleteNote(Integer noteId) {
        try {
            _noteMapper.deleteNote(noteId);
            return true;
        } catch(Exception e) {
            //TODO Log?
            System.out.println("Deleted failed");
            return false;
        }
    }

}
