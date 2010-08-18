package feedthecat.tools;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AjaxTools {

	public static void waitForElement(WebDriver driver, final By by) {
		Wait<WebDriver> wait = new WebDriverWait(driver, 10);
		wait.until(new ExpectedCondition<Boolean>() {

			@Override
			public Boolean apply(WebDriver from) {
				return from.findElement(by) != null;
			}
		});

	}

}
