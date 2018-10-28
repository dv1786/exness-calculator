package com.exness;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasKey;

public class TestAccountTypeECN {

    @Before
    public void setupRestAssured() {

        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setBaseUri("https://www.exness.com")
                .setBasePath("/api/calculator/calculate/")
                .setAccept("application/json, text/javascript, */*; q=0.01")
                .setContentType(ContentType.JSON)
                // .addCookie(RandomCookieGenerate.cookie)
                //  .log(LogDetail.ALL)
                .build();
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    public void checkAUDCADeCalculation(){
        RestAssured.given()
                .header("cookie",RandomCookieGenerate.cookie)
                .queryParam("form_type","ECN")
                .queryParam("instrument","Forex")
                .queryParam("symbol","AUDCADe")
                .queryParam("lot","0.01")
                .queryParam("leverage","200")
                .queryParam("user_currency","USD")
                .when().get()
                .then()
                .statusCode(200)
                .body("commission", equalTo(null))
                .body("$", hasKey("margin"))
                .body("$", hasKey("profit"))
                .body("conversion_pairs.AUDUSD", Matchers.is(Float.class))
                .body("conversion_pairs.USDCAD", Matchers.is(Float.class))
                .body("user_currency", equalTo("USD"))
                .body("profit_formula2", equalTo("0.00010 x 100000.0 x 0.01 = 0.10 CAD"))
                .body("form_type", equalTo("ECN"))
                .extract().response()
                .prettyPrint();

    }

    @Test
    public void checkNZDJPYeCalculation(){
        RestAssured.given()
                .header("cookie",RandomCookieGenerate.cookie)
                .queryParam("form_type","ECN")
                .queryParam("instrument","Forex")
                .queryParam("symbol","NZDJPYe")
                .queryParam("lot","0.01")
                .queryParam("leverage","200")
                .queryParam("user_currency","AZN")
                .when().get()
                .then()
                .statusCode(200)
                .body("commission", equalTo(null))
                .body("$", hasKey("margin"))
                .body("$", hasKey("profit"))
                .body("conversion_pairs.NZDUSD", Matchers.is(Float.class))
                .body("conversion_pairs.USDJPY", Matchers.is(Float.class))
                .body("conversion_pairs.USDAZN", Matchers.is(Float.class))
                .body("user_currency", equalTo("AZN"))
                .body("profit_formula2", equalTo("0.01000 x 100000.0 x 0.01 = 10.00 JPY"))
                .body("form_type", equalTo("ECN"))
                .extract().response()
                .prettyPrint();

    }

    @Test
    public void lotEnotationValue(){
        RestAssured.given()
                .header("cookie",RandomCookieGenerate.cookie)
                .queryParam("form_type","ECN")
                .queryParam("instrument","Forex")
                .queryParam("symbol","AUDCADe")
                .queryParam("lot","10e6")
                .queryParam("leverage","2")
                .queryParam("user_currency","AUD")
                .when().get()
                .then()
                .statusCode(200)
                .body("commission", equalTo(null))
                .body("$", hasKey("margin"))
                .body("$", hasKey("profit"))
                .body("user_currency", equalTo("AUD"))
                .body("profit_formula2", equalTo("0.00010 x 100000.0 x 10000000.00 = 100000000.00 CAD"))
                .body("form_type", equalTo("ECN"))
                .extract().response()
                .prettyPrint();
    }

    @Test
    public void lotLargerThanBorder(){
        RestAssured.given()
                .header("cookie",RandomCookieGenerate.cookie)
                .queryParam("form_type","ECN")
                .queryParam("instrument","Forex")
                .queryParam("symbol","AUDCADe")
                .queryParam("lot","3000000")
                .queryParam("leverage","200")
                .queryParam("user_currency","AUD")
                .when().get()
                .then()
                .statusCode(200)
                .body("commission", equalTo(null))
                .body("$", hasKey("margin"))
                .body("$", hasKey("profit"))
                .body("user_currency", equalTo("AUD"))
                .body("profit_formula2", equalTo("0.00010 x 100000.0 x 3000000.00 = 30000000.00 CAD"))
                .body("form_type", equalTo("ECN"))
                .extract().response()
                .prettyPrint();

    }



    @Test
    public void leverageLargerThanBorder(){
        RestAssured.given()
                .header("cookie",RandomCookieGenerate.cookie)
                .queryParam("form_type","ECN")
                .queryParam("instrument","Forex")
                .queryParam("symbol","AUDCADe")
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

    @Test
    public void leverageLessThanBorder(){
        RestAssured.given()
                .header("cookie",RandomCookieGenerate.cookie)
                .queryParam("form_type","ECN")
                .queryParam("instrument","Forex")
                .queryParam("symbol","AUDCADe")
                .queryParam("lot","0.1")
                .queryParam("leverage","1")
                .queryParam("user_currency","AUD")
                .when().get()
                .then()
                .statusCode(200)
                .body("leverage[0]", equalTo("\"1\" не является корректным значением."))
                .extract().response()
                .prettyPrint();

    }

    @Test
    public void leverageIncorrectValue(){
        RestAssured.given()
                .header("cookie",RandomCookieGenerate.cookie)
                .queryParam("form_type","ECN")
                .queryParam("instrument","Forex")
                .queryParam("symbol","AUDCADe")
                .queryParam("lot","0.1")
                .queryParam("leverage","-200")
                .queryParam("user_currency","AUD")
                .when().get()
                .then()
                .statusCode(200)
                .body("leverage[0]", equalTo("\"-200\" не является корректным значением."))
                .extract().response()
                .prettyPrint();

    }

    @Test
    public void queryWithoutFormTypeValue(){
        RestAssured.given()
                .header("cookie",RandomCookieGenerate.cookie)
                .queryParam("form_type","")
                .queryParam("instrument","Forex")
                .queryParam("symbol","AUDCADe")
                .queryParam("lot","0.1")
                .queryParam("leverage","200")
                .queryParam("user_currency","AUD")
                .when().get()
                .then()
                .statusCode(200)
                .body("form_type[0]", equalTo("Поле обязательно для заполнения."))
                .extract().response()
                .prettyPrint();

    }

    @Test
    public void queryWithoutInstrumentValue(){
        RestAssured.given()
                .header("cookie",RandomCookieGenerate.cookie)
                .queryParam("form_type","ECN")
                .queryParam("instrument","")
                .queryParam("symbol","AUDCADe")
                .queryParam("lot","0.1")
                .queryParam("leverage","200")
                .queryParam("user_currency","AUD")
                .when().get()
                .then()
                .statusCode(200)
                .body("instrument[0]", equalTo("Поле обязательно для заполнения."))
                .extract().response()
                .prettyPrint();

    }

    @Test
    public void queryWithoutSymbolValue(){
        RestAssured.given()
                .header("cookie",RandomCookieGenerate.cookie)
                .queryParam("form_type","ECN")
                .queryParam("instrument","Forex")
                .queryParam("symbol","")
                .queryParam("lot","0.1")
                .queryParam("leverage","200")
                .queryParam("user_currency","AUD")
                .when().get()
                .then()
                .statusCode(200)
                .body("symbol[0]", equalTo("Поле обязательно для заполнения."))
                .extract().response()
                .prettyPrint();

    }

    @Test
    public void queryWithoutLotValue(){
        RestAssured.given()
                .header("cookie",RandomCookieGenerate.cookie)
                .queryParam("form_type","ECN")
                .queryParam("instrument","Forex")
                .queryParam("symbol","AUDCADe")
                .queryParam("lot","")
                .queryParam("leverage","200")
                .queryParam("user_currency","AUD")
                .when().get()
                .then()
                .statusCode(200)
                .body("lot[0]", equalTo("Поле обязательно для заполнения."))
                .extract().response()
                .prettyPrint();

    }

    @Test
    public void queryWithoutLeverageValue(){
        RestAssured.given()
                .header("cookie",RandomCookieGenerate.cookie)
                .queryParam("form_type","ECN")
                .queryParam("instrument","Forex")
                .queryParam("symbol","AUDCADe")
                .queryParam("lot","0.1")
                .queryParam("leverage","")
                .queryParam("user_currency","AUD")
                .when().get()
                .then()
                .statusCode(200)
                .body("leverage[0]", equalTo("Поле обязательно для заполнения."))
                .extract().response()
                .prettyPrint();

    }

    @Test
    public void queryWithoutUserCurrencyValue(){
        RestAssured.given()
                .header("cookie",RandomCookieGenerate.cookie)
                .queryParam("form_type","ECN")
                .queryParam("instrument","Forex")
                .queryParam("symbol","AUDCADe")
                .queryParam("lot","0.1")
                .queryParam("leverage","200")
                .queryParam("user_currency","")
                .when().get()
                .then()
                .statusCode(200)
                .body("user_currency[0]", equalTo("Поле обязательно для заполнения."))
                .extract().response()
                .prettyPrint();

    }


    @Test
    // Валюта счета в цифровом виде ISO 4217 , EUR = 978
    public void userCurrencyOnISOValue(){
        RestAssured.given()
                .header("cookie",RandomCookieGenerate.cookie)
                .queryParam("form_type","ECN")
                .queryParam("instrument","Forex")
                .queryParam("symbol","AUDCADe")
                .queryParam("lot","3")
                .queryParam("leverage","200")
                .queryParam("user_currency","978")
                .when().get()
                .then()
                .statusCode(200)
                .body("user_currency[0]", equalTo( "\"978\" не является корректным значением."))
                .extract().response()
                .prettyPrint();

    }

    @Test
    public void userCurrencyOnLowerCaseValue(){
        RestAssured.given()
                .header("cookie",RandomCookieGenerate.cookie)
                .queryParam("form_type","ECN")
                .queryParam("instrument","Forex")
                .queryParam("symbol","AUDCADe")
                .queryParam("lot","3")
                .queryParam("leverage","200")
                .queryParam("user_currency","eur")
                .when().get()
                .then()
                .statusCode(200)
                .body("user_currency[0]", equalTo( "\"eur\" не является корректным значением."))
                .extract().response()
                .prettyPrint();

    }

    @Test
    //EURO
    public void userCurrencyFullNameValue(){
        RestAssured.given()
                .header("cookie",RandomCookieGenerate.cookie)
                .queryParam("form_type","ECN")
                .queryParam("instrument","Forex")
                .queryParam("symbol","AUDCADe")
                .queryParam("lot","3")
                .queryParam("leverage","200")
                .queryParam("user_currency","EURO")
                .when().get()
                .then()
                .statusCode(200)
                .body("user_currency[0]", equalTo( "\"EURO\" не является корректным значением."))
                .extract().response()
                .prettyPrint();

    }



    @Test
    public void lotLessThanBorder(){
        RestAssured.given()
                .header("cookie",RandomCookieGenerate.cookie)
                .queryParam("form_type","ECN")
                .queryParam("instrument","Forex")
                .queryParam("symbol","AUDCADe")
                .queryParam("lot","0.0001")
                .queryParam("leverage","200")
                .queryParam("user_currency","AUD")
                .when().get()
                .then()
                .statusCode(200)
                .body("lot[0]", equalTo("Убедитесь что значение больше или равно 0.01."))
                .extract().response()
                .prettyPrint();

    }


    @Test
    public void lotIncorrectValue(){
        RestAssured.given()
                .header("cookie",RandomCookieGenerate.cookie)
                .queryParam("form_type","ECN")
                .queryParam("instrument","Forex")
                .queryParam("symbol","AUDCADe")
                .queryParam("lot","-1")
                .queryParam("leverage","200")
                .queryParam("user_currency","AUD")
                .when().get()
                .then()
                .statusCode(200)
                .body("lot[0]", equalTo("Убедитесь что значение больше или равно 0.01."))
                .extract().response()
                .prettyPrint();
    }


    @Test
    //symbolDoesNotMatchAccountType ?
    public void symbolDoesNotMatchFormType(){
        RestAssured.given()
                .header("cookie",RandomCookieGenerate.cookie)
                .queryParam("form_type","ECN")
                .queryParam("instrument","Forex")
                .queryParam("symbol","AUDCADc")
                .queryParam("lot","3")
                .queryParam("leverage","200")
                .queryParam("user_currency","EUR")
                .when().get()
                .then()
                .statusCode(200)
                .body("symbol[0]", equalTo("\"AUDCADc\" не является корректным значением."))
                .extract().response()
                .prettyPrint();

    }


}
