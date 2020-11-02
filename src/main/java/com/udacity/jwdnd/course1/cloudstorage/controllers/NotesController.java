package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.config.SDDConstants;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.ActiveTabService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * This controller handles logic regarding notes
 */
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

    /**
     * This method handles the logic for creating and updating Notes
     * It takes a note object from HTML side and sends it to the service to be handled
     * The method also sets the active tab and then sends an updated list of notes
     * @param newNote note from the user to be added / updated
     * @param auth Authentication object to get user details
     * @param model Model object for data binding
     * @return redirects to results page to show user status
     */
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

    /**
     * This method takes an id from the HTML and pass it to the service to be deleted
     * @param noteId id of note to be deleted
     * @param auth Authentication object to get user details
     * @param model Model object for data binding
     * @return redirects to results page to show user status
     */
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
