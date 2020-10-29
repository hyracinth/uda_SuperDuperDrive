package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {
    private final NoteService _noteService;
    private final UserService _userService;

    private static final String NEW_NOTE = "newNote";
    private static final String LIST_NOTES = "listNotes";

    public HomeController(NoteService _noteService, UserService _userService) {
        this._noteService = _noteService;
        this._userService = _userService;
    }

    @GetMapping
    public String getHomePage(@ModelAttribute(NEW_NOTE) Note newNote, Authentication auth, Model model) {
        return "home";
    }

    @PostMapping(params = "createNote")
    public String createNote(@ModelAttribute(NEW_NOTE) Note newNote, Authentication auth, Model model) {
        _noteService.addNote(new Note(null,
                newNote.getNoteTitle(),
                newNote.getNoteDescription(),
                _userService.getUser(auth.getName()).getUserId()));

        model.addAttribute(LIST_NOTES, _noteService.getNotes(auth.getName()));

        return "home";
    }

    // TODO Fix delete button location
    @PostMapping(params = "deleteNote")
    public String deleteNote(@ModelAttribute(NEW_NOTE) Note newNote, @RequestParam("deleteNote") Integer noteId, Authentication auth, Model model) {
        _noteService.deleteNote(noteId);

        model.addAttribute(LIST_NOTES, _noteService.getNotes(auth.getName()));
        return "home";
    }
}

/*
Questions:
th:action="@{/home}" vs th:action="@{'/home'}"


 */

/*
Notes:
Need to add an empty model for getmapping for form
    otherwise will complain regarding missing properties
 */