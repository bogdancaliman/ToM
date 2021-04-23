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
import com.project.project.services.DepartmentService;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class HRController {

    private final HRService hrService;
    private final DepartmentService departmentService;

    @Autowired
    public HRController(HRService hrService, DepartmentService departmentService) {
        this.hrService = hrService;
        this.departmentService = departmentService;
    }

    @GetMapping("/sign-up")
    public ModelAndView signUp() {
        ModelAndView mv = new ModelAndView("sign-up");
        mv.addObject("departments", departmentService.loadDepartments());
            mv.addObject("error", "");
            return mv;
        }
    
    @PostMapping("/sign-up")
    public ModelAndView signUp(@RequestParam Map<String, String> params, RedirectAttributes ra) {
        ModelAndView mv = new ModelAndView("sign-up");
        try {
            hrService.checkIfEmailIsAvailable(params);
            mv = new ModelAndView("redirect:/create-account");
            ra.addFlashAttribute("employeeId", hrService.addEmployee(params));
            ra.addFlashAttribute("teamLeaderId", params.get("teamLeaderId"));
        } catch (UsedEmailException e) {
            mv.addObject("departments", departmentService.loadDepartments());
            mv.addObject("error", "The email is already used!");
        } catch (SignUpException e) {
            mv.addObject("departments", departmentService.loadDepartments());
            mv.addObject("error", "An error has occurred!");
        }
        return mv;
         
        }
    @GetMapping("/update-sign-up-form")
    @ResponseBody
    public List<Pair<Integer, String>> getEmployeesOfDepartment(@RequestParam("departmentId") int departmentId) {
        return departmentService.loadEmployeesOfDepartmentById(departmentId).stream().map(s -> new Pair<>(s.getId(), s.getName())).collect(Collectors.toList());
    }

}