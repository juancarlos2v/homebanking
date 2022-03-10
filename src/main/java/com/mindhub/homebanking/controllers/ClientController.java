package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.AccountType;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.services.implementation.ClientServiceImplementation;
import com.mindhub.homebanking.utils.CardUtils;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
public class ClientController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ClientService clientService;

    @Autowired
    private AccountService accountService;


    @GetMapping("api/clients")
    public List<ClientDTO> getClients() {
        return clientService.getClients();
    }

    @RequestMapping("api/clients/{id}")
    public ClientDTO getClient(@PathVariable Long id) {
        return clientService.getClient(id);
    }

    @PostMapping("/api/clients")
    public ResponseEntity<Object> register(
            @RequestParam String firstName, @RequestParam String lastName,
            @RequestParam String email, @RequestParam String password, @RequestParam String accountType) {

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            return new ResponseEntity<>("Datos incompletos.", HttpStatus.FORBIDDEN);
        }

        if (clientService.findClientByEmail(email) != null) {
            return new ResponseEntity<>("El usuario ya esta registrado", HttpStatus.FORBIDDEN);
        }

        Client client = new Client(firstName, lastName, email, passwordEncoder.encode(password));
        clientService.saveClient(client);

        int number = CardUtils.numberRandom(99999999, 11111111);
        String numberAccount = CardUtils.numberToString(number);

        accountService.saveAccount(new Account("VIN-" + numberAccount, LocalDateTime.now(), 0.00, AccountType.valueOf(accountType), true,client));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/api/clients/current")
    public ClientDTO getCurrentClient(Authentication authentication) {
        return clientService.findByEmail(authentication.getName());
    }

}
