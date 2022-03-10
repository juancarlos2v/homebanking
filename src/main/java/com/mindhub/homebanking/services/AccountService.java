package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Set;

public interface AccountService {
    public List<AccountDTO> getAccounts();
    public AccountDTO getAccountById(Long id);
    public AccountDTO findAccountByNumber(String number);
    public Account findByNumber(String number);
    public Account saveAccount(Account account);

}
