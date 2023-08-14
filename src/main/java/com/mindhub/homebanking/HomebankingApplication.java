package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.mindhub.homebanking.models.TransactionType.*;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository,
									  TransactionRepository transactionRepository, LoanRepository loanRepository,
									  ClientLoanRepository clientLoanRepository){
		return (args) -> {
			Client client1 = new Client("Melba", "Morel", "melba@mindhub.com");
			Client client2 = new Client("Diego", "Aravena", "daravena@mindhub.com");
			Client client3 = new Client("Enzo", "Aravena", "earavena@mindhub.com");
			Client client4 = new Client("Sandra", "Lazaro", "sandra.lazaro@mindhub.com");
			clientRepository.save(client1);
			clientRepository.save(client2);
			clientRepository.save(client3);
			clientRepository.save(client4);

			Account account1 = new Account("VIN001", LocalDate.now(), 5000 );
			Account account2 = new Account("VIN002", LocalDate.now().plusDays(1), 7000 );
			Account account3 = new Account("VIN003", LocalDate.now().plusDays(5), 10000 );

			client1.addAccount(account1);
			client1.addAccount(account2);
			client2.addAccount(account3);

			accountRepository.save(account1);
			accountRepository.save(account2);
			accountRepository.save(account3);

			Transaction transaction1 = new Transaction(20000.50, CREDIT,
					"Deposito por venta",LocalDateTime.now());
			Transaction transaction2 = new Transaction(-10000, DEBIT,
					"Por pago de cuenta de luz",LocalDateTime.now());
			Transaction transaction3 = new Transaction(13000, CREDIT,
					"Depósito en efectivo",LocalDateTime.now());
			Transaction transaction4 = new Transaction(-1000, DEBIT,
					"Por carga de tarjeta sube",LocalDateTime.now());

			account1.addTransaction(transaction1);
			account1.addTransaction(transaction2);
			account3.addTransaction(transaction3);
			account3.addTransaction(transaction4);

			transactionRepository.save(transaction1);
			transactionRepository.save(transaction2);
			transactionRepository.save(transaction3);
			transactionRepository.save(transaction4);

			Loan prestamo1 = new Loan("Hipotecario", 500000, List.of(12, 24, 36, 48, 60));
			Loan prestamo2 = new Loan("Personal", 100000, List.of(6, 12, 24));
			Loan prestamo3 = new Loan("Automotriz", 300000, List.of(6, 12, 24, 36));

			loanRepository.save(prestamo1);
			loanRepository.save(prestamo2);
			loanRepository.save(prestamo3);

			ClientLoan hipotecario1 = new ClientLoan(400000, 60, client1, prestamo1);
			ClientLoan personal1 = new ClientLoan(50000, 12, client1, prestamo2);

			ClientLoan personal2 = new ClientLoan(100000, 24, client3, prestamo2);
			ClientLoan automotriz1 = new ClientLoan(200000, 36, client3, prestamo3);

			client1.addLoan(hipotecario1);
			client1.addLoan(personal1);

			client3.addLoan(personal2);
			client3.addLoan(automotriz1);

			prestamo1.addLoan(hipotecario1);
			prestamo2.addLoan(personal1);
			prestamo2.addLoan(personal2);
			prestamo3.addLoan(automotriz1);

			clientLoanRepository.save(hipotecario1);
			clientLoanRepository.save(personal1);
			clientLoanRepository.save(personal2);
			clientLoanRepository.save(automotriz1);


		};
	}

}
