package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.config.SDDConstants;
import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {
    private final NoteService noteService;
    private final FileService fileService;
    private final CredentialService credentialService;
    private final EncryptionService encryptionService;
    private final ActiveTabService activeTabService;

    public HomeController(NoteService noteService,
                          FileService fileService,
                          CredentialService credentialService,
                          EncryptionService encryptionService,
                          ActiveTabService activeTabService) {
        this.noteService = noteService;
        this.fileService = fileService;
        this.credentialService = credentialService;
        this.encryptionService = encryptionService;
        this.activeTabService = activeTabService;
    }

    @GetMapping("/home")
    public String getHomePage(Authentication auth, Model model) {
        model.addAttribute(SDDConstants.LIST_NOTES, noteService.getNotes(auth.getName()));
        model.addAttribute(SDDConstants.LIST_FILES, fileService.getFiles(auth.getName()));
        model.addAttribute(SDDConstants.LIST_CREDENTIALS, credentialService.getCredentials(auth.getName()));
        model.addAttribute(SDDConstants.ENCRYPTION_SERVICE, encryptionService);
        model.addAttribute(SDDConstants.NEW_NOTE, new Note());
        model.addAttribute(SDDConstants.NEW_CREDENTIAL, new Credential());
        model.addAttribute(SDDConstants.ACTIVE_TAB, activeTabService.getActiveTab());
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
}
