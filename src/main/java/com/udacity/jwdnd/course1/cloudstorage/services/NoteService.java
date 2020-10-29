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

    public void addNote(Note note) {
        _noteMapper.insertNote(note);
    }

    public List<Note> getNotes(String username) {
        return _noteMapper.getNotes(username);
    }
}
