package com.qacart.todo.testcases;

import com.qacart.todo.apis.TodoApi;
import com.qacart.todo.models.Todo;
import com.qacart.todo.models.User;
import com.qacart.todo.steps.TodoSteps;
import com.qacart.todo.steps.UserSteps;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class TodoTest {


    @Test
    public void shouldBeAbleToAddTodo() {
        String token = UserSteps.getUserToken();
        Todo todo = TodoSteps.generateTodo();



        Response response = TodoApi.addTodo(todo, token);

        Todo returnedTodo = response.body().as(Todo.class);

        assertThat(response.statusCode(), equalTo(201));
        assertThat(returnedTodo.getItem(), equalTo(todo.getItem()));
        assertThat(returnedTodo.getIsCompleted(), equalTo(todo.getIsCompleted()));

//                .assertThat().statusCode(201)
//                .assertThat().body("item", equalTo("Learn Appium"))
//                .assertThat().body("isCompleted", equalTo(false));


    }

    @Test
    public void shouldNotBeAbleToAddTodoIfIsCompletedIsMissing() {

        Todo todo = new Todo("Learn Appium");
        String token = UserSteps.getUserToken();
        Response response = TodoApi.addTodo(todo, token);


        Error returnedError = response.body().as(Error.class);

        assertThat(response.statusCode(), equalTo(400));
        assertThat(returnedError.getMessage(), equalTo("\"isCompleted\" is required"));

//                .assertThat().statusCode(400)
//                .assertThat().body("message", equalTo("\"isCompleted\" is required"));


    }

    @Test
    public void shouldBeAbleToGetTodoByID() {
        Todo todo =TodoSteps.generateTodo();
        String token = UserSteps.getUserToken();
        String todoID = TodoSteps.getTodoID(todo,token);

        Response response = TodoApi.getTodo(todoID,token);

        Todo returnedTodo = response.body().as(Todo.class);

        assertThat(response.statusCode(), equalTo(200));
        assertThat(returnedTodo.getItem(), equalTo(todo.getItem()));
        assertThat(returnedTodo.getIsCompleted(), equalTo(false));


//                .assertThat().statusCode(200)
//                .assertThat().body("item", equalTo("Learn Appium"))
//                .assertThat().body("isCompleted", equalTo(false));


    }

    @Test
    public void shouldBeAbleToDeleteTodo() {

        Todo todo =TodoSteps.generateTodo();
        String token = UserSteps.getUserToken();
        String todoID = TodoSteps.getTodoID(todo,token);
        Response response =TodoApi.deleteTodo(todoID,token);
        Todo returnedTodo = response.body().as(Todo.class);


        assertThat(response.statusCode(), equalTo(200));
        assertThat(returnedTodo.getItem(), equalTo(todo.getItem()));
        assertThat(returnedTodo.getIsCompleted(), equalTo(false));

//                .assertThat().statusCode(200)
//                .assertThat().body("item", equalTo("Learn Appium"))
//                .assertThat().body("isCompleted", equalTo(false));


    }
}
