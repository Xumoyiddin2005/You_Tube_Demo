package com.company.service;

import com.company.entity.EmailHistoryEntity;
import com.company.entity.ProfileEntity;
import com.company.repository.EmailHistoryRepository;
import com.company.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    @Lazy
    private ProfileService profileService;

    @Autowired
    private  EmailHistoryRepository emailHistoryRepository;
    @Autowired
    private JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String fromAccount;

    @Value("${spring.server.url}")
    private String serverUrl;



    public void sendRegistrationEmail(String toAccount, String token) {
        String url = String.format("<a href='%s/api/v1/email/verification/%s'>Verification Link</a>",
                serverUrl, token);

        StringBuilder builder = new StringBuilder();
        builder.append("<h1 style='align-text:center'>Assalomu aleykum\nSizning youtube dan " +
                "registratsiyadan o'tish uchun linkni ustiga bosing</h1>");
        builder.append("<p>");
        builder.append(url);
        builder.append("</p>");

        sendEmail(toAccount, "Registration", builder.toString());

        EmailHistoryEntity emailHistoryEntity = new EmailHistoryEntity();

        emailHistoryEntity.setEmail(toAccount);

        emailHistoryRepository.save(emailHistoryEntity);
    }

    private void sendEmail(String toAccount, String subject, String text) {
        try {
            MimeMessage msg = javaMailSender.createMimeMessage();
            msg.setFrom(fromAccount);
            MimeMessageHelper helper = new MimeMessageHelper(msg, true);
            helper.setTo(toAccount);
            helper.setSubject(subject);
            helper.setText(text, true);
            javaMailSender.send(msg);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public void resendRegistrationEmail(String email) {
        ProfileEntity profile = profileService.get(email);

        String token = JwtUtil.encode(profile.getId());

        sendRegistrationEmail(email,token);
    }
}
