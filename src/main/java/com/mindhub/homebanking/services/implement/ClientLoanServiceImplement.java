package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.dtos.ClientLoanDTO;
import com.mindhub.homebanking.models.ClientLoan;
import com.mindhub.homebanking.repositories.ClientLoanRepository;
import com.mindhub.homebanking.services.ClientLoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientLoanServiceImplement implements ClientLoanService {
    @Autowired
    private ClientLoanRepository clientLoanRepository;

    @Override
    public ClientLoan findById(Long id) {
        return clientLoanRepository.findById(id).orElse(null);
    }

    @Override
    public void save(ClientLoan clientLoan) {
        clientLoanRepository.save(clientLoan);
    }

    @Override
    public void edit(ClientLoan clientLoan) {
        clientLoanRepository.save(clientLoan);
    }

    @Override
    public List<ClientLoan> getClientLoans() {
        return clientLoanRepository.findAll();
    }

    @Override
    public List<ClientLoanDTO> getClientLoansDTO(List<ClientLoan> clientLoans) {
        return clientLoans
                .stream()
                .map(clientLoan -> new ClientLoanDTO(clientLoan))
                .collect(Collectors.toList());
    }

    @Override
    public ClientLoanDTO getClientLoanDTO(Long id) {
        return new ClientLoanDTO(clientLoanRepository.findById(id).orElse(null));
    }

    @Override
    public void deleteClientLoan(Long id) {
        clientLoanRepository.deleteById(id);
    }

    @Override
    public void deleteClientLoan(ClientLoan clientLoan) {
        clientLoanRepository.delete(clientLoan);
    }
}
