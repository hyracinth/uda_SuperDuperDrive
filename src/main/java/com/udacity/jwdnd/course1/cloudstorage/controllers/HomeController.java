package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class HomeController {
    private final NoteService _noteService;
    private final UserService _userService;
    private final FileService _fileService;
    private final CredentialService _credentialService;
    private final EncryptionService _encryptService;

    private static final String NEW_NOTE = "newNote";
    private static final String NEW_CRED = "newCred";
    private static final String LIST_NOTES = "listNotes";
    private static final String LIST_FILES = "listFiles";
    private static final String LIST_CREDENTIALS = "listCredentials";

    public HomeController(NoteService _noteService,
                          UserService _userService,
                          FileService _fileService,
                          CredentialService _credentialService,
                          EncryptionService _encryptService) {
        this._noteService = _noteService;
        this._userService = _userService;
        this._fileService = _fileService;
        this._credentialService = _credentialService;
        this._encryptService = _encryptService;
    }

    @GetMapping("/home")
    public String getHomePage(
            @ModelAttribute(NEW_NOTE) Note newNote,
            Authentication auth,
            Model model) {
        model.addAttribute(LIST_NOTES, _noteService.getNotes(auth.getName()));
        model.addAttribute(LIST_FILES, _fileService.getFiles(auth.getName()));
        model.addAttribute(LIST_CREDENTIALS, _credentialService.getCredentials(auth.getName()));
        model.addAttribute("_encryptionService", _encryptService);
        return "home";
    }

    @GetMapping("/result")
    public String result(@RequestParam(required = false, name = "isSuccess") Boolean isSuccess,
                         @RequestParam(required = false, name = "errorType") Integer errorType,
                         Model model) {
        model.addAttribute("isSuccess", isSuccess);
        model.addAttribute("errorType", errorType);
        return "result";
    }

    @PostMapping(value = "/notes", params = "createUpdateNote")
    public String createUpdateNote(@ModelAttribute(NEW_NOTE) Note newNote,
                                   Authentication auth,
                                   Model model) {
        Boolean result = _noteService.createUpdateNote(new Note(newNote.getNoteId(),
                newNote.getNoteTitle(),
                newNote.getNoteDescription(),
                _userService.getUser(auth.getName()).getUserId()));

        model.addAttribute(LIST_NOTES, _noteService.getNotes(auth.getName()));
        return "redirect:/result?isSuccess=" + result;
    }

    @GetMapping("/notes/delete/{noteId}")
    public String deleteNote(@ModelAttribute(NEW_NOTE) Note newNote,
                             @PathVariable Integer noteId,
                             Authentication auth,
                             Model model) {
        Boolean result = _noteService.deleteNote(noteId);
        model.addAttribute(LIST_NOTES, _noteService.getNotes(auth.getName()));
        return "redirect:/result?isSuccess=" + result;
    }

    @PostMapping(value = "/files/upload")
    public String uploadFile(@ModelAttribute(NEW_NOTE) Note newNote,
                             @RequestParam("fileUpload") MultipartFile fileIn,
                             Authentication auth,
                             Model model) throws IOException {
        if(fileIn.isEmpty()) {
            return "redirect:/result?isSuccess=" + false + "&errorType=1";
        }

        if(_fileService.doesFileExist(fileIn.getOriginalFilename(), auth.getName())) {
            return "redirect:/result?isSuccess=" + false + "&errorType=2";
        }
        Boolean result = _fileService.uploadFile(new File(
            null,
                fileIn.getOriginalFilename(),
                fileIn.getContentType(),
                String.valueOf(fileIn.getSize()),
                _userService.getUser(auth.getName()).getUserId(),
                fileIn.getBytes()));

        model.addAttribute(LIST_FILES, _fileService.getFiles(auth.getName()));
        return "redirect:/result?isSuccess=" + result;
    }

    @GetMapping("/files/delete/{fileId}")
    public String deleteFile(@PathVariable Integer fileId,
                             Authentication auth,
                             Model model) {
        Boolean result = _fileService.deleteFile(fileId);
        model.addAttribute(LIST_FILES, _fileService.getFiles(auth.getName()));
        return "redirect:/result?isSuccess=" + result;
    }

    @GetMapping("/files/download/{fileId}")
    public ResponseEntity download(@PathVariable Integer fileId,
                                   Authentication auth,
                                   Model model){
        File selectedFile = _fileService.getFile(fileId);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + selectedFile.getFilename() + "\"")
                .body(selectedFile);
    }

    // TODO implement view in another window?

    @PostMapping(value = "/credentials/createUpdateCredential")
    public String createUpdateCredential(@ModelAttribute(NEW_NOTE) Note newNote,
                                         @ModelAttribute(NEW_CRED)Credential newCred,
                                         Authentication auth,
                                         Model model){
        Boolean result = _credentialService.createUpdateCredential(new Credential(
                        newCred.getCredentialId(),
                        newCred.getUrl(),
                        newCred.getUsername(),
                        newCred.getKey(),
                        newCred.getPassword(),
                        _userService.getUser(auth.getName()).getUserId()));

        model.addAttribute(LIST_CREDENTIALS, _credentialService.getCredentials(auth.getName()));
        return "redirect:/result?isSuccess=" + result;
    }

    @GetMapping("/credentials/delete/{credId}")
    public String deleteCredential(@PathVariable Integer credId,
                             Authentication auth,
                             Model model) {
        Boolean result = _credentialService.deleteCredential(credId);
        model.addAttribute(LIST_NOTES, _credentialService.getCredentials(auth.getName()));
        return "redirect:/result?isSuccess=" + result;
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