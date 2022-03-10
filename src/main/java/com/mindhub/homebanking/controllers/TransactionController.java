package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.*;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import com.mindhub.homebanking.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@RestController
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @Autowired
    ClientService clientService;

    @Autowired
    AccountService accountService;

    @Autowired
    CardService cardService;

    @Autowired
    ExpensesService expensesService;

    @GetMapping("/api/transactions")
    public List<TransactionDTO> getTransactions() {
        return transactionService.getTransactions();
    }

    @Transactional
    @PostMapping("/api/transactions")
    public ResponseEntity<Object> transaction(
            Authentication authentication, @RequestParam double amount, @RequestParam String description,
            @RequestParam String numberAccount, @RequestParam String numberAccountDestiny) {
        Client client = clientService.findClientByEmail(authentication.getName());
        Account accountOrigin = accountService.findByNumber(numberAccount);
        Account accountDestiny = accountService.findByNumber(numberAccountDestiny);

        if (amount == 0 || description.isEmpty() || numberAccount.isEmpty() || numberAccountDestiny.isEmpty()) {
            return new ResponseEntity<>("403 FALTAN DATOS.", HttpStatus.FORBIDDEN);
        }

        if (accountOrigin.getNumber() == accountDestiny.getNumber()) {
            return new ResponseEntity<>("403 PROHIBIDO CUENTA ORIGEN Y DESTINO IGUALES", HttpStatus.FORBIDDEN);
        }

        if (accountOrigin == null) {
            return new ResponseEntity<>("403 CUENTA ORIGEN INEXISTENTE", HttpStatus.FORBIDDEN);
        }

        boolean verificated = false;
        for (Account account : client.getAccounts()
        ) {
            System.out.println(account.getNumber());
            if (accountOrigin.getNumber() == account.getNumber()) {
                verificated = true;
            }
        }

        if (accountDestiny == null) {
            return new ResponseEntity<>("403 PROHIBIDO CUENTA DESTINO INEXISTENTE", HttpStatus.FORBIDDEN);
        }

        if (verificated == false) {
            return new ResponseEntity<>("403 ESTA CUENTA NO PERTENECE AL USUARIO", HttpStatus.FORBIDDEN);
        }

        if (accountOrigin.getBalance() < amount) {
            return new ResponseEntity<>("403 PROHIBIDO SALDO INSUFICIENTE", HttpStatus.FORBIDDEN);
        }

        double restAmount = accountOrigin.getBalance() - amount;
        accountOrigin.setBalance(restAmount);
        transactionService.saveTransacion(new Transaction(TransactionType.DEBITO, amount, description, LocalDateTime.now(), accountOrigin, true));

        double addAmount = accountDestiny.getBalance() + amount;
        accountDestiny.setBalance(addAmount);
        transactionService.saveTransacion(new Transaction(TransactionType.CREDITO, amount, accountOrigin.getNumber() + " " + description, LocalDateTime.now(), accountDestiny, true));

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Transactional
    @PostMapping("/api/payments")
    public ResponseEntity<Object> servicePayment(@RequestBody CardPaymentDTO payment) {
        Card card = cardService.findByNumber(payment.getNumberCard());
        Account account = card.getAccount();
        LocalDate date = LocalDate.now();

        if (payment.getNumberCard().isEmpty() || payment.getCvv() == 0 || payment.getAmount() == 0 || payment.getDescription().isEmpty()) {
            return new ResponseEntity<>("Datos incompletos", HttpStatus.FORBIDDEN);
        }

        if (card.getNumber() != payment.getNumberCard() && card.getCvv() != payment.getCvv()) {
            return new ResponseEntity<>("Datos de tarjeta incorrectos", HttpStatus.FORBIDDEN);
        }

        if (card.getThruDate().isBefore(date)) {
            return new ResponseEntity<>("Tarjeta vencida", HttpStatus.FORBIDDEN);
        }

        if (card.getType().equals(CardType.DEBIT)) {
            if (card.getAccount() == null) {
                return new ResponseEntity<>("La tarjeta no esta asociada a niguna cuenta", HttpStatus.FORBIDDEN);
            }

            if (account.getBalance() < payment.getAmount()) {
                return new ResponseEntity<>("Cuenta sin fondos suficiente", HttpStatus.FORBIDDEN);
            }

            double balanceFinal = account.getBalance() - payment.getAmount();
            account.setBalance(balanceFinal);
            transactionService.saveTransacion(new Transaction(TransactionType.DEBITO, payment.getAmount(), payment.getDescription(), LocalDateTime.now(), account, true));
            return new ResponseEntity<>("Pago realizado", HttpStatus.CREATED);
        }


        expensesService.saveExpenses(new CreditExpenses(payment.getDescription(), payment.getAmount(), LocalDateTime.now(), card, card.getActivate()));
        return new ResponseEntity<>("Pago realizado", HttpStatus.CREATED);
    }
}
