package com.mindhub.homebanking.services;

import com.mindhub.homebanking.models.ClientLoan;
import org.springframework.stereotype.Service;

public interface ClientLoanService {
    public ClientLoan saveClientLoan(ClientLoan clientLoan);
}
