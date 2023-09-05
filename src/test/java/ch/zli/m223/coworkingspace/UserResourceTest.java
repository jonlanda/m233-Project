package ch.zli.m223.coworkingspace;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import ch.zli.m223.coworkingspace.model.ApplicationUser;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserResourceTest {
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
                                .when().get("/users")
                                .then()
                                .statusCode(200)
                                .body(is(
                                                "[{\"id\":18,\"firstname\":\"Jon\",\"lastname\":\"Landa\",\"password\":\"123\",\"isAdmin\":true,\"email\":\"jon@test.ch\",\"booking\":[]}]"));
        }

        @Test
        public void testSpecificUserEndpoint() {
                ApplicationUser user = new ApplicationUser();
                user.setFirstname("Jon");
                user.setLastname("Landa2");
                user.setPassword("bla");
                user.setIsAdmin(false);
                user.setEmail("jon@jon.jon");
                user = given().contentType(ContentType.JSON)
                                .body(user)
                                .auth().oauth2(token)
                                .when().post("/users")
                                .then()
                                .statusCode(200)
                                .extract().body().as(ApplicationUser.class);
                given().contentType(ContentType.JSON)
                                .auth().oauth2(token)
                                .when().get("/users/" + user.getId())
                                .then()
                                .statusCode(200)
                                .body("firstname", is(user.getFirstname().toString()))
                                .body("email", is(user.getEmail().toString()))
                                .body("password", is(user.getPassword().toString()));
                given().contentType(ContentType.JSON)
                                .auth().oauth2(token)
                                .when().delete("/users/" + user.getId())
                                .then()
                                .statusCode(204);

        }

        @Test
        public void testCreateEndpoint() {
                ApplicationUser user = new ApplicationUser();
                user.setFirstname("Jon");
                user.setLastname("Landa2");
                user.setPassword("bla");
                user.setIsAdmin(false);
                user.setEmail("jon@jon.jon");
                user = given().contentType(ContentType.JSON)
                                .body(user)
                                .auth().oauth2(token)
                                .when().post("/users")
                                .then()
                                .statusCode(200)
                                .body("firstname", is(user.getFirstname().toString()))
                                .body("email", is(user.getEmail().toString()))
                                .body("password", is(user.getPassword().toString()))
                                .extract().body().as(ApplicationUser.class);
                given().contentType(ContentType.JSON)
                                .auth().oauth2(token)
                                .when().delete("/users/" + user.getId())
                                .then()
                                .statusCode(204);
        }

        @Test
        public void testDeleteEndpoint() {
                ApplicationUser user = new ApplicationUser();
                user.setFirstname("Jon");
                user.setLastname("Landa2");
                user.setPassword("bla");
                user.setIsAdmin(false);
                user.setEmail("jon@jon.jon");
                user = given().contentType(ContentType.JSON)
                                .body(user)
                                .auth().oauth2(token)
                                .when().post("/users")
                                .then()
                                .statusCode(200)
                                .extract().body().as(ApplicationUser.class);
                given()
                                .auth().oauth2(token)
                                .when().delete("/users/" + user.getId())
                                .then()
                                .statusCode(204);
        }

        @Test
        public void testEditEndpoint() {
                ApplicationUser user = new ApplicationUser();
                user.setFirstname("Jon");
                user.setLastname("Landa2");
                user.setPassword("bla");
                user.setIsAdmin(false);
                user.setEmail("jon@jon.jon");
                user = given().contentType(ContentType.JSON)
                                .body(user)
                                .auth().oauth2(token)
                                .when().post("/users")
                                .then()
                                .statusCode(200)
                                .extract().body().as(ApplicationUser.class);
                user.setFirstname("Jono");
                user.setPassword("bla123");
                given().contentType(ContentType.JSON)
                                .body(user)
                                .auth().oauth2(token)
                                .when().put("/users")
                                .then()
                                .statusCode(200)
                                .body("firstname", is(user.getFirstname().toString()))
                                .body("password", is(user.getPassword().toString()));
                given()
                                .auth().oauth2(token)
                                .when().delete("/users/" + user.getId())
                                .then()
                                .statusCode(204);
        }
}
