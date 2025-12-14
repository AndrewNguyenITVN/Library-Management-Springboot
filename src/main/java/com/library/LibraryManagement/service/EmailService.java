package com.library.LibraryManagement.service;

import com.library.LibraryManagement.entity.Borrowing;
import com.library.LibraryManagement.service.imp.EmailServiceImp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class EmailService implements EmailServiceImp {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    @Autowired
    private JavaMailSender mailSender;

    @org.springframework.beans.factory.annotation.Value("${spring.mail.host}")
    private String mailHost;

    @Override
    public void sendBorrowingConfirmationEmail(Borrowing borrowing) {
        if (borrowing == null) {
            logger.error("Cannot send email: Borrowing is null");
            return;
        }

        if ("smtp.example.com".equals(mailHost) || "your-email@example.com".equals(mailHost)) {
            logger.warn("EMAIL SENDING SKIPPED: Mail host is configured as '{}'. Please update application.yml with real SMTP settings.", mailHost);
            return;
        }

        if (borrowing.getReaderId() == null) {
            logger.error("Cannot send email: Reader is null for borrowing ID: {}", borrowing.getId());
            return;
        }

        String readerEmail = borrowing.getReaderId().getEmail();
        if (readerEmail == null || readerEmail.trim().isEmpty()) {
            logger.warn("Cannot send email: Reader {} has no email address", 
                    borrowing.getReaderId().getNameReader());
            return;
        }

        if (!isValidEmail(readerEmail)) {
            logger.warn("Cannot send email: Invalid email format: {}", readerEmail);
            return;
        }

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(readerEmail);
            message.setSubject("Xác nhận mượn sách thành công");

            String readerName = borrowing.getReaderId().getNameReader();
            String bookName = borrowing.getBookId() != null ? 
                    borrowing.getBookId().getNameBook() : "N/A";
            String borrowedDate = formatDate(borrowing.getBorrowedAt());
            String dueDate = formatDate(borrowing.getDueDate());

            String content = String.format(
                    "Chào %s,\n\n" +
                            "Bạn đã mượn thành công cuốn sách sau từ thư viện:\n\n" +
                            "  - Tên sách: %s\n" +
                            "  - Ngày mượn: %s\n" +
                            "  - Ngày hết hạn: %s\n\n" +
                            "Vui lòng trả sách đúng hạn. Cảm ơn bạn!\n\n" +
                            "Trân trọng,\n" +
                            "Thư viện",
                    readerName, bookName, borrowedDate, dueDate
            );

            message.setText(content);
            mailSender.send(message);
            
            logger.info("Email sent successfully to {} for borrowing ID: {}", 
                    readerEmail, borrowing.getId());
        } catch (org.springframework.mail.MailException e) {
            logger.error("Failed to send email to {} for borrowing ID: {}. Error: {}", 
                    readerEmail, borrowing.getId(), e.getMessage(), e);
        } catch (Exception e) {
            logger.error("Unexpected error while sending email to {} for borrowing ID: {}. Error: {}", 
                    readerEmail, borrowing.getId(), e.getMessage(), e);
        }
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
