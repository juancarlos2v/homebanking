package com.mindhub.homebanking;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class HomebankingApplication {


    public static void main(String[] args) {
        SpringApplication.run(HomebankingApplication.class, args);
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner initData(ClientRepository clientRepository,
                                      AccountRepository accountRepository,
                                      TransactionRepository transactionRepository,
                                      LoanRepository loanRepository,
                                      ClientLoanRepository clientLoanRepository,
                                      CardRepository cardRepository,
                                      ExpensesRepository expensesRepository) {
        return (args) -> {
            //CLIENT 1
            Client mabel = new Client("Mabel", "Morel", "melba@mindhub.com", passwordEncoder.encode("mabel"));
            clientRepository.save(mabel);

            Account VIN001 = accountRepository.save(new Account("VIN00000001", LocalDateTime.now(), 50000.0, AccountType.CORRIENTE, true, mabel));
            Account VIN002 = accountRepository.save(new Account("VIN00000002", LocalDateTime.now().plusDays(1), 75000.0, AccountType.AHORRO, true, mabel));

            transactionRepository.save(new Transaction(TransactionType.CREDITO, 9000.0, "Expensas", LocalDateTime.now(), VIN001, true));
            transactionRepository.save(new Transaction(TransactionType.DEBITO, 720.90, " Pet shop ", LocalDateTime.now().plusDays(1), VIN001, true));
            transactionRepository.save(new Transaction(TransactionType.CREDITO, 1450.0, "Venta ML", LocalDateTime.now().plusDays(3), VIN001, true));


            transactionRepository.save(new Transaction(TransactionType.DEBITO, 2000.0, "Alquiler", LocalDateTime.now(), VIN002, true));
            transactionRepository.save(new Transaction(TransactionType.CREDITO, 5000.0, "UALA Transf..", LocalDateTime.now().plusDays(2), VIN002, true));
            transactionRepository.save(new Transaction(TransactionType.DEBITO, 4700.0, "Seguro BBVA", LocalDateTime.now().plusDays(5), VIN002, true));

            List<Integer> paymentsHip = Arrays.asList(12, 24, 36, 48, 60);
            Loan loanHipotecario = (new Loan("Hipotecario", 500000.0, paymentsHip, 20));
            loanRepository.save(loanHipotecario);

            List<Integer> paymentsPer = Arrays.asList(6, 12, 24);
            Loan loanPersonal = ((new Loan("Personal", 100000.0, paymentsPer, 25)));
            loanRepository.save(loanPersonal);

            List<Integer> paymentsAuto = Arrays.asList(6, 12, 24, 36);
            Loan loanAutomotriz = ((new Loan("Automotriz", 300000.0, paymentsAuto, 30)));
            loanRepository.save(loanAutomotriz);

            clientLoanRepository.save(new ClientLoan(loanHipotecario.getPayments().get(4), 400000, mabel, loanHipotecario));
            clientLoanRepository.save(new ClientLoan(loanPersonal.getPayments().get(1), 50000, mabel, loanPersonal));

            cardRepository.save(new Card(mabel.getFirstName() + " " + mabel.getLastName(), CardType.DEBIT, CardColor.GOLD, "3498-5309-3321-9054", 534, LocalDate.now(), LocalDate.now().plusYears(5), true, VIN002, mabel));
            cardRepository.save(new Card(mabel.getFirstName() + " " + mabel.getLastName(), CardType.DEBIT, CardColor.TITANIUM, "5603-2343-2345-6543", 342, LocalDate.now(), LocalDate.now().plusYears(5), mabel, true));

            //CLIENT 2
            Client juan = new Client("Juan Carlos", "Vilcherrez", "juancarlos@fadu.com", passwordEncoder.encode("juancarlos"));
            clientRepository.save(juan);

            Account VIN003 = accountRepository.save(new Account("VIN00000003", LocalDateTime.now(), 50590.0, AccountType.CORRIENTE, true, juan));
            Account VIN004 = accountRepository.save(new Account("VIN00000004", LocalDateTime.now().plusDays(1), 97000.0, AccountType.AHORRO, true, juan));

            transactionRepository.save(new Transaction(TransactionType.DEBITO, 9890.0, "e-shop Nintendo", LocalDateTime.now(), VIN003, true));
            transactionRepository.save(new Transaction(TransactionType.DEBITO, 2759.30, "Impuesto pais", LocalDateTime.now().plusDays(1), VIN003, true));
            transactionRepository.save(new Transaction(TransactionType.DEBITO, 990.0, "Impuesto serv. dig.", LocalDateTime.now().plusDays(3), VIN003, true));

            transactionRepository.save(new Transaction(TransactionType.DEBITO, 3500.0, "Netflix", LocalDateTime.now(), VIN004, true));
            transactionRepository.save(new Transaction(TransactionType.CREDITO, 8000.0, "Deposito", LocalDateTime.now().plusDays(2), VIN004, true));
            transactionRepository.save(new Transaction(TransactionType.DEBITO, 2200.0, "Cinemark", LocalDateTime.now().plusDays(5), VIN004, true));

            clientLoanRepository.save(new ClientLoan(loanPersonal.getPayments().get(2), 100000, juan, loanPersonal));
            clientLoanRepository.save(new ClientLoan(loanAutomotriz.getPayments().get(3), 200000, juan, loanAutomotriz));

            Card card1 = new Card(juan.getFirstName() + " " + juan.getLastName(), CardType.CREDIT, CardColor.SILVER, "5434-7849-9856-3245", 276, LocalDate.now(), LocalDate.now().plusYears(5), juan, true);
            cardRepository.save(card1);
            Card card2=new Card(juan.getFirstName() + " " + juan.getLastName(), CardType.DEBIT, CardColor.TITANIUM, "5434-7844-6543-1254", 156, LocalDate.now(), LocalDate.now().plusYears(-2), juan, true);

            expensesRepository.save(new CreditExpenses("Tanque -Easy", 10000, LocalDateTime.now().plusDays(-15),card1,card1.getActivate() ));
            //ADMIN
            Client admin = new Client("mindhub@admin.com", passwordEncoder.encode("admin"));
            clientRepository.save(admin);
        };
    }
}


