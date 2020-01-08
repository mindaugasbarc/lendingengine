package com.peerlender.lendingengine.model;

import java.time.Duration;

public final class LoanRequest {

    private final int amount;
    private final User borrower;
    private final Duration repaymentTerm;
    private final double interestRate;

    public LoanRequest(int amount, User borrower, Duration repaymentTerm,
                       double interestRate) {
        this.amount = amount;
        this.borrower = borrower;
        this.repaymentTerm = repaymentTerm;
        this.interestRate = interestRate;
    }

    public int getAmount() {
        return amount;
    }

    public User getBorrower() {
        return borrower;
    }

    public Duration getRepaymentTerm() {
        return repaymentTerm;
    }

    public double getInterestRate() {
        return interestRate;
    }
}
