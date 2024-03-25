package com.ssg.ssgssag.service;

import com.ssg.ssgssag.dto.InventoryShortageDTO;
import com.ssg.ssgssag.mapper.UtilMapper;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class UtilServiceImpl implements UtilService {

    @Autowired
    private final UtilMapper utilMapper;

    @Autowired
    private JavaMailSender javaMailSender;


    // 선언

    @Override
    public void sendShortageNotificationEmails() {

        // 1. 재고 부족 상품 목록 조회
        List<InventoryShortageDTO> shortageProducts = utilMapper.findShortageInventory();

        log.info("Shortage Products: {}", shortageProducts);

        // 2. 메일 보낼 대상의 이메일 주소 조회
        List<String> recipientEmails = utilMapper.selectMemberEmail();

        // 3. 재고 부족 상품 목록을 이메일로 발송
        String subject = "재고 부족 알림";
        StringBuilder contentBuilder = new StringBuilder();

        for (InventoryShortageDTO product : shortageProducts) {

            String productName = product.getVProductNm();
            int inventoryCount = product.getTotalInventoryCount();
            String warehouseCode = product.getVWarehouseCd();

            String content = "상품명: " + productName + ", 재고 수량: " + inventoryCount + ", 창고 코드: " + warehouseCode;
            contentBuilder.append(content).append("\n");
        }

        // 모든 수신자에게 한 번만 이메일 전송
        String content = contentBuilder.toString();
        for (String recipientEmail : recipientEmails) {
            try {
                sendEmail(recipientEmail, subject, content);
            } catch (MessagingException e) {
                e.printStackTrace(); // 이메일 전송에 실패한 경우 예외 처리
            }
        }

    }

    // 박아 놓기
    private void sendEmail(String recipientEmail, String subject, String content) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(recipientEmail);
        helper.setSubject(subject);
        helper.setText(content, true); // HTML 형식의 내용을 허용

        javaMailSender.send(message); // 이메일 전송
    }

}

