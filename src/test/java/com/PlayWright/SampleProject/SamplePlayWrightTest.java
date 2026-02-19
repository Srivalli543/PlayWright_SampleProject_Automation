package com.PlayWright.SampleProject;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class SamplePlayWrightTest {
	
	Playwright playwright;
	Browser browser;
	Page page;
	
	@BeforeEach
	public void setup() {
		playwright=Playwright.create();
		browser=playwright.chromium().launch();
		page=browser.newPage();
		
		page.navigate("https://practicesoftwaretesting.com/");
	}
	
	@AfterEach
	public void tearDown() {
		browser.close();
		playwright.close();
	}

	@Test
	public void DisplayTitle() {
		String title=page.title();
		System.out.println("Page Title is: " + title);
		Assertions.assertTrue(title.contains("Practice Software Testing"));		
	}
}
