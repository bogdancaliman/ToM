package com.project.project.emails;

import org.junit.jupiter.api.Test;
import com.project.project.models.Account;

import static org.assertj.core.api.Assertions.assertThat;


public class CredentialsEmailTest {

    @Test
    void checkConstructorAndGetters() {

        Account account = new Account();
        CredentialsEmail credentialsEmail = new CredentialsEmail(account, "test", "test");
        assertThat(credentialsEmail.getTo().equals(account)).isTrue();
        assertThat(credentialsEmail.getSubject().equals("Account data")).isTrue();
        assertThat(credentialsEmail.getContext().getVariable("username").equals("test")).isTrue();
    }

}