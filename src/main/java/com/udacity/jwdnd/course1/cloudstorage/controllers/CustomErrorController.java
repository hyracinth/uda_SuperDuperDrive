package com.udacity.jwdnd.course1.cloudstorage.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

/**
 * This controller handles custom logic for handling errors
 */
@Controller
@RequestMapping("/error")
public class CustomErrorController implements ErrorController {
    /**
     * This method routes errors to the appropriate error page, defaulting to the plain error page if none found
     * @param request HttpServletRequest to process
     * @return error page to be displayed
     */
    @GetMapping()
    public String handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());
            if(statusCode == HttpStatus.NOT_FOUND.value()) {
                return "error-404";
            }
            else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return "error-500";
            }
        }
        return "error";
    }

    @Override
    public String getErrorPath() {
        return null;
    }
}
