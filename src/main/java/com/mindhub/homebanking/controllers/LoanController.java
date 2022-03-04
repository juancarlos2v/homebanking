package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.dtos.LoanApplicationDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
public class LoanController {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ClientLoanRepository clientLoanRepository;

    @GetMapping("api/loans")
    public List<LoanDTO> getLoans() {

        return loanRepository.findAll()
                .stream().map(loan -> new LoanDTO(loan)).collect(toList());
    }

    @Transactional
    @PostMapping("/api/loans")
    public ResponseEntity<Object> addLoan(
            Authentication authentication, @RequestBody LoanApplicationDTO loanApplicationDTO) {


        Client client = clientRepository.findByEmail(authentication.getName());
        Account accountDest = accountRepository.findByNumber(loanApplicationDTO.getAccount());
        Loan loan = loanRepository.findById(loanApplicationDTO.getId());


        if (loanApplicationDTO.getAmount() == 0 || loanApplicationDTO.getId() == 0 || loanApplicationDTO.getPayments() == 0) {
            return new ResponseEntity<>("Datos incompletos.", HttpStatus.FORBIDDEN);
        }

        if (loan == null) {
            return new ResponseEntity<>("El prestamo solicitado no existe.", HttpStatus.FORBIDDEN);
        }

        if (loanApplicationDTO.getAmount() >= loan.getMaxAmount()) {
            return new ResponseEntity<>("El monto solicitado excede al monto maximo del prestamo.", HttpStatus.FORBIDDEN);
        }

        boolean validatePayment = false;
        for (int pay : loan.getPayments()
        ) {
            if (pay == loanApplicationDTO.getPayments()) {
                validatePayment = true;

            }
        }

        if (validatePayment == false) {
            return new ResponseEntity<>("403 CUOTA NO PERMITIDA.", HttpStatus.FORBIDDEN);
        }

        if (accountDest == null) {
            return new ResponseEntity<>("403 CUENTA DESTINO NO EXISTE.", HttpStatus.FORBIDDEN);
        }

        boolean validateAccount = false;
        for (Account account : client.getAccounts()
        ) {
            if (account.getNumber() == accountDest.getNumber()) {
                validateAccount = true;
            }
        }
        if (validateAccount == false) {
            return new ResponseEntity<>("403 CUENTA NO PERTECENE AL CLIENTE", HttpStatus.FORBIDDEN);
        }

        double pagoTotal = loanApplicationDTO.getAmount() + loanApplicationDTO.getAmount() * 0.2;

        transactionRepository.save(new Transaction(TransactionType.CREDITO, loanApplicationDTO.getAmount(), loan.getName() + " " + "loan approved", LocalDateTime.now(), accountDest, accountDest.getActivate()));
        clientLoanRepository.save(new ClientLoan(loanApplicationDTO.getPayments(), loanApplicationDTO.getAmount(), client, loan));
        accountDest.setBalance(loanApplicationDTO.getAmount() + accountDest.getBalance());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/api/loans")
    public ResponseEntity<Object> createLoan(Authentication authentication,@RequestBody Loan loan) {

        Client client=clientRepository.findByEmail(authentication.getName());

        if (!client.getEmail().contains("admin")){
            return new ResponseEntity<>("Sin autorizacion.",HttpStatus.FORBIDDEN);
        }
        if (loan.getName().isEmpty() ||loan.getPayments().isEmpty() || loan.getMaxAmount()==0 || loan.getRate()==0) {
            return new ResponseEntity<>("Datos de prestamo incompleto", HttpStatus.FORBIDDEN);
        }
        loanRepository.save(new Loan(loan.getName(), loan.getMaxAmount(), loan.getPayments(), loan.getRate()));
        return new ResponseEntity<>("Prestamo creado con exito.", HttpStatus.CREATED);
    }
}
