package ru.netology.data;

import lombok.Value;

public class DataHelper {
    private DataHelper() {
    }

    @Value
    public static class AuthInfo {
        private String login;
        private String password;
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    public static AuthInfo getInvalidPass() {
        return new AuthInfo("vasya", "123");
    }

    @Value
    public static class VerificationCode {
        private String login;
        private String code;
    }

    @Value
    public static class Card {
        private String id;
        private String number;
        private String balance;
    }

    @Value
    public static class Transfer {
        private String fromCard;
        private String toCard;
        private String amount;
    }
}
