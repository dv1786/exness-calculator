package com.exness;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
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
                //.log(LogDetail.ALL)
                .build();
       // RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }





    @Test
    public void checkCurrencyMarginCalculation(){
        RestAssured.given()
//                .baseUri("https://www.exness.com")
//                .basePath("/api/calculator/calculate/")
//                .contentType(ContentType.JSON)
//                .accept("application/json, text/javascript, */*; q=0.01")
                .header("cookie","language=ru; _ga=GA1.2.1339193250.1539191461; ss3=0pl9l5d1fxawnhw3tw53hb1cjqc8zr5v; _gcl_au=1.1.525612295.1539191462; visid_incap_1784725=Koe/dTkHQNyqdccnzcrVjKQyvlsAAAAAQUIPAAAAAADDmc/VkuxqeKfT1BrJ9pNA; visid_incap_1251658=j0Ar2y4BTMqSuy+BD8WlDaQyvlsAAAAAQUIPAAAAAAB8ZX5ixcK+vqzXKSiCDe87; _ym_uid=1539191462968783246; _ym_d=1539191462; __zlcmid=oohhpdcBuKZZmo; cookies_notice_closed=true; regdate=1539191810122; Hm_lvt_3327e6ea6a751efcc3584f7e48c05531=1539191855; exness_user_id=904778; exness_csrfcookie=TlN7ToFv8eUIh8PELpI7GJS4BgVflRdr; incap_ses_471_961876=7bWaSVjmzmwppsI3JFWJBoie0FsAAAAAEWs40S8giCROrjUE+KWwKQ==; visid_incap_961876=y8yDKnXKQaC9q3R+w9jBhKMyvlsAAAAAQkIPAAAAAACAYL6HAbJKSf0tqWjCp57AkkkjkJ/aA4mj; nlbi_961876=jYq8Nt2ixlHzE+Mc5LU8xAAAAABMuKAJEzjiajLDiDKj2iN+; _gid=GA1.2.460382865.1540398741; incap_ses_471_1784725=j72RFYIhrmTUuMI3JFWJBpKe0FsAAAAAoz9sQ+LueHjpTtw581mb+Q==; nlbi_1251658=/CSRMphbWn+YPsXI2I5c+AAAAACRodDDs26jUoEnK4hQBSFG; incap_ses_471_1251658=3PEKAIniwl/WuMI3JFWJBpKe0FsAAAAAJVxMLMGmfz3Voi4YGmWCfQ==; _dc_gtm_UA-8651572-1=1; _ym_visorc_971386=w; _ym_isad=1; _gat_UA-8651572-1=1; fp=26c7c62ac8015dea80ce543c7c239ac3")
                .queryParam("form_type","mini")
                .queryParam("instrument","Forex")
                .queryParam("symbol","AUDCADm")
                .queryParam("lot","0.1")
                .queryParam("leverage","200")
                .queryParam("user_currency","USD")
                .when().get()
                .then()
                .statusCode(200)
                .body("margin", Matchers.equalTo("35.31"))
                .extract().response()
                .prettyPrint();


    }

    @Test
    public void checkcryptocurrencyMarginCalculation(){


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
