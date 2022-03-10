package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.CardService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.utils.CardUtils;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
public class CardController {

    @Autowired
    private CardService cardService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private AccountService accountService;

    @GetMapping("/api/cards")
    public List<CardDTO> getCards() {
        return cardService.getCards();
    }


    @PatchMapping("/api/clients/current/cards")
    public ResponseEntity<Object> activateCard(
            Authentication authentication, @RequestParam boolean activate, @RequestParam String number) {

        Client client = clientService.findClientByEmail(authentication.getName());
        Card card = cardService.findByNumber(number);

        boolean validate = false;
        for (Card c : client.getCards()
        ) {
            if (c.getNumber() == card.getNumber()) {
                validate = true;
            }
        }
        if (validate = false) {
            return new ResponseEntity<>("La tarjeta no pertenece al cliente.", HttpStatus.FORBIDDEN);
        }
        card.setActivate(activate);
        cardService.saveCard(card);
        return new ResponseEntity<>("Solicitud procesada con exito.", HttpStatus.OK);
    }

    @PostMapping("/api/clients/current/cards")
    public ResponseEntity<Object> newCard(
            Authentication authentication,
            @RequestParam String cardType, @RequestParam String cardColor
    ) {
        Client client = clientService.findClientByEmail(authentication.getName());

        int debit = 0;
        int credit = 0;
        for (Card card : client.getCards()
        ) {
            if (card.getType().equals(CardType.DEBIT) && card.getActivate() == true) {
                debit = 1 + debit;
            }
            if (card.getType().equals(CardType.CREDIT) && card.getActivate() == true) {
                credit = 1 + credit;
            }
        }

        switch (cardType) {
            case "DEBIT":
                if (debit == 3) {
                    return new ResponseEntity<>("LIMITE DE TARJETAS DE DEBITOS", HttpStatus.FORBIDDEN);
                }
            case "CREDIT":
                if (credit == 3) {
                    return new ResponseEntity<>("LIMITE DE TARJETAS DE CREDITO", HttpStatus.FORBIDDEN);
                }
        }

        if (debit >= 3 && credit >=3) {
            return new ResponseEntity<>("LIMITE DE TARJETAS GENERADAS", HttpStatus.FORBIDDEN);
        }
        int cvv = CardUtils.getCVV();
        String numberCard = CardUtils.getCardNumber();
        Card cardCreate = new Card(client.getFirstName() + " " + client.getLastName(), CardType.valueOf(cardType), CardColor.valueOf(cardColor), numberCard, cvv, LocalDate.now(), LocalDate.now().plusYears(5), client, true);
        cardService.saveCard(cardCreate);
        return new ResponseEntity<>(cardCreate.getNumber(), HttpStatus.CREATED);
    }

    @PostMapping("/api/card/associate")
    public ResponseEntity<Object> associateCard(
            Authentication authentication,
            @RequestParam String cardNumber,
            @RequestParam String accountNumber) {

        Client client = clientService.findClientByEmail(authentication.getName());
        Account account = accountService.findByNumber(accountNumber);
        Card card = cardService.findByNumber(cardNumber);

        if (cardNumber.isEmpty() || accountNumber.isEmpty()) {
            return new ResponseEntity<>("Datos incompletos", HttpStatus.FORBIDDEN);
        }
        boolean verifiedAccount = false;
        boolean verifiedCard = false;

        for (Card c : client.getCards()
        ) {
            if (c.getNumber() == card.getNumber()) {
                verifiedCard = true;
            }
        }

        for (Account a : client.getAccounts()
        ) {
            if (a.getNumber() == account.getNumber()) {
                verifiedAccount = true;
            }
        }

        if (verifiedAccount == false) {
            return new ResponseEntity<>("Cuenta no pertenece al cliente", HttpStatus.FORBIDDEN);
        }

        if (verifiedCard == false) {
            return new ResponseEntity<>("Tarjeta no pertenece al cliente", HttpStatus.FORBIDDEN);
        }

        if (card.getType() == CardType.CREDIT) {
            return new ResponseEntity<>("Las tarjetas de credito no pueden ser asociadas a una cuenta.", HttpStatus.FORBIDDEN);
        }

        card.setAccount(account);
        cardService.saveCard(card);
        return new ResponseEntity<>("Tarjeta asociada a Cuenta" + account.getNumber(), HttpStatus.OK);
    }
}