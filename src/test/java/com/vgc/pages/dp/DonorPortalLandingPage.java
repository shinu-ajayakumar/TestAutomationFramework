package com.vgc.pages.dp;

import com.tmb.enums.WaitStrategy;
import com.tmb.pages.BasePage;
import org.openqa.selenium.By;

public class DonorPortalLandingPage extends BasePage {

    public static final String HEADER_MENU_PARENT = "//div[@class='top-nav-box']//a[contains(.,'HEADERMENUITEM')]";
    public static final String LINK_FOOTER_MENUS = "//h2[contains(@id,'footer-menu')]/..//a[contains(.,'FOOTERMENUITEM')]";

    public DonorPortalOpenAccountPage clickHeaderMenu(String headerMenuItem) {
        By linkHeaderMenuItem = By.xpath(HEADER_MENU_PARENT.replace("HEADERMENUITEM",headerMenuItem));
        click(linkHeaderMenuItem, WaitStrategy.CLICKABLE,headerMenuItem);
        return new DonorPortalOpenAccountPage();
    }
    public DonorPortalOpenAccountPage clickFooterMenu(String headerMenuItem) {
        By linkFooterMenuItem = By.xpath(LINK_FOOTER_MENUS.replace("FOOTERMENUITEM",headerMenuItem));
        click(linkFooterMenuItem, WaitStrategy.CLICKABLE,headerMenuItem);
        return new DonorPortalOpenAccountPage();
    }
}
