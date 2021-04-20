package com.project.project.controllers;

import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.project.project.exceptions.UsedEmailException;
import com.project.project.exceptions.SignUpException;
import com.project.project.services.HRService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class HRController {

    private final HRService hrService;

    @Autowired
    public HRController(HRService hrService) {
        this.hrService = hrService;
    }

    @GetMapping("/signUp")
    public ModelAndView signUp(HttpServletRequest request, RedirectAttributes ra) {
            ModelAndView mv = new ModelAndView("signUp");
            if(request.getSession().getAttribute("active")==null) {
                mv = new ModelAndView("redirect:/auth");
                ra.addFlashAttribute("upperNotification", "Please log in again (Session expired)!");
                return mv;
            }

            mv.addObject("departments", hrService.loadDepartments());
            mv.addObject("error", "");
            return mv;
        }
    
    @GetMapping("/updateSignUpForm")
    @ResponseBody
    public List<Pair<Integer, String>> getEventCount(@RequestParam("departmentId") int departmentId) {
        return hrService.loadEmployeesOfDepartmentById(departmentId).stream().map(s -> new Pair<>(s.getId(), s.getName())).collect(Collectors.toList());
    }

    @PostMapping("/signUp")
    public ModelAndView resolveSignUP(@RequestParam Map<String, String> params, HttpServletRequest request, RedirectAttributes ra) {
        ModelAndView mv = new ModelAndView("signUp");
        if(request.getSession().getAttribute("active")==null) {
            mv = new ModelAndView("redirect:/auth");
            ra.addFlashAttribute("upperNotification", "Please log in again (Session expired)!");
            return mv;
        }
        try {
            hrService.checkIfEmailIsAvailable(params);
            int empl_id = hrService.addEmployee(params);
            mv = new ModelAndView("redirect:/createAccount");
            ra.addFlashAttribute("emplId", empl_id);
            ra.addFlashAttribute("tlId", params.get("tlId"));
        } catch (UsedEmailException e) {
            mv.addObject("departments", hrService.loadDepartments());
            mv.addObject("error", "The email is already used!");
        } catch (SignUpException e) {
            mv.addObject("departments", hrService.loadDepartments());
            mv.addObject("error", "An error has occured!");
        }
        return mv;

    }

}