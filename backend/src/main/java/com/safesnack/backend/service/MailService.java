package com.safesnack.backend.service;

import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    private final JavaMailSender emailSender;

    /// Constructor for MailService.
    ///
    /// @param emailSender the JavaMailSender to be used for sending emails
    public MailService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    /// Constructs an email for resetting the password.
    ///
    /// @param contextPath the url of the application
    /// @param token       the reset token to be included in the email
    /// @param userEmail   the email address of the user
    /// @return a SimpleMailMessage object containing the reset password email
    public SimpleMailMessage constructResetTokenEmail(String contextPath, String token, String userEmail) {
        String url = contextPath + "/user/changePassword?token=" + token;
        // TODO: Implement localization
        String message = "Use the link below to reset your password.";
        return constructEmail(userEmail, "Reset Password", message + " \r\n" + url);
    }

    /// Constructs a generic email.
    ///
    /// @param emailAddress the recipient's email address
    /// @param subject      the subject of the email
    /// @param message      the body of the email
    /// @return a SimpleMailMessage object containing the email
    public SimpleMailMessage constructEmail(String emailAddress, String subject, String message) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject(subject);
        email.setText(message);
        email.setTo(emailAddress);
        email.setFrom("noreply@safesnack.de");
        return email;
    }

    /// Sends an email.
    ///
    /// @param email the SimpleMailMessage object to be sent
    public void sendEmail(SimpleMailMessage email) throws MailException {
        emailSender.send(email);
    }
}