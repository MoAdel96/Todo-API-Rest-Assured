package com.qacart.todo.testcases;

import com.qacart.todo.apis.UserApi;
import com.qacart.todo.models.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matcher;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

public class UserTest {


    @Test
    public void shouldBeAbleToRegister() {

        User user = new User("Hatem","Hatamleh","hatem50905@example.com","12345678");


        Response response = UserApi.register(user);

       User returnedUser = response.body().as(User.class);

       assertThat(response.statusCode(),equalTo(201));
       assertThat(returnedUser.getFirstName(),equalTo(user.getFirstName()));



//                .assertThat().statusCode(201)
//                .assertThat().body("firstName",equalTo("Hatem"));


    }

    @Test
    public void shouldNotBeAbleToRegisterWithSameEmail(){
        User user = new User("Hatem","Hatamleh","hatem555@example.com","12345678");

        Response response = UserApi.register(user);;

        Error returnedError = response.body().as(Error.class);

        assertThat(response.statusCode(),equalTo(400));
        assertThat(returnedError.getMessage(),equalTo("Email is already exists in the Database"));



//                .assertThat().statusCode(400)
//                .assertThat().body("message",equalTo("Email is already exists in the Database"));
    }

    @Test
    public void shouldBeAbleToLogin(){
        User user = new User("hatem555@example.com","12345678");

        Response response= UserApi.Login(user);

        User returnedUser = response.body().as(User.class);


        assertThat(response.statusCode(),equalTo(200));
        assertThat(returnedUser.getFirstName(),equalTo("Hatem"));
        assertThat(returnedUser.getAccess_token(),not(equalTo(null)));


//                .assertThat().statusCode(200)
//                .assertThat().body("firstName",equalTo("Hatem"))
//                .assertThat().body("access_token",not(equalTo(null)));
    }

    @Test
    public void shouldNotBeAbleToLoginIfThePasswordIsNotCorrect(){
        User user = new User("hatem555@example.com","12345679");

       Response response= UserApi.Login(user);
        Error returnedError = response.body().as(Error.class);


        assertThat(response.statusCode(),equalTo(401));
        assertThat(returnedError.getMessage(),equalTo("The email and password combination is not correct, please fill a correct email and password"));



//                .assertThat().statusCode(401)
//                .assertThat().body("message",equalTo("The email and password combination is not correct, please fill a correct email and password"))
//                .assertThat().body("access_tokken",equalTo(null));
    }


}
