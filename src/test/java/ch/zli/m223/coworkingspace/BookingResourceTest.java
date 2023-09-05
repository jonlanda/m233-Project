package ch.zli.m223.coworkingspace;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // Specify the order of test methods
public class BookingResourceTest {

        @Test
        @Order(1)
        public void testIndexEndpoint() {
                given()
                                .when().get("/bookings")
                                .then()
                                .statusCode(200)
                                .body(is("[]"));
        }

        @Test
        @Order(2)
        public void testCreateEndpoint() {
                given().contentType(ContentType.JSON)
                                .body("{\"date\": \"2022-03-10T12:15:50\",\"halfday\": \"true\",\"morning\": \"true\",\"afternoon\": \"false\",\"status\": \"true\",\"applicationuser\": \"{}}")
                                .when().post("/bookings")
                                .then()
                                .statusCode(200)
                                .body(is("{\"date\": \"2022-03-10T12:15:50\",\"halfday\": \"true\",\"morning\": \"true\",\"afternoon\": \"false\",\"status\": \"true\",\"applicationuser\": \"{}}"));
        }

        @Test
        @Order(3)
        public void testDeleteEndpoint() {
                given().contentType(ContentType.JSON)
                                .body("{\"date\": \"2022-03-10T12:15:50\",\"halfday\": \"true\",\"morning\": \"true\",\"afternoon\": \"false\",\"status\": \"true\",\"applicationuser\": \"{}}")
                                .when().post("/bookings")
                                .then()
                                .statusCode(200)
                                .body(is("{\"id\":2,\"date\": \"2022-03-10T12:15:50\",\"halfday\": \"true\",\"morning\": \"true\",\"afternoon\": \"false\",\"status\": \"true\",\"applicationuser\": \"{}}"));

                given()
                                .when().delete("/entries/1")
                                .then()
                                .statusCode(204);
        }

        @Test
        @Order(4)
        public void testEditEndpoint() {

                given().contentType(ContentType.JSON)
                                .body("{\"id\":1,\"date\": \"2022-03-11T12:15:50\",\"halfday\": \"true\",\"morning\": \"true\",\"afternoon\": \"false\",\"status\": \"true\",\"applicationuser\": \"{}}")
                                .when().put("/entries")
                                .then()
                                .statusCode(200)
                                .body(is("{\"id\":1,\"date\": \"2022-03-11T12:15:50\",\"halfday\": \"true\",\"morning\": \"true\",\"afternoon\": \"false\",\"status\": \"true\",\"applicationuser\": \"{}}"));
        }

}