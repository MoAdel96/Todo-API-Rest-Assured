package com.qacart.todo.testcases;

import com.qacart.todo.apis.UserApi;
import com.qacart.todo.data.ErrorMessages;
import com.qacart.todo.models.User;
import com.qacart.todo.steps.UserSteps;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

@Feature("User Feature")
public class UserTest {

    @Story("Should Be Able To Register")
    @Test(description = "Should Be Able To Register")
    public void shouldBeAbleToRegister() {

        User user = UserSteps.generateUser();
        Response response = UserApi.register(user);
        User returnedUser = response.body().as(User.class);
        assertThat(response.statusCode(), equalTo(201));
        assertThat(returnedUser.getFirstName(), equalTo(user.getFirstName()));

    }

    @Story("Should Not Be Able To Register With Same Email")
    @Test(description = "Should Not Be Able To Register With Same Email")
    public void shouldNotBeAbleToRegisterWithSameEmail() {
        User user = UserSteps.getRegisteredUser();
        Response response = UserApi.register(user);
        Error returnedError = response.body().as(Error.class);
        assertThat(response.statusCode(), equalTo(400));
        assertThat(returnedError.getMessage(), equalTo(ErrorMessages.EMAIL_IS_ALREADY_REGISTERED));

    }

    @Story("Should Be Able To Login")
    @Test(description = "Should Be Able To Login")
    public void shouldBeAbleToLogin() {
        User user = UserSteps.getRegisteredUser();
        User loginData = new User(user.getEmail(), user.getPassword());
        Response response = UserApi.Login(loginData);
        User returnedUser = response.body().as(User.class);
        assertThat(response.statusCode(), equalTo(200));
        assertThat(returnedUser.getFirstName(), equalTo(user.getFirstName()));
        assertThat(returnedUser.getAccess_token(), not(equalTo(null)));

    }

    @Story("Should Not Be Able To Login If The Password Is Not Correct")
    @Test(description = "Should Not Be Able To Login If The Password Is Not Correct")
    public void shouldNotBeAbleToLoginIfThePasswordIsNotCorrect() {
        User user = UserSteps.getRegisteredUser();
        User loginData = new User(user.getEmail(), "wronged password");
        Response response = UserApi.Login(loginData);
        Error returnedError = response.body().as(Error.class);
        assertThat(response.statusCode(), equalTo(401));
        assertThat(returnedError.getMessage(), equalTo(ErrorMessages.EMAIL_OR_PASSWORD_IS_WRONG));

    }


}
