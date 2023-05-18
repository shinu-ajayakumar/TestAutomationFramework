package com.vgc.web.tests;

import com.tmb.annotations.FrameworkAnnotation;
import com.tmb.enums.CategoryType;
import com.tmb.tests.BaseTest;
import com.tmb.utils.ExcelUtils;
import com.vgc.web.pages.dp.DPLandingPage;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

import java.util.Map;

public class DPLandingPageTests extends BaseTest {

    private DPLandingPageTests() {
    }

    @Test
    @FrameworkAnnotation(author = {"Shinu", "TestAuthor"},
            category = {CategoryType.REGRESSION, CategoryType.MINIREGRESSION})
    public void verifyHeaderLinksAndTitle(Map<String, String> data) {
        String headerText = new DPLandingPage().clickHeaderMenu(data.get("menutext")).getTitle();
        Assertions.assertThat(headerText).isNotNull();
        //ExcelUtils.writeTestData("menutext2",headerText);
    }

    @Test
    @FrameworkAnnotation(author = {"Shinu", "TestAuthor"},
            category = {CategoryType.REGRESSION, CategoryType.MINIREGRESSION})
    public void verifyFooterLinksAndTitle(Map<String, String> data) {
        String headerText = new DPLandingPage().clickFooterMenu(data.get("menutext")).getTitle();
        Assertions.assertThat(headerText).isNotNull();
    }
    @Test
    @FrameworkAnnotation(author = {"Shinu", "TestAuthor"},
            category = {CategoryType.REGRESSION, CategoryType.MINIREGRESSION})
    public void verifyHeaderLinksAndHeaderText(Map<String, String> data) {
        String headerText = new DPLandingPage().clickHeaderMenu(data.get("menutext")).getHeaderText();
        Assertions.assertThat(headerText).isNotNull();
    }

    @Test
    @FrameworkAnnotation(author = {"Shinu", "TestAuthor"},
            category = {CategoryType.REGRESSION, CategoryType.MINIREGRESSION})
    public void verifyFooterLinksAndHeaderText(Map<String, String> data) {
        String headerText = new DPLandingPage().clickFooterMenu(data.get("menutext")).getHeaderText();
        Assertions.assertThat(headerText).isNotNull();
    }
}