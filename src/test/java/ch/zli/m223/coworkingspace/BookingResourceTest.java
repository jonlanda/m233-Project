package ch.zli.m223.coworkingspace;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import ch.zli.m223.coworkingspace.model.ApplicationUser;
import ch.zli.m223.coworkingspace.model.Booking;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import java.time.LocalDateTime;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // Specify the order of test methods
public class BookingResourceTest {

        private String token;

        private ApplicationUser appUser;

        @BeforeEach
        public void getToken() {
                ApplicationUser user = new ApplicationUser();
                user.setFirstname("Jon");
                user.setLastname("Landa");
                user.setPassword("123");
                user.setEmail("jon@test.ch");
                appUser = given().contentType(ContentType.JSON)
                                .body(user)
                                .when().post("/users")
                                .then()
                                .statusCode(200)
                                .extract().body().as(ApplicationUser.class);
                token = given().contentType(ContentType.JSON)
                                .body(user)
                                .when().post("/login/generate")
                                .then()
                                .statusCode(200)
                                .extract().body().asString();
        }

        @AfterEach
        void deleteUser() {
                given().contentType(ContentType.JSON)
                                .auth().oauth2(token)
                                .when().delete("/users/" + appUser.getId())
                                .then()
                                .statusCode(204);
        }

        @Test
        public void testIndexEndpoint() {
                given()
                                .auth().oauth2(token)
                                .when().get("/bookings")
                                .then()
                                .statusCode(200)
                                .body(is("[]"));
        }

        @Test
        public void testIndexEndpointNotLoggedIn() {
                given()
                                .when().get("/bookings")
                                .then()
                                .statusCode(401);
        }

        @Test
        public void testSpecificBookingEndpoint() {
                Booking booking = new Booking();
                booking.setDate(LocalDateTime.of(2023, 06, 12, 12, 10, 05));
                booking.setHalfDay(false);
                booking.setAfternoon(false);
                booking.setMorning(false);
                booking.setStatus(false);
                booking.setUser(appUser);
                booking = given().contentType(ContentType.JSON)
                                .body(booking)
                                .auth().oauth2(token)
                                .when().post("/bookings")
                                .then()
                                .statusCode(200)
                                .extract().body().as(Booking.class);
                given().contentType(ContentType.JSON)
                                .auth().oauth2(token)
                                .when().get("/bookings/" + booking.getId())
                                .then()
                                .statusCode(200)
                                .body("date", is(booking.getDate().toString()))
                                .body("halfDay", is(booking.getHalfDay()))
                                .body("status", is(booking.getStatus()));
                given().contentType(ContentType.JSON)
                                .auth().oauth2(token)
                                .when().delete("/bookings/" + booking.getId())
                                .then()
                                .statusCode(204);
        }

        @Test
        public void testCreateEndpoint() {
                Booking booking = new Booking();
                booking.setDate(LocalDateTime.of(2023, 06, 12, 12, 10, 05));
                booking.setHalfDay(false);
                booking.setAfternoon(false);
                booking.setMorning(false);
                booking.setStatus(false);
                booking.setUser(appUser);
                booking = given().contentType(ContentType.JSON)
                                .body(booking)
                                .auth().oauth2(token)
                                .when().post("/bookings")
                                .then()
                                .statusCode(200)
                                .body("date", is(booking.getDate().toString()))
                                .body("halfDay", is(booking.getHalfDay()))
                                .body("status", is(booking.getStatus()))
                                .extract().body().as(Booking.class);
                given().contentType(ContentType.JSON)
                                .auth().oauth2(token)
                                .when().delete("/bookings/" + booking.getId())
                                .then()
                                .statusCode(204);
        }

        @Test
        public void testDeleteEndpoint() {
                Booking booking = new Booking();
                booking.setDate(LocalDateTime.of(2023, 06, 12, 12, 10, 05));
                booking.setHalfDay(false);
                booking.setAfternoon(false);
                booking.setMorning(false);
                booking.setStatus(false);
                booking.setUser(appUser);
                booking = given().contentType(ContentType.JSON)
                                .body(booking)
                                .auth().oauth2(token)
                                .when().post("/bookings")
                                .then()
                                .statusCode(200)
                                .extract().body().as(Booking.class);
                given()
                                .auth().oauth2(token)
                                .when().delete("/bookings/" + booking.getId())
                                .then()
                                .statusCode(204);
        }

        @Test
        public void testDeleteEndpointNotYouOrAdmin() {
                String token1;
                ApplicationUser user2 = new ApplicationUser();
                user2.setFirstname("Jon2");
                user2.setLastname("Landa2");
                user2.setPassword("1232");
                user2.setEmail("jon2@test.ch");
                user2.setIsAdmin(false);
                user2 = given().contentType(ContentType.JSON)
                                .body(user2)
                                .when().post("/users")
                                .then()
                                .statusCode(200)
                                .extract().body().as(ApplicationUser.class);
                token1 = given().contentType(ContentType.JSON)
                                .body(user2)
                                .when().post("/login/generate")
                                .then()
                                .statusCode(200)
                                .extract().body().asString();
                Booking booking = new Booking();
                booking.setDate(LocalDateTime.of(2023, 06, 12, 12, 10, 05));
                booking.setHalfDay(false);
                booking.setAfternoon(false);
                booking.setMorning(false);
                booking.setStatus(false);
                booking.setUser(appUser);
                booking = given().contentType(ContentType.JSON)
                                .body(booking)
                                .auth().oauth2(token)
                                .when().post("/bookings")
                                .then()
                                .statusCode(200)
                                .extract().body().as(Booking.class);
                given()
                                .auth().oauth2(token1)
                                .when().delete("/bookings/" + booking.getId())
                                .then()
                                .statusCode(403);
                given()
                                .auth().oauth2(token)
                                .when().delete("/bookings/" + booking.getId())
                                .then()
                                .statusCode(204);
                given()
                                .auth().oauth2(token)
                                .when().delete("/users/" + user2.getId())
                                .then()
                                .statusCode(204);
        }

        @Test
        public void testSpecificBookingEndpointNotFound() {
                given().contentType(ContentType.JSON)
                                .auth().oauth2(token)
                                .when().get("/bookings/" + 56)
                                .then()
                                .statusCode(404);
        }

        @Test
        public void testEditEndpoint() {
                Booking booking = new Booking();
                booking.setDate(LocalDateTime.of(2023, 06, 12, 12, 10, 05));
                booking.setHalfDay(false);
                booking.setAfternoon(false);
                booking.setMorning(false);
                booking.setStatus(false);
                booking.setUser(appUser);
                booking = given().contentType(ContentType.JSON)
                                .body(booking)
                                .auth().oauth2(token)
                                .when().post("/bookings")
                                .then()
                                .statusCode(200)
                                .extract().body().as(Booking.class);
                booking.setHalfDay(true);
                booking.setMorning(true);
                given().contentType(ContentType.JSON)
                                .body(booking)
                                .auth().oauth2(token)
                                .when().put("/bookings")
                                .then()
                                .statusCode(200)
                                .body("halfDay", is(booking.getHalfDay()))
                                .body("morning", is(booking.getMorning()));
                given()
                                .auth().oauth2(token)
                                .when().delete("/bookings/" + booking.getId())
                                .then()
                                .statusCode(204);
        }
}