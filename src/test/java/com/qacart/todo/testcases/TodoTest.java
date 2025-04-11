package com.qacart.todo.testcases;

import com.qacart.todo.models.Todo;
import com.qacart.todo.models.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class TodoTest {


    @Test
    public void shouldBeAbleToAddTodo() {

        Todo todo= new Todo(false, "Learn Appium");

        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjY3ZWQ2MzJlMmEyMWRiMDAxNDU1N2M2MCIsImZpcnN0TmFtZSI6IkhhdGVtIiwibGFzdE5hbWUiOiJIYXRhbWxlaCIsImlhdCI6MTc0MzYxMjQ4OH0.Gb3JR87ZFovtlubd_jmOoVmxszFeuYBKsj1BpvW4-9k";

        Response response= given()
                .baseUri("https://qacart-todo.herokuapp.com")
                .body(todo)
                .contentType(ContentType.JSON)
                .auth().oauth2(token)
                .when()
                .post("api/v1/tasks")
                .then()
                .log().all()
                .extract().response();

        Todo returnedTodo = response.body().as(Todo.class);

        assertThat(response.statusCode(),equalTo(201));
        assertThat(returnedTodo.getItem(),equalTo(todo.getItem()));
        assertThat(returnedTodo.getIsCompleted(),equalTo(todo.getIsCompleted()));

//                .assertThat().statusCode(201)
//                .assertThat().body("item", equalTo("Learn Appium"))
//                .assertThat().body("isCompleted", equalTo(false));


    }

    @Test
    public void shouldNotBeAbleToAddTodoIfIsCompletedIsMissing() {

        Todo todo= new Todo( "Learn Appium");
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjY3ZWQ2MzJlMmEyMWRiMDAxNDU1N2M2MCIsImZpcnN0TmFtZSI6IkhhdGVtIiwibGFzdE5hbWUiOiJIYXRhbWxlaCIsImlhdCI6MTc0MzYxMjQ4OH0.Gb3JR87ZFovtlubd_jmOoVmxszFeuYBKsj1BpvW4-9k";

        Response response=given()
                .baseUri("https://qacart-todo.herokuapp.com")
                .body(todo)
                .contentType(ContentType.JSON)
                .auth().oauth2(token)
                .when()
                .post("api/v1/tasks")
                .then()
                .log().all()
                .extract().response();

        Todo returnedTodo = response.body().as(Todo.class);
        Error returnedError = response.body().as(Error.class);

        assertThat(response.statusCode(),equalTo(400));
        assertThat(returnedError.getMessage(),equalTo("\"isCompleted\" is required"));

//                .assertThat().statusCode(400)
//                .assertThat().body("message", equalTo("\"isCompleted\" is required"));


    }

    @Test
    public void shouldBeAbleToGetTodoByID() {

        String taskID = "67f99765a8d20f001468e788";
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjY3ZWQ2MzJlMmEyMWRiMDAxNDU1N2M2MCIsImZpcnN0TmFtZSI6IkhhdGVtIiwibGFzdE5hbWUiOiJIYXRhbWxlaCIsImlhdCI6MTc0MzYxMjQ4OH0.Gb3JR87ZFovtlubd_jmOoVmxszFeuYBKsj1BpvW4-9k";

        Response response=given()
                .baseUri("https://qacart-todo.herokuapp.com")
                .contentType(ContentType.JSON)
                .auth().oauth2(token)
                .when()
                .get("api/v1/tasks/"+taskID)
                .then()
                .log().all()
                .extract().response();

        Todo returnedTodo = response.body().as(Todo.class);

        assertThat(response.statusCode(),equalTo(200));
        assertThat(returnedTodo.getItem(),equalTo("Learn Appium"));
        assertThat(returnedTodo.getIsCompleted(),equalTo(false));


//                .assertThat().statusCode(200)
//                .assertThat().body("item", equalTo("Learn Appium"))
//                .assertThat().body("isCompleted", equalTo(false));


    }

    @Test
    public void shouldBeAbleToDeleteTodo() {

        String taskID = "67f99765a8d20f001468e788";
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjY3ZWQ2MzJlMmEyMWRiMDAxNDU1N2M2MCIsImZpcnN0TmFtZSI6IkhhdGVtIiwibGFzdE5hbWUiOiJIYXRhbWxlaCIsImlhdCI6MTc0MzYxMjQ4OH0.Gb3JR87ZFovtlubd_jmOoVmxszFeuYBKsj1BpvW4-9k";

        Response response=given()
                .baseUri("https://qacart-todo.herokuapp.com")
                .contentType(ContentType.JSON)
                .auth().oauth2(token)
                .when()
                .delete("api/v1/tasks/"+taskID)
                .then()
                .log().all()
                .extract().response();
        Todo returnedTodo = response.body().as(Todo.class);


        assertThat(response.statusCode(),equalTo(200));
        assertThat(returnedTodo.getItem(),equalTo("Learn Appium"));
        assertThat(returnedTodo.getIsCompleted(),equalTo(false));

//                .assertThat().statusCode(200)
//                .assertThat().body("item", equalTo("Learn Appium"))
//                .assertThat().body("isCompleted", equalTo(false));


    }
}
