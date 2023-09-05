package ch.zli.m223.coworkingspace;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import ch.zli.m223.coworkingspace.model.ApplicationUser;
import ch.zli.m223.coworkingspace.model.Material;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MaterialResourceTest {

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
                .when().get("/materials")
                .then()
                .statusCode(200)
                .body(is("[]"));
    }

    @Test
    public void testSpecificMaterialEndpoint() {
        Material material = new Material();
        material.setTitle("Keyboard");
        material.setAmount(5);
        material = given().contentType(ContentType.JSON)
                .body(material)
                .auth().oauth2(token)
                .when().post("/materials")
                .then()
                .statusCode(200)
                .extract().body().as(Material.class);
        given().contentType(ContentType.JSON)
                .auth().oauth2(token)
                .when().get("/materials/" + material.getId())
                .then()
                .statusCode(200)
                .body("title", is(material.getTitle().toString()))
                .body("amount", is(material.getAmount()));
        given().contentType(ContentType.JSON)
                .auth().oauth2(token)
                .when().delete("/materials/" + material.getId())
                .then()
                .statusCode(204);
    }

    @Test
    public void testCreateEndpoint() {
        Material material = new Material();
        material.setTitle("Keyboard");
        material.setAmount(5);
        material = given().contentType(ContentType.JSON)
                .body(material)
                .auth().oauth2(token)
                .when().post("/materials")
                .then()
                .statusCode(200)
                .body("title", is(material.getTitle().toString()))
                .body("amount", is(material.getAmount()))
                .extract().body().as(Material.class);
        given().contentType(ContentType.JSON)
                .auth().oauth2(token)
                .when().delete("/materials/" + material.getId())
                .then()
                .statusCode(204);
    }

    @Test
    public void testDeleteEndpoint() {
        Material material = new Material();
        material.setTitle("Keyboard");
        material.setAmount(5);
        material = given().contentType(ContentType.JSON)
                .body(material)
                .auth().oauth2(token)
                .when().post("/materials")
                .then()
                .statusCode(200)
                .extract().body().as(Material.class);
        given()
                .auth().oauth2(token)
                .when().delete("/materials/" + material.getId())
                .then()
                .statusCode(204);
    }

    @Test
    public void testEditEndpoint() {
        Material material = new Material();
        material.setTitle("Keyboard");
        material.setAmount(5);
        material = given().contentType(ContentType.JSON)
                .body(material)
                .auth().oauth2(token)
                .when().post("/materials")
                .then()
                .statusCode(200)
                .extract().body().as(Material.class);
        material.setTitle("Mouse");
        material.setAmount(7);
        given().contentType(ContentType.JSON)
                .body(material)
                .auth().oauth2(token)
                .when().put("/materials")
                .then()
                .statusCode(200)
                .body("title", is(material.getTitle().toString()))
                .body("amount", is(material.getAmount()));
        given()
                .auth().oauth2(token)
                .when().delete("/materials/" + material.getId())
                .then()
                .statusCode(204);
    }
}
