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
    private final NoteService noteService;
    private final UserService userService;
    private final FileService fileService;
    private final CredentialService credentialService;
    private final EncryptionService encryptionService;

    private static final String NEW_NOTE = "newNote";
    private static final String NEW_CREDENTIAL = "newCred";
    private static final String LIST_NOTES = "listNotes";
    private static final String LIST_FILES = "listFiles";
    private static final String LIST_CREDENTIALS = "listCredentials";

    public HomeController(NoteService noteService,
                          UserService userService,
                          FileService fileService,
                          CredentialService credentialService,
                          EncryptionService encryptionService) {
        this.noteService = noteService;
        this.userService = userService;
        this.fileService = fileService;
        this.credentialService = credentialService;
        this.encryptionService = encryptionService;
    }

    @GetMapping("/home")
    public String getHomePage(
            @ModelAttribute(NEW_NOTE) Note newNote,
            Authentication auth,
            Model model) {
        model.addAttribute(LIST_NOTES, noteService.getNotes(auth.getName()));
        model.addAttribute(LIST_FILES, fileService.getFiles(auth.getName()));
        model.addAttribute(LIST_CREDENTIALS, credentialService.getCredentials(auth.getName()));
        model.addAttribute("_encryptionService", encryptionService);
        model.addAttribute(NEW_NOTE, new Note());
        return "home";
    }

    @GetMapping("/result")
    public String result(@RequestParam(required = false, name = "success") Boolean success,
                         @RequestParam(required = false, name = "errorType") Integer errorType,
                         Model model) {
        model.addAttribute("success", success);
        model.addAttribute("errorType", errorType);
        return "result";
    }

    @PostMapping(value = "/notes", params = "createUpdateNote")
    public String createUpdateNote(@ModelAttribute(NEW_NOTE) Note newNote,
                                   Authentication auth,
                                   Model model) {
        Boolean result = noteService.createUpdateNote(new Note(newNote.getNoteId(),
                newNote.getNoteTitle(),
                newNote.getNoteDescription(),
                userService.getUser(auth.getName()).getUserId()));

        model.addAttribute(LIST_NOTES, noteService.getNotes(auth.getName()));
        return "redirect:/result?success=" + result;
    }

    @GetMapping("/notes/delete/{noteId}")
    public String deleteNote(@PathVariable Integer noteId,
                             Authentication auth,
                             Model model) {
        Boolean result = noteService.deleteNote(noteId);
        model.addAttribute(LIST_NOTES, noteService.getNotes(auth.getName()));
        return "redirect:/result?success=" + result;
    }

    @PostMapping(value = "/files/upload")
    public String uploadFile(@RequestParam("fileUpload") MultipartFile fileIn,
                             Authentication auth,
                             Model model) throws IOException {
        if(fileIn.isEmpty()) {
            return "redirect:/result?success=" + false + "&errorType=1";
        }

        if(fileService.doesFileExist(fileIn.getOriginalFilename(), auth.getName())) {
            return "redirect:/result?success=" + false + "&errorType=2";
        }
        Boolean result = fileService.uploadFile(new File(
            null,
                fileIn.getOriginalFilename(),
                fileIn.getContentType(),
                String.valueOf(fileIn.getSize()),
                userService.getUser(auth.getName()).getUserId(),
                fileIn.getBytes()));

        model.addAttribute(LIST_FILES, fileService.getFiles(auth.getName()));
        return "redirect:/result?success=" + result;
    }

    @GetMapping("/files/delete/{fileId}")
    public String deleteFile(@PathVariable Integer fileId,
                             Authentication auth,
                             Model model) {
        Boolean result = fileService.deleteFile(fileId);
        model.addAttribute(LIST_FILES, fileService.getFiles(auth.getName()));
        return "redirect:/result?success=" + result;
    }

    @GetMapping("/files/download/{fileId}")
    public ResponseEntity download(@PathVariable Integer fileId,
                                   Authentication auth,
                                   Model model){
        File selectedFile = fileService.getFile(fileId);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + selectedFile.getFilename() + "\"")
                .body(selectedFile);
    }

    @PostMapping(value = "/credentials/createUpdateCredential")
    public String createUpdateCredential(@ModelAttribute(NEW_CREDENTIAL)Credential newCred,
                                         Authentication auth,
                                         Model model){
        Boolean result = credentialService.createUpdateCredential(new Credential(
                        newCred.getCredentialId(),
                        newCred.getUrl(),
                        newCred.getUsername(),
                        newCred.getKey(),
                        newCred.getPassword(),
                        userService.getUser(auth.getName()).getUserId()));

        model.addAttribute(LIST_CREDENTIALS, credentialService.getCredentials(auth.getName()));
        return "redirect:/result?success=" + result;
    }

    @GetMapping("/credentials/delete/{credId}")
    public String deleteCredential(@PathVariable Integer credId,
                             Authentication auth,
                             Model model) {
        Boolean result = credentialService.deleteCredential(credId);
        model.addAttribute(LIST_NOTES, credentialService.getCredentials(auth.getName()));
        return "redirect:/result?success=" + result;
    }
}


// TODO implement view in another window?
// TODO implement remember active tabs

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