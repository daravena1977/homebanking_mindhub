package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;


import java.util.List;

public interface AccountService {

    Account findById(Long id);

    void saveAccount(Account account);

    void editAccount(Account account);

    List<Account> getAccounts();

    List<AccountDTO> getAccountsDTO(List<Account> accounts);

    AccountDTO getAccountDTO(Long id);

    void deleteAccount(Long id);

    void deleteAccount(Account account);

    Account generateNewAccount();

    boolean existsByNumber(String number);

    Account findByNumber(String number);

    boolean existsByNumberAndClient(String number, Client client);
}
