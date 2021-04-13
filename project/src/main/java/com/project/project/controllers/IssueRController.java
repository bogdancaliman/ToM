package com.project.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.project.project.exceptions.*;
import com.project.project.models.Account;
import com.project.project.services.AuthService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


@Controller

public class IssueRController {

    //@Autowired

    @GetMapping("/reportIssue")
    public ModelAndView reportIssue(HttpServletRequest request, RedirectAttributes ra) {
        ModelAndView mv = new ModelAndView("reportIssue");
        Account acc = (Account) request.getSession().getAttribute("active");
        if(acc == null) {
            mv = new ModelAndView("redirect:/auth");
            ra.addFlashAttribute("upperNotification", "Please log in again (Session expired)!");
            return mv;
        }
        mv.addObject("myID",acc.getId());
        return mv;
    }

    @PostMapping("/reportIssue")

    public String reportIssue(HttpServletRequest request) {
        Account acc = (Account) request.getSession().getAttribute("active");
        if (acc != null) {
            //acc.
            return "reportIssue";
        } else
            return "redirect:/auth";
    }

}