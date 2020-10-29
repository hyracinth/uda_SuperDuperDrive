package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class HomeController {
    private final NoteService _noteService;
    private final UserService _userService;

    private static final String NEW_NOTE = "newNote";
    private static final String LIST_NOTES = "listNotes";

    public HomeController(NoteService _noteService, UserService _userService) {
        this._noteService = _noteService;
        this._userService = _userService;
    }

    @GetMapping("/home")
    public String getHomePage(@ModelAttribute(NEW_NOTE) Note newNote, Authentication auth, Model model) {
        model.addAttribute(LIST_NOTES, _noteService.getNotes(auth.getName()));
        return "home";
    }

    @PostMapping(value = "/notes", params = "createUpdateNote")
    public String createUpdateNote(@ModelAttribute(NEW_NOTE) Note newNote, Authentication auth, Model model) {
        Boolean result = _noteService.createUpdateNote(new Note(newNote.getNoteId(),
                newNote.getNoteTitle(),
                newNote.getNoteDescription(),
                _userService.getUser(auth.getName()).getUserId()));

        model.addAttribute(LIST_NOTES, _noteService.getNotes(auth.getName()));
        return "redirect:/result?isSuccess=" + result;
    }

    @GetMapping("/deleteNote/{noteId}")
    public String deleteNote(@ModelAttribute(NEW_NOTE) Note newNote, @PathVariable Integer noteId, Authentication auth, Model model) {
        Boolean result = _noteService.deleteNote(noteId);

        model.addAttribute(LIST_NOTES, _noteService.getNotes(auth.getName()));
        return "redirect:/result?isSuccess=" + result;
    }

    @GetMapping("/result")
    public String result(@RequestParam(name = "isSuccess") Boolean isSuccess, Model model) {
        model.addAttribute("isSuccess", isSuccess);
        return "result";
    }
}

/*
Questions:
th:action="@{/home}" vs th:action="@{'/home'}"
action vs href
*/

/*
Notes:
Need to add an empty model for getMapping for form
    otherwise will complain regarding missing properties
 */