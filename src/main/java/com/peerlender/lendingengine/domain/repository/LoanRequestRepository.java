package com.peerlender.lendingengine.domain.repository;

import com.peerlender.lendingengine.domain.model.LoanRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRequestRepository extends JpaRepository<LoanRequest, Long> {
}
