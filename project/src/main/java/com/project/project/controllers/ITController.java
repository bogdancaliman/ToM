package com.project.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.project.project.models.Department;
import com.project.project.models.Employee;
import com.project.project.services.ITService;
import javax.transaction.SystemException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;


@Controller
public class ITController {


    @Autowired
    private ITService itService;

    @GetMapping("/createAccount")
    public String createAccount(@ModelAttribute("emplId") int id_empl, @ModelAttribute("tlId") int id_tl, RedirectAttributes ra) {
        try {
            itService.generateAccount(id_empl, id_tl);
        }catch (SystemException e){
            itService.informItAboutError(id_empl);
        }
        ra.addFlashAttribute("upperNotification", "The employee record was added!");
        return "redirect:/";

    }
} 