package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
class ClientRepositoryTest {


    @Autowired
    private ClientRepository clientRepository;

    @Test
    void findByEmail() {
        Client client = clientRepository.findByEmail("daravena@mindhub.com");
        assertThat(client, notNullValue());
    }

    @Test
    void saveClient(){
        Client client = new Client("Carlos", "Esquivel",
                "carlos@mindhub.com", "1234" , Role.CLIENT);
        clientRepository.save(client);
        Client clientSaved = clientRepository.findByEmail(client.getEmail());
        assertThat(clientSaved, notNullValue());
    }
}