package com.project.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.project.project.services.ClearDataService;
import com.project.project.services.EmployeeService;
import com.project.project.services.PasswordService;


@Controller
public class AuthController {

    private final ClearDataService clearDataService;
    private final PasswordService passwordService;
    private final EmployeeService employeeService;

    @Autowired
    public AuthController(ClearDataService clearDataService, PasswordService passwordService, EmployeeService employeeService) {
        this.clearDataService = clearDataService;
        this.passwordService = passwordService;
        this.employeeService = employeeService;
    }

    @GetMapping("/")
    public ModelAndView index(Model model, Authentication authentication) {
        clearDataService.clearData();
        ModelAndView mv = new ModelAndView("index");
        if (model.getAttribute("upperNotification") == null)
            mv.addObject("upperNotification", "");
        mv.addObject("iAmTeamLeader", employeeService.isTeamLeader(authentication.getName()));
        mv.addObject("employee", employeeService.findEmployeeByUsername(authentication.getName()));
        return mv;
    }

    @GetMapping("/log-in")
    public ModelAndView logIn(Model model,  Authentication authentication) {
        if (isUserAuthenticated(authentication))
            return new ModelAndView("redirect:/");
        ModelAndView mv = new ModelAndView("log-in");
        if (model.getAttribute("upperNotification") == null)
            mv.addObject("upperNotification", "");
        return mv;
    }

    @GetMapping("/get-salt")
    @ResponseBody
    public String getSaltOfUser(@RequestParam("username") String username) {
        return passwordService.getSaltOfUser(username);
    }

    private boolean isUserAuthenticated(Authentication authentication) {
        return authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken);
    }

}