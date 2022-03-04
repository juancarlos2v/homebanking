package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ClientDTO {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    Set<AccountDTO> accounts=new HashSet<>();
    Set<ClientLoanDTO> prestamos=new HashSet<>();
    Set<CardDTO> cards=new HashSet<>();

    public ClientDTO() {
    }

    public ClientDTO(Client client) {
        this.id = client.getId();
        this.firstName = client.getFirstName();
        this.lastName = client.getLastName();
        this.email=client.getEmail();
        this.password = client.getPassword();
        this.accounts= client.getAccounts().stream().map(account->new AccountDTO(account)).collect(Collectors.toSet());
        this.prestamos=client.getClientLoans().stream().map(prestamo->new ClientLoanDTO(prestamo)).collect(Collectors.toSet());
        this.cards=client.getCards().stream().map(card->new CardDTO(card)).collect(Collectors.toSet());
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {return email;}

    public void setEmail(String email) {this.email = email;}

    public String getPassword() {return password;}

    public void setPassword(String password) {this.password = password;}

    public Set<AccountDTO> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<AccountDTO> accounts) {
        this.accounts = accounts;
    }

    public Set<ClientLoanDTO> getPrestamos() {return prestamos;}

    public void setPrestamos(Set<ClientLoanDTO> prestamos) {this.prestamos = prestamos;}

    public Set<CardDTO> getCards() {return cards;}

    public void setCards(Set<CardDTO> cards) {this.cards = cards;}
}
