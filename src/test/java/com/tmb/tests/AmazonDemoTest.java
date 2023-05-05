package com.tmb.tests;

import com.google.common.util.concurrent.Uninterruptibles;
import com.tmb.annotations.FrameworkAnnotation;
import com.tmb.driver.DriverManager;
import com.tmb.enums.CategoryType;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.tmb.enums.LogType.INFO;
import static com.tmb.enums.LogType.PASS;
import static com.tmb.reports.FrameworkLogger.log;

/**
 * Contains the test cases of Amazon website for practice.
 *
 * <pre>
 * <b>
 * <a href="https://www.youtube.com/channel/UC6PTXUHb6j4Oxf0ccdRI11A">Testing Mini Bytes Youtube channel</a>
 * </b>
 * </pre>
 * Jan 22, 2021
 *
 * @author Amuthan Sakthivel
 * @version 1.0
 * @since 1.0
 */
public final class AmazonDemoTest extends BaseTest {

    /**
     * Private constructor to avoid external instantiation
     */
    private AmazonDemoTest() {
    }

    /**
     * Test Name mentioned here should match the column name "testname" in excel sheet.This is mandatory to run this
     * test. Otherwise it will be ignored. <p>
     * The match has to be there in both of the RUNMANAGER and TESTDATA sheet.
     * Set the authors who have the created the test which will be logged to the reports.
     * Set the category which this particular test case belongs to
     *
     * @param data HashMap containing all the values of test data needed to run the tests
     * @author Amuthan Sakthivel
     * Jan 22, 2021
     */
    @Test
    @FrameworkAnnotation(author = {"Amuthan", "Sachin"},
            category = {CategoryType.REGRESSION, CategoryType.MINIREGRESSION})
    public void amazonTest(Map<String, String> data) {
        List<String> urls = new ArrayList<>();
        urls.add("https://www.youtube.com/watch?v=k61NqkS2pbU");
        urls.add("https://www.youtube.com/watch?v=J8CbTnZnmbA");
        for(String eachUrl : urls) {
            while (true) {
                DriverManager.getDriver().navigate().to(eachUrl);
                Uninterruptibles.sleepUninterruptibly(1, TimeUnit.SECONDS);
                By btnSkip = By.xpath("//div[text()='Skip Ads']");
                By btnPlay = By.xpath("//button[@aria-label='Play']");
                By btnPlayNow = By.xpath("//a[text()='Play Now']");
                By btnReplay = By.xpath("//button[@title='Replay']");
                int skipCount = DriverManager.getDriver().findElements(btnSkip).size();
                int playCount = DriverManager.getDriver().findElements(btnPlay).size();
                int playNowCount = DriverManager.getDriver().findElements(btnPlayNow).size();
                int replayCount = DriverManager.getDriver().findElements(btnReplay).size();
                if (skipCount > 0) {
                    try {
                        new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(1)).until(ExpectedConditions.elementToBeClickable(btnSkip));
                        DriverManager.getDriver().findElement(btnSkip).click();
                        log(PASS, "Clicked button : Skip");
                    } catch (Exception e) {
                        log(INFO, "Button not displayed : Skip");
                    }
                }
                if (playCount > 0) {
                    try {
                        new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(1)).until(ExpectedConditions.elementToBeClickable(btnPlay));
                        DriverManager.getDriver().findElement(btnPlay).click();
                        log(PASS, "Clicked button : Play");
                    } catch (Exception e) {
                        log(INFO, "Button not displayed : Play");
                    }
                }
                if (playNowCount > 0) {
                    try {
                        new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(1)).until(ExpectedConditions.elementToBeClickable(btnPlayNow));
                        log(PASS, "Found button : Play Now, So skipping the test");
                        break;
                    } catch (Exception e) {
                        log(INFO, "Button not displayed : Play Now");
                    }
                }
                if (replayCount > 0) {
                    try {
                        new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(1)).until(ExpectedConditions.elementToBeClickable(btnReplay));
                        log(PASS, "Found button : Replay, So skipping the test");
                        break;
                    } catch (Exception e) {
                        log(INFO, "Button not displayed : Replay");
                    }
                }
            }
        }
		/*String title =new AmazonHomePage().clickHamburger()
				.clickComputer()
				.clickOnSubMenuItem(data.get("menutext")).getTitle();
		Assertions.assertThat(title).isNotNull();*/
    }
}
