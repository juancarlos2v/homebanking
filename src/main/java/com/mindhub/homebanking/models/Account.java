package com.mindhub.homebanking.models;

import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Account {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator (name = "native", strategy = "native")
    private long id;

    private String number;
    private LocalDateTime creationDate;
    private  Double balance;
    private AccountType accountType;
    private boolean activate;

    @OneToMany( mappedBy = "account",fetch = FetchType.EAGER)
    Set<Card> cards = new HashSet<>();

    @OneToMany(mappedBy="account", fetch=FetchType.EAGER)
    Set<Transaction> transactions = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="client_id")
    private Client client;

    public Account(){

    }

    public Account(String number, Double balance) {
        this.number = number;
        this.balance = balance;
    }

    public Account(String number, LocalDateTime creationDate, Double balance) {
        this.number = number;
        this.creationDate = creationDate;
        this.balance = balance;
    }

    public Account(String number, LocalDateTime creationDate, Double balance, Client client) {
        this.number=number;
        this.creationDate = creationDate;
        this.balance = balance;
        this.client=client;
    }

    public Account(String number, LocalDateTime creationDate, Double balance, AccountType accountType,boolean activate,Client client) {
        this.number = number;
        this.creationDate = creationDate;
        this.balance = balance;
        this.accountType = accountType;
        this.activate=activate;
        this.client = client;
    }


    public long getId() {return id;}

    public String getNumber() {return number;}

    public LocalDateTime getCreationDate() {return creationDate;}

    public Double getBalance() {return balance;}

    public void setBalance(Double balance) {this.balance = balance;}

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }

    public AccountType getAccountType() {return accountType;}

    public void setAccountType(AccountType accountType) {this.accountType = accountType;}

    public boolean getActivate() {return activate;}

    public void setActivate(boolean activate) {this.activate = activate;}

    public Set<Card> getCards() {
        return cards;
    }

    public void setCards(Set<Card> cards) {
        this.cards = cards;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", creationDate='" + creationDate + '\'' +
                ", balance=" + balance +
                ", client=" + client +
                '}';
    }
}
