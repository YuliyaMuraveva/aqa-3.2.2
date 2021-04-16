package ru.netology.data;

import com.google.gson.Gson;
import io.restassured.http.ContentType;
import lombok.val;

import static io.restassured.RestAssured.given;
import static ru.netology.data.DataHelper.*;

public class ApiHelper {

    public static void login(AuthInfo info) {
        given()
                .baseUri("http://localhost:9999/api")
                .contentType(ContentType.JSON)
                .body(new Gson().toJson(new AuthInfo(info.getLogin(), info.getPassword())))
                .when()
                .post("/auth")
                .then()
                .statusCode(200);
    }

    public static String verification(String login, String code) {
        String token =
            given()
                    .baseUri("http://localhost:9999/api")
                    .contentType(ContentType.JSON)
                    .body(new Gson().toJson(new VerificationCode(login, code)))
                    .when()
                    .post("/auth/verification")
                    .then()
                    .statusCode(200)
                    .extract()
                    .path("token");
        return token;
    }

    public static Card[] getCards(String token) {
        val cards =
                given()
                        .baseUri("http://localhost:9999/api")
                        .contentType(ContentType.JSON)
                        .header("Authorization", "Bearer" + token)
                        .when()
                        .get("/cards")
                        .then()
                        .statusCode(200)
                        .extract()
                        .response()
                        .getBody();
        return cards.as(Card[].class);
    }

    public static void transfer(String token, String from, String to, String amount, int statusCode) {
        given()
                .baseUri("http://localhost:9999/api")
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer" + token)
                .body(new Gson().toJson(new Transfer(from, to, amount)))
                .when()
                .post("/transfer")
                .then()
                .statusCode(statusCode);
    }
}
