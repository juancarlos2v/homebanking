package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    private String cardholer;
    private CardType type;
    private CardColor color;
    private String number;
    private Integer cvv;
    private LocalDate thruDate;
    private LocalDate fromDate;
    private boolean activate;

    @OneToMany(mappedBy="card", fetch=FetchType.EAGER)
    private Set<CreditExpenses> expenses = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="account_id")
    private Account account;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private Client client;

    public Card() {
    }

    public Card(String cardholer, CardType type, CardColor color, String number, Integer cvv, LocalDate thruDate, LocalDate fromDate, Client client, boolean activate) {
        this.cardholer = cardholer;
        this.type = type;
        this.color = color;
        this.number = number;
        this.cvv = cvv;
        this.thruDate = thruDate;
        this.fromDate = fromDate;
        this.client = client;
        this.activate = activate;
    }

    public Card(String cardholer, CardType type, CardColor color, String number, Integer cvv, LocalDate thruDate, LocalDate fromDate, boolean activate, Account account, Client client) {
        this.cardholer = cardholer;
        this.type = type;
        this.color = color;
        this.number = number;
        this.cvv = cvv;
        this.thruDate = thruDate;
        this.fromDate = fromDate;
        this.activate = activate;
        this.account = account;
        this.client = client;
    }

    public long getId() {return id;}

    public String getCardholer() {return cardholer;}

    public void setCardholer(String cardholer) {this.cardholer = cardholer;}

    public CardType getType() {return type;}

    public void setType(CardType type) {this.type = type;}

    public CardColor getColor() {return color;}

    public void setColor(CardColor color) {this.color = color;}

    public String getNumber() {return number;}

    public void setNumber(String number) {this.number = number;}

    public Integer getCvv() {return cvv;}

    public void setCvv(Integer cvv) {this.cvv = cvv;}

    public LocalDate getThruDate() {return thruDate;}

    public void setThruDate(LocalDate thruDate) {this.thruDate = thruDate;}

    public LocalDate getFromDate() {return fromDate;}

    public void setFromDate(LocalDate fromDate) {this.fromDate = fromDate;}

    public Client getClient() {return client;}

    public void setClient(Client client) {this.client = client;}

    public boolean getActivate() {return activate;}

    public void setActivate(boolean activate) {this.activate = activate;}

    public Account getAccount() {return account;}

    public void setAccount(Account account) {this.account = account;}

    public Set<CreditExpenses> getExpenses() {return expenses;}

    public void setExpenses(Set<CreditExpenses> expenses) {this.expenses = expenses;}
}