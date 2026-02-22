package com.PlayWright.SampleProject;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.junit.UsePlaywright;
import com.microsoft.playwright.options.AriaRole;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import java.util.List;

@UsePlaywright(HeadlessChromeOptions.class)
public class AddingItemsToTheCart {
	@DisplayName("Search for Pliers")
	@Test
	void SearchForPliers(Page page) {
		
		page.navigate("https://practicesoftwaretesting.com/");
		
		page.getByPlaceholder("Search").fill("Pliers");
		page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Search")).click();
		
		assertThat(page.locator(".card")).hasCount(4);
		
		List<String> productNames=page.getByTestId("product-name").allTextContents();
		Assertions.assertThat(productNames).allMatch(name->name.contains("Pliers"));
		
		Locator outOfStockItem=page.locator(".card").
				filter(new Locator.FilterOptions().setHasText("Out Of Stock"))
				.getByTestId("product-name");
	    assertThat(outOfStockItem).hasCount(1);
	    assertThat(outOfStockItem).hasText("Long Nose Pliers");
	}

}
