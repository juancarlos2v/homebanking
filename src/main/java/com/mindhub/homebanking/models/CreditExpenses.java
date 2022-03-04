package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class CreditExpenses {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private String description;
    private double amount;
    private int payment;
    private LocalDateTime date;
    private  boolean activate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="card_id")
    private Card card;

    public CreditExpenses() {}


    public CreditExpenses(String description, double amount, LocalDateTime date, Card card ,boolean activate) {
        this.description = description;
        this.amount = amount;
        this.date = date;
        this.card = card;
    }

    public CreditExpenses(String description, double amount, int payment, LocalDateTime date, Card card,boolean activate) {
        this.description = description;
        this.amount = amount;
        this.payment = payment;
        this.date = date;
        this.card = card;
    }

    public long getId() {return id;}


    public String getDescription() {return description;}

    public void setDescription(String description) {this.description = description;}

    public double getAmount() {return amount;}

    public void setAmount(double amount) {this.amount = amount;}

    public LocalDateTime getDate() {return date;}

    public void setDate(LocalDateTime date) {this.date = date;}

    public Card getCard() {return card;}

    public void setCard(Card card) {this.card = card;}

    public int getPayment() {return payment;}

    public void setPayment(int payment) {this.payment = payment;}

    public boolean getActivate() {return activate;}

    public void setActivate(boolean activate) {this.activate = activate;}
}
