package com.qacart.todo.testcases;

import com.qacart.todo.models.Todo;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class TodoTest {


    @Test
    public void shouldBeAbleToAddTodo() {

        Todo todo= new Todo(false, "Learn Appium");

        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjY3ZWQ2MzJlMmEyMWRiMDAxNDU1N2M2MCIsImZpcnN0TmFtZSI6IkhhdGVtIiwibGFzdE5hbWUiOiJIYXRhbWxlaCIsImlhdCI6MTc0MzYxMjQ4OH0.Gb3JR87ZFovtlubd_jmOoVmxszFeuYBKsj1BpvW4-9k";

        given()
                .baseUri("https://qacart-todo.herokuapp.com")
                .body(todo)
                .contentType(ContentType.JSON)
                .auth().oauth2(token)
                .when()
                .post("api/v1/tasks")
                .then()
                .log().all()
                .assertThat().statusCode(201)
                .assertThat().body("item", equalTo("Learn Appium"))
                .assertThat().body("isCompleted", equalTo(false));


    }

    @Test
    public void shouldNotBeAbleToAddTodoIfIsCompletedIsMissing() {

        Todo todo= new Todo( "Learn Appium");
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjY3ZWQ2MzJlMmEyMWRiMDAxNDU1N2M2MCIsImZpcnN0TmFtZSI6IkhhdGVtIiwibGFzdE5hbWUiOiJIYXRhbWxlaCIsImlhdCI6MTc0MzYxMjQ4OH0.Gb3JR87ZFovtlubd_jmOoVmxszFeuYBKsj1BpvW4-9k";

        given()
                .baseUri("https://qacart-todo.herokuapp.com")
                .body(todo)
                .contentType(ContentType.JSON)
                .auth().oauth2(token)
                .when()
                .post("api/v1/tasks")
                .then()
                .log().all()
                .assertThat().statusCode(400)
                .assertThat().body("message", equalTo("\"isCompleted\" is required"));


    }

    @Test
    public void shouldBeAbleToGetTodoByID() {

        String taskID = "67ef06e9f5a3e80014f41f47";
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjY3ZWQ2MzJlMmEyMWRiMDAxNDU1N2M2MCIsImZpcnN0TmFtZSI6IkhhdGVtIiwibGFzdE5hbWUiOiJIYXRhbWxlaCIsImlhdCI6MTc0MzYxMjQ4OH0.Gb3JR87ZFovtlubd_jmOoVmxszFeuYBKsj1BpvW4-9k";

        given()
                .baseUri("https://qacart-todo.herokuapp.com")
                .contentType(ContentType.JSON)
                .auth().oauth2(token)
                .when()
                .get("api/v1/tasks/"+taskID)
                .then()
                .log().all()
                .assertThat().statusCode(200)
                .assertThat().body("item", equalTo("Learn Appium"))
                .assertThat().body("isCompleted", equalTo(false));


    }

    @Test
    public void shouldBeAbleToDeleteTodo() {

        String taskID = "67ef06e9f5a3e80014f41f47";
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjY3ZWQ2MzJlMmEyMWRiMDAxNDU1N2M2MCIsImZpcnN0TmFtZSI6IkhhdGVtIiwibGFzdE5hbWUiOiJIYXRhbWxlaCIsImlhdCI6MTc0MzYxMjQ4OH0.Gb3JR87ZFovtlubd_jmOoVmxszFeuYBKsj1BpvW4-9k";

        given()
                .baseUri("https://qacart-todo.herokuapp.com")
                .contentType(ContentType.JSON)
                .auth().oauth2(token)
                .when()
                .delete("api/v1/tasks/"+taskID)
                .then()
                .log().all()
                .assertThat().statusCode(200)
                .assertThat().body("item", equalTo("Learn Appium"))
                .assertThat().body("isCompleted", equalTo(false));


    }
}
