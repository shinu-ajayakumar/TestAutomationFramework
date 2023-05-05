/*
MIT License
Copyright (c) 2021 Amuthan Sakthivel
Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:
The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */

package com.tmb.driver;

import com.tmb.enums.ConfigProperties;
import com.tmb.exceptions.BrowserInvocationFailedException;
import com.tmb.factories.DriverFactory;
import com.tmb.utils.PropertyUtils;

import java.net.MalformedURLException;
import java.util.Objects;

/**
 * 
 * Driver class is responsible for invoking and closing the browsers.
 * 
 * <p>
 * It is also responsible for 
 * setting the driver variable to DriverManager which handles the thread safety for the 
 * webdriver instance.
 * 
 * <pre>
 * <b>
 * <a href="https://www.youtube.com/channel/UC6PTXUHb6j4Oxf0ccdRI11A">Testing Mini Bytes Youtube channel</a>
 * </b>
 * </pre>
 * 

 * Jan 20, 2021 
 * @author Amuthan Sakthivel
 * @version 1.0
 * @since 1.0
 * @see DriverManager
 * @see com.tmb.tests
 */

public final class Driver {

	/**
	 * Private constructor to avoid external instantiation
	 */
	private Driver() {}

	/**
	 * Gets the browser value and initialise the browser based on that
	 * 
	 * @author Amuthan Sakthivel
	 * Jan 20, 2021
	 * @param browser Value will be passed from {@link com.tmb.tests}. Values can be chrome and firefox
	 * 
	 */
	public static void initDriver(String browser,String version)  {

		
		if(Objects.isNull(DriverManager.getDriver())) {
			try {
				DriverManager.setDriver(DriverFactory.getDriver(browser,version));
			} catch (MalformedURLException e) {
				Driver.quitDriver();
				throw new BrowserInvocationFailedException("Please check the capabilities of browser");
			}
			//maximizeWindow();
			//DriverManager.getDriver().get(PropertyUtils.get(ConfigProperties.URL));
			DriverManager.getDriver().navigate().to(PropertyUtils.get(ConfigProperties.URL));
			//DriverManager.getDriver().manage().timeouts().pageLoadTimeout(FrameworkConstants.getExplicitwait(), TimeUnit.SECONDS);
		}
	}

	/**
	 * Terminates the browser instance.
	 * Sets the threadlocal to default value, i.e null.
	 * @author Amuthan Sakthivel
	 * Jan 22, 2021
	 */
	public static void quitDriver() {
		if(Objects.nonNull(DriverManager.getDriver())) {
			DriverManager.getDriver().quit();
			DriverManager.unload();
		}
	}
	public static String getPageTitle() {
		return DriverManager.getDriver().getTitle();
	}

	public static void maximizeWindow(){
		DriverManager.getDriver().manage().window().maximize();
	}
}
