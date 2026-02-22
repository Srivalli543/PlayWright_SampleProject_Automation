package com.PlayWright.SampleProject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.junit.UsePlaywright;
import com.microsoft.playwright.options.AriaRole;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

@UsePlaywright(HeadlessChromeOptions.class)
public class PlaywrightFormsTest {
	
	@BeforeEach
	void openContactPage(Page page) {
		page.navigate("https://practicesoftwaretesting.com/contact");
	}
	
	@DisplayName("Complete Form")
	@Test
	void completeForm(Page page) throws URISyntaxException{
		Locator firstNameField=page.getByLabel("First Name");
		Locator lastNameField=page.getByLabel("Last Name");
		Locator emailField=page.getByLabel("Email");
		Locator messageField=page.getByLabel("Message");
		Locator subject=page.getByLabel("Subject");
		Locator uploadFile=page.getByLabel("Attachment");

		
		firstNameField.fill("Dietmar");
		lastNameField.fill("Terrohrist");
		emailField.fill("dietmarterrohrist@external.com");
		messageField.fill("Hello Playwright Test");
		subject.selectOption("Warranty");
		
		Path filetoUpload=Paths.get(ClassLoader.getSystemResource("Data/Test.txt").toURI());
		
		page.setInputFiles("#attachment", filetoUpload);
		
		assertThat(firstNameField).hasValue("Dietmar");
		assertThat(lastNameField).hasValue("Terrohrist");
		assertThat(emailField).hasValue("dietmarterrohrist@external.com");
		assertThat(messageField).hasValue("Hello Playwright Test");
		assertThat(subject).hasValue("warranty");
		
		String uploadedFile=uploadFile.inputValue();
		org.assertj.core.api.Assertions.assertThat(uploadedFile).endsWith("Test.txt");
	}
	@DisplayName("Mandatory Fileds")
	@ParameterizedTest
	@ValueSource(strings= {"First Name","Last Name","Email","Message"})
	void mandatoryFields(String fieldName, Page page) {
		Locator firstNameField=page.getByLabel("First Name");
		Locator lastNameField=page.getByLabel("Last Name");
		Locator emailField=page.getByLabel("Email");
		Locator messageField=page.getByLabel("Message");
		Locator subject=page.getByLabel("Subject");
		Locator sendBtn=page.getByText("Send");
		
		//Filling the field values
		firstNameField.fill("Dietmar");
		lastNameField.fill("Terrohrist");
		emailField.fill("dietmarterrohrist@external.com");
		messageField.fill("Hello Playwright Test");
		subject.selectOption("Warranty");
		
		//clear any one field
		page.getByLabel(fieldName).clear();
		
		sendBtn.click();
		
		//check the error msg 
		Locator errorMsg=page.getByRole(AriaRole.ALERT).getByText(fieldName+" is required");
		assertThat(errorMsg).isVisible();
	}

}
