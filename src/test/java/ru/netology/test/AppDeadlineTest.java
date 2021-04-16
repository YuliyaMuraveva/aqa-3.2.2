package ru.netology.test;

import lombok.val;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import ru.netology.data.ApiHelper;
import ru.netology.data.DataHelper;
import ru.netology.data.DbHelper;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Selenide.open;
import static ru.netology.data.ApiHelper.*;
import static ru.netology.data.DataHelper.getAuthInfo;

public class AppDeadlineTest {

    @AfterAll
    static void tidyUp() { DbHelper.cleanCodes(); }

    @Test
    void shouldTransferToOwnCard() {
        val info = getAuthInfo();
        login(info);
        String token = verification(info.getLogin(), DbHelper.getVerificationCode(info.getLogin()));
        val cards = getCards(token);
//        final int currentBalanceFirstCard = DbHelper.getCardBalance(cards[0].);

    }
}
