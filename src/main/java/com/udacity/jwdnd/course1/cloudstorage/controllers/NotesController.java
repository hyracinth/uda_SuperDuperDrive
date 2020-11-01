package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.config.SDDConstants;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.ActiveTabService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class NotesController {
    private NoteService noteService;
    private UserService userService;
    private final ActiveTabService activeTabService;

    public NotesController(NoteService noteService,
                           UserService userService,
                           ActiveTabService activeTabService) {
        this.noteService = noteService;
        this.userService = userService;
        this.activeTabService = activeTabService;
    }

    @PostMapping(value = "/notes/createUpdate", params = "createUpdateNote")
    public String createUpdateNote(@ModelAttribute(SDDConstants.NEW_NOTE) Note newNote,
                                   Authentication auth,
                                   Model model) {
        activeTabService.setActiveTab(SDDConstants.TAB_NOTES);
        Boolean result = noteService.createUpdateNote(new Note(newNote.getNoteId(),
                newNote.getNoteTitle(),
                newNote.getNoteDescription(),
                userService.getUser(auth.getName()).getUserId()));

        model.addAttribute(SDDConstants.LIST_NOTES, noteService.getNotes(auth.getName()));
        return "redirect:/result?success=" + result;
    }

    @GetMapping("/notes/delete/{noteId}")
    public String deleteNote(@PathVariable Integer noteId,
                             Authentication auth,
                             Model model) {
        activeTabService.setActiveTab(SDDConstants.TAB_NOTES);
        Boolean result = noteService.deleteNote(noteId);
        model.addAttribute(SDDConstants.LIST_NOTES, noteService.getNotes(auth.getName()));
        return "redirect:/result?success=" + result;
    }
}
