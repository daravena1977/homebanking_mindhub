package com.mindhub.homebanking.dtos;

import java.util.ArrayList;
import java.util.List;

public class NewLoanDTO {
    private String name;
    private Double maxAmount;

    private List<Integer> payments = new ArrayList<>();

    public String getName() {
        return name;
    }

    public Double getMaxAmount() {
        return maxAmount;
    }

    public List<Integer> getPayments() {
        return payments;
    }
}
