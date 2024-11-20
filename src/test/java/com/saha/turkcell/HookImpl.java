package com.saha.turkcell;

import com.saha.turkcell.selector.Selector;
import com.saha.turkcell.selector.SelectorFactory;
import com.saha.turkcell.selector.SelectorType;
import com.saha.turkcell.utils.ReadProperties;
import com.thoughtworks.gauge.AfterScenario;
import com.thoughtworks.gauge.AfterStep;
import com.thoughtworks.gauge.BeforeScenario;
import com.thoughtworks.gauge.ExecutionContext;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.FluentWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

public class HookImpl {
    protected static AppiumDriver<MobileElement> appiumDriver;
    private Logger logger = LoggerFactory.getLogger(getClass());

    protected static FluentWait<AppiumDriver> appiumFluentWait;
    public static boolean localAndroid = false;
    public static boolean isDeviceAnd = false;
    protected static Selector selector ;
    public static String senarioName;
    public static ResourceBundle kartBilgileri;
    public static ResourceBundle kullanıcıBilgileri;
    public static final String USERNAME = "mehmetaksahin";
    public static final String ACCESS_KEY = "5f646c3d670e9f439874d71d56d1d5b8";
    public static final String KEY = USERNAME + ":" + ACCESS_KEY;
    public static final String URL = "http://hub.testinium.io/wd/hub";
    private static AppiumDriver<AndroidElement> driver;
    String key = "mehmetaksahin:5f646c3d670e9f439874d71d56d1d5b8";



    @BeforeScenario
    public void beforeScenario() throws MalformedURLException {
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!Test basliyor!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        if (StringUtils.isEmpty(key)) {
            if (localAndroid) {
                isDeviceAnd=true;
                logger.info("Local Browser");
                DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
                desiredCapabilities
                        .setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
                // desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "android");
                desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "device");
                desiredCapabilities
                        .setCapability(AndroidMobileCapabilityType.APP_PACKAGE,
                                "com.ttech.android.onlineislem");
                desiredCapabilities
                        .setCapability(AndroidMobileCapabilityType.APP_ACTIVITY,
                                "com.ttech.android.onlineislem.ui.splash.SplashActivity");
                desiredCapabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "uiautomator2");
                //desiredCapabilities
                //      .setCapability(MobileCapabilityType.NO_RESET, true);
                // desiredCapabilities
                //       .setCapability(MobileCapabilityType.FULL_RESET, false);
                desiredCapabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 3000);
                // desiredCapabilities.setCapability("unicodeKeyboard", true);
                // desiredCapabilities.setCapability("resetKeyboard", true);
                URL url = new URL("http://127.0.0.1:4723/wd/hub");
                appiumDriver = new AndroidDriver(url, desiredCapabilities);
            } else {
                isDeviceAnd=false;
                DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
                desiredCapabilities
                        .setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.IOS);
                desiredCapabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "XCUITest");
                desiredCapabilities.setCapability(MobileCapabilityType.UDID, "00008030-0011654C0A00802E");
                // desiredCapabilities.setCapability(MobileCapabilityType.UDID, "00008101-0008195A1180001E");
                //  desiredCapabilities.setCapability(MobileCapabilityType.UDID, "932ddc86eadf9c4cf5aa08392147ff999d5b2fbb");

                desiredCapabilities
                        .setCapability(IOSMobileCapabilityType.BUNDLE_ID, "com.turkcell.CSI");
                desiredCapabilities
                        .setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone 11");

                desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "15.1");
                //  desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "14.1");
                //   desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "12.0");

                desiredCapabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 300);
                URL url = new URL("http://127.0.0.1:4723/wd/hub");
                appiumDriver = new IOSDriver(url, desiredCapabilities);


            }
        } else {
            //String hubURL = "http://hub.testinium.io/wd/hub";
            //String hubURL = "http://localhost:4444/wd/hub";
            String hubURL ="https://hubclouddev.testinium.com";



            DesiredCapabilities capabilities = new DesiredCapabilities();
            System.out.println("key:" + System.getenv("key"));
            System.out.println("platform" + System.getenv("platform"));
            System.out.println("version" + System.getenv("version"));

            if (System.getenv("platform").equals("ANDROID")) {
                isDeviceAnd=true;
                // capabilities.setCapability("key", System.getenv("key"));
                //capabilities.setCapability("key", "mehmetaksahin:66e85f66c902b99253229b608203e5e2");  //clouddev
                //capabilities.setCapability("key", System.getenv("key"));  //clouddev
                capabilities.setCapability("key", "mehmetaksahin:b0fa2cca656533bb82e5978f677b4b4a");  //clouddev


                capabilities
                        .setCapability(AndroidMobileCapabilityType.APP_PACKAGE,
                                "com.ttech.android.onlineislem");
                capabilities.setCapability("testinium:appToken", System.getenv("appToken"));
                capabilities.setCapability("testinium:testID", System.getenv("testID"));


                capabilities
                        .setCapability(AndroidMobileCapabilityType.APP_ACTIVITY,
                                "com.ttech.android.onlineislem.ui.splash.SplashActivity");
                capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME,"uiautomator2");
                capabilities.setCapability(MobileCapabilityType.NO_RESET, true);
                capabilities.setCapability(MobileCapabilityType.FULL_RESET, false);
                capabilities.setCapability("unicodeKeyboard", true);
                capabilities.setCapability("resetKeyboard", true);
                appiumDriver = new AndroidDriver(new URL(hubURL), capabilities);
                localAndroid = true;
            } else {
                isDeviceAnd=false;
                localAndroid=false;
                System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!İos Test basliyor!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                logger.info("ıos testinium");

                capabilities.setCapability(MobileCapabilityType.NO_RESET, true);
                capabilities.setCapability(MobileCapabilityType.FULL_RESET, false);
                //---------------------------------------
                capabilities.setCapability(CapabilityType.PLATFORM, Platform.MAC);
                capabilities.setCapability("usePrebuiltWDA", true); //changed
                // capabilities.setCapability("maxTypeFrequency", 5);
                // capabilities.setCapability("key", System.getenv("key"));
                // capabilities.setCapability("key", "mehmetaksahin:afbe7d859ad86b5149f75077c52035e3");
                //capabilities.setCapability("key", "enesturan:39572507e9c3db2d6e975ae537a9c80b");
                //capabilities.setCapability("key", "mehmetaksahin:66e85f66c902b99253229b608203e5e2");  //prod
               // capabilities.setCapability("key", "mehmetaksahin:66e85f66c902b99253229b608203e5e2");  //clouddev
                capabilities.setCapability("key", "mehmetaksahin:b0fa2cca656533bb82e5978f677b4b4a");  //clouddev
               // capabilities.setCapability("key", System.getenv("key"));
                capabilities.setCapability("testinium:appToken", System.getenv("appToken"));
                capabilities.setCapability("testinium:testID", System.getenv("testID"));
                capabilities.setCapability("waitForAppScript", "$.delay(1000);");
                capabilities.setCapability("bundleId", "com.pharos.Gratis");
                capabilities.setCapability("useNewWDA", false);

                appiumDriver = new IOSDriver(new URL(hubURL), capabilities);

            }
        }
        selector = SelectorFactory
                .createElementHelper(localAndroid ? SelectorType.ANDROID : SelectorType.IOS);
        //appiumDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        appiumFluentWait = new FluentWait(appiumDriver);
        appiumFluentWait.withTimeout(Duration.ofSeconds(40))
                .pollingEvery(Duration.ofMillis(250))
                .ignoring(NoSuchElementException.class);
    }


    @AfterScenario
    public void afterScenario(){

        if(appiumDriver != null){
            appiumDriver.quit();
        }

        logger.info("*************************************************************************" + "\r\n");
    }

    @AfterStep
    public void afterStep(ExecutionContext executionContext){

        if(executionContext.getCurrentStep().getIsFailing()) {
            logger.info(executionContext.getCurrentStep().getErrorMessage());
            logger.info(executionContext.getCurrentStep().getStackTrace());
        }
    }
}


















