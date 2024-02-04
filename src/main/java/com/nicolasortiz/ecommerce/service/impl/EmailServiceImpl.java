package com.nicolasortiz.ecommerce.service.impl;

import com.nicolasortiz.ecommerce.service.IEmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements IEmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String myEmail;

    @Override
    @Async
    public void sendEmail(String receiver, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(myEmail);
        message.setTo(receiver);

        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
        System.out.println("Email enviado correctamente");
    }

    @Override
//    @Async
    public void sendEmailWithPdf(String receiver, String subject, String body, File pdf) throws MessagingException {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom(myEmail);
        helper.setTo(receiver);

        helper.setSubject(subject);
        helper.setText(body);

        helper.addAttachment("Factura de compra", pdf);

        mailSender.send(message);
        System.out.println("Email enviado correctamente");
    }

}
