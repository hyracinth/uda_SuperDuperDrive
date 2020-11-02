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

/**
 * This controller handles logic regarding credentials
 */
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

    /**
     * This method handles the logic for creating and updating credentials
     * It takes a note object from HTML side and sends it to the service to be handled
     * The method also sets the active tab and then sends an updated list of credentials
     * @param newCred credential from user to be added / updated
     * @param auth Authentication object to get user details
     * @param model Model object for data binding
     * @return redirects to results page to show user status
     */
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

    /**
     This method takes an id from the HTML and pass it to the service to be deleted
     * @param credId id of note to be deleted
     * @param auth Authentication object to get user details
     * @param model Model object for data binding
     * @return redirects to results page to show user status
     */
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
