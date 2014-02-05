package actions.selenium;

import org.openqa.selenium.WebDriver
import org.openqa.selenium.ie.InternetExplorerDriver
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.ie.InternetExplorerDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeDriverService
import org.openqa.selenium.ie.InternetExplorerDriverService
import org.openqa.selenium.remote.RemoteWebDriver
import org.openqa.selenium.remote.DesiredCapabilities

class Browser{
  
  public static WebDriver Driver = null
  public static MainWinHandle = null
  
  //start browser
  public void run(def params){
    println params
	sleep(1000)
    if (params."Browser Type" == "Firefox"){
      Driver = new FirefoxDriver()
    }
    else if (params."Browser Type" == "Chrome"){
      def service = new ChromeDriverService.Builder().usingPort(9515).usingDriverExecutable(new File("chromedriver.exe")).build()
      service.start()
      Driver = new RemoteWebDriver(service.getUrl(),DesiredCapabilities.chrome())
    }
    else{
      
      def service = new InternetExplorerDriverService.Builder().usingDriverExecutable(new File("IEDriverServer.exe")).build()
      service.start()
      DesiredCapabilities d = DesiredCapabilities.internetExplorer()
      d.setCapability("nativeEvents", false)
      d.setCapability("forceCreateProcessApi", true)
      d.setCapability("browserCommandLineSwitches", "-private")
       
      Driver = new RemoteWebDriver(service.getUrl(),d)
    }
    
    if (params.URL){
      if (params.URL.startsWith("http://")){
        Driver.get(params.URL)
      }
      else{
        Driver.get("http://"+params.URL)
      }
      
    }
    Driver.manage().window().maximize()
    Driver.manage().timeouts().implicitlyWait(10, java.util.concurrent.TimeUnit.SECONDS)
    MainWinHandle = Driver.getWindowHandle()   
    
  }
}