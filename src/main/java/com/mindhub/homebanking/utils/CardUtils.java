package com.mindhub.homebanking.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


public final class CardUtils {

    private CardUtils() {
    }

    public  static int numberRandom(int max, int min) {
        return (int) (Math.random() * (max - min) + min);
    }

    public static  int getCVV(){
        return numberRandom(111,999);
    };

    public static String numberToString(int number) {
        return String.valueOf(number);
    }

    public static String getCardNumber() {
        String account = "";
        String digitos = "";
        for (int i = 0; i < 4; i++) {
            if (i == 3) {
                account = account + digitos;
            }
            if (i < 3) {
                digitos = numberToString(numberRandom(9999, 1111));
                account += (digitos + "-");
            }
        }
        return account;
    }
}
