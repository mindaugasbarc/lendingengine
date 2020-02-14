package com.peerlender.lendingengine.application;

import com.peerlender.lendingengine.application.model.LoanRequest;
import com.peerlender.lendingengine.application.service.TokenValidationService;
import com.peerlender.lendingengine.domain.model.Loan;
import com.peerlender.lendingengine.domain.model.LoanApplication;
import com.peerlender.lendingengine.domain.model.User;
import com.peerlender.lendingengine.domain.repository.LoanApplicationRepository;
import com.peerlender.lendingengine.domain.service.LoanApplicationAdapter;
import com.peerlender.lendingengine.domain.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class LoanController {

    private final LoanApplicationRepository loanApplicationRepository;
    private final LoanApplicationAdapter loanApplicationAdapter;
    private final LoanService loanService;
    private final TokenValidationService tokenValidationService;

    @Autowired
    public LoanController(LoanApplicationRepository loanApplicationRepository,
                          LoanApplicationAdapter loanApplicationAdapter, LoanService loanService,
                          TokenValidationService tokenValidationService) {
        this.loanApplicationRepository = loanApplicationRepository;
        this.loanApplicationAdapter = loanApplicationAdapter;
        this.loanService = loanService;
        this.tokenValidationService = tokenValidationService;
    }

    @PostMapping(value = "/loan/request")
    public void requestLoan(@RequestBody final LoanRequest loanRequest, HttpServletRequest request) {
        User borrower = tokenValidationService.validateTokenAndGetUser(request.getHeader(HttpHeaders.AUTHORIZATION));
        loanApplicationRepository.save(loanApplicationAdapter.transform(loanRequest, borrower));
    }

    @GetMapping(value = "/loan/requests")
    public List<LoanApplication> findAllLoanApplications(HttpServletRequest request) {
        User user = tokenValidationService.validateTokenAndGetUser(request.getHeader(HttpHeaders.AUTHORIZATION));
        return loanApplicationRepository.findAllByBorrower(user);
    }

    @GetMapping("/loan/borrowed")
    public List<Loan> findBorrowedLoans(@RequestHeader String authorization) {
       User user = tokenValidationService.validateTokenAndGetUser(authorization);
       return loanService.findAllBorrowedLoans(user);
    }

    @GetMapping("/loan/lent")
    public List<Loan> findLentLoans(@RequestHeader String authorization) {
        User user = tokenValidationService.validateTokenAndGetUser(authorization);
        return loanService.findAllLentLoans(user);
    }

    @PostMapping(value = "/loan/accept/{loanApplicationId}")
    public void acceptLoan(@PathVariable final String loanApplicationId,
                           HttpServletRequest request) {
        User lender = tokenValidationService.validateTokenAndGetUser(request.getHeader(HttpHeaders.AUTHORIZATION));
        loanService.acceptLoan(Long.parseLong(loanApplicationId), lender.getUsername());
    }
}
