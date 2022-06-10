import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ApplicationDebetCardTest {
    private WebDriver driver;

    @BeforeAll
    public static void setUpAll(){
        System.setProperty("webdriver.chrome.driver","./drivers/win/chromedriver.exe");
    }

    @BeforeEach
    public void setUp(){
        driver = new ChromeDriver();
    }

    @AfterEach
    public void tearDown(){
        driver.quit();
        driver = null;
    }

    @Test
    public void validData(){
        driver.get("http://localhost:9999/");
        driver.findElement(By.name("name")).sendKeys("Василий");
        driver.findElement(By.name("phone")).sendKeys("+79112223344");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector("button")).click();

        String actual = driver.findElement(By.cssSelector("[data-test-id = 'order-success']")).getText().trim();
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";

        assertEquals (expected,actual);
    }

    @Test
    public void dataSpaceValid(){
        driver.get("http://localhost:9999/");
        driver.findElement(By.name("name")).sendKeys("Василий Васильевич");
        driver.findElement(By.name("phone")).sendKeys("+79112223344");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector("button")).click();

        String actual = driver.findElement(By.cssSelector("[data-test-id = 'order-success']")).getText().trim();
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";

        assertEquals (expected,actual);
    }


    @Test
    public void dataHiphenValid(){
        driver.get("http://localhost:9999/");
        driver.findElement(By.name("name")).sendKeys("Василий-Васильевич");
        driver.findElement(By.name("phone")).sendKeys("+79112223344");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector("button")).click();

        String actual = driver.findElement(By.cssSelector("[data-test-id = 'order-success']")).getText().trim();
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";

        assertEquals (expected,actual);
    }

    @Disabled
    @Test
    public void yoNameValid(){
        driver.get("http://localhost:9999/");
        driver.findElement(By.name("name")).sendKeys("Васёлий");
        driver.findElement(By.name("phone")).sendKeys("+79112223344");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector("button")).click();

        String actual = driver.findElement(By.cssSelector("[data-test-id = 'order-success']")).getText().trim();
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";

        assertEquals (expected,actual);
    }

    @Test
    public void emptyForm(){
        driver.get("http://localhost:9999/");
        List<WebElement> elements = driver.findElements(By.cssSelector("[class = input__sub"));
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector("button")).click();

        String actual = elements.get(0).getText().trim();
        String expected = "Поле обязательно для заполнения";

        assertEquals (expected,actual);
    }

    @Test
    public void fieldNameEmpty(){
        driver.get("http://localhost:9999/");
        List<WebElement> elements = driver.findElements(By.cssSelector("[class = input__sub"));
        driver.findElement(By.name("phone")).sendKeys("+79112223344");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector("button")).click();

        String actual = elements.get(0).getText().trim();
        String expected = "Поле обязательно для заполнения";

        assertEquals (expected,actual);
    }

    @Test
    public void emptyFieldPhone(){
        driver.get("http://localhost:9999/");
        List<WebElement> elements = driver.findElements(By.cssSelector("[class = input__sub"));
        driver.findElement(By.name("name")).sendKeys("Василий");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector("button")).click();

        String actual = elements.get(1).getText().trim();
        String expected = "Поле обязательно для заполнения";

        assertEquals (expected,actual);
    }

    @Test
    public void littleNumberPhone(){
        driver.get("http://localhost:9999/");
        List<WebElement> elements = driver.findElements(By.cssSelector("[class = input__sub"));
        driver.findElement(By.name("name")).sendKeys("Василий");
        driver.findElement(By.name("phone")).sendKeys("123456");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector("button")).click();

        String actual = elements.get(1).getText().trim();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";

        assertEquals (expected,actual);
    }

    @Test
    public void nonePlusPhone(){
        driver.get("http://localhost:9999/");
        List<WebElement> elements = driver.findElements(By.cssSelector("[class = input__sub"));
        driver.findElement(By.name("name")).sendKeys("Василий");
        driver.findElement(By.name("phone")).sendKeys("89211112233");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector("button")).click();

        String actual = elements.get(1).getText().trim();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";

        assertEquals (expected,actual);
    }

    @Test
    public void signsToPhone(){
        driver.get("http://localhost:9999/");
        List<WebElement> elements = driver.findElements(By.cssSelector("[class = input__sub"));
        driver.findElement(By.name("name")).sendKeys("Василий");
        driver.findElement(By.name("phone")).sendKeys("+791103321**");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector("button")).click();

        String actual = elements.get(1).getText().trim();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";

        assertEquals (expected,actual);
    }

    @Test
    public void phoneManyFigure(){
        driver.get("http://localhost:9999/");
        List<WebElement> elements = driver.findElements(By.cssSelector("[class = input__sub"));
        driver.findElement(By.name("name")).sendKeys("Василий");
        driver.findElement(By.name("phone")).sendKeys("+79211112236565656563");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector("button")).click();

        String actual = elements.get(1).getText().trim();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";

        assertEquals (expected,actual);
    }

    @Test
    public void wrongLanguageName(){
        driver.get("http://localhost:9999/");
        List<WebElement> elements = driver.findElements(By.cssSelector("[class = input__sub"));
        driver.findElement(By.name("name")).sendKeys("Vasilii");
        driver.findElement(By.name("phone")).sendKeys("+79110236589");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector("button")).click();

        String actual = elements.get(0).getText().trim();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";

        assertEquals (expected,actual);
    }

    @Test
    public void figureToName(){
        driver.get("http://localhost:9999/");
        List<WebElement> elements = driver.findElements(By.cssSelector("[class = input__sub"));
        driver.findElement(By.name("name")).sendKeys("Василий21");
        driver.findElement(By.name("phone")).sendKeys("+79110236589");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector("button")).click();

        String actual = elements.get(0).getText().trim();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";

        assertEquals (expected,actual);
    }

    @Test
    public void signsToName(){
        driver.get("http://localhost:9999/");
        List<WebElement> elements = driver.findElements(By.cssSelector("[class = input__sub"));
        driver.findElement(By.name("name")).sendKeys("Василий*");
        driver.findElement(By.name("phone")).sendKeys("+79110236589");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector("button")).click();

        String actual = elements.get(0).getText().trim();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";

        assertEquals (expected,actual);
    }

    @Test
    public void nonClickCheckbox(){
        driver.get("http://localhost:9999/");
        driver.findElement(By.name("name")).sendKeys("Василий*");
        driver.findElement(By.name("phone")).sendKeys("+79110236589");
        driver.findElement(By.cssSelector("button")).click();
        assertFalse(driver.findElement(By.cssSelector(".checkbox__control")).isSelected());
    }

    @Test
    public void clickCheckbox(){
        driver.get("http://localhost:9999/");
        driver.findElement(By.name("phone")).sendKeys("+79110236589");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector("button")).click();

        assertTrue(driver.findElement(By.cssSelector(".checkbox__control")).isSelected());
    }
}
