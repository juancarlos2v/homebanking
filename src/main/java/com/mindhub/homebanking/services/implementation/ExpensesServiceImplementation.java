package com.mindhub.homebanking.services.implementation;

import com.mindhub.homebanking.models.CreditExpenses;
import com.mindhub.homebanking.repositories.ExpensesRepository;
import com.mindhub.homebanking.services.ExpensesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExpensesServiceImplementation implements ExpensesService {

    @Autowired
    ExpensesRepository expensesRepository;

    @Override
    public CreditExpenses saveExpenses(CreditExpenses expenses) {
        return expensesRepository.save(expenses);
    }
}
