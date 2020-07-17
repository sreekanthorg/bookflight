package utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PageHelper {

	public static WebElement WaitForElement(WebDriver driver, By element) {
		WebDriverWait wait  = new WebDriverWait(driver, 60);
		return wait.until(ExpectedConditions.elementToBeClickable(element));
	}
	
	public static String getNextDate() {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM, yyyy");
		calendar.add(Calendar.DAY_OF_MONTH, 1);		
		return dateFormat.format(calendar.getTime());		
	}
	
	public static int convertToMinutes(String timeDuration) {		
		String[] arr = timeDuration.split(" ");
		int value = 0;
		for(int i=0; i<arr.length; i++) {
			if(arr[i].contains("h")) {
				value = Integer.valueOf(arr[i].replace("h", ""))*60;
			} else if(arr[i].contains("m")) {
				value += Integer.valueOf(arr[i].replace("m", ""));
			}			
		}
		return value;
	}
    public static void waitForPageToLoad(WebDriver driver, long timeOutInSeconds) {
        ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
            }
        };
        try {
            WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
            wait.until(expectation);
        } catch (Throwable error) {
            error.printStackTrace();
        }
    }
}
