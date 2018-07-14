package org.tsdes.advanced.rest.wrapper

import io.restassured.RestAssured
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import org.hamcrest.Matchers.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.junit4.SpringRunner
import org.tsdes.advanced.rest.dto.WrappedResponse
import org.tsdes.advanced.rest.dto.WrappedResponse.ResponseStatus.*


@RunWith(SpringRunner::class)
@SpringBootTest(classes = [(MathApplication::class)],
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MathApiTest{


    @LocalServerPort
    protected var port = 0

    @Before
    fun initTest() {

        // RestAssured configs shared by all the tests
        RestAssured.baseURI = "http://localhost"
        RestAssured.port = port
        RestAssured.basePath = "/math/divide"
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails()
    }


    @Test
    fun testSuccessfulDivision(){

        val x = 10
        val y = 2
        val expected = x / y

        given().accept(ContentType.JSON)
                .param("x", x)
                .param("y", y)
                .get()
                .then()
                .statusCode(200)
                .body("code", equalTo(200))
                .body("status", equalToIgnoringCase(SUCCESS.toString()))
                .body("data.result", equalTo(expected))
                .body("message", equalTo(null))
    }


    @Test
    fun testSuccessDivideByZero(){

        given().accept(ContentType.JSON)
                .param("x", 10)
                .param("y", 0)
                .get()
                .then()
                .statusCode(400)
                .body("code", equalTo(400))
                .body("status", equalToIgnoringCase(ERROR.toString()))
                .body("message", containsString("Cannot divide"))
    }

    @Test
    fun testMissingParam(){

        /*
            Here, the validation is done by Spring, and fails before
            the API code is even called.
            Spring sends a response with JSON data, with its own format.
            To make it consistent with our own format of the wrapped,
            we will need to customize how exceptions are handled
         */

        given().accept(ContentType.JSON)
                .param("x", 10)
                .get()
                .then()
                .statusCode(400)
                //note here that "status" represent the HTTP code
                .body("status", equalTo(400))
                .body("message", not(equalTo(null)))

        /*
            {
                "timestamp": "2018-07-14T12:06:22.906+0000",
                "status": 400,
                "error": "Bad Request",
                "message": "Required int parameter 'y' is not present",
                "path": "/math/divide"
            }
         */
    }
}