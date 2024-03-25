package com.ssg.ssgssag;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class EmailServiceTest {

    @Autowired(required = false)
    private JavaMailSender javaMailSender;

    @Test
    public void testSendEmail() {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo("example@gmail.com"); // 받는 이메일 주소 설정
            helper.setSubject("Test2 Subject"); // 이메일 제목 설정
            helper.setText("Test2 Content", true); // 이메일 내용 설정, true는 HTML 형식을 사용한다는 것을 나타냅니다.
            javaMailSender.send(message);

            // 이메일이 성공적으로 전송되면 테스트를 성공으로 표시합니다.
            assertTrue(true);
        } catch (MessagingException e) {
            e.printStackTrace();
            // 메일 전송이 실패한 경우 테스트를 실패로 표시합니다.
            assertTrue(false);
        }
    }
}
