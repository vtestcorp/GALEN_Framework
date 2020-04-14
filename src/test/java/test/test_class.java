package test;
import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import com.galenframework.api.Galen;
import com.galenframework.reports.GalenTestInfo;
import com.galenframework.reports.HtmlReportBuilder;
import com.galenframework.reports.model.LayoutReport;

import Base.Base;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
public class test_class extends Base{
	List<GalenTestInfo> objGalentestsList	= new LinkedList<GalenTestInfo>();
	public GalenTestInfo objSingleGalenTest;
	LayoutReport objLayoutReport;
	public String report_name;
	 
	
	 public test_class() throws IOException {
		
		// TODO Auto-generated constructor stub
	}
	 
	 
@BeforeClass
public void set_reportname() {
	 report_name="Galen_report";
	 objSingleGalenTest 		= GalenTestInfo.fromString(report_name);
	 
}


//@BeforeMethod
//	public void setup() throws InterruptedException, IOException {
//		     initialization();	
//			}	
		

@Test(priority=1,enabled=true)
	public void test_shelf() throws IOException, InterruptedException {
	initialization();
		//Create a layoutReport object
        //checkLayout function checks the layout and returns a LayoutReport object

		//Thread.sleep(30000);
		objLayoutReport =Galen.checkLayout(driver,prop.getProperty("specFilePath"), Arrays.asList("desktop_GeneralWebelementAttributes"));
		objSingleGalenTest.getReport().layout(objLayoutReport, "Page_layout");      
        objGalentestsList.add(objSingleGalenTest);

        if (objLayoutReport.errors() > 0)
        {
        	System.out.println("Layout test failed for GeneralWebelementAttributes");
            Assert.fail();
        }
        System.out.println("Layout test PASSED for GeneralWebelementAttributes");
	}

@Test(priority=2,enabled=false)
public void test_withpagedump() throws IOException, InterruptedException {
	
	//Create a layoutReport object
    //checkLayout function checks the layout and returns a LayoutReport object
	initialization_withpagedump();
	//Thread.sleep(30000);
	objLayoutReport =Galen.checkLayout(driver,prop.getProperty("suggestion"), Arrays.asList("desktop_GeneralWebelementAttributes"));
	objSingleGalenTest.getReport().layout(objLayoutReport, "Page_layout");      
    objGalentestsList.add(objSingleGalenTest);

    if (objLayoutReport.errors() > 0)
    {
    	System.out.println("Layout test failed for GeneralWebelementAttributes");
        Assert.fail();
    }
    System.out.println("Layout test PASSED for GeneralWebelementAttributes");
}



@AfterClass()
	public void close_report() throws IOException {
		create_report(objGalentestsList);
		driver.quit();
	}

}
