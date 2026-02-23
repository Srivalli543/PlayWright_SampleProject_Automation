package com.PlayWright.SampleProject;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.junit.UsePlaywright;
import com.microsoft.playwright.options.LoadState;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import java.util.Comparator;
import java.util.List;

@UsePlaywright(HeadlessChromeOptions.class)
public class PlaywrightAsssertionsTest {
	
	@DisplayName("Making assertions about the contents of a field")
    @Nested
    class LocatingElementsUsingCSS {
		  @BeforeEach
	        void openContactPage(Page page) {
	            page.navigate("https://practicesoftwaretesting.com/contact");
	        }
		  @DisplayName("Checking the value of a field")
	      @Test
	      void fieldValues(Page page) {
			  Locator firstNameField=page.getByLabel("First name");;
			  firstNameField.fill("Dietmar");
			  assertThat(firstNameField).hasValue("Dietmar");
			  assertThat(firstNameField).not().isDisabled();
			  assertThat(firstNameField).isEnabled();
			  assertThat(firstNameField).isEditable();
			  
		  }
	}
	@DisplayName("Making assertions about data values")
    @Nested
    class MakingAssertionsAboutDataValues {
		@BeforeEach
        void openContactPage(Page page) {
            page.navigate("https://practicesoftwaretesting.com");
            page.waitForCondition(()->page.getByTestId("product-name").count()>0);
        }
		@Test
		void allProductPricesShouldBeCorrectValues(Page page) {
			List<Double> prices=page.getByTestId("product-price")
					.allInnerTexts()
					.stream()
					.map(price->Double.parseDouble(price.replace("$", "")))
					.toList();
			Assertions.assertThat(prices)
			     .isNotEmpty()
			     .allMatch(price->price>0)
			     .doesNotContain(0.0)
			     .allMatch(price->price<1000)
			     .allSatisfy(price->
			     Assertions.assertThat(price)
			     .isGreaterThan(0.0)
			     .isLessThan(1000.0));
		}
		@Test
		void shouldSortInAlphabeticalOrder(Page page) {
			page.getByLabel("sort").selectOption("Name (A - Z)");
			page.waitForLoadState(LoadState.NETWORKIDLE);
			List<String> products=page.getByTestId("product-name").allTextContents();
			
			Assertions.assertThat(products).isSortedAccordingTo(Comparator.naturalOrder());
			Assertions.assertThat(products).isSortedAccordingTo(String.CASE_INSENSITIVE_ORDER);

		}
		@Test
		void shouldSortInReverseAlphabeticalOrder(Page page) {
			page.getByLabel("sort").selectOption("Name (Z - A)");
			page.waitForLoadState(LoadState.NETWORKIDLE);
			List<String> products=page.getByTestId("product-name").allTextContents();
			
			Assertions.assertThat(products).isSortedAccordingTo(Comparator.reverseOrder());
		}
	}

}
