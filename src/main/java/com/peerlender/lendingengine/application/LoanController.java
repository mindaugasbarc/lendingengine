package com.peerlender.lendingengine.application;

import com.peerlender.lendingengine.domain.model.LoanRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoanController {

    @PostMapping
    public void requestForLoan(@RequestBody final LoanRequest loanRequest) {
        System.out.println(loanRequest);
    }

}
