package com.project.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import com.project.project.dtos.TOMUserDetails;
import com.project.project.exceptions.*;
import com.project.project.services.PasswordService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class PasswordController {

    private final PasswordService passwordService;


    @Autowired
    public PasswordController(PasswordService passwordService) {
        this.passwordService = passwordService;
    }

    @PostMapping("/resetPassword")
    public RedirectView resetPassword(@RequestParam("username") String username, HttpServletRequest request, RedirectAttributes ra) {
        RedirectView rv = new RedirectView("/tom/log-in");
        ra.addFlashAttribute("upperNotification", "Check your email address!");
        try {
            String hostLink = request.getScheme() + "://" + request.getServerName()+":8181/tom";
            passwordService.addResetRequest(username, hostLink);
        } catch (UserNotFoundException e) {
            ra.addFlashAttribute("upperNotification", "User not found!");
        } catch (SystemException e) {
            ra.addFlashAttribute("upperNotification", "There was a problem in sending the reset email!");
        }
        return rv;
    }


    @GetMapping("/validate-password-reset-request")
    public RedirectView validatePasswordResetRequest(@RequestParam("token") String token, RedirectAttributes ra) {
        RedirectView rv;
        try {
            rv = new RedirectView("/tom/set-new-password");
            int id = passwordService.identifyAccountUsingToken(token);
            ra.addFlashAttribute("userId", id);
        } catch (InvalidTokenException e) {
            rv = new RedirectView("/tom/log-in");
            ra.addFlashAttribute("upperNotification", "The token expired or is invalid!");
        }
        return rv;
    }

    @GetMapping("/set-new-password")
    public ModelAndView setNewPassword(@ModelAttribute("userId") int id) {
        ModelAndView mv = new ModelAndView("reset-password");
        mv.addObject("userId", id);
        return mv;
    }

    @PostMapping("/set-new-password")
    public ModelAndView setNewPassword(@RequestParam Map<String, String> data, @ModelAttribute("userId") int id, RedirectAttributes ra, Authentication authentication) {
        ModelAndView mv = new ModelAndView("reset-password");
        mv.addObject("userId", id);
        if(!((TOMUserDetails)authentication.getPrincipal()).isActivated()) {
            List<GrantedAuthority> updatedAuthorities = new ArrayList<>(authentication.getAuthorities());
            updatedAuthorities.add(new SimpleGrantedAuthority("ACTIVATED"));
            Authentication newAuth = new UsernamePasswordAuthenticationToken(authentication.getPrincipal(), authentication.getCredentials(), updatedAuthorities);
            passwordService.activateMyAccount(authentication.getName());
            SecurityContextHolder.getContext().setAuthentication(newAuth);
        }
        try {
            passwordService.validatePassword(data.get("password"), data.get("passwordVerification"));
            passwordService.updateAccountPasswordById(Integer.parseInt(data.get("userId")), data.get("password"));
            mv = new ModelAndView("redirect:/log-in");
            ra.addFlashAttribute("upperNotification", "Password updated!");
            return mv;
        } catch (WeakPasswordException e) {
            mv.addObject("error", "The password is too weak!");
        } catch (PasswordVerificationException e) {
            mv.addObject("error", "The password does not match!");
        } catch (SignUpException e) {
            mv.addObject("error", "We're having some system issues! Try again later!");
        }
        return mv;
    }
}