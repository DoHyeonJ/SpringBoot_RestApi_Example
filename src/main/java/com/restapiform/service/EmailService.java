package com.restapiform.service;

import com.restapiform.model.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender emailSender;
    private final SpringTemplateEngine templateEngine;

    public void sendTemplateMessage(Email email, HashMap<String, String> emailValues) {
        try{
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            //메일 제목 설정
            helper.setSubject(email.getTitle());

            //수신자 설정
            helper.setTo(email.getAddress());

            //참조자 설정
            helper.setCc(email.getCcAddress());

            Context context = new Context();
            emailValues.forEach(context::setVariable);

            //메일 내용 설정 : 템플릿 프로세스
            String html = templateEngine.process(email.getTemplate(), context);
            helper.setText(html, true);

            //메일 보내기
            emailSender.send(message);
        } catch (MessagingException ignored) {

        }
    }
}
