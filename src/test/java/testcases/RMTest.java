package testcases;

import org.testng.annotations.Test;

import core.Page;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.openqa.selenium.By;
import org.testng.annotations.DataProvider;

public class RMTest extends Page
{
  @Test(dataProvider = "dp")
  public void test(String n, String s,String x) 
  {
	  driver.findElement(By.xpath("//*[@id=\"t1\"]")).sendKeys(n);
	  driver.findElement(By.xpath("//*[@id=\"t2\"]")).sendKeys(s);
	  
	  log.debug("rm info uid "+n+" pwd "+s);
  }

  @DataProvider
  public Object[][] dp() throws Exception 
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
