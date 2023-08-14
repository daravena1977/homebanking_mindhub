package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private String name;
    private double maxAmount;

    @ElementCollection
    @Column(name = "payment")
    private List<Integer> payments = new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "loan")
    private Set<ClientLoan> clientLoans = new HashSet<>();

    public Loan() {
    }

    public Loan(String name, double maxAmount, List<Integer> payments) {
        this.name = name;
        this.maxAmount = maxAmount;
        this.payments = payments;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(double maxAmount) {
        this.maxAmount = maxAmount;
    }

    public List<Integer> getPayments() {
        return payments;
    }

    public void setPayments(List<Integer> payments) {
        this.payments = payments;
    }

    public Set<ClientLoan> getLoans() {
        return clientLoans;
    }

    public void addLoan(ClientLoan clientLoan){
        clientLoan.setLoan(this);
        clientLoans.add(clientLoan);
    }

    public List<Client> getClients(){
        return clientLoans.stream().map(loan -> loan.getClient()).collect(Collectors.toList());
    }
}
