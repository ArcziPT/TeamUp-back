package com.arczipt.teamup.controller;

import org.dom4j.rule.Mode;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * Redirect on error to angular app.
 */
@RestController
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

    public String getErrorPath() {
        return "/";
    }

    @RequestMapping("/error")
    public ModelAndView redirectToAngularApp(){
        return new ModelAndView("redirect:/");
    }
}
