package com.project.project.dtos;

import lombok.Getter;
import com.project.project.models.Account;

@Getter
public class ResetPasswordEmail extends EmailData{

    public ResetPasswordEmail(Account to, String link) {
        super(to, "Reset Password");
        context.setVariable("username", to.getUsername());
        context.setVariable("link", link);
        context.setVariable("templateName", "reset-password-email");
    }

}