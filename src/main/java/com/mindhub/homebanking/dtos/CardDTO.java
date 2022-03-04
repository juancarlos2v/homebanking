package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class CardDTO {
    private long id;
    private String cardholer;
    private CardType type;
    private CardColor color;
    private String number;
    private Integer cvv;
    private LocalDate thruDate;
    private LocalDate fromDate;
    private boolean activate;
    private Account account;
    Set<CreditExpensesDTO> expenses=new HashSet<>();

    public CardDTO() {
    }

    public CardDTO(Card card) {
        this.id=card.getId();
        this.cardholer = card.getCardholer();
        this.type = card.getType();
        this.color = card.getColor();
        this.number = card.getNumber();
        this.cvv = card.getCvv();
        this.thruDate = card.getThruDate();
        this.fromDate = card.getFromDate();
        this.activate=card.getActivate();
        this.expenses=card.getExpenses().stream().map(expenses->new CreditExpensesDTO(expenses)).collect(Collectors.toSet());
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

    public boolean getActivate() {return activate;}

    public void setActivate(boolean activate) {this.activate = activate;}

    public Set<CreditExpensesDTO> getExpenses() {return expenses;}

    public void setExpenses(Set<CreditExpensesDTO> expenses) {this.expenses = expenses;}
}
