import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardDeliveryTest {
    public String generateDate(long addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }

    String planningDate = generateDate(4, "dd.MM.yyyy");

    @BeforeEach
    void setUpAll() {
        open("http://localhost:9999/");
    }

    @Test
    public void shouldSomethingTest1()  {
        $("[data-test-id=city] input") .setValue("Казань");
        $("[data-test-id=date] input") .doubleClick().sendKeys(planningDate);
        $("[data-test-id=name] input") .setValue("Иван");
        $("[data-test-id=phone] input") .setValue("+79274185999");
        $("[data-test-id=agreement]") .click();
        $(By.className("button")) .click();
        $("[data-test-id=notification]").shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate), Duration.ofSeconds(14)).shouldBe(visible);


    }

    @Test
    public void emptyFieldCityTest2()  {
        $("[data-test-id=city] input") .setValue("");
        $("[data-test-id=date] input") .doubleClick().sendKeys(planningDate);
        $("[data-test-id=name] input") .setValue("Иван");
        $("[data-test-id=phone] input") .setValue("+79274185999");
        $("[data-test-id=agreement]") .click();
        $(By.className("button")) .click();
        $("[data-test-id=city]").shouldHave(Condition.text("Поле обязательно для заполнения"));


    }

    @Test
    public void wrongCityEnteredTest3()  {
        $("[data-test-id=city] input") .setValue("Мусква");
        $("[data-test-id=date] input") .doubleClick().sendKeys(planningDate);
        $("[data-test-id=name] input") .setValue("Иван");
        $("[data-test-id=phone] input") .setValue("+79274185999");
        $("[data-test-id=agreement]") .click();
        $(By.className("button")) .click();
        $("[data-test-id=city]").shouldHave(Condition.text("Доставка в выбранный город недоступна"));


    }

    @Test
    public void emptyFieldNameTest4()  {
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").doubleClick().sendKeys(planningDate);
        $("[data-test-id=name] input").setValue("");
        $("[data-test-id=phone] input").setValue("+79274185999");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();
        $("[data-test-id=name]").shouldHave(Condition.text("Поле обязательно для заполнения"));

    }

    @Test
    public void enterAnApostropheTest5()  {
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").doubleClick().sendKeys(planningDate);
        $("[data-test-id=name] input").setValue("Жанна д'Арк");
        $("[data-test-id=phone] input").setValue("+79274185999");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();
        $("[data-test-id=name]").shouldHave(Condition.text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));

    }

    @Test
    public void emptyFieldPhoneTest6()  {
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").doubleClick().sendKeys(planningDate);
        $("[data-test-id=name] input").setValue("Иосиф");
        $("[data-test-id=phone] input").setValue("");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();
        $("[data-test-id=phone]").shouldHave(Condition.text("Поле обязательно для заполнения"));

    }

    @Test
    public void leaveTheCheckboxEmpty7()  {
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").doubleClick().sendKeys(planningDate);
        $("[data-test-id=name] input").setValue("Иосиф");
        $("[data-test-id=phone] input").setValue("+71234567890");
        $(By.className("button")).click();
        $("[data-test-id=agreement]").shouldHave(Condition.text("Я соглашаюсь с условиями обработки и использования моих персональных данных"));

    }

    @Test
    public void leaveTheDataEmpty8()  {
        $("[data-test-id=city] input").setValue("Москва");
        $(".calendar-input__custom-control input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=name] input").setValue("Иосиф");
        $("[data-test-id=phone] input").setValue("+71234567890");
        $("[data-test-id=agreement]").click();
        $(By.className("button")).click();
        $(".calendar-input   .input__sub").shouldHave(exactText("Неверно введена дата"));
    }
}


