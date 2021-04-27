package com.project.project.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import com.project.project.dtos.TOMUserDetails;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {


    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException {

        TOMUserDetails userDetails = (TOMUserDetails) authentication.getPrincipal();

        if (userDetails.isActivated()) {
            httpServletResponse.sendRedirect("/tom/");
        } else {
            httpServletResponse.sendRedirect("/tom/set-new-password?userId="+userDetails.getId());
        }
    }
} 
