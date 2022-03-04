package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Account;
import org.hibernate.annotations.GenericGenerator;

public class LoanApplicationDTO {

    private long id;
    private double amount;
    private int payments;
    private String account;
    private double amountFinal;

    public LoanApplicationDTO() {
    }

    public LoanApplicationDTO(long id, double amount, int payments, String account, double amountFinal) {
        this.id = id;
        this.amount = amount;
        this.payments = payments;
        this.account = account;
        this.amountFinal=amountFinal;
    }

    public long getId() {return id;}

    public void setId(long id) {this.id = id;}

    public double getAmount() {return amount;}

    public void setAmount(double amount) {this.amount = amount;}

    public int getPayments() {return payments;}

    public void setPayments(int payments) {this.payments = payments;}

    public String getAccount() {return account;}

    public void setAccount(String account) {this.account = account;}

    public double getAmountFinal() {return amountFinal;}

    public void setAmountFinal(double amountFinal) {this.amountFinal = amountFinal;}
}
