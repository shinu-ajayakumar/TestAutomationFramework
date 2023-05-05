package com.tmb.pages;

import com.google.common.util.concurrent.Uninterruptibles;
import com.tmb.driver.Driver;
import com.tmb.driver.DriverManager;
import com.tmb.enums.WaitStrategy;
import com.tmb.factories.ExplicitWaitFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.tmb.enums.LogType.*;
import static com.tmb.reports.FrameworkLogger.log;

public class BasePage {

      /**
     * Locates element by given wait strategy, performs the clicking operation on webelement and
     * writes the pass even to the extent report.
     *
     * @param by           By Locator of the webelement
     * @param waitstrategy Strategy to find webelement. Known  strategies {@link com.tmb.enums.WaitStrategy}
     * @param elementname  Name of the element that needs to be logged in the report.
     * @author Amuthan Sakthivel
     * Jan 22, 2021
     */
    protected void click(By by, WaitStrategy waitstrategy, String elementname) {
        WebElement element = ExplicitWaitFactory.performExplicitWait(waitstrategy, by);
        try {
            JavascriptExecutor javascriptExecutor = (JavascriptExecutor) DriverManager.getDriver();
            javascriptExecutor.executeScript("arguments[0].scrollIntoView(true);",element);
            javascriptExecutor.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", element);
            Uninterruptibles.sleepUninterruptibly(500, TimeUnit.MILLISECONDS);
            javascriptExecutor.executeScript("arguments[0].setAttribute('style', 'border:none');", element);
            new Actions(DriverManager.getDriver()).moveToElement(element).click().build().perform();
            log(PASS, "Clicked element : " + elementname);
        } catch (Exception e) {
            log(FAIL, "Failed to click element '" + elementname + "' with xpath '" + by + "'");
            log(FAIL, e.toString());
        }
    }

    /**
     * Locates element by given wait strategy, sends the value to located webelement and
     * writes the pass even to the extent report.
     *
     * @param by           By Locator of the webelement
     * @param value        value to be send the text box
     * @param waitstrategy Strategy to find webelement. Known  strategies {@link com.tmb.enums.WaitStrategy}
     * @param elementname  Name of the element that needs to be logged in the report.
     * @author Amuthan Sakthivel
     * Jan 22, 2021
     */
    protected void sendKeys(By by, String value, WaitStrategy waitstrategy, String elementname) {
        WebElement element = ExplicitWaitFactory.performExplicitWait(waitstrategy, by);
        element.sendKeys(value);
        log(PASS, value + " is entered successfully in " + elementname);
    }

    /**
     * Return page title of webpage in String
     *
     * @return Page title of the webpage where the selenium is currently interacting.
     * @author Amuthan Sakthivel
     * Jan 22, 2021
     */
    protected String getPageTitle() {
        return Driver.getPageTitle();
    }

    protected String getElementText(String elementXpath) {
        String elementText = DriverManager.getDriver().findElement(By.xpath(elementXpath)).getText().trim();
        log(INFO, "Fetched element text : " + elementText);
        return elementText;
    }

    protected String waitForAnyOfTheGivenElement(List<String> xpaths) {
        return xpaths.stream().filter(eachXpath -> DriverManager.getDriver().findElements(By.xpath(eachXpath)).size() > 0)
                .findFirst()
                .get();
    }
}
