package com.qacart.todo.testcases;

import com.qacart.todo.models.User;
import io.restassured.http.ContentType;
import org.hamcrest.Matcher;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

public class UserTest {


    @Test
    public void shouldBeAbleToRegister() {

        User user = new User("Hatem","Hatamleh","hatem555@example.com","12345678");


        given()
                .baseUri("https://qacart-todo.herokuapp.com")
                .contentType(ContentType.JSON)
                .body(user)
        .when()
                .post("/api/v1/users/register")
        .then()
                .log().all()
                .assertThat().statusCode(201)
                .assertThat().body("firstName",equalTo("Hatem"));


    }

    @Test
    public void shouldNotBeAbleToResgisterWithSameEmail(){
        User user = new User("Hatem","Hatamleh","hatem555@example.com","12345678");

        given()
                .baseUri("https://qacart-todo.herokuapp.com")
                .contentType(ContentType.JSON)
                .body(user)
        .when()
                .post("/api/v1/users/register")
        .then()
                .log().all()
                .assertThat().statusCode(400)
                .assertThat().body("message",equalTo("Email is already exists in the Database"));
    }

    @Test
    public void shouldBeAbleToLogin(){
        User user = new User("hatem555@example.com","12345678");

        given()
                .baseUri("https://qacart-todo.herokuapp.com")
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("/api/v1/users/login")
                .then()
                .log().all()
                .assertThat().statusCode(200)
                .assertThat().body("firstName",equalTo("Hatem"))
                .assertThat().body("access_token",not(equalTo(null)));
    }

    @Test
    public void shouldNotBeAbleToLoginIfThePasswordIsNotCorrect(){
        User user = new User("hatem555@example.com","12345678");

        given()
                .baseUri("https://qacart-todo.herokuapp.com")
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("/api/v1/users/login")
                .then()
                .log().all()
                .assertThat().statusCode(401)
                .assertThat().body("message",equalTo("The email and password combination is not correct, please fill a correct email and password"))
                .assertThat().body("access_tokken",equalTo(null));
    }


}
