import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byClassName;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class CardDelivery {
    @Test
    void cardDeliveryDetails() throws InterruptedException {
        open("http://localhost:9999");

        SelenideElement form = $("[method=post]");

        form.$("[data-test-id=city] input").setValue("Москва");
        form.$("[data-test-id=date] input").setValue("11.01.2024");
        form.$("[data-test-id=name] input").setValue("Роман Николаевич");
        form.$("[data-test-id=phone] input").setValue("+79618610000");
        form.$("[data-test-id=agreement]").click();
        form.$(byClassName("button")).click();
        $("[data-test-id='notification'] .notification__title")
                .shouldHave(text("Успешно!"), Duration.ofSeconds(15))
                .shouldBe(visible);
        $("[data-test-id='notification'] .notification__content")
                .shouldHave(exactText("Встреча успешно забронирована на 11.01.2024"))
                .shouldBe(visible);

    }
}
