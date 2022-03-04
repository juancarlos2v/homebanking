package com.mindhub.homebanking.dtos;

public class CardPaymentDTO {
    private String numberCard;
    private Integer cvv;
    private double amount;
    private String description;

    public CardPaymentDTO() {}

    public CardPaymentDTO(String numberCard, int cvv, double amount, String description) {
        this.numberCard = numberCard;
        this.cvv = cvv;
        this.amount = amount;
        this.description = description;
    }

    public String getNumberCard() {return numberCard;}

    public void setNumberCard(String numberCard) {this.numberCard = numberCard;}

    public int getCvv() {return cvv;}

    public void setCvv(Integer cvv) {this.cvv = cvv;}

    public double getAmount() {return amount;}

    public void setAmount(double amount) {this.amount = amount;}

    public String getDescription() {return description;}

    public void setDescription(String description) {this.description = description;}
}
