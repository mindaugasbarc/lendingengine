package com.peerlender.lendingengine.application;

import com.peerlender.lendingengine.domain.model.LoanRequest;
import com.peerlender.lendingengine.domain.repository.LoanRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoanController {

    private final LoanRequestRepository loanRequestRepository;

    @Autowired
    public LoanController(LoanRequestRepository loanRequestRepository) {
        this.loanRequestRepository = loanRequestRepository;
    }

    @PostMapping(value = "/loan/request")
    public void requestLoan(@RequestBody final LoanRequest loanRequest) {
        System.out.println(loanRequest);
    }
}
