package com.revolut.integration;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.parsing.Parser;
import com.revolut.entity.Account;
import com.revolut.entity.Customer;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static com.jayway.restassured.RestAssured.get;
import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;

/**
 * Rest tests
 *
 * @author vsushko
 */
public class IntegrationTest {

    /**
     * Before
     */
    @BeforeClass
    public static void configureRestAssured() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = Integer.getInteger("http.port", 8080);
        RestAssured.defaultParser = Parser.JSON;
    }

    /**
     * After
     */
    @AfterClass
    public static void unconfigureRestAssured() {
        RestAssured.reset();
    }

    /**
     * Checks transaction creation
     */
    @Test
    public void checkTransactionsCreations() {
        Customer customer1 = given()
                .body("{\"firstName\":\"Jameson1\", \"middleName\":\"John1\", \"lastName\":\"Ireland1\"}")
                .request().post("/api/customers").thenReturn().as(Customer.class);
        assertEquals(customer1.getFirstName(), "Jameson1");
        assertEquals(customer1.getMiddleName(), "John1");
        assertEquals(customer1.getLastName(), "Ireland1");

        Customer customer2 = given()
                .body("{\"firstName\":\"Jameson2\", \"middleName\":\"John2\", \"lastName\":\"Ireland2\"}")
                .request().post("/api/customers").thenReturn().as(Customer.class);
        assertEquals(customer2.getFirstName(), "Jameson2");
        assertEquals(customer2.getMiddleName(), "John2");
        assertEquals(customer2.getLastName(), "Ireland2");

        get("/api/customers/").then()
                .assertThat()
                .statusCode(200)
                .body(containsString("Jameson1"))
                .body(containsString("John1"))
                .body(containsString("Ireland1"))
                .body(containsString("Jameson2"))
                .body(containsString("John2"))
                .body(containsString("Ireland2"));

        Account account1 = given()
                .body("{\"customerId\": \"1\"}")
                .request().post("/api/accounts").thenReturn().as(Account.class);
        assertEquals(account1.getOwner().getId().toString(), "1");

        get("/api/accounts/").then()
                .assertThat()
                .statusCode(200)
                .body(containsString("Jameson1"))
                .body(containsString("John1"))
                .body(containsString("Ireland1"));
        Account account2 = given()
                .body("{\"customerId\": \"2\"}")
                .request().post("/api/accounts").thenReturn().as(Account.class);
        assertEquals(account2.getOwner().getId().toString(), "2");

        get("/api/accounts/").then()
                .assertThat()
                .statusCode(200)
                .body(containsString("Jameson2"))
                .body(containsString("John2"))
                .body(containsString("Ireland2"));

        given()
                .contentType("application/json")
                .body("{\"operation\": \"create_transfer\", \"donorAccount\": \"3\", \"recipientAccount\": \"4\", " +
                        "\"amount\": \"1\"}")
                .when().post("/api/transactions").then()
                .statusCode(201);

        get("/api/transactions").then()
                .assertThat()
                .statusCode(200)
                .body(containsString("OUTCOME"))
                .body(containsString("INCOME"));

        get("/api/related").then()
                .assertThat()
                .statusCode(200)
                .body(containsString("OUTCOME"))
                .body(containsString("INCOME"));
    }
}