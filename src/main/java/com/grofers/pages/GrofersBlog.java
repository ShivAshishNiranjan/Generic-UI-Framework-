package com.grofers.pages;

import com.grofers.test.ConfigureConstant;
import com.grofers.utility.ConfigReader;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

public class GrofersBlog {

	Logger logger = LoggerFactory.getLogger(GrofersBlog.class);

	WebDriver driver;
	String pageUrl;
	String baseUrl;
	List<String> linksToValidate;
	String facebookPageUrl;
	String twitterPageUrl;
	String instagramPageUrl;
	String linkedinPageUrl;
	int totalSocialMediaButton;

	By pageFooter = By.xpath("//h5[contains(text(),'Grofers India Pvt. Ltd. 2016-2017')]");
	By socialMediaButtons = By.xpath("//*[contains(text(),'Connect with us')]/..//li[@class='social']//a");

	By likeArticleButtons = By.xpath("//button[contains(@class,'kodex_button kodex_like_button')]");
	By likeArticleCounters = By.xpath("//button[contains(@class,'kodex_button kodex_like_button')]//span[@class='counter']");

	public GrofersBlog(WebDriver driver) throws ConfigurationException {

		this.driver = driver;
		String configFilePath = ConfigureConstant.getConfigFilesValue("grofersconfigfilepath");
		String configFileName = ConfigureConstant.getConfigFilesValue("grofersfilename");

		baseUrl = ConfigReader.getValueFromConfigFile(configFilePath, configFileName, "default", "baseurl");
		pageUrl = ConfigReader.getValueFromConfigFile(configFilePath, configFileName, "default", "pageurl");

		facebookPageUrl = ConfigReader.getValueFromConfigFile(configFilePath, configFileName, "socialmedia", "facebookurl");
		twitterPageUrl = ConfigReader.getValueFromConfigFile(configFilePath, configFileName, "socialmedia", "instaurl");
		instagramPageUrl = ConfigReader.getValueFromConfigFile(configFilePath, configFileName, "socialmedia", "twitterurl");
		linkedinPageUrl = ConfigReader.getValueFromConfigFile(configFilePath, configFileName, "socialmedia", "linkedinurl");
		totalSocialMediaButton = Integer.parseInt(ConfigReader.getValueFromConfigFile(configFilePath, configFileName, "socialmedia", "totalbutton"));

		linksToValidate = Arrays.asList(ConfigReader.getValueFromConfigFile(configFilePath, configFileName, "linkstovalidate", "linktext").split(","));
	}

	public List<String> getLinksToValidate() {
		return linksToValidate;
	}

	public String getFacebookPageUrl() {
		return facebookPageUrl;
	}

	public String getTwitterPageUrl() {
		return twitterPageUrl;
	}

	public String getInstagramPageUrl() {
		return instagramPageUrl;
	}

	public String getLinkedinPageUrl() {
		return linkedinPageUrl;
	}

	public int getTotalSocialMediaButton() {
		return totalSocialMediaButton;
	}

	public String getPageUrl() {
		return pageUrl;
	}

	public String getBaseUrl() {
		return baseUrl;
	}


	public boolean validatePageFooter() {
		WebElement element = driver.findElement(pageFooter);
		if (element != null)
			return true;
		else
			return false;
	}

	public List<WebElement> getAllSocialMediaInteractionButtons() {
		List<WebElement> elements = driver.findElements(socialMediaButtons);
		return elements;
	}

	// this function will return map of all the like button with their counter counts
	public LinkedHashMap<WebElement, Integer> getAllLikeArticlesButtonsWithCounterCounts() {
		List<WebElement> buttons = driver.findElements(likeArticleButtons);
		List<WebElement> counters = driver.findElements(likeArticleCounters);
		LinkedHashMap<WebElement, Integer> map = new LinkedHashMap<WebElement, Integer>();

		try {
			int i = 0;
			for (WebElement button : buttons) {
				map.put(button, Integer.parseInt(counters.get(i++).getText()));
			}
		} catch (Exception e) {
			logger.error("Error : [{}] , While Making Map of Counters Count with Like Button", e.getLocalizedMessage());
		}
		return map;
	}

	public boolean validateLinkExistance(String linkText) {
		WebElement element = driver.findElement(By.xpath("//a[contains(text(),'" + linkText + "')]"));
		if (element != null)
			return true;
		else
			return false;
	}


}

