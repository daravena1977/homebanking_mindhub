package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


import static com.mindhub.homebanking.models.CardType.CREDIT;
import static com.mindhub.homebanking.models.CardType.DEBIT;
import static com.mindhub.homebanking.models.CardColor.*;

@SpringBootApplication
public class HomebankingApplication {
	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository,
									  TransactionRepository transactionRepository, LoanRepository loanRepository,
									  ClientLoanRepository clientLoanRepository, CardRepository cardRepository){
		return (args) -> {
			Client client1 = new Client("Melba", "Morel", "melba@mindhub.com", passwordEncoder.encode("melba123"));
			Client client2 = new Client("Diego", "Aravena", "daravena@mindhub.com", passwordEncoder.encode("diego123"));
			Client client3 = new Client("Enzo", "Aravena", "earavena@mindhub.com", passwordEncoder.encode("enzo123") );
			Client client4 = new Client("Sandra", "Lazaro", "sandra.lazaro@mindhub.com", passwordEncoder.encode("sandra123"));
			Client client5 = new Client("admin", "admin", "admin@mindhub.com", passwordEncoder.encode("admin123"));
			clientRepository.save(client1);
			clientRepository.save(client2);
			clientRepository.save(client3);
			clientRepository.save(client4);
			clientRepository.save(client5);

			Account account1 = new Account("VIN001", LocalDate.now(), 5000 );
			Account account2 = new Account("VIN002", LocalDate.now().plusDays(1), 7000 );
			Account account3 = new Account("VIN003", LocalDate.now().plusDays(5), 10000 );
			Account account4 = new Account("VIN004", LocalDate.now().plusDays(7), 15000 );

			client1.addAccount(account1);
			client1.addAccount(account2);
			client2.addAccount(account3);
			client4.addAccount(account4);

			accountRepository.save(account1);
			accountRepository.save(account2);
			accountRepository.save(account3);
			accountRepository.save(account4);

			Transaction transaction1 = new Transaction(20000.50, TransactionType.CREDIT,
					"Deposito por venta",LocalDateTime.now());
			Transaction transaction2 = new Transaction(-10000, TransactionType.DEBIT,
					"Por pago de cuenta de luz",LocalDateTime.now());
			Transaction transaction3 = new Transaction(13000, TransactionType.CREDIT,
					"Depósito en efectivo",LocalDateTime.now());
			Transaction transaction4 = new Transaction(-1000, TransactionType.DEBIT,
					"Por carga de tarjeta sube",LocalDateTime.now());
			Transaction transaction5 = new Transaction(55000, TransactionType.CREDIT,
					"Cobro de honorarios", LocalDateTime.now());

			account1.addTransaction(transaction1);
			account1.addTransaction(transaction2);
			account3.addTransaction(transaction3);
			account3.addTransaction(transaction4);
			account4.addTransaction(transaction5);

			transactionRepository.save(transaction1);
			transactionRepository.save(transaction2);
			transactionRepository.save(transaction3);
			transactionRepository.save(transaction4);
			transactionRepository.save(transaction5);

			Loan prestamo1 = new Loan("Hipotecario", 500000, List.of(12, 24, 36, 48, 60));
			Loan prestamo2 = new Loan("Personal", 100000, List.of(6, 12, 24));
			Loan prestamo3 = new Loan("Automotriz", 300000, List.of(6, 12, 24, 36));


			loanRepository.save(prestamo1);
			loanRepository.save(prestamo2);
			loanRepository.save(prestamo3);

			ClientLoan hipotecario1 = new ClientLoan(400000, 60);
			ClientLoan personal1 = new ClientLoan(50000, 12);

			ClientLoan personal2 = new ClientLoan(100000, 24);
			ClientLoan automotriz1 = new ClientLoan(200000, 36);

			ClientLoan automotriz2 = new ClientLoan(250000, 24);

			client1.addLoan(hipotecario1);
			client1.addLoan(personal1);

			client3.addLoan(personal2);
			client3.addLoan(automotriz1);

			client4.addLoan(automotriz2);

			prestamo1.addLoan(hipotecario1);
			prestamo2.addLoan(personal1);
			prestamo2.addLoan(personal2);
			prestamo3.addLoan(automotriz1);
			prestamo3.addLoan(automotriz2);

			clientLoanRepository.save(hipotecario1);
			clientLoanRepository.save(personal1);
			clientLoanRepository.save(personal2);
			clientLoanRepository.save(automotriz1);
			clientLoanRepository.save(automotriz2);

			Card gold1 = new Card(DEBIT, GOLD, "2222-5555-3333-9999", 255,
					LocalDate.now(), LocalDate.now().plusYears(5));
			Card titanium1 = new Card(CREDIT, TITANIUM, "2255-5522-4488-9966", 555,
					LocalDate.now(), LocalDate.now().plusYears(5));

			Card silver1 = new Card(CREDIT, SILVER, "4567-8866-4741-2123", 207,
					LocalDate.now(), LocalDate.now().plusYears(5));

			Card titanium2 = new Card(CREDIT, TITANIUM, "4555-1155-3684-6512", 401,
					LocalDate.now(), LocalDate.now().plusYears(5));

			client1.addCard(gold1);
			client1.addCard(titanium1);
			client2.addCard(silver1);
			client4.addCard(titanium2);

			cardRepository.save(gold1);
			cardRepository.save(titanium1);
			cardRepository.save(silver1);
			cardRepository.save(titanium2);


		};
	}

}
