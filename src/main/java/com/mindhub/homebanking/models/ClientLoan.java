package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
public class ClientLoan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="client_id")
    private Client client;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="loan_id")
    private Loan loan;

    private int payments;
    private double amount;

    public ClientLoan() {}

    public ClientLoan(int payments, double amount, Client client, Loan loan) {
        this.payments = payments;
        this.amount = amount;
        this.client = client;
        this.loan = loan;
    }

    public long getId() {return id;}

    public int getPayments() {return payments;}

    public void setPayments(int payments) {this.payments = payments;}

    public double getAmount() {return amount;}

    public void setAmount(double amount) {this.amount = amount;}

    public Client getClient() {return client;}

    public void setClient(Client client) {this.client = client;}

    public Loan getLoan() {return loan;}

    public void setLoan(Loan loan) {this.loan = loan;}
}
