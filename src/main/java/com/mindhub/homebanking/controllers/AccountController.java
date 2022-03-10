package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.AccountType;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.utils.CardUtils;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private ClientService clientService;



    @GetMapping("/api/accounts")
    public List<AccountDTO> getAccounts() {
        return accountService.getAccounts();
    }

    @GetMapping("/api/accounts/{id}")
    public AccountDTO getAccountById(@PathVariable Long id) {
        return accountService.getAccountById(id);
    }

    @GetMapping("/api/clients/current/accounts")
    public Set<AccountDTO> getCurrentAccounts(Authentication authentication) {
        return clientService.findByEmail(authentication.getName()).getAccounts();
    }

    @GetMapping("/api/accounts/number/{number}")
    public AccountDTO findByNumber(@PathVariable String number) {
        return accountService.findAccountByNumber(number);
    }

    @PostMapping("/api/clients/current/accounts")
    public ResponseEntity<Object> registerAccount(Authentication authentication, @RequestParam String accountType) {
        Client client = clientService.findClientByEmail(authentication.getName());

        int count = 0;
        for (Account account : client.getAccounts()
        ) {
            if (account.getActivate() == true) {
                count += 1;
            }
        }
        if (count == 3) {
            return new ResponseEntity<>("Limite de cuentas generadas.", HttpStatus.FORBIDDEN);
        }

        int number = CardUtils.numberRandom(99999999, 11111111);
        String numberAccount = CardUtils.numberToString(number);
        accountService.saveAccount(new Account("VIN" + numberAccount, LocalDateTime.now(), 0.0, AccountType.valueOf(accountType), true, client));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping("/api/accounts")
    public ResponseEntity<Object> statusAccount(Authentication authentication, @RequestParam String number, @RequestParam boolean bool) {
        Client client = clientService.findClientByEmail(authentication.getName());
        Account account = accountService.findByNumber(number);

        boolean validate = false;
        for (Account acc : client.getAccounts()
        ) {
            if (acc.getNumber() == account.getNumber()) {
                validate = true;
            }
        }

        if (validate == false) {
            return new ResponseEntity<>("Cuenta no pertece al usuario", HttpStatus.FORBIDDEN);
        }
        if (number.isEmpty()) {
            return new ResponseEntity<>("Datos incompletos", HttpStatus.FORBIDDEN);
        }

        for (Card c: account.getCards()
        ) {
            c.setAccount(null);
        }

        account.setActivate(bool);
        accountService.saveAccount(account);
        return new ResponseEntity<>("Solicitud procesada con exito", HttpStatus.OK);

    }
}
