package com.library.LibraryManagement.scheduler;

import com.library.LibraryManagement.entity.Borrowing;
import com.library.LibraryManagement.repository.BorrowingRepository;
import com.library.LibraryManagement.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class BorrowingScheduler {

    private static final Logger logger = LoggerFactory.getLogger(BorrowingScheduler.class);
    private static final BigDecimal FINE_PER_DAY = new BigDecimal("10000");

    @Autowired
    private BorrowingRepository borrowingRepository;

    @Autowired
    private EmailService emailService;

    @Scheduled(cron = "0 0 8 * * ?")
    public void scanOverdueBorrowings() {
        logger.info("Starting scheduled job: Scan overdue borrowings...");

        Date now = new Date();
        List<Borrowing> overdueList = borrowingRepository.findByDueDateBeforeAndStatus(now, 0);

        logger.info("Found {} overdue borrowings.", overdueList.size());

        for (Borrowing borrowing : overdueList) {
            try {
                long overdueDays = calculateOverdueDays(borrowing.getDueDate(), now);
                BigDecimal fineAmount = FINE_PER_DAY.multiply(new BigDecimal(overdueDays));

                emailService.sendOverdueNoticeEmail(borrowing, overdueDays, fineAmount);
                
            } catch (Exception e) {
                logger.error("Error processing overdue borrowing ID: {}", borrowing.getId(), e);
            }
        }

        logger.info("Scheduled job completed.");
    }

    private long calculateOverdueDays(Date dueDate, Date now) {
        long diffInMillies = Math.abs(now.getTime() - dueDate.getTime());
        return TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }
}

