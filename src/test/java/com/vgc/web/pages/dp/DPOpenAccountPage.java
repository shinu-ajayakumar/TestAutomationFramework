package com.vgc.web.pages.dp;

import com.tmb.pages.BasePage;

import java.util.Arrays;

public class DPOpenAccountPage extends BasePage {

    private String labelHeader1 = "//div/h1";
    private String labelHeader3 = "//div/h3";

    public String getTitle() {
        waitForAnyOfTheGivenElement(Arrays.asList(labelHeader1,labelHeader3));
        return getPageTitle();
    }

    public String getHeaderText() {
        String labelHeader = waitForAnyOfTheGivenElement(Arrays.asList(labelHeader1,labelHeader3));
        return getElementText(labelHeader);
    }
}
