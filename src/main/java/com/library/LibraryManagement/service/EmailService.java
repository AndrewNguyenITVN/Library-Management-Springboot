package com.library.LibraryManagement.service;

import com.library.LibraryManagement.entity.Borrowing;
import com.library.LibraryManagement.service.imp.EmailServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService implements EmailServiceImp {

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendBorrowingConfirmationEmail(Borrowing borrowing) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(borrowing.getReaderId().getEmail());
            message.setSubject("Xác nhận mượn sách thành công");

            String content = String.format(
                    "Chào %s,\n\n" +
                            "Bạn đã mượn thành công cuốn sách sau từ thư viện:\n\n" +
                            "  - Tên sách: %s\n" +
                            "  - Ngày mượn: %s\n" +
                            "  - Ngày hết hạn: %s\n\n" +
                            "Vui lòng trả sách đúng hạn. Cảm ơn bạn!\n\n" +
                            "Trân trọng,\n" +
                            "Thư viện XYZ",
                    borrowing.getReaderId().getNameReader(),
                    borrowing.getBookId().getNameBook(),
                    borrowing.getBorrowedAt().toString(),
                    borrowing.getDueDate().toString()
            );

            message.setText(content);
            mailSender.send(message);
        } catch (Exception e) {
            // Log the exception for debugging purposes
            // Consider a more robust error handling strategy for production
            e.printStackTrace();
        }
    }
}
