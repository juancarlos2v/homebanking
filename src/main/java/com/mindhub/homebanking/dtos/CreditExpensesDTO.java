package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.CreditExpenses;

import java.time.LocalDateTime;

public class CreditExpensesDTO {
    private long id;
    private String description;
    private double amount;
    private LocalDateTime date;
    private int payment;
    private boolean activate;

    public CreditExpensesDTO(CreditExpenses expenses) {
        this.id = expenses.getId();
        this.description = expenses.getDescription();
        this.amount = expenses.getAmount();
        this.date = expenses.getDate();
        this.payment = expenses.getPayment();
        this.activate=expenses.getActivate();
    }

    public long getId() {return id;}

    public void setId(long id) {this.id = id;}

    public String getDescription() {return description;}

    public void setDescription(String description) {this.description = description;}

    public double getAmount() {return amount;}

    public void setAmount(double amount) {this.amount = amount;}

    public LocalDateTime getDate() {return date;}

    public void setDate(LocalDateTime date) {this.date = date;}

    public int getPayment() {return payment;}

    public void setPayment(int payment) {this.payment = payment;}

    public boolean isActivate() {return activate;}

    public void setActivate(boolean activate) {this.activate = activate;}
}
