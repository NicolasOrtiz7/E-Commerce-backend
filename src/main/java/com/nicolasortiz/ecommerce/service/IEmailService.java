package com.nicolasortiz.ecommerce.service;

import jakarta.mail.MessagingException;

import java.io.File;

public interface IEmailService {

    void sendEmail(String receiver, String subject, String body);

    void sendEmailWithPdf(String receiver, String subject, String body, File pdf) throws MessagingException;

}
