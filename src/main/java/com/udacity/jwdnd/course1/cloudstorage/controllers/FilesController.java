package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.config.SDDConstants;
import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.services.ActiveTabService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class FilesController {
    private final UserService userService;
    private final FileService fileService;
    private final ActiveTabService activeTabService;

    public FilesController(UserService userService,
                           FileService fileService,
                           ActiveTabService activeTabService) {
        this.userService = userService;
        this.fileService = fileService;
        this.activeTabService = activeTabService;
    }

    @PostMapping(value = "/files/upload")
    public String uploadFile(@RequestParam("fileUpload") MultipartFile fileIn,
                             Authentication auth,
                             Model model) throws IOException {
        activeTabService.setActiveTab(SDDConstants.TAB_FILES);
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

        model.addAttribute(SDDConstants.LIST_FILES, fileService.getFiles(auth.getName()));
        return "redirect:/result?success=" + result;
    }

    @GetMapping("/files/delete/{fileId}")
    public String deleteFile(@PathVariable Integer fileId,
                             Authentication auth,
                             Model model) {
        activeTabService.setActiveTab(SDDConstants.TAB_FILES);
        Boolean result = fileService.deleteFile(fileId);
        model.addAttribute(SDDConstants.LIST_FILES, fileService.getFiles(auth.getName()));
        return "redirect:/result?success=" + result;
    }

    @GetMapping("/files/download/{fileId}")
    public ResponseEntity download(@PathVariable Integer fileId,
                                   Authentication auth,
                                   Model model){
        activeTabService.setActiveTab(SDDConstants.TAB_FILES);
        File selectedFile = fileService.getFile(fileId);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + selectedFile.getFilename() + "\"")
                .body(selectedFile);
    }
}
