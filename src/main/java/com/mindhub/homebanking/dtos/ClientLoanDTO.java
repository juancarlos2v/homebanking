package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.ClientLoan;

public class ClientLoanDTO {
    private long id;
    private long loanId;
    private int payment;
    private  double amount;
    private String name;

    public ClientLoanDTO() {}

    public ClientLoanDTO(ClientLoan clientLoan) {
        this.id= clientLoan.getId();
        this.loanId= clientLoan.getLoan().getId();
        this.payment= clientLoan.getPayments();
        this.amount=clientLoan.getAmount();
        this.name=clientLoan.getLoan().getName();
    }

    public long getId() {return id;}

    public long getLoanId() {return loanId;}

    public void setLoanId(long loanId) {this.loanId = loanId;}

    public int getPayment() {return payment;}

    public void setPayment(int payment) {this.payment = payment;}

    public double getAmount() {return amount;}

    public void setAmount(double amount) {this.amount = amount;}

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}
}
