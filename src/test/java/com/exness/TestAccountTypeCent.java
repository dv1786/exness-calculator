package com.exness;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasKey;

public class TestAccountTypeCent {

    @Before
    public void setupRestAssured() {

        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setBaseUri("https://www.exness.com")
                .setBasePath("/api/calculator/calculate/")
                .setAccept("application/json, text/javascript, */*; q=0.01")
                .setContentType(ContentType.JSON)
                .build();
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    public void checkGBPCADcCalculation(){
        RestAssured.given()
                .header("cookie",RandomCookieGenerate.cookie)
                .queryParam("form_type","cent")
                .queryParam("instrument","Forex")
                .queryParam("symbol","GBPCADc")
                .queryParam("lot","0.01")
                .queryParam("leverage","200")
                .queryParam("user_currency","GBC")
                .when().get()
                .then()
                .statusCode(200)
                .body("commission", equalTo(null))
                .body("$", hasKey("margin"))
                .body("$", hasKey("profit"))
                .body("conversion_pairs.USDGBC", Matchers.is(Float.class))
                .body("conversion_pairs.USDCAD", Matchers.is(Float.class))
                .body("conversion_pairs.GBPGBC", Matchers.is(Integer.class))
            //    .body("conversion_pairs[0].XAUUSD", equalTo("1233.635"))
                .body("user_currency", equalTo("GBC"))
                .body("profit_formula2", equalTo("0.00010 x 1000.0 x 0.01 = 0.00 CAD"))
                .body("form_type", equalTo("cent"))
                .extract().response()
                .prettyPrint();

    }

    @Test
    public void checkXAGUSDcCalculation(){
        RestAssured.given()
                .header("cookie",RandomCookieGenerate.cookie)
                .queryParam("form_type","cent")
                .queryParam("instrument","Forex")
                .queryParam("symbol","XAGUSDc")
                .queryParam("lot","0.01")
                .queryParam("leverage","200")
                .queryParam("user_currency","USC")
                .when().get()
                .then()
                .statusCode(200)
                .body("commission", equalTo(null))
                .body("$", hasKey("margin"))
                .body("$", hasKey("profit"))
                .body("conversion_pairs.USCUSD", Matchers.is(Float.class))
                .body("conversion_pairs.XAGUSD", Matchers.is(Float.class))
                .body("conversion_pairs.USDUSC", Matchers.is(Integer.class))
                .body("user_currency", equalTo("USC"))
                .body("profit_formula2", equalTo("0.01000 x 50.0 x 0.01 = 0.01 USD"))
                .body("form_type", equalTo("cent"))
                .extract().response()
                .prettyPrint();

    }

    @Test
    public void checkXAUUSDcCalculation(){
        RestAssured.given()
                .header("cookie",RandomCookieGenerate.cookie)
                .queryParam("form_type","cent")
                .queryParam("instrument","Forex")
                .queryParam("symbol","XAUUSDc")
                .queryParam("lot","0.01")
                .queryParam("leverage","200")
                .queryParam("user_currency","USC")
                .when().get()
                .then()
                .statusCode(200)
                .body("commission", equalTo(null))
                .body("$", hasKey("margin"))
                .body("$", hasKey("profit"))
                .body("conversion_pairs.XAUUSD", Matchers.is(Float.class))
                .body("conversion_pairs.USCUSD", Matchers.is(Float.class))
                .body("conversion_pairs.USDUSC", Matchers.is(Integer.class))
                .body("user_currency", equalTo("USC"))
                .body("profit_formula2", equalTo("0.01000 x 1.0 x 0.01 = 0.00 USD"))
                .body("form_type", equalTo("cent"))
                .extract().response()
                .prettyPrint();

    }

    @Test
    public void symbolDoesNotMatchFormType(){
        RestAssured.given()
                .header("cookie",RandomCookieGenerate.cookie)
                .queryParam("form_type","cent")
                .queryParam("instrument","Forex")
                .queryParam("symbol","GBPCAD")
                .queryParam("lot","0.01")
                .queryParam("leverage","200")
                .queryParam("user_currency","GBC")
                .when().get()
                .then()
                .statusCode(200)
                .body("symbol[0]", equalTo("\"GBPCAD\" не является корректным значением."))
                .extract().response()
                .prettyPrint();

    }

    @Test
    public void leverageLargerThanBorder(){
        RestAssured.given()
                .header("cookie",RandomCookieGenerate.cookie)
                .queryParam("form_type","cent")
                .queryParam("instrument","Forex")
                .queryParam("symbol","AUDCADc")
                .queryParam("lot","0.1")
                .queryParam("leverage","20000")
                .queryParam("user_currency","AUD")
                .when().get()
                .then()
                .statusCode(200)
                .body("leverage[0]", equalTo("\"20000\" не является корректным значением."))
                .extract().response()
                .prettyPrint();

    }






}
