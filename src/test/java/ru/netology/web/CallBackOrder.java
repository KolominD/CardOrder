package ru.netology.web;

import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;


public class CallBackOrder {
    @Test
    void shouldTestMyOwnData() {
        open("http://localhost:9999");
        $("[data-test-id=name] input").setValue("Иванов Дмитрий");
        $("[data-test-id=phone] input").setValue("+79998885522");
        $("[data-test-id=agreement]").click();
        $("button").click();
        $("[data-test-id=order-success]").shouldHave(exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    void shouldTestWithoutPhoneData() {
        open("http://localhost:9999");
        $("[data-test-id=name] input").setValue("Иванов Дмитрий");
        $("[data-test-id=phone] input").setValue("");
        $("[data-test-id=agreement]").click();
        $("button").click();
        $("[data-test-id=phone]").shouldHave(cssClass("input_invalid"));
        $("[data-test-id=phone] .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldTestWithEnglishName() {
        open("http://localhost:9999");
        $("[data-test-id=name] input").setValue("Ivanov Ivan");
        $("[data-test-id=phone] input").setValue("+79998885544");
        $("[data-test-id=agreement]").click();
        $("button").click();
        $("[data-test-id=name]").shouldHave(cssClass("input_invalid"));
        $("[data-test-id=name] .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }


    @Test
    void shouldTestWithoutNameData() {
        open("http://localhost:9999");
        $("[data-test-id=name] input").setValue("");
        $("[data-test-id=phone] input").setValue("+79996665522");
        $("[data-test-id=agreement]").click();
        $("button").click();
        $("[data-test-id=name]").shouldHave(cssClass("input_invalid"));
        $("[data-test-id=name] .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldSendWithEmptyFields() {
        open("http://localhost:9999");
        $("[data-test-id=name] input").setValue("");
        $("[data-test-id=phone] input").setValue("");
        $("[data-test-id=agreement]").click();
        $("button").click();
        $("[data-test-id=name]").shouldHave(cssClass("input_invalid"));
        $("[data-test-id=name] .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldSendWithoutCheckBox() {
        open("http://localhost:9999");
        $("[data-test-id=name] input").setValue("Иванов Дмитрий");
        $("[data-test-id=phone] input").setValue("+79996665588");
        $("button").click();
        $("[data-test-id=agreement]").shouldHave(cssClass("input_invalid"));
    }

    @Test
    void shouldSendWithNumberBelowLimit() {
        open("http://localhost:9999");
        $("[data-test-id=name] input").setValue("Иванов Дмитрий");
        $("[data-test-id=phone] input").setValue("+7999555");
        $("[data-test-id=agreement]").click();
        $("button").click();
        $("[data-test-id=phone]").shouldHave(cssClass("input_invalid"));
        $("[data-test-id=phone] .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldSendWithNumberOverThanLimit() {
        open("http://localhost:9999");
        $("[data-test-id=name] input").setValue("Иванов Дмитрий");
        $("[data-test-id=phone] input").setValue("+799955588454");
        $("[data-test-id=agreement]").click();
        $("button").click();
        $("[data-test-id=phone]").shouldHave(cssClass("input_invalid"));
        $("[data-test-id=phone] .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldSendNumberStartsWithEight() {
        open("http://localhost:9999");
        $("[data-test-id=name] input").setValue("Иванов Дмитрий");
        $("[data-test-id=phone] input").setValue("89995554477");
        $("[data-test-id=agreement]").click();
        $("button").click();
        $("[data-test-id=phone]").shouldHave(cssClass("input_invalid"));
        $("[data-test-id=phone] .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }
}