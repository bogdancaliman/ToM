package com.project.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.project.project.exceptions.LogInException;
import com.project.project.exceptions.PasswordMatchException;
import com.project.project.exceptions.SystemException;
import com.project.project.exceptions.UserNotFoundException;
import com.project.project.models.Account;
import com.project.project.services.AccountService;
import com.project.project.services.SessionService;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AuthController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private SessionService sessionService;

    @PostMapping("/auth")
    public String authenticate(@RequestParam("username") String username, @RequestParam("password") String password, HttpServletRequest request, RedirectAttributes ra) {
        ra.addFlashAttribute("user", username);
        if (!username.equals("") && !password.equals("")) {
            ra.addFlashAttribute("error", "");
            try {
                Account acc = accountService.checkCredentials(username, password);
                sessionService.addAccountSession(acc, request);
                return "redirect:/";
            } catch (UserNotFoundException e) {
                ra.addFlashAttribute("error", "User not found!");
            } catch (PasswordMatchException e) {
                ra.addFlashAttribute("error", "Wrong password!");
            } catch (SystemException | LogInException e) {
                ra.addFlashAttribute("error", "We're having some system issues! Try again later!");
            }
        } else {
            ra.addFlashAttribute("error", "Please complete all fields!");
        }
        return "redirect:/auth";
    }

    @PostMapping("/resetPassword")
    public String resetPassword(@RequestParam("username") String username, HttpServletRequest request, RedirectAttributes ra) {
        ra.addFlashAttribute("sentEmail", "Check your email address!");
        try {
            accountService.sendResetEmail(username, request);
        } catch (UserNotFoundException e) {
            ra.addFlashAttribute("sentEmail", "User Not Found!");
        }
        return "redirect:/auth";
    }
}