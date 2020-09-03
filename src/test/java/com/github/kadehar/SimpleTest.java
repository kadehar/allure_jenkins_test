package com.github.kadehar;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;

public class SimpleTest {
    @BeforeAll
    public static void init() {
        Configuration.headless = true;
        SelenideLogger.addListener("allure", new AllureSelenide()
                .savePageSource(true)
                .screenshots(true));
    }

    @Test
    public void testArchiveDeposits() {
        step("Открыть главную страницу", () -> open("https://alfabank.ru/"));

        step("Открыть страницу Вклады", () -> {
            step("Перейти на страницу Вклады", () ->
                    $$(byText("Вклады")).find(visible).click()
            );
            step("Проверить что страница Вклады открыта", () ->
                    $(".sub-navigation__h1").$("a")
                            .shouldHave(text("Вклады и инвестиции"))
            );
        });

        step("Открыть страницу Депозиты", () -> {
            step("Перейти на страницу Депозиты", () ->
                    $$(byText("Депозиты")).find(visible).click()
            );
            step("Проверить что страница Депозиты открыта", () ->
                    $(".product-cell__cell-header").shouldHave(text("Самый высокий доход"))
            );
        });

        step("Открыть страницу Архивные депозиты", () -> {
            step("Перейти на страницу Архивные депозиты", () ->
                    $(byText("Архивные депозиты")).scrollIntoView(false).click()
            );
            step("Проверить что страница Архивные депозиты содержит 3 элемента", () ->
                    $$(".product-cell__cell").shouldHave(size(3))
            );
        });
    }

    @AfterEach
    public void closeDriver() {
        Selenide.closeWebDriver();
    }
}
