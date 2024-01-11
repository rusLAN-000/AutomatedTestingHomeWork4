import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byClassName;
import static com.codeborne.selenide.Selenide.*;

public class CardDelivery {
    public String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));/* "-метод генерации даты" */
    }

    String planingDate = generateDate(6);   /*"<- сохраняем дату в переменную planingDate"*/

    @Test
    void cardDeliveryDetails() throws InterruptedException {
        open("http://localhost:9999");
        SelenideElement form = $("[method=post]");
        form.$("[data-test-id='city'] input").setValue("Москва");
        form.$("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE); // -очистка поля (дата)
        form.$("[data-test-id='date'] input").setValue(planingDate);
        form.$("[data-test-id='name'] input").setValue("Роман Николаевич");
        form.$("[data-test-id='phone'] input").setValue("+79618610000");
        form.$("[data-test-id='agreement']").click();
        form.$(byClassName("button")).click();
        $("[data-test-id='notification'] .notification__title")
                .shouldHave(text("Успешно!"), Duration.ofSeconds(15))
                .shouldBe(visible);
        $("[data-test-id='notification'] .notification__content")
                .shouldHave(exactText("Встреча успешно забронирована на " + planingDate))
                .shouldBe(visible);
        $("[data-test-id='notification'] .notification__closer")
                .shouldBe(visible).click();
    }
}
