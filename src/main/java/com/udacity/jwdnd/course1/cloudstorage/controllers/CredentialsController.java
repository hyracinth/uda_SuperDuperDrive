package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.config.SDDConstants;
import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.ActiveTabService;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CredentialsController {
    private final CredentialService credentialService;
    private final UserService userService;
    private final ActiveTabService activeTabService;

    public CredentialsController(CredentialService credentialService,
                                 UserService userService,
                                 ActiveTabService activeTabService) {
        this.credentialService = credentialService;
        this.userService = userService;
        this.activeTabService = activeTabService;
    }

    @PostMapping(value = "/credentials/createUpdate")
    public String createUpdateCredential(@ModelAttribute(SDDConstants.NEW_CREDENTIAL) Credential newCred,
                                         Authentication auth,
                                         Model model){
        activeTabService.setActiveTab(SDDConstants.TAB_CREDENTIALS);
        Boolean result = credentialService.createUpdateCredential(new Credential(
                newCred.getCredentialId(),
                newCred.getUrl(),
                newCred.getUsername(),
                newCred.getKey(),
                newCred.getPassword(),
                userService.getUser(auth.getName()).getUserId()));

        model.addAttribute(SDDConstants.LIST_CREDENTIALS, credentialService.getCredentials(auth.getName()));
        return "redirect:/result?success=" + result;
    }

    @GetMapping("/credentials/delete/{credId}")
    public String deleteCredential(@PathVariable Integer credId,
                                   Authentication auth,
                                   Model model) {
        activeTabService.setActiveTab(SDDConstants.TAB_CREDENTIALS);
        Boolean result = credentialService.deleteCredential(credId);
        model.addAttribute(SDDConstants.LIST_NOTES, credentialService.getCredentials(auth.getName()));
        return "redirect:/result?success=" + result;
    }
}
