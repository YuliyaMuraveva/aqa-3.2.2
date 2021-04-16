package ru.netology.data;

import lombok.SneakyThrows;
import lombok.val;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

public class DbHelper {
    private final static QueryRunner runner = new QueryRunner();
    private final static Connection conn = getConnection();

    @SneakyThrows
    private static Connection getConnection() {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass");
    }

    @SneakyThrows
    public static String getVerificationCode(String user) {
        val code = "SELECT code FROM auth_codes INNER JOIN users ON auth_codes.user_id = users.id WHERE login = \"" + user + "\" ORDER BY created DESC";
        return runner.query(conn, code, new ScalarHandler<>());
    }
//
//    @SneakyThrows
//    public static int getFirstCardBalance(String login) {
//        val query = "SELECT balance_in_kopecks FROM cards INNER JOIN users ON cards.user_id = users.id  WHERE user_id = \"" + login + "\"";
//        List balanceInKopecks = runner.query(conn, query, new BeanListHandler<>());
//        int balanceInRubles = balanceInKopecks / 100;
//        return balanceInRubles;
//    }


    @SneakyThrows
    public static void cleanCodes() {
        runner.execute(conn, "DELETE FROM auth_codes");
    }
}
