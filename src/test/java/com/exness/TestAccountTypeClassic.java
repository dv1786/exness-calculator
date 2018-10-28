package com.exness;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasKey;

public class TestAccountTypeClassic {


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
    public void checkAUDCADCalculation(){
        RestAssured.given()
                .header("cookie",RandomCookieGenerate.cookie)
                .queryParam("form_type","classic")
                .queryParam("instrument","Forex")
                .queryParam("symbol","AUDCAD")
                .queryParam("lot","0.01")
                .queryParam("leverage","200")
                .queryParam("user_currency","AUD")
                .when().get()
                .then()
                .statusCode(200)
                .body("commission", equalTo(null))
                .body("$", hasKey("margin"))
                .body("$", hasKey("profit"))
                .body("user_currency", equalTo("AUD"))
                .body("profit_formula2", equalTo("0.00010 x 100000.0 x 0.01 = 0.10 CAD"))
                .body("form_type", equalTo("classic"))
                .extract().response()
                .prettyPrint();

    }

    @Test
    public void checkNOKJPYCalculation(){
        RestAssured.given()
                .header("cookie",RandomCookieGenerate.cookie)
                .queryParam("form_type","classic")
                .queryParam("instrument","Forex")
                .queryParam("symbol","NOKJPY")
                .queryParam("lot","100000")
                .queryParam("leverage","200")
                .queryParam("user_currency","JPY")
                .when().get()
                .then()
                .statusCode(200)
                .body("commission", equalTo(null))
                .body("$", hasKey("margin"))
                .body("$", hasKey("profit"))
                .body("user_currency", equalTo("JPY"))
                .body("profit_formula2", equalTo("0.01000 x 100000.0 x 100000.00 = 100000000.00 JPY"))
                .body("form_type", equalTo("classic"))
                .extract().response()
                .prettyPrint();

    }

    @Test
    public void checkBTCUSDCalculation(){
        RestAssured.given()
                .header("cookie",RandomCookieGenerate.cookie)
                .queryParam("form_type","classic")
                .queryParam("instrument","Forex")
                .queryParam("symbol","BTCUSD")
                .queryParam("lot","0.1")
                .queryParam("leverage","2000")
                .queryParam("user_currency","USD")
                .when().get()
                .then()
                .statusCode(200)
                .body("commission", equalTo(null))
                .body("$", hasKey("margin"))
                .body("$", hasKey("profit"))
                .body("user_currency", equalTo("USD"))
                .body("profit_formula2", equalTo("0.10000 x 1.0 x 0.10 = 0.01 USD"))
                .body("form_type", equalTo("classic"))
                .extract().response()
                .prettyPrint();

    }

    @Test
    public void checkXAGUSDCalculation(){
        RestAssured.given()
                .header("cookie",RandomCookieGenerate.cookie)
                // .queryParam("form_type",CalculatorProperties.getProperty("com.exness.account"))
                .queryParam("form_type","classic")
                .queryParam("instrument","Forex")
                .queryParam("symbol","XAGUSD")
                .queryParam("lot","2")
                .queryParam("leverage","2")
                .queryParam("user_currency","USD")
                .when().get()
                .then()
                .statusCode(200)
                .body("commission", equalTo(null))
                .body("$", hasKey("margin"))
                .body("$", hasKey("profit"))
                .body("user_currency", equalTo("USD"))
                .body("profit_formula2", equalTo("0.01000 x 5000.0 x 2.00 = 100.00 USD"))
                .body("form_type", equalTo("classic"))
                .extract().response()
                .prettyPrint();

    }

    @Test
    public void checkXAUEURCalculation(){
        RestAssured.given()
                .header("cookie",RandomCookieGenerate.cookie)
                .queryParam("form_type","classic")
                .queryParam("instrument","Forex")
                .queryParam("symbol","XAUEUR")
                .queryParam("lot","0.1")
                .queryParam("leverage","2")
                .queryParam("user_currency","EUR")
                .when().get()
                .then()
                .statusCode(200)
                .body("commission", equalTo(null))
                .body("$", hasKey("margin"))
                .body("$", hasKey("profit"))
                .body("user_currency", equalTo("EUR"))
                .body("profit_formula2", equalTo("0.01000 x 100.0 x 0.10 = 0.10 EUR"))
                .body("form_type", equalTo("classic"))
                .extract().response()
                .prettyPrint();

    }

    @Test
    public void checkXAUEURCalcAccountInUSD(){
        RestAssured.given()
                .header("cookie",RandomCookieGenerate.cookie)
                .queryParam("form_type","classic")
                .queryParam("instrument","Forex")
                .queryParam("symbol","XAUEUR")
                .queryParam("lot","0.1")
                .queryParam("leverage","100")
                .queryParam("user_currency","USD")
                .when().get()
                .then()
                .statusCode(200)
                .body("commission", equalTo(null))
                .body("$", hasKey("margin"))
                .body("$", hasKey("profit"))
                .body("$", hasKey("conversion_pairs"))
                .body("user_currency", equalTo("USD"))
                .body("conversion_pairs.XAUUSD", Matchers.is(Float.class))
                .body("conversion_pairs.EURUSD", Matchers.is(Float.class))
                //.body("conversion_pairs.XAUUSD", Matchers.equalTo(1233.635f))
                .body("profit_formula2", equalTo("0.01000 x 100.0 x 0.10 = 0.10 EUR"))
                .body("form_type", equalTo("classic"))
                .extract().response()
                .prettyPrint();

    }

    @Test
    public void lotEnotationValue(){
        RestAssured.given()
                .header("cookie",RandomCookieGenerate.cookie)
                .queryParam("form_type","classic")
                .queryParam("instrument","Forex")
                .queryParam("symbol","AUDCAD")
                .queryParam("lot","10e6")
                .queryParam("leverage","200")
                .queryParam("user_currency","AUD")
                .when().get()
                .then()
                .statusCode(200)
                .body("commission", equalTo(null))
                .body("$", hasKey("margin"))
                .body("$", hasKey("profit"))
                .body("user_currency", equalTo("AUD"))
                .body("profit_formula2", equalTo("0.00010 x 100000.0 x 10000000.00 = 100000000.00 CAD"))
                .body("form_type", equalTo("classic"))
                .extract().response()
                .prettyPrint();
    }

    @Test
    public void lotLargerThanBorder(){
        RestAssured.given()
                .header("cookie",RandomCookieGenerate.cookie)
                .queryParam("form_type","classic")
                .queryParam("instrument","Forex")
                .queryParam("symbol","AUDCAD")
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
                .body("form_type", equalTo("classic"))
                .extract().response()
                .prettyPrint();

    }



    @Test
    public void leverageLargerThanBorder(){
        RestAssured.given()
                .header("cookie",RandomCookieGenerate.cookie)
                .queryParam("form_type","classic")
                .queryParam("instrument","Forex")
                .queryParam("symbol","AUDCAD")
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
                .queryParam("form_type","classic")
                .queryParam("instrument","Forex")
                .queryParam("symbol","AUDCAD")
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
                .queryParam("form_type","classic")
                .queryParam("instrument","Forex")
                .queryParam("symbol","AUDCAD")
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
                .queryParam("symbol","AUDCAD")
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
                .queryParam("form_type","classic")
                .queryParam("instrument","")
                .queryParam("symbol","AUDCAD")
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
                .queryParam("form_type","classic")
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
                .queryParam("form_type","classic")
                .queryParam("instrument","Forex")
                .queryParam("symbol","AUDCAD")
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
                .queryParam("form_type","classic")
                .queryParam("instrument","Forex")
                .queryParam("symbol","AUDCAD")
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
                .queryParam("form_type","classic")
                .queryParam("instrument","Forex")
                .queryParam("symbol","AUDCAD")
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
                .queryParam("form_type","classic")
                .queryParam("instrument","Forex")
                .queryParam("symbol","AUDCAD")
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
                .queryParam("form_type","classic")
                .queryParam("instrument","Forex")
                .queryParam("symbol","AUDCAD")
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
                .queryParam("form_type","classic")
                .queryParam("instrument","Forex")
                .queryParam("symbol","AUDCAD")
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
                .queryParam("form_type","classic")
                .queryParam("instrument","Forex")
                .queryParam("symbol","AUDCAD")
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
                .queryParam("form_type","classic")
                .queryParam("instrument","Forex")
                .queryParam("symbol","AUDCAD")
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
                .queryParam("form_type","classic")
                .queryParam("instrument","Forex")
                .queryParam("symbol","AUDCADm")
                .queryParam("lot","3")
                .queryParam("leverage","200")
                .queryParam("user_currency","EUR")
                .when().get()
                .then()
                .statusCode(200)
                .body("symbol[0]", equalTo("\"AUDCADm\" не является корректным значением."))
                .extract().response()
                .prettyPrint();

    }





}
