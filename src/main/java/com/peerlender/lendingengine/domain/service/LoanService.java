package com.peerlender.lendingengine.domain.service;

import com.peerlender.lendingengine.domain.exception.LoanApplicationNotFoundException;
import com.peerlender.lendingengine.domain.exception.UserNotFoundException;
import com.peerlender.lendingengine.domain.model.*;
import com.peerlender.lendingengine.domain.repository.LoanApplicationRepository;
import com.peerlender.lendingengine.domain.repository.LoanRepository;
import com.peerlender.lendingengine.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component
public class LoanService {

    private final LoanApplicationRepository loanApplicationRepository;
    private final UserRepository userRepository;
    private final LoanRepository loanRepository;

    @Autowired
    public LoanService(LoanApplicationRepository loanApplicationRepository,
                       UserRepository userRepository, LoanRepository loanRepository) {
        this.loanApplicationRepository = loanApplicationRepository;
        this.userRepository = userRepository;
        this.loanRepository = loanRepository;
    }

    @Transactional
    public void acceptLoan(final long loanApplicationId, final String lenderUsername) {
        User lender = findUser(lenderUsername);
        LoanApplication loanApplication = findLoanApplication(loanApplicationId);
        User borrower = loanApplication.getBorrower();
        Money money = new Money(loanApplication.getAmount(), Currency.USD);
        lender.withDraw(money);
        borrower.topUp(money);
        loanRepository.save(new Loan(lender, loanApplication));
    }

    public List<Loan> findAllBorrowedLoans(final User borrower) {
        return loanRepository.findAllByBorrower(borrower);
    }

    public List<Loan> findAllLentLoans(final User lender) {
        return loanRepository.findAllByLender(lender);
    }

    private LoanApplication findLoanApplication(long loanApplicationId) {
        return loanApplicationRepository.findById(loanApplicationId)
                .orElseThrow(() -> new LoanApplicationNotFoundException(loanApplicationId));
    }

    private User findUser(String lenderUsername) {
        return userRepository.findById(lenderUsername).orElseThrow(() -> new UserNotFoundException(lenderUsername));
    }
}
