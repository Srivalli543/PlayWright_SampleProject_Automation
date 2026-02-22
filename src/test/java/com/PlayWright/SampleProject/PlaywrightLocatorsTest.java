package com.PlayWright.SampleProject;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.microsoft.playwright.*;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.junit.UsePlaywright;

@UsePlaywright(HeadlessChromeOptions.class)
public class PlaywrightLocatorsTest {
	
//	protected static Playwright playwright;
//	protected static Browser browser;
//	protected static BrowserContext browserContext;
//	
//	Page page;
//	
//	@BeforeAll
//	static void setUpBrowser() {
//		playwright=Playwright.create();
//		  browser = playwright.chromium().launch(
//	                new BrowserType.LaunchOptions().setHeadless(true)
//	                        .setArgs(Arrays.asList("--no-sandbox", "--disable-extensions", "--disable-gpu"))
//	        );
//	}
//	@BeforeEach
//	void setUp() {
//		browserContext=browser.newContext();
//		page=browserContext.newPage();
//	}
//	@AfterEach
//	void closeContext() {
//		browserContext.close();
//	}
//	@AfterAll
//	static void tearDown() {
//		browser.close();
//		playwright.close();
//	}
//	
	private void openPage(Page page) {
	    page.navigate("https://practicesoftwaretesting.com");
	 }
	
	@DisplayName("Locating elements by text")
	@Nested
	class LocatingElementByText{
		
		@BeforeEach
		void openTheCatalogPage(Page page) {
			openPage(page);
		}
		
	    @DisplayName("Locating element by text content")
	    @Test
	    void byText(Page page) {
		   page.getByText("Bolt Cutters").click();
		   PlaywrightAssertions.assertThat(page.getByText("MightyCraft Hardware")).isVisible();
	    }
	    
	    @DisplayName("Using alt text")
	    @Test
	    void byAltText(Page page) {
	    	page.getByAltText("Combination Pliers").click();
	    	PlaywrightAssertions.assertThat(page.getByText("ForgeFlex Tools")).isVisible();
	    }
	    
	    @DisplayName("Using title text")
	    @Test
	    void byTitleText(Page page) {
	    	page.getByAltText("Combination Pliers").click();
	    	page.getByTitle("Practice Software Testing - Toolshop").click();
	    }
	}
	
	
	@DisplayName("Locating elements using CSS")
    @Nested
	class LocatingElementByCSS{
		@BeforeEach
        void openContactPage(Page page) {
            page.navigate("https://practicesoftwaretesting.com/contact");
        }

        @DisplayName("By id")
        @Test
        void locateFirstNameByID(Page page) {
        	page.locator("#first_name").fill("Dietmar");
        	PlaywrightAssertions.assertThat(page.locator("#first_name")).hasValue("Dietmar");
        }
        @DisplayName("By CSS Class")
        @Test
        void locateSendBtnByClass(Page page) {
        	page.locator("#first_name").fill("Dietmar");
        	page.locator(".btnSubmit").click();
        	List<String> alerts=page.locator(".alert").allTextContents();
        	Assertions.assertTrue(!alerts.isEmpty());
        }
        @DisplayName("By Attribute")
        @Test
        void locateSendBtnByAttribute(Page page) {
        	page.locator("[placeholder='Your last name *']").fill("Terrohrist");
            PlaywrightAssertions.assertThat(page.locator("#last_name")).hasValue("Terrohrist");
        }
	}
	
	
	
}
	

















