package ru.netology.testmode.test;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.testmode.data.DataGenerator.Registration.getRegisteredUser;
import static ru.netology.testmode.data.DataGenerator.Registration.getUser;
import static ru.netology.testmode.data.DataGenerator.getRandomLogin;
import static ru.netology.testmode.data.DataGenerator.getRandomPassword;

class AuthTest {

    private static final String LOGIN_INPUT = "[data-test-id='login'] input";
    private static final String PASSWORD_INPUT = "[data-test-id='password'] input";
    private static final String SUBMIT_BUTTON = "button.button";

    private static final String SUCCESS_HEADER = "h2";
    private static final String ERROR_NOTIFICATION = "[data-test-id='error-notification'] .notification__content";

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successfully login with active registered user")
    void shouldSuccessfulLoginIfRegisteredActiveUser() {
        var registeredUser = getRegisteredUser("active");

        $(LOGIN_INPUT).setValue(registeredUser.getLogin());
        $(PASSWORD_INPUT).setValue(registeredUser.getPassword());
        $(SUBMIT_BUTTON).click();

        $(SUCCESS_HEADER).shouldHave(Condition.exactText("Личный кабинет")).shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("Should get error message if login with not registered user")
    void shouldGetErrorIfNotRegisteredUser() {
        var notRegisteredUser = getUser("active");

        $(LOGIN_INPUT).setValue(notRegisteredUser.getLogin());
        $(PASSWORD_INPUT).setValue(notRegisteredUser.getPassword());
        $(SUBMIT_BUTTON).click();

        $(ERROR_NOTIFICATION).shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("Should get error message if login with blocked registered user")
    void shouldGetErrorIfBlockedUser() {
        var blockedUser = getRegisteredUser("blocked");

        $(LOGIN_INPUT).setValue(blockedUser.getLogin());
        $(PASSWORD_INPUT).setValue(blockedUser.getPassword());
        $(SUBMIT_BUTTON).click();

        $(ERROR_NOTIFICATION).shouldHave(Condition.text("Ошибка! Пользователь заблокирован"), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("Should get error message if login with wrong login")
    void shouldGetErrorIfWrongLogin() {
        var registeredUser = getRegisteredUser("active");
        var wrongLogin = getRandomLogin();

        $(LOGIN_INPUT).setValue(wrongLogin);
        $(PASSWORD_INPUT).setValue(registeredUser.getPassword());
        $(SUBMIT_BUTTON).click();

        $(ERROR_NOTIFICATION)
                .shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("Should get error message if login with wrong password")
    void shouldGetErrorIfWrongPassword() {
        var registeredUser = getRegisteredUser("active");
        var wrongPassword = getRandomPassword();

        $(LOGIN_INPUT).setValue(registeredUser.getLogin());
        $(PASSWORD_INPUT).setValue(wrongPassword);
        $(SUBMIT_BUTTON).click();

        $(ERROR_NOTIFICATION)
                .shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);

    }
}