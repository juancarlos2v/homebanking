package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.AccountType;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

public class AccountDTO {
    private long id;
    private String number;
    private LocalDateTime creationDate;
    private  Double balance;
    private boolean activate;
    private AccountType accountType;
    Set<TransactionDTO> transactions;
    Set<CardDTO> cards;

    public AccountDTO(){
    }

    public AccountDTO(Account account) {
        this.id= account.getId();
        this.number=account.getNumber();
        this.creationDate = account.getCreationDate();
        this.balance = account.getBalance();
        this.activate= account.getActivate();
        this.accountType=account.getAccountType();
        this.cards=account.getCards().stream().map(card->new CardDTO(card)).collect(Collectors.toSet());
        this.transactions= account.getTransactions().stream().map(transaction->new TransactionDTO(transaction)).collect(Collectors.toSet());
    }

    public long getId() {return id;}

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Set<TransactionDTO> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<TransactionDTO> transactions) {
        this.transactions = transactions;
    }

    public boolean getActivate() {return activate;}

    public void setActivate(boolean activate) {this.activate = activate;}

    public AccountType getAccountType() {return accountType;}

    public void setAccountType(AccountType accountType) {this.accountType = accountType;}

    public Set<CardDTO> getCards() {return cards;}

    public void setCards(Set<CardDTO> cards) {this.cards = cards;}
}
