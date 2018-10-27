package com.exness;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;



public class TestAccountTypeMini {

    @Before
    public void setupRestAssured() {

        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setBaseUri("https://www.exness.com")
                .setBasePath("/api/calculator/calculate/")
                .setAccept("application/json, text/javascript, */*; q=0.01")
                .setContentType(ContentType.JSON)
                .addCookie(RandomCookieGenerate.cookie)
              //  .log(LogDetail.ALL)
                .build();
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }





    @Test
    public void checkCurrencyMarginCalculation(){
        RestAssured.given()
//                .baseUri("https://www.exness.com")
//                .basePath("/api/calculator/calculate/")
//                .contentType(ContentType.JSON)
//                .accept("application/json, text/javascript, */*; q=0.01")
               // .header("cookie",RandomCookieGenerate.cookie)
                .queryParam("form_type","mini")
                .queryParam("instrument","Forex")
                .queryParam("symbol","USDCADm")
                .queryParam("lot","0.1")
                .queryParam("leverage","200")
                .queryParam("user_currency","USD")
                .when().get()
                .then()
                .statusCode(200)
                .body("margin", Matchers.greaterThan("2"))
               // .body(Matchers.)
                .extract().response()
                .prettyPrint();


    }

    @Test
    public void checkcryptocurrencyMarginCalculation(){
        RestAssured.given()

                .header("cookie",RandomCookieGenerate.cookie)
                .queryParam("form_type","mini")
                .queryParam("instrument","Forex")
                .queryParam("symbol","EURCADm")
                .queryParam("lot","0.1")
                .queryParam("leverage","200")
                .queryParam("user_currency","USD")
                .when().get()
                .then()
                .statusCode(200)
                .body("margin", Matchers.greaterThan("2"))
                // .body(Matchers.)
                .extract().response()
                .prettyPrint();

    }

    @Test
    public void checkTroyOunceMarginCalculation(){


    }

    @Test
    public void checkMaxLotValue(){


    }

    @Test
    public void checkMinLotValue(){


    }

    @Test
    public void checkOverMaxLotValue(){


    }

    @Test
    public void checkOverMinLotValue(){


    }

    @Test
    public void checkNullLotValue(){


    }

    @Test
    public void checkSpecialSymbolLotValue(){

    }



}
