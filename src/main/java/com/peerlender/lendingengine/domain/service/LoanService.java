package com.peerlender.lendingengine.domain.service;

import com.peerlender.lendingengine.domain.exception.LoanApplicationNotFoundException;
import com.peerlender.lendingengine.domain.exception.UserNotFoundException;
import com.peerlender.lendingengine.domain.model.Loan;
import com.peerlender.lendingengine.domain.model.LoanApplication;
import com.peerlender.lendingengine.domain.model.User;
import com.peerlender.lendingengine.domain.repository.LoanApplicationRepository;
import com.peerlender.lendingengine.domain.repository.LoanRepository;
import com.peerlender.lendingengine.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LoanService {

    private final LoanApplicationRepository loanApplicationRepository;
    private final LoanRepository loanRepository;
    private final UserRepository userRepository;

    @Autowired
    public LoanService(LoanApplicationRepository loanApplicationRepository,
                       LoanRepository loanRepository, UserRepository userRepository) {
        this.loanApplicationRepository = loanApplicationRepository;
        this.loanRepository = loanRepository;
        this.userRepository = userRepository;
    }

    public void acceptLoan(final long loanApplicationId, final long lenderId) {
        User lender = userRepository.findById(lenderId).orElseThrow(() -> new UserNotFoundException(lenderId));
        LoanApplication loanApplication = loanApplicationRepository.findById(loanApplicationId)
                .orElseThrow(() -> new LoanApplicationNotFoundException(loanApplicationId));
            loanRepository.save(new Loan(lender, loanApplication));
    }
}
