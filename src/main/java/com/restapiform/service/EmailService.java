package com.restapiform.service;

import com.restapiform.config.ConstVariable;
import com.restapiform.model.Account;
import com.restapiform.model.AuthToken;
import com.restapiform.model.Email;
import com.restapiform.model.TokenType;
import com.restapiform.repository.AuthTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender emailSender;
    private final SpringTemplateEngine templateEngine;
    private final AuthTokenRepository authTokenRepository;
    // 메일 전송 성공 여부 확인을 위한 메일 참조자
    @Value("${spring.mail.username}")
    private String ccMail;

    /**
     * 회원인증 토큰 메일전송
     * @param account
     * @throws MessagingException
     */
    public String sendTokenMail(Account account) {
        String uuid = makeTokenAndSave(account);
        Email email = new Email();
        email.setTitle(ConstVariable.SERVICE_NAME + " 인증메일 입니다.");
        email.setAddress(account.getEmail());
        email.setCcAddress(ccMail);
        email.setTemplate("confirm_email");

        HashMap<String, String> emailValues = new HashMap<>();

        emailValues.put("name", account.getName() + " 님");
        emailValues.put("service_name", ConstVariable.SERVICE_NAME);
        emailValues.put("url", ConstVariable.MAIN_URL + "/auth/signup/" + uuid);
        sendTemplateMessage(email, emailValues);

        return uuid;
    }

    /**
     * 메일 인증 token 생성 및 저장
     * @param account
     * @return
     */
    public String makeTokenAndSave(Account account) {
        AuthToken authToken = new AuthToken();
        String uuid = UUID.randomUUID().toString();

        authToken.setToken(uuid);
        authToken.setAccount(account);
        authToken.setType(TokenType.ACCOUNT_AUTH);
        authTokenRepository.save(authToken);

        return uuid;
    }

    /**
     * 템플릿 메일 전송
     * @param email
     * @param emailValues
     * @throws MessagingException
     */
    private void sendTemplateMessage(Email email, HashMap<String, String> emailValues) {
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
            throw new RuntimeException();
        }
    }
}
