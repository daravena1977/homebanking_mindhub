package com.mindhub.homebanking.utils;

import java.util.Random;

public final class CardUtils {

    private CardUtils() {
    }

    public static String generateCardNumber() {
        Random random = new Random();
        StringBuilder cardNumber = new StringBuilder();

            for (int i = 0; i < 3; i++ ){
                int randomInt = 1000 + random.nextInt(10000 - 1000);
                cardNumber.append(String.valueOf(randomInt).concat("-"));
            }
            int randomInt = 1000 + random.nextInt(10000 - 1000);
            cardNumber.append(String.valueOf(randomInt));

            return cardNumber.toString();
    }

    public static int generateCvvNumber(){
        Random random = new Random();
        return 100 + random.nextInt(1000 - 100);
    }
}
