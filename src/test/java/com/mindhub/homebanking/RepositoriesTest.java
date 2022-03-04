package com.mindhub.homebanking;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.Loan;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.LoanRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RepositoriesTest {

    @Autowired
    LoanRepository loanRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    CardRepository cardRepository;

    @Test
    public void existLoans(){
        List<Loan> loans = loanRepository.findAll();
        assertThat(loans,is(not(empty())));
    }

    @Test
    public void existPersonalLoan(){
        List<Loan> loans = loanRepository.findAll();
        assertThat(loans, hasItem(hasProperty("name", is("Personal"))));
    }

    @Test
    public void existStrNumber(){
        List<Account> accounts=accountRepository.findAll();
        assertThat(accounts, hasItem(hasProperty("number", containsString("VI"))));
    }

    @Test
    public void existAccounts(){
        List<Account> accounts=accountRepository.findAll();
        assertThat(accounts,is(not(empty())));
    }

    @Test
    public void existCvv(){
        List<Card> cards=cardRepository.findAll();
        assertThat(cards,hasItems(hasProperty("cvv")));
    }
    @Test
    public void existNumber(){
        List<Card> cards=cardRepository.findAll();
        assertThat(cards,hasItems(hasProperty("number")));
    }



}
