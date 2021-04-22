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

    @Value
    public static class VerificationCode {
        private String login;
        private String code;
    }

    @Value
    public static class Card {
        private String id;
        private String number;
        private int balance;
    }

    @Value
    public static class Cards {
        Card[] fistCard;
        Card[] secondCard;
    }

    @Value
    public static class Transfer {
        private String from;
        private String to;
        private String amount;
    }

    public static Transfer transferFromSecondToFirst() {
        return new Transfer ("5559 0000 0000 0002", "5559 0000 0000 0001", "5000");
    }

    public static Transfer transferFromFirstToAnotherUser() {
        return new Transfer ("5559 0000 0000 0001", "5559 0000 0000 0008", "5000");
    }

    public static Transfer transferFromAnotherUserToFirst() {
        return new Transfer ("5559 0000 0000 0008", "5559 0000 0000 0001", "5000");
    }
}
