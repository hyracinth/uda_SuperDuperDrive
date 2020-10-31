package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;

@Controller
public class HomeController {
    private final NoteService _noteService;
    private final UserService _userService;
    private final FileService _fileService;

    private static final String NEW_NOTE = "newNote";
    private static final String LIST_NOTES = "listNotes";
    private static final String LIST_FILES = "listFiles";

    public HomeController(NoteService _noteService, UserService _userService, FileService _fileService) {
        this._noteService = _noteService;
        this._userService = _userService;
        this._fileService = _fileService;
    }

    @GetMapping("/home")
    public String getHomePage(
            @ModelAttribute(NEW_NOTE) Note newNote,
            Authentication auth,
            Model model) {
        model.addAttribute(LIST_NOTES, _noteService.getNotes(auth.getName()));
        model.addAttribute(LIST_FILES, _fileService.getFiles(auth.getName()));
        model.addAttribute("newNote", new Note());
        return "home";
    }

    @GetMapping("/result")
    public String result(@RequestParam(name = "isSuccess") Boolean isSuccess, Model model) {
        model.addAttribute("isSuccess", isSuccess);
        return "result";
    }

    @PostMapping(value = "/notes", params = "createUpdateNote")
    public String createUpdateNote(@ModelAttribute(NEW_NOTE) Note newNote, Authentication auth, Model model) {

        System.out.println(newNote.getNoteTitle());
        System.out.println(newNote.getNoteDescription());

        Boolean result = _noteService.createUpdateNote(new Note(newNote.getNoteId(),
                newNote.getNoteTitle(),
                newNote.getNoteDescription(),
                _userService.getUser(auth.getName()).getUserId()));

        model.addAttribute(LIST_NOTES, _noteService.getNotes(auth.getName()));
        return "redirect:/result?isSuccess=" + result;
    }

    @GetMapping("/deleteNote/{noteId}")
    public String deleteNote(@PathVariable Integer noteId, Authentication auth, Model model) {
        Boolean result = _noteService.deleteNote(noteId);

        model.addAttribute(LIST_NOTES, _noteService.getNotes(auth.getName()));
        return "redirect:/result?isSuccess=" + result;
    }

    @PostMapping(value = "/files/upload")
    public String uploadFile(@RequestParam("fileUpload") MultipartFile fileIn, Authentication auth, Model model) throws IOException {
        if(fileIn.isEmpty()) {
            return "redirect:/result?isSuccess=" + false;
        }

        _fileService.uploadFile(new File(
                null,
                fileIn.getOriginalFilename(),
                fileIn.getContentType(),
                String.valueOf(fileIn.getSize()),
                _userService.getUser(auth.getName()).getUserId(),
                fileIn.getBytes()));

        model.addAttribute(LIST_FILES, _fileService.getFiles(auth.getName()));
        model.addAttribute("newNote", new Note());
        return "home";
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