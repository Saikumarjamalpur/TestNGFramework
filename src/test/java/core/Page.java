package core;

import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import java.time.Duration;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.TestException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.AfterTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
public class Page 
{
	// global variable
	public WebDriver driver = null;
	public Connection con = null;
	public Statement stm = null;
	public ResultSet rs = null;
	public Logger log = null;
	public ExtentReports report = null;
	public ExtentTest test = null;
  
  @Parameters({"browser","url","scrshot"})	
  @BeforeMethod
  public void openBrowser(String browser, String url, String scrshot) throws Exception 
  {
	  System.out.println("browser "+browser+" url "+url);
	  if(browser.equals("chrome"))
	  {
		  driver = new ChromeDriver();
		  log.debug("chrome browser open");
		  test.log(LogStatus.PASS, "chrome open");
	  }
	  else if(browser.equals("firefox"))
	  {
		  driver = new FirefoxDriver();
		  log.debug("firefox browser open");
		  test.log(LogStatus.PASS, "firefx open");

	  }
	  
	  driver.navigate().to(url);
	  
	  log.debug("url open"+url);
	  test.log(LogStatus.PASS, "open url "+url);
	  PageFactory.initElements(driver, this); // for @FindBy
	  
	  driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60L));
	  driver.manage().window().maximize();
	  
	  // screen shot
	  File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE); // store file to temprary location
		//Now you can do whatever you need to do with it, for example copy somewhere download org.apache.commons.io.FileUtils class API set classpath and use this class to copy.
		String screenshotpath = System.getProperty("user.dir")+"\\src\\test\\java\\screenshot\\"+scrshot+".jpeg";
		
		FileUtils.copyFile(scrFile, new File(screenshotpath));

  }
  

  @AfterMethod
  public void closeBrowser()
  {
	  driver.quit();
  }

  @Parameters({"wbpath","Sheetname"})
  @BeforeClass
  public void openWBConnection(String wbpath,String Sheetname) throws Exception
  {
	  Class.forName("com.googlecode.sqlsheet.Driver");
	  con = DriverManager.getConnection("jdbc:xls:file:"+wbpath); 
	  stm = con.createStatement();
	  rs = stm.executeQuery("select * from "+Sheetname);
	  log.debug("connected to work book open");
	  test.log(LogStatus.PASS, "connected to workbook");

  }

  @AfterClass
  public void closeWBConnection() throws Exception
  {
	  con.commit();
	  con.close();
  }

  @Parameters({"filename","key"})
  @BeforeTest
  public void generateLogReport(String filename,String key) throws Exception
  {
	  if(!Boolean.parseBoolean(key))
	  {
		  

		  throw new TestException("skip test");
		  
	  }
	  else
	  {
		  
		
		// log 
					Properties p = new Properties();
					FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\"+filename+".properties");
				p.load(fis);
				PropertyConfigurator.configure(p);
				log = Logger.getLogger(filename);
				
		// report
				report = new ExtentReports(System.getProperty("user.dir")+"\\src\\test\\java\\reports\\"+filename+".html");
				test = report.startTest(filename);
				  log.debug("Report open");
				  test.log(LogStatus.PASS, "report open");

				    
	  }
  }

  @AfterTest
  public void closeReport() 
  {
	  report.endTest(test);
		report.flush();

  }

}
