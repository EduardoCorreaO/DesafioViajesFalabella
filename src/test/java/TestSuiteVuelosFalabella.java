import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;


public class TestSuiteVuelosFalabella {
    public static WebDriver driver;
    public By localizadorOrigin = By.xpath("//input[contains(@class, \"sbox-bind-reference-flight-roundtrip-origin-input\" )]");
    public By localizadorDestination= By.xpath("//input[contains(@class, \"sbox-bind-reference-flight-roundtrip-destination-input\" )]");
    public By localizadorDateStart = By.xpath("//input[contains(@class, \"sbox-bind-reference-flight-start-date-input\" )]");
    public By localizadorAutocomplete = By.xpath("//li[@class=\"item -active\"]");
    public By localizadorBtnNextDate = By.xpath("//div[@class = \"_dpmg2--controls-next\"]/descendant::i");
    public By localizadorMonthActive = By.xpath("//div[contains(@class, \"_dpmg2--month-active\")]");
    public By localizadorBtnAplicarDate = By.xpath("//button[@class=\"_dpmg2--desktopFooter-button _dpmg2--desktopFooter-button-apply sbox-3-btn -lg -primary\"]");
    public By localizadorPasajeros = By.xpath("(//div[contains(@class, \"sbox-passengers-container\")])[1]");
    public By localizadorAumentarAdultos = By.xpath("(//a[@class = \"steppers-icon-right sbox-3-icon-plus\"])[11]");
    public By localizadorBtnAplicarPasajeros = By.xpath("(//a[text() =\"Aplicar\"])[3]");
    public By localizadorBtnBuscar = By.xpath("(//div [@class = \"sbox-button-container\"] )[1]");
    public By localizadorBtnCookie = By.xpath("//a[@class=\"lgpd-banner--button eva-3-btn -white -md\"]");
    @BeforeClass
    public static void init(){
        WebDriverManager.chromedriver().setup();
    }
    @Before
    public void setUp(){
        //preparar el driver
        driver = new ChromeDriver();
        driver.manage().deleteAllCookies();
        driver.manage().window().maximize();
    }

    @Test public void VTC01(){
        int mesViajeIda = 5;
        int mesViajeVuelta = 5;
        int diaViajeIda = 1;
        int diaViajeVuelta = 5;
        int añoViajeIda = 2022;
        int añoViajeVuelta = 2022;
        String origen = "SCL";
        String destino = "BSB";
        //Abrir la pagina
        driver.get("https://www.viajesfalabella.cl/vuelos");
        WebElement cookie = driver.findElement(localizadorBtnCookie);
        if(cookie.isDisplayed()){
            cookie.click();
        }
        WebElement origin = driver.findElement(localizadorOrigin);
        origin.sendKeys(origen);
        WebDriverWait delay = new WebDriverWait(driver, Duration.ofSeconds(5));
        delay.until(ExpectedConditions.elementToBeClickable(localizadorAutocomplete));
        WebElement autocomplete = driver.findElement(localizadorAutocomplete);
        autocomplete.click();
        WebElement destination = driver.findElement(localizadorDestination);
        destination.sendKeys(destino);
        delay.until(ExpectedConditions.elementToBeClickable(localizadorAutocomplete));
        autocomplete.click();
        WebElement dateStart = driver.findElement(localizadorDateStart);
        dateStart.click();
        WebElement btnNextDate = driver.findElement(localizadorBtnNextDate);
        WebElement mesActual = driver.findElement(localizadorMonthActive);
        String monthInit = mesActual.getAttribute("data-month").split("-")[1];
        String yearInit = mesActual.getAttribute("data-month").split("-")[0];
        if (Integer.parseInt(yearInit) == añoViajeIda){
            for (int i = Integer.parseInt(monthInit); i <= mesViajeIda; i++) {
                if (i == mesViajeIda){
                    break;
                }
                btnNextDate.click();
            }
        }
        else if (Integer.parseInt(yearInit) < añoViajeIda){
            for (int i = Integer.parseInt(monthInit); i < 12 + mesViajeIda; i++) {
                if (i == mesViajeIda){
                    break;
                }
                btnNextDate.click();
            }
        }
        String dayDataIda = mesViajeIda > 9 ? añoViajeIda + "-"+ mesViajeIda : añoViajeIda+"-"+"0"+mesViajeIda;
        By localizadorDay = By.xpath("//div[contains(@data-month, \"" +  dayDataIda +"\")]/descendant::span[text()=\""+diaViajeIda+"\"]");
        WebElement day = driver.findElement(localizadorDay);
        day.click();
        WebElement mesActualVuelta = driver.findElement(localizadorMonthActive);
        String monthInitVuelta = mesActualVuelta.getAttribute("data-month").split("-")[1];
        String yearInitVuelta = mesActualVuelta.getAttribute("data-month").split("-")[0];
        if (mesViajeIda<=mesViajeVuelta || añoViajeIda<=añoViajeVuelta){
            if (Integer.parseInt(yearInitVuelta) == añoViajeVuelta){
                for (int i = Integer.parseInt(monthInitVuelta); i <= mesViajeVuelta  ; i++) {
                    if (i == mesViajeVuelta){
                        break;
                    }
                    btnNextDate.click();
                }
            }
            else if (Integer.parseInt(yearInitVuelta) < añoViajeVuelta){
                for (int i = Integer.parseInt(monthInitVuelta); i < 12 + mesViajeVuelta; i++) {
                    if (i == mesViajeVuelta){
                        break;
                    }
                    btnNextDate.click();
                }
            }
        }
        String dayDataVuelta = mesViajeVuelta > 9 ? añoViajeVuelta + "-"+ mesViajeVuelta : añoViajeVuelta+"-"+"0"+mesViajeVuelta;
        By localizadorDayR = By.xpath("//div[contains(@data-month, \"" +  dayDataVuelta +"\")]/descendant::span[text()=\""+diaViajeVuelta+"\"]");
        WebElement dayR = driver.findElement(localizadorDayR);
        dayR.click();
        WebElement aplicar = driver.findElement(localizadorBtnAplicarDate);
        aplicar.click();
        WebElement pasajeros = driver.findElement(localizadorPasajeros);
        pasajeros.click();
        WebElement adultos = driver.findElement(localizadorAumentarAdultos);
        adultos.click();
        WebElement aplicarPasajeros = driver.findElement(localizadorBtnAplicarPasajeros);
        aplicarPasajeros.click();
        WebElement btnBuscar = driver.findElement(localizadorBtnBuscar);
        btnBuscar.click();
    }



    @After
    public void close(){
       if(driver != null){
            driver.close();
        }
    }


}
