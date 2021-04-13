package com.project.project.controllers;

import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.project.project.exceptions.EmailUsedException;
import com.project.project.exceptions.SignUpException;
import com.project.project.models.Employee;
import com.project.project.services.HRService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class HRController {

    @Autowired
    HRService hrService;

    @GetMapping("/signUp")
    public ModelAndView signUp(HttpServletRequest request) {
        if (request.getSession().getAttribute("active") == null)
            return new ModelAndView("redirect:/auth");


            ModelAndView mv = new ModelAndView("signUp");
            mv.addObject("departments", hrService.loadDepartments());
            mv.addObject("error", "");
            return mv;
        }
    
    @GetMapping("/updateSignUpForm")
    @ResponseBody
    public List<Pair<Integer, String>> getEventCount(@RequestParam("departmentId") int departmentId) {
        return hrService.loadEmployeesOfDepartmentId(departmentId).stream().map(s -> new Pair<>(s.getId(), s.getName())).collect(Collectors.toList());
    }

    @PostMapping("/signUp")
    public ModelAndView resolveSignUP(@RequestParam Map<String, String> params, RedirectAttributes ra) {
        ModelAndView mv = new ModelAndView("signUp");
        try {
            hrService.validateFormData(params);
            int empl_id = hrService.addEmployeeRecord(params);
            mv = new ModelAndView("redirect:/createAccount");
            ra.addFlashAttribute("emplId", empl_id);
            ra.addFlashAttribute("tlId", params.get("tlId"));
        } catch (EmailUsedException e) {
            mv.addObject("departments", hrService.loadDepartments());
            mv.addObject("error", "The email is already used!");
        } catch (SignUpException e) {
            mv.addObject("departments", hrService.loadDepartments());
            mv.addObject("error", "An error has occured!");
        }
        return mv;

    }

}