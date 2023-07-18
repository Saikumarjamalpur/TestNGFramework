package testcases;

import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import core.Page;
import java.sql.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.annotations.DataProvider;

public class FacebookTest extends Page
{
	// global
	@FindBy(xpath = xpath.FacebookXpath.uid)
	WebElement uid;
	
	@FindBy(xpath = xpath.FacebookXpath.pwd)
	WebElement password;
	
  @Test(dataProvider = "loginData")
  public void login(String id, String pwd,String x) 
  {
	 // driver.findElement(By.xpath("//*[@id=\"email\"]")).sendKeys(id);
	  
	      // or..
	  uid.sendKeys(id);
	  
	  log.debug("entered uid "+uid);
	  test.log(LogStatus.PASS, "Entered uid "+uid);
	  
	  password.sendKeys(pwd);
	  log.debug("entered pwdd "+pwd);
	  test.log(LogStatus.PASS, "Entered pwd "+pwd);
	  
  }

  @DataProvider
  public Object[][] loginData() throws Exception 
  {
	  ResultSetMetaData rsmt=rs.getMetaData();
	  int columncount=rsmt.getColumnCount();

	  rs.last();
	  int rowcount=rs.getRow();

	  System.out.println(columncount+" , "+rowcount);
	  rs.beforeFirst(); // reset

	  Object data[][] = new Object[rowcount][columncount]; //-> size of array 
	  			
	  for(int rowNum = 1 ; rowNum <= rowcount ; rowNum++)
	     { 
	  				
	  for(int colNum=1 ; colNum<= columncount; colNum++)
	        {
	                   rs.absolute(rowNum); // point to row  
	  	String data1= rs.getString(colNum); // getting values from excel
	  	 System.out.println(data1);

	  		data[rowNum-1][colNum-1]= data1 ; //adding table data in  array , array starts from 0
	  				}
	  			}
	  			
	 	  return data;

  }
}
