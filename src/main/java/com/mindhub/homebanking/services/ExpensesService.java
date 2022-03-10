package com.mindhub.homebanking.services;

import com.mindhub.homebanking.models.CreditExpenses;

public interface ExpensesService {

    public CreditExpenses saveExpenses(CreditExpenses expenses);
}
