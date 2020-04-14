package Base;

import java.io.BufferedReader;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;

import com.galenframework.actions.*;

import org.apache.commons.io.FileUtils;
import org.apache.http.client.utils.URIBuilder;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

//import com.galenframework.actions.GalenActionGenerate;
import com.galenframework.api.Galen;

import com.galenframework.api.GalenPageDump;
import com.galenframework.api.PageDump;
import com.galenframework.reports.GalenTestInfo;
import com.galenframework.reports.HtmlReportBuilder;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
public class Base {
	public static  Properties prop;
	 public String URL;
	 private static final String START ="#Declare objects/ web-elements with css and xpath locators";
		private static final String line2 ="@objects";
		private static final String line3 ="# general objects";
	 public static WebDriver driver=null;
	 
	
	 public Base() throws IOException {
		 prop = new Properties();
		   FileInputStream ip=new FileInputStream("src\\main\\java\\Config\\config.properties");
		   
		   prop.load(ip);
	 }
	 
	 public void initialization() throws InterruptedException, IOException {
		 WebDriverManager.chromedriver().setup();
			System.out.println("Browser is launching");
			Map<String, Object> deviceMetrics = new HashMap<String, Object>();
			Map<String, Object> mobileEmulation = new HashMap<String, Object>();
			deviceMetrics.put("width", 1488);
         deviceMetrics.put("height", 3640);
         deviceMetrics.put("pixelRatio", 1.0);
         mobileEmulation.put("deviceMetrics", deviceMetrics);
			ChromeOptions chromeOptions = new ChromeOptions();
			//chromeOptions.addArguments("--headless");
			chromeOptions.addArguments("--disable-dev-shm-usage");
			chromeOptions.addArguments("--no-sandbox");
            chromeOptions.addArguments("--no-cache");
			chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);
			driver=new ChromeDriver(chromeOptions);
				driver.get(prop.getProperty("URL"));
				driver.manage().window().maximize();
	 } 
	 
	 
	 public void create_report(List<GalenTestInfo> objGalentestsList) throws IOException {
		 
		 HtmlReportBuilder htmlReportBuilder = new HtmlReportBuilder();
		 htmlReportBuilder.build(objGalentestsList, "Report");
		
		 
	 }
	 public String getcurrentdateandtime(){
			String str = null;
			try{
			DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss:SSS");
			Date date = new Date();
			str= dateFormat.format(date);
			str = str.replace(" ", "").replaceAll("/", "").replaceAll(":", "");
	}catch(Exception e){

}
		return str;
}
	 public void initialization_withpagedump() throws InterruptedException, IOException {
		 WebDriverManager.chromedriver().setup();
			System.out.println("Browser is launching");
			Map<String, Object> deviceMetrics = new HashMap<String, Object>();
			Map<String, Object> mobileEmulation = new HashMap<String, Object>();
			deviceMetrics.put("width", 1488);
         deviceMetrics.put("height", 3640);
         deviceMetrics.put("pixelRatio", 1.0);
         mobileEmulation.put("deviceMetrics", deviceMetrics);
			ChromeOptions chromeOptions = new ChromeOptions();
			//chromeOptions.addArguments("--headless");
			chromeOptions.addArguments("--disable-dev-shm-usage");
			chromeOptions.addArguments("--no-sandbox");
            chromeOptions.addArguments("--no-cache");
			chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);
			driver=new ChromeDriver(chromeOptions);
				driver.get(prop.getProperty("URL"));
				driver.manage().window().maximize();
				 String report_name="Galen_report";
			Thread.sleep(20000);
				new GalenPageDump("Home page").dumpPage(driver,prop.getProperty("input"),prop.getProperty("output"));
				 Thread.sleep(30000);
			
            File html_file=new File("src/main/java/resources/output/page.html");
            String path=html_file.getAbsolutePath();
			driver.get("file:"+path);
           System.out.println("file:"+path);

			Thread.sleep(2000);
			
			 List<WebElement>allitems=driver.findElements(By.xpath("//ul/li")); 
		        int elemntsize=allitems.size();
		        System.out.println(elemntsize);
		        System.out.println(allitems);
		        for (WebElement element: allitems)
		        {
		           System.out.println(element.getText());
		            element.click();
		        }
//		        
//		      
		        WebElement specs=driver.findElement(By.xpath(".//*[@id='object-suggestions']/div"));
		        List<WebElement> childs =specs.findElements(By.xpath(".//*"));        
		        File file1 = new File("src/main/java/resources/suggesion.gspec");
		        		// if file doesnt exists, then create it
				if (!file1.exists()) 
				{
					file1.createNewFile();
				}
				
				//write previous file content
				
				List<String> startContent= readLines("src/main/java/resources/inputspec.gspec");
				//System.out.println(startContent);
				startContent.add("= Page: Home page ="); 
	            startContent.add("  @on desktop_GeneralWebelementAttributes");
		        for (WebElement childelement: childs)
		        {

		        	
		           // System.out.println(childelement.getText());
		            String chtxt=childelement.getText();
		            System.out.println(chtxt);
		            
		            //startContent.add(chtxt);
		            String delims = "\n"; // these are my delimiters
		            StringTokenizer st= new StringTokenizer(chtxt,delims);
		            
		            while(st.hasMoreElements())
		            {
		               	String msg =(String)st.nextElement();
		            	if(msg.contains("e+3%"))
		            	{
		            		String msg1=msg.replace("e+3%", "%");
		            		msg1="";
		               		startContent.add(msg1);
		            	}
		            	else if(msg.contains("Infinity%"))
		            	{
		            		String msg2=msg.replace("Infinity%", "10%");
		            		msg2="";
		            		startContent.add(msg2);
		            	}
		            	else if(msg.contains("NaN%"))
		            	{
		            		String msg3=msg.replace("NaN%", "10%");
		            		msg3="";
		            		startContent.add(msg3);
		            	}
		            	
		            	else
		            	{
		            		startContent.add("   "+msg);
		            	}
		            	if(msg.contains("."))
		            	{
//		            		String str = new Double(msg).toString().substring(0,msg.indexOf('.'));
//		            		double v = Double.valueOf(str);
		            		System.out.print(msg);
		            	int msg4=msg.indexOf(".");
		            	int msg5=msg.indexOf("%");
		            	System.out.print(msg4);
		            	System.out.print(msg5);
		            	System.out.print(msg.length());
		            	String msg6=msg.substring(msg4,msg5);
//		            		String sample=Double. toString(v);
//		            		String	msg6=sample.replaceAll(": ", "");
		            	float f= Float.parseFloat(msg6);
		            	Math.round(f);
		            	Math.round(f);
		            	String convertTostring=Float.toString(f);
		            	msg.replaceAll(msg6, convertTostring);
		            	
		            	}
		            }}   FileUtils.writeLines(file1, startContent);
		            driver.get(prop.getProperty("URL"));
		            driver.manage().window().maximize();
		            Thread.sleep(20000);
	 } 
	 public static List<String> readLines(String filename) throws IOException   
	    {  
	        FileReader fileReader = new FileReader(filename);  
	          
	        BufferedReader bufferedReader = new BufferedReader(fileReader);  
	        List<String> lines = new ArrayList<String>();  
	        String line = null;  
	          boolean isstartindexfound =false;
//	          lines.add(START); 
//		        lines.add(line2);
//		        lines.add(line3);
		       
	        while ((line = bufferedReader.readLine()) != null)
	        	{//System.out.println(line);
	        lines.add(line); 
	        	}
	        bufferedReader.close();  
	          
	        return lines;  
	    }
	
}