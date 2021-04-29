package com.project.project.emails;

import lombok.Getter;
import com.project.project.emails.EmailData;
import com.project.project.models.Account;

@Getter
public class CredentialsEmail extends EmailData {

    public CredentialsEmail(Account to, String subject, String username, String password) {
        super(to, subject);

        context.setVariable("username", username);
        context.setVariable("password", password);
        context.setVariable("templateName", "credentials-email");
    }
}
