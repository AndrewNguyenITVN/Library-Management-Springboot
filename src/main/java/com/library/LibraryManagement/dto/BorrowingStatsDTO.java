package com.library.LibraryManagement.dto;

public class BorrowingStatsDTO {
    private long weeklyCount;
    private long monthlyCount;

    public BorrowingStatsDTO() {}

    public BorrowingStatsDTO(long weeklyCount, long monthlyCount) {
        this.weeklyCount = weeklyCount;
        this.monthlyCount = monthlyCount;
    }

    public long getWeeklyCount() {
        return weeklyCount;
    }

    public void setWeeklyCount(long weeklyCount) {
        this.weeklyCount = weeklyCount;
    }

    public long getMonthlyCount() {
        return monthlyCount;
    }

    public void setMonthlyCount(long monthlyCount) {
        this.monthlyCount = monthlyCount;
    }
}
