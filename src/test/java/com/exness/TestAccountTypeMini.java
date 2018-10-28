package com.exness;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.*;


public class TestAccountTypeMini {

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
    public void checkAUDCADmCalculation(){
        RestAssured.given()
                .header("cookie",RandomCookieGenerate.cookie)
                .queryParam("form_type","mini")
                .queryParam("instrument","Forex")
                .queryParam("symbol","AUDCADm")
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
                .body("form_type", equalTo("mini"))
                .extract().response()
                .prettyPrint();

    }

    @Test
    public void checkNOKJPYmCalculation(){
        RestAssured.given()
                .header("cookie",RandomCookieGenerate.cookie)
                .queryParam("form_type","mini")
                .queryParam("instrument","Forex")
                .queryParam("symbol","NOKJPYm")
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
                .body("form_type", equalTo("mini"))
                .extract().response()
                .prettyPrint();

    }

    @Test
    public void checkBTCUSDmCalculation(){
        RestAssured.given()
                .header("cookie",RandomCookieGenerate.cookie)
                .queryParam("form_type","mini")
                .queryParam("instrument","Forex")
                .queryParam("symbol","BTCUSDm")
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
                .body("form_type", equalTo("mini"))
                .extract().response()
                .prettyPrint();

    }

    @Test
    public void checkXAGUSDmCalculation(){
        RestAssured.given()
                .header("cookie",RandomCookieGenerate.cookie)
                .queryParam("form_type","mini")
                .queryParam("instrument","Forex")
                .queryParam("symbol","XAGUSDm")
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
                .body("form_type", equalTo("mini"))
                .extract().response()
                .prettyPrint();

    }

    @Test
    public void checkXAUEURmCalculation(){
        RestAssured.given()
                .header("cookie",RandomCookieGenerate.cookie)
                .queryParam("form_type","mini")
                .queryParam("instrument","Forex")
                .queryParam("symbol","XAUEURm")
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
                .body("form_type", equalTo("mini"))
                .extract().response()
                .prettyPrint();

    }

    @Test
    public void checkXAUEURmCalcAccountInUSD(){
        RestAssured.given()
                .header("cookie",RandomCookieGenerate.cookie)
                .queryParam("form_type","mini")
                .queryParam("instrument","Forex")
                .queryParam("symbol","XAUEURm")
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
           //     .body("conversion_pairs", hasValue("{EURUSD=1.1403, XAUUSD=1233.635}"))
                .body("conversion_pairs[0].XAUUSD", equalTo("1233.635"))
                .body("profit_formula2", equalTo("0.01000 x 100.0 x 0.10 = 0.10 EUR"))
                .body("form_type", equalTo("mini"))
                .extract().response()
                .prettyPrint();

    }

    @Test
    public void lotEnotationValue(){
        //{"form_type":["Поле обязательно для заполнения."],"lot":["Поле обязательно для заполнения."],"leverage":["Поле обязательно для заполнения."],
        // "instrument":["Поле обязательно для заполнения."],"symbol":["Поле обязательно для заполнения."],"user_currency":["Поле обязательно для заполнения."]}
        RestAssured.given()
                .header("cookie",RandomCookieGenerate.cookie)
                .queryParam("form_type","mini")
                .queryParam("instrument","Forex")
                .queryParam("symbol","AUDCADm")
                .queryParam("lot","10e6")
                .queryParam("leverage","200")
                .queryParam("user_currency","AUD")
                .when().get()
                .then()
                .statusCode(200)
                .body("form_type", equalTo("mini"))
                .extract().response()
                .prettyPrint();
    }

    @Test
    public void lotLargerThanBorder(){
        //{"form_type":["Поле обязательно для заполнения."],"lot":["Поле обязательно для заполнения."],"leverage":["Поле обязательно для заполнения."],
        // "instrument":["Поле обязательно для заполнения."],"symbol":["Поле обязательно для заполнения."],"user_currency":["Поле обязательно для заполнения."]}
        RestAssured.given()
                .header("cookie",RandomCookieGenerate.cookie)
                .queryParam("form_type","mini")
                .queryParam("instrument","Forex")
                .queryParam("symbol","AUDCADm")
                .queryParam("lot","3000000")
                .queryParam("leverage","200")
                .queryParam("user_currency","AUD")
                .when().get()
                .then()
                .statusCode(200)
                .body("form_type", equalTo("mini"))
                .extract().response()
                .prettyPrint();

    }



    @Test
    public void leverageLargerThanBorder(){
        RestAssured.given()
                .header("cookie",RandomCookieGenerate.cookie)
                .queryParam("form_type","mini")
                .queryParam("instrument","Forex")
                .queryParam("symbol","AUDCADm")
                .queryParam("lot","0.1")
                .queryParam("leverage","20000")
                .queryParam("user_currency","AUD")
                .when().get()
                .then()
                .statusCode(200)
                .body("commission", equalTo(null))
                .body("$", hasKey("margin"))
                .body("$", hasKey("profit"))
                .body("user_currency", equalTo("AUD"))
                .body("volume_formula2", equalTo("0.10 x 100000 = 10000.00 AUD"))
                .body("form_type", equalTo("mini"))
                .extract().response()
                .prettyPrint();

    }

    @Test
    public void leverageLessThanBorder(){
        RestAssured.given()
                .header("cookie",RandomCookieGenerate.cookie)
                .queryParam("form_type","mini")
                .queryParam("instrument","Forex")
                .queryParam("symbol","AUDCADm")
                .queryParam("lot","0.1")
                .queryParam("leverage","1")
                .queryParam("user_currency","AUD")
                .when().get()
                .then()
                .statusCode(200)
                .body("commission", equalTo(null))
                .body("$", hasKey("margin"))
                .body("$", hasKey("profit"))
                .body("user_currency", equalTo("AUD"))
                .body("volume_formula2", equalTo("0.10 x 100000 = 10000.00 AUD"))
                .body("form_type", equalTo("mini"))
                .extract().response()
                .prettyPrint();

    }

    @Test
    public void leverageIncorrectValue(){
        RestAssured.given()
                .header("cookie",RandomCookieGenerate.cookie)
                .queryParam("form_type","mini")
                .queryParam("instrument","Forex")
                .queryParam("symbol","AUDCADm")
                .queryParam("lot","0.1")
                .queryParam("leverage","-200")
                .queryParam("user_currency","AUD")
                .when().get()
                .then()
                .statusCode(200)
                .body("commission", equalTo(null))
                .body("$", hasKey("margin"))
                .body("$", hasKey("profit"))
                .body("user_currency", equalTo("AUD"))
                .body("volume_formula2", equalTo("0.10 x 100000 = 10000.00 AUD"))
                .body("form_type", equalTo("mini"))
                .extract().response()
                .prettyPrint();

    }

    @Test
    public void queryWithoutFormTypeValue(){
        //{"form_type":["Поле обязательно для заполнения."],"lot":["Поле обязательно для заполнения."],"leverage":["Поле обязательно для заполнения."],
        // "instrument":["Поле обязательно для заполнения."],"symbol":["Поле обязательно для заполнения."],"user_currency":["Поле обязательно для заполнения."]}
        RestAssured.given()
                .header("cookie",RandomCookieGenerate.cookie)
                .queryParam("form_type","")
                .queryParam("instrument","Forex")
                .queryParam("symbol","AUDCADm")
                .queryParam("lot","0.1")
                .queryParam("leverage","200")
                .queryParam("user_currency","AUD")
                .when().get()
                .then()
                .statusCode(200)
                .body("form_type", equalTo("mini"))
                .extract().response()
                .prettyPrint();

    }

    @Test
    public void queryWithoutInstrumentValue(){
        //{"form_type":["Поле обязательно для заполнения."],"lot":["Поле обязательно для заполнения."],"leverage":["Поле обязательно для заполнения."],
        // "instrument":["Поле обязательно для заполнения."],"symbol":["Поле обязательно для заполнения."],"user_currency":["Поле обязательно для заполнения."]}
        RestAssured.given()
                .header("cookie",RandomCookieGenerate.cookie)
                .queryParam("form_type","mini")
                .queryParam("instrument","")
                .queryParam("symbol","AUDCADm")
                .queryParam("lot","0.1")
                .queryParam("leverage","200")
                .queryParam("user_currency","AUD")
                .when().get()
                .then()
                .statusCode(200)
                .body("form_type", equalTo("mini"))
                .extract().response()
                .prettyPrint();

    }

    @Test
    public void queryWithoutSymbolValue(){
        //{"form_type":["Поле обязательно для заполнения."],"lot":["Поле обязательно для заполнения."],"leverage":["Поле обязательно для заполнения."],
        // "instrument":["Поле обязательно для заполнения."],"symbol":["Поле обязательно для заполнения."],"user_currency":["Поле обязательно для заполнения."]}
        RestAssured.given()
                .header("cookie",RandomCookieGenerate.cookie)
                .queryParam("form_type","mini")
                .queryParam("instrument","Forex")
                .queryParam("symbol","")
                .queryParam("lot","0.1")
                .queryParam("leverage","200")
                .queryParam("user_currency","AUD")
                .when().get()
                .then()
                .statusCode(200)
                .body("form_type", equalTo("mini"))
                .extract().response()
                .prettyPrint();

    }

    @Test
    public void queryWithoutLotValue(){
        //{"form_type":["Поле обязательно для заполнения."],"lot":["Поле обязательно для заполнения."],"leverage":["Поле обязательно для заполнения."],
        // "instrument":["Поле обязательно для заполнения."],"symbol":["Поле обязательно для заполнения."],"user_currency":["Поле обязательно для заполнения."]}
        RestAssured.given()
                .header("cookie",RandomCookieGenerate.cookie)
                .queryParam("form_type","mini")
                .queryParam("instrument","Forex")
                .queryParam("symbol","AUDCADm")
                .queryParam("lot","")
                .queryParam("leverage","200")
                .queryParam("user_currency","AUD")
                .when().get()
                .then()
                .statusCode(200)
                .body("form_type", equalTo("mini"))
                .extract().response()
                .prettyPrint();

    }

    @Test
    public void queryWithoutLeverageValue(){
        //{"form_type":["Поле обязательно для заполнения."],"lot":["Поле обязательно для заполнения."],"leverage":["Поле обязательно для заполнения."],
        // "instrument":["Поле обязательно для заполнения."],"symbol":["Поле обязательно для заполнения."],"user_currency":["Поле обязательно для заполнения."]}
        RestAssured.given()
                .header("cookie",RandomCookieGenerate.cookie)
                .queryParam("form_type","mini")
                .queryParam("instrument","Forex")
                .queryParam("symbol","AUDCADm")
                .queryParam("lot","0.1")
                .queryParam("leverage","")
                .queryParam("user_currency","AUD")
                .when().get()
                .then()
                .statusCode(200)
                .body("form_type", equalTo("mini"))
                .extract().response()
                .prettyPrint();

    }

    @Test
    public void queryWithoutUserCurrencyValue(){
        //{"form_type":["Поле обязательно для заполнения."],"lot":["Поле обязательно для заполнения."],"leverage":["Поле обязательно для заполнения."],
        // "instrument":["Поле обязательно для заполнения."],"symbol":["Поле обязательно для заполнения."],"user_currency":["Поле обязательно для заполнения."]}
        RestAssured.given()
                .header("cookie",RandomCookieGenerate.cookie)
                .queryParam("form_type","mini")
                .queryParam("instrument","Forex")
                .queryParam("symbol","AUDCADm")
                .queryParam("lot","0.1")
                .queryParam("leverage","200")
                .queryParam("user_currency","")
                .when().get()
                .then()
                .statusCode(200)
                .body("form_type", equalTo("mini"))
                .extract().response()
                .prettyPrint();

    }


    @Test
    // Валюта счета в цифровом виде ISO 4217 , EUR = 978
    public void userCurrencyOnISOValue(){
        RestAssured.given()
                .header("cookie",RandomCookieGenerate.cookie)
                .queryParam("form_type","mini")
                .queryParam("instrument","Forex")
                .queryParam("symbol","AUDCADm")
                .queryParam("lot","3")
                .queryParam("leverage","200")
                .queryParam("user_currency","978")
                .when().get()
                .then()
                .statusCode(200)
                .body("form_type", equalTo("mini"))
                .extract().response()
                .prettyPrint();

    }

    @Test
    public void userCurrencyOnLowerCaseValue(){
        RestAssured.given()
                .header("cookie",RandomCookieGenerate.cookie)
                .queryParam("form_type","mini")
                .queryParam("instrument","Forex")
                .queryParam("symbol","AUDCADm")
                .queryParam("lot","3")
                .queryParam("leverage","200")
                .queryParam("user_currency","eur")
                .when().get()
                .then()
                .statusCode(200)
                .body("form_type", equalTo("mini"))
                .extract().response()
                .prettyPrint();

    }

    @Test
    //EURO
    public void userCurrencyFullNameValue(){
        RestAssured.given()
                .header("cookie",RandomCookieGenerate.cookie)
                .queryParam("form_type","mini")
                .queryParam("instrument","Forex")
                .queryParam("symbol","AUDCADm")
                .queryParam("lot","3")
                .queryParam("leverage","200")
                .queryParam("user_currency","EURO")
                .when().get()
                .then()
                .statusCode(200)
                .body("form_type", equalTo("mini"))
                .extract().response()
                .prettyPrint();

    }



    @Test
    public void lotLessThanBorder(){
//        "lot": [
//        "Убедитесь что значение больше или равно 0.01."
//    ]
        RestAssured.given()
                .header("cookie",RandomCookieGenerate.cookie)
                .queryParam("form_type","mini")
                .queryParam("instrument","Forex")
                .queryParam("symbol","AUDCADm")
                .queryParam("lot","0.0001")
                .queryParam("leverage","200")
                .queryParam("user_currency","AUD")
                .when().get()
                .then()
                .statusCode(200)
                .body("form_type", equalTo("mini"))
                .extract().response()
                .prettyPrint();

    }


    @Test
    public void lotIncorrectValue(){
//        "lot": [
//        "Убедитесь что значение больше или равно 0.01."
//    ]
        RestAssured.given()
                .header("cookie",RandomCookieGenerate.cookie)
                .queryParam("form_type","mini")
                .queryParam("instrument","Forex")
                .queryParam("symbol","AUDCADm")
                .queryParam("lot","-1")
                .queryParam("leverage","200")
                .queryParam("user_currency","AUD")
                .when().get()
                .then()
                .statusCode(200)
                .body("form_type", equalTo("mini"))
                .extract().response()
                .prettyPrint();
    }


    @Test
    //symbolDoesNotMatchAccountType ?
    public void symbolDoesNotMatchFormType(){
        RestAssured.given()
                .header("cookie",RandomCookieGenerate.cookie)
                .queryParam("form_type","mini")
                .queryParam("instrument","Forex")
                .queryParam("symbol","AUDCADc")
                .queryParam("lot","3")
                .queryParam("leverage","200")
                .queryParam("user_currency","EUR")
                .when().get()
                .then()
                .statusCode(200)
                .body("form_type", equalTo("mini"))
                .extract().response()
                .prettyPrint();

    }


}
