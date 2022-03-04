package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.dtos.TransactionDTO;
import com.mindhub.homebanking.models.AccountType;
import com.mindhub.homebanking.utils.CardUtils;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@RestController
public class AccountController {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;


    @GetMapping("/api/accounts")
    public List<AccountDTO> getAccounts() {
        return accountRepository.findAll()
                .stream().map(account -> new AccountDTO(account)).collect(toList());
    }

    /*@GetMapping("/api/accounts/{id}")
    public AccountDTO getAccount(@PathVariable Long id) {
        AccountDTO account = new AccountDTO(accountRepository.findById(id).orElse(null));
        return account;
    }*/

    @GetMapping("/api/clients/current/accounts")
    public Set<AccountDTO> getAll(Authentication authentication) {
        Set<AccountDTO> accounts = new ClientDTO(clientRepository.findByEmail(authentication.getName())).getAccounts();
        return accounts;
    }

    @GetMapping("/api/accounts/{number}")
    public AccountDTO getAccount(@PathVariable String number) {
        AccountDTO  account = new AccountDTO(accountRepository.findByNumber(number));
        return account;
    }

    @PostMapping("/api/clients/current/accounts")
    public ResponseEntity<Object> registerAccount(Authentication authentication, @RequestParam String accountType) {
        Client client= clientRepository.findByEmail(authentication.getName());

        int count=0;
        for (Account account:client.getAccounts()
             ) {
            if (account.getActivate()==true){
                count+=1;
            }
        }
        if (count == 3) {
            return new ResponseEntity<>("Limite de cuentas generadas.", HttpStatus.FORBIDDEN);
        }

        int number= CardUtils.numberRandom(99999999,11111111);
        String numberAccount= CardUtils.numberToString(number);
        accountRepository.save(new Account("VIN"+numberAccount, LocalDateTime.now(), 0.0,AccountType.valueOf(accountType),true,client));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping("/api/accounts")
    public ResponseEntity<Object> statusAccount(Authentication authentication, @RequestParam String number,@RequestParam boolean bool){
        Client client=clientRepository.findByEmail(authentication.getName());
        Account account=accountRepository.findByNumber(number);

        boolean validate=false;
        for (Account acc: client.getAccounts()
             ) {
            if(acc.getNumber()==account.getNumber()){
                validate=true;
            }
        }

        if(validate==false){
            return  new ResponseEntity<>("Cuenta no pertece al usuario",HttpStatus.FORBIDDEN);
        }
        if(number.isEmpty()){
            return  new ResponseEntity<>("Datos incompletos",HttpStatus.FORBIDDEN);
        }
        account.setActivate(bool);
        accountRepository.save(account);
        return new ResponseEntity<>("Solicitud procesada con exito",HttpStatus.OK);

    }
}
