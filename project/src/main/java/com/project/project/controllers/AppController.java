package com.project.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import javax.servlet.http.HttpServletRequest;
import com.project.project.services.ClearDataService;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AppController {

    private final ClearDataService clearDataService;

    @Autowired
    public AppController(ClearDataService clearDataService) {
        this.clearDataService = clearDataService;
    }

    @GetMapping("/")
    public ModelAndView index(HttpServletRequest request, RedirectAttributes ra) {
        // Update database
        clearDataService.clearData();
        if (request.getSession().getAttribute("active") == null)
            return new ModelAndView("log-in");
        return new ModelAndView("index");
    }

} 
