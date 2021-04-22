package ru.netology.test;

import lombok.val;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import ru.netology.data.RestApiHelper;
import ru.netology.data.DataHelper;
import ru.netology.data.DbHelper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.data.RestApiHelper.*;
import static ru.netology.data.RestApiHelper.login;
import static ru.netology.data.DataHelper.getAuthInfo;

public class AppDeadlineTest {

    @AfterAll
    static void tidyUp() { DbHelper.cleanCodes(); }

    @Test
    void shouldTransferToOwnCard() {
        val info = getAuthInfo();
        login(info);
        String token = verification(info.getLogin(), DbHelper.getVerificationCode(info.getLogin()));
        val transferInfo = DataHelper.transferFromSecondToFirst();
        val expectedFirstCardBalance = getFirstCardBalance(token) + 5000;
        val expectedSecondCardBalance = getSecondCardBalance(token) - 5000;
        RestApiHelper.transfer(token, transferInfo.getFrom(), transferInfo.getTo(), transferInfo.getAmount(), 200);
        assertEquals(expectedFirstCardBalance, getFirstCardBalance(token));
        assertEquals(expectedSecondCardBalance, getSecondCardBalance(token));
    }

    @Test
    void shouldTransferToAnotherUser() {
        val info = getAuthInfo();
        login(info);
        String token = verification(info.getLogin(), DbHelper.getVerificationCode(info.getLogin()));
        val expectedFirstCardBalance = getFirstCardBalance(token) - 5000;
        val transferInfo = DataHelper.transferFromFirstToAnotherUser();
        RestApiHelper.transfer(token, transferInfo.getFrom(), transferInfo.getTo(), transferInfo.getAmount(), 200);
        assertEquals(expectedFirstCardBalance, getFirstCardBalance(token));
    }

    @Test
    void shouldNotTransferFromAnotherUser() {
        val info = getAuthInfo();
        login(info);
        String token = verification(info.getLogin(), DbHelper.getVerificationCode(info.getLogin()));
        val expectedFirstCardBalance = getFirstCardBalance(token);
        val transferInfo = DataHelper.transferFromAnotherUserToFirst();
        RestApiHelper.transfer(token, transferInfo.getFrom(), transferInfo.getTo(), transferInfo.getAmount(), 400);
        assertEquals(expectedFirstCardBalance, getFirstCardBalance(token));
    }

    @Test
    void shouldNotTransferOutOfLimit() {
        val info = getAuthInfo();
        login(info);
        String token = verification(info.getLogin(), DbHelper.getVerificationCode(info.getLogin()));
        val transferInfo = DataHelper.transferFromSecondToFirst();
        val expectedFirstCardBalance = getFirstCardBalance(token);
        val expectedSecondCardBalance = getSecondCardBalance(token);
        RestApiHelper.transfer(token, transferInfo.getFrom(), transferInfo.getTo(), "20000", 400);
        assertEquals(expectedFirstCardBalance, getFirstCardBalance(token));
        assertEquals(expectedSecondCardBalance, getSecondCardBalance(token));
    }
}
