package ru.netology.delivery.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.delivery.data.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class DeliveryReplanTest {

    private static final String CITY_INPUT = "[data-test-id='city'] input";
    private static final String DATE_INPUT = "[data-test-id='date'] input";
    private static final String NAME_INPUT = "[data-test-id='name'] input";
    private static final String PHONE_INPUT = "[data-test-id='phone'] input";
    private static final String AGREEMENT_CHECKBOX = "[data-test-id='agreement']";
    private static final String SUBMIT_BUTTON = "button.button_view_extra .button__text"; // далее уточняется по тексту
    private static final String SUCCESS_NOTIFICATION = "[data-test-id='success-notification']";
    private static final String REPLAN_NOTIFICATION = "[data-test-id='replan-notification']";
    private static final String REPLAN_BUTTON = "[data-test-id='replan-notification'] button";

    private static final String BTN_TEXT_PLAN = "Запланировать";
    private static final String REPLAN_TITLE = "Необходимо подтверждение";
    private static final String SUCCESS_PREFIX = "Встреча успешно запланирована на ";

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }


    @Test
    @DisplayName("Should successful plan and replan meeting")
    void shouldSuccessfulPlanAndReplanMeeting() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);

        // Первичное заполнение формы
        $(CITY_INPUT).setValue(validUser.getCity());
        var dateField = $(DATE_INPUT);
        dateField.doubleClick().sendKeys(Keys.BACK_SPACE);
        dateField.setValue(firstMeetingDate);
        $(NAME_INPUT).setValue(validUser.getName());
        $(PHONE_INPUT).setValue(validUser.getPhone());
        $(AGREEMENT_CHECKBOX).click();

        $(SUBMIT_BUTTON).shouldHave(text(BTN_TEXT_PLAN)).click();
        // Проверяем уведомление об успешном планировании
        $(SUCCESS_NOTIFICATION)
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(text(SUCCESS_PREFIX + firstMeetingDate));

        // Повторное заполнение формы с новой датой
        dateField.doubleClick().sendKeys(Keys.BACK_SPACE);
        dateField.setValue(secondMeetingDate);

        $(SUBMIT_BUTTON).shouldHave(text(BTN_TEXT_PLAN)).click();

        // Модальное окно с предложением перепланировать
        $(REPLAN_NOTIFICATION)
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(text(REPLAN_TITLE));

        $(REPLAN_BUTTON).click();

        // Уведомление об успешном перепланировании
        $(SUCCESS_NOTIFICATION)
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(text(SUCCESS_PREFIX + secondMeetingDate));
    }
}
