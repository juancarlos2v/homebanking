package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.Loan;

import java.util.List;

public interface LoanService {
    public List<LoanDTO> getLoans();
    public Loan findById(Long id);
    public  Loan saveLoan(Loan loan);
}
