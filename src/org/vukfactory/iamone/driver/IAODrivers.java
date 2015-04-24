package org.vukfactory.iamone.driver;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;

import java.io.File;
import java.io.FileInputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.Assert;

public class IAODrivers {
	
	public static WebDriver driver;
	private DesiredCapabilities objCapability;
	private  String OS = null;
	
	@SuppressWarnings("static-access")
	DesiredCapabilities capabilities = new DesiredCapabilities();
	
	
	public WebDriver setWebBrowser(String sBrowserType)
	{
		if(sBrowserType.equals("Firefox") && sBrowserType.equalsIgnoreCase("firefox"))
		{
			driver = setFirefoxBrowser();
		}
		else if(sBrowserType.equals("chrome") && sBrowserType.equalsIgnoreCase("chrome"))
		{
			driver = setChromeBrowser();
		}
		else if(sBrowserType.equals("InternetExplorer") && sBrowserType.equalsIgnoreCase("InternetExplorer"))
		{
			driver = setInternetExplorerBrowser();
		}
		return driver;
		
	}
	
	//This method will invoke InternetExplorer driver
	public WebDriver setInternetExplorerBrowser() 
	{
		if (isthisWindowsOS()) 
		{
			objCapability = DesiredCapabilities.internetExplorer();
			 try {
				driver = new RemoteWebDriver(new URL("http://localhost:5555"), objCapability);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			return driver;
	}
		
	//This method will invoke Firefox driver
	public WebDriver setFirefoxBrowser() 
	{
		driver = new FirefoxDriver();
		return driver;
	}
	
	//This method will invoke chrome driver
	public WebDriver setChromeBrowser() 
	{
		if (isthisWindowsOS()) {
			
			 objCapability = DesiredCapabilities.chrome();
			 objCapability.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			 ChromeOptions options = new ChromeOptions();
			 options.addArguments("test-type");
			 objCapability.setCapability(ChromeOptions.CAPABILITY, options);
			 try {
				driver = new RemoteWebDriver(new URL("http://localhost:9515"), objCapability);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} 
		
		return driver;
	}
	
	//This method will invoke Safari driver
	public WebDriver setSafariBrowser() 
	{
			driver = new SafariDriver();
			return driver;
	}
	
	//This method will invoke Android driver with Chrome as Browser
	public WebDriver setAndroidBrowser(String sDeviceName, String sOSVersion, String sServerName) {

        capabilities.setCapability(CapabilityType.BROWSER_NAME, "Chrome");
        capabilities.setCapability("deviceName",sDeviceName);
        capabilities.setCapability("platformVersion", sOSVersion);
        capabilities.setCapability("platformName",MobilePlatform.ANDROID);
        capabilities.setCapability("app", "chrome");
        
        try {
			driver = new AndroidDriver(new URL(sServerName), capabilities);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return driver;
	}

	//This method will invoke Android driver with Chrome as Browser
	public AndroidDriver setAndroidApp(String sDeviceName, String sOSVersion,String sAppPath,String sPackageName, String sActivityName,String sServerName) {

			File fappPath = new File(sAppPath);
	        capabilities.setCapability("deviceName",sDeviceName);
	        capabilities.setCapability("platformVersion", sOSVersion);
	        capabilities.setCapability("platformName",MobilePlatform.ANDROID);
	        capabilities.setCapability("app", fappPath.getAbsolutePath());
	        capabilities.setCapability("appPackage", sPackageName);
	        capabilities.setCapability("appActivity", sActivityName);
	        capabilities.setCapability(CapabilityType.BROWSER_NAME, "Android");
	        capabilities.setCapability("autoWebview", true);
	        
	        try {
				driver = new AndroidDriver(new URL(sServerName), capabilities);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return (AndroidDriver) driver;
		}
	public IOSDriver setIPhoneBrowser(String sDeviceName, String sOSVersion,String sServerName) {

		capabilities.setCapability("browserName", "safari");
		capabilities.setCapability("platformVersion", sOSVersion);
		capabilities.setCapability("platformName", MobilePlatform.IOS);
		capabilities.setCapability("deviceName", sDeviceName);

        try {
			driver = new IOSDriver(new URL(sServerName), capabilities);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (IOSDriver) driver;
	}
	public IOSDriver setIOSApp(String sDeviceName, String sOSVersion,String sAppPath,String sServerName) {

		File fappPath = new File(sAppPath);
		capabilities.setCapability("browserName", "");
	    capabilities.setCapability("platformVersion", sOSVersion);
	    capabilities.setCapability("platformName", MobilePlatform.IOS);
	    capabilities.setCapability("deviceName", sDeviceName);
	    capabilities.setCapability("app", fappPath.getAbsolutePath());

        try {
			driver = new IOSDriver(new URL(sServerName), capabilities);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (IOSDriver) driver;
	}
	
	
	public void closeDriver(WebDriver driver) 
	{
			driver.quit();
	}	
	
	
	public static void main(String args[])
	{
		IAODrivers od = new IAODrivers();
		WebDriver driver = od.setSafariBrowser();
		od.closeDriver(driver);
	}
	
	public  String getOperatingSystem()
	   {
	      if(OS == null) { OS = System.getProperty("os.name"); }
	      return OS;
	   }
	public  boolean isthisWindowsOS()
	   {
	      return getOperatingSystem().startsWith("Windows");
	   }

}
