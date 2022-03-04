package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Loan;

import java.util.ArrayList;
import java.util.List;

public class LoanDTO {
    private long id;
    private String name;
    private double maxAmount;
    private List<Integer> payments = new ArrayList<>();
    private int rate;

    public LoanDTO() {}

    public LoanDTO(Loan loan) {
        this.id= loan.getId();
        this.name = loan.getName();
        this.maxAmount = loan.getMaxAmount();
        this.payments = loan.getPayments();
        this.rate=loan.getRate();
    }

    public long getId() {return id;}

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public double getMaxAmount() {return maxAmount;}

    public void setMaxAmount(double maxAmount) {this.maxAmount = maxAmount;}

    public List<Integer> getPayments() {return payments;}

    public void setPayments(List<Integer> payments) {this.payments = payments;}

    public int getRate() {return rate;}

    public void setRate(int rate) {this.rate = rate;}
}
