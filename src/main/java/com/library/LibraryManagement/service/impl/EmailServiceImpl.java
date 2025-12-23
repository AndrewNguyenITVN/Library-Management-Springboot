package com.library.LibraryManagement.service.impl;

import com.library.LibraryManagement.entity.Borrowing;
import com.library.LibraryManagement.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class EmailServiceImpl implements EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    @Autowired
    private JavaMailSender mailSender;

    @org.springframework.beans.factory.annotation.Value("${spring.mail.host}")
    private String mailHost;

    @Override
    public void sendBorrowingConfirmationEmail(Borrowing borrowing) {
        if (!shouldSendEmail(borrowing)) return;

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(borrowing.getReaderId().getEmail());
            message.setSubject("Xác nhận mượn sách thành công");
            message.setText(buildBorrowingContent(borrowing));
            mailSender.send(message);
            
            logger.info("Email sent successfully to {} for borrowing ID: {}", 
                    borrowing.getReaderId().getEmail(), borrowing.getId());
        } catch (Exception e) {
            handleException(e, borrowing);
        }
    }

    @Override
    public void sendOverdueNoticeEmail(Borrowing borrowing, long overdueDays, java.math.BigDecimal fineAmount) {
        if (!shouldSendEmail(borrowing)) return;

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(borrowing.getReaderId().getEmail());
            message.setSubject("THÔNG BÁO QUÁ HẠN: " + borrowing.getBookId().getNameBook());
            message.setText(buildOverdueContent(borrowing, overdueDays, fineAmount));
            mailSender.send(message);

            logger.info("Overdue email sent successfully to {} for borrowing ID: {}", 
                    borrowing.getReaderId().getEmail(), borrowing.getId());
        } catch (Exception e) {
            handleException(e, borrowing);
        }
    }

    private boolean shouldSendEmail(Borrowing borrowing) {
        if (borrowing == null) {
            logger.error("Cannot send email: Borrowing is null");
            return false;
        }
        if ("smtp.example.com".equals(mailHost)) {
            logger.warn("EMAIL SKIPPED: Default SMTP host");
            return false;
        }
        if (borrowing.getReaderId() == null || borrowing.getReaderId().getEmail() == null) {
            logger.warn("Cannot send email: Reader or Email is null");
            return false;
        }
        if (!isValidEmail(borrowing.getReaderId().getEmail())) {
            logger.warn("Cannot send email: Invalid email format");
            return false;
        }
        return true;
    }

    private String buildBorrowingContent(Borrowing borrowing) {
        return String.format(
                "Chào %s,\n\n" +
                "Bạn đã mượn thành công cuốn sách sau:\n" +
                "  - Tên sách: %s\n" +
                "  - Ngày mượn: %s\n" +
                "  - Ngày hết hạn: %s\n\n" +
                "Vui lòng trả sách đúng hạn. Cảm ơn bạn!\n\n" +
                "Thư viện",
                borrowing.getReaderId().getNameReader(),
                borrowing.getBookId().getNameBook(),
                formatDate(borrowing.getBorrowedAt()),
                formatDate(borrowing.getDueDate())
        );
    }

    private String buildOverdueContent(Borrowing borrowing, long overdueDays, java.math.BigDecimal fineAmount) {
        return String.format(
                "CẢNH BÁO QUÁ HẠN\n\n" +
                "Chào %s,\n\n" +
                "Cuốn sách bạn mượn đã QUÁ HẠN:\n" +
                "  - Tên sách: %s\n" +
                "  - Ngày hết hạn: %s\n" +
                "  - Số ngày quá hạn: %d ngày\n" +
                "  - Phí phạt dự tính: %s VNĐ\n\n" +
                "Vui lòng trả sách ngay lập tức để tránh phát sinh thêm phí.\n\n" +
                "Thư viện",
                borrowing.getReaderId().getNameReader(),
                borrowing.getBookId().getNameBook(),
                formatDate(borrowing.getDueDate()),
                overdueDays,
                fineAmount.toString()
        );
    }

    private void handleException(Exception e, Borrowing borrowing) {
         logger.error("Failed to send email for borrowing ID: {}. Error: {}", borrowing.getId(), e.getMessage());
    }



    private boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }

    private String formatDate(Date date) {
        if (date == null) {
            return "N/A";
        }
        return dateFormat.format(date);
    }
}
