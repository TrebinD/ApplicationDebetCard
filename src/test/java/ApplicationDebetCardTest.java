import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeOptions;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ApplicationDebetCardTest {

    WebDriver driver;


    @BeforeAll
    static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setupTest() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
    }

    @AfterEach
    void teardown() {
        driver.quit();
        driver = null;
    }


    @Test
    public void validData() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.name("name")).sendKeys("Петров Василий");
        driver.findElement(By.name("phone")).sendKeys("+79112223344");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector("button")).click();

        String actual = driver.findElement(By.cssSelector("[data-test-id = 'order-success']")).getText().trim();
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";

        assertEquals(expected, actual);
    }


    @Test
    public void dataHiphenValid() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.name("name")).sendKeys("Кирилл Орлов-Львовский");
        driver.findElement(By.name("phone")).sendKeys("+79112223344");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector("button")).click();

        String actual = driver.findElement(By.cssSelector("[data-test-id = 'order-success']")).getText().trim();
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";

        assertEquals(expected, actual);
    }

    @Test
    public void yoNameValid() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.name("name")).sendKeys("Василий Потёмкин");
        driver.findElement(By.name("phone")).sendKeys("+79112223344");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector("button")).click();

        String actual = driver.findElement(By.cssSelector("[data-test-id = 'order-success']")).getText().trim();
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";

        assertEquals(expected, actual);
    }

    @Test
    public void emptyForm() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector("button")).click();

        String actual = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText().trim();
        String expected = "Поле обязательно для заполнения";

        assertEquals(expected, actual);
    }

    @Test
    public void fieldNameEmpty() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.name("phone")).sendKeys("+79112223344");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector("button")).click();

        String actual = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText().trim();
        String expected = "Поле обязательно для заполнения";

        assertEquals(expected, actual);
    }

    @Test
    public void emptyFieldPhone() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.name("name")).sendKeys("Василий");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector("button")).click();

        String actual = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText().trim();
        String expected = "Поле обязательно для заполнения";

        assertEquals(expected, actual);
    }

    @Test
    public void littleNumberPhone() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.name("name")).sendKeys("Василий");
        driver.findElement(By.name("phone")).sendKeys("123456");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector("button")).click();

        String actual = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText().trim();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";

        assertEquals(expected, actual);
    }

    @Test
    public void nonePlusPhone() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.name("name")).sendKeys("Василий");
        driver.findElement(By.name("phone")).sendKeys("89211112233");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector("button")).click();

        String actual = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText().trim();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";

        assertEquals(expected, actual);
    }

    @Test
    public void signsToPhone() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.name("name")).sendKeys("Василий");
        driver.findElement(By.name("phone")).sendKeys("+791103321**");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector("button")).click();

        String actual = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText().trim();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";

        assertEquals(expected, actual);
    }

    @Test
    public void phoneManyFigure() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.name("name")).sendKeys("Василий");
        driver.findElement(By.name("phone")).sendKeys("+79211112236565656563");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector("button")).click();

        String actual = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText().trim();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";

        assertEquals(expected, actual);
    }

    @Test
    public void wrongLanguageName() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.name("name")).sendKeys("Vasilii");
        driver.findElement(By.name("phone")).sendKeys("+79110236589");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector("button")).click();

        String actual = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText().trim();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";

        assertEquals(expected, actual);
    }

    @Test
    public void figureToName() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.name("name")).sendKeys("Василий21");
        driver.findElement(By.name("phone")).sendKeys("+79110236589");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector("button")).click();

        String actual = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText().trim();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";

        assertEquals(expected, actual);
    }

    @Test
    public void signsToName() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.name("name")).sendKeys("Василий*");
        driver.findElement(By.name("phone")).sendKeys("+79110236589");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector("button")).click();

        String actual = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText().trim();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";

        assertEquals(expected, actual);
    }

    @Test
    public void nonclickCheckbox() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.name("name")).sendKeys("Василий Теркин");
        driver.findElement(By.name("phone")).sendKeys("+79110236589");
        driver.findElement(By.cssSelector("button")).click();

        assertTrue(driver.findElement(By.cssSelector("[data-test-id='agreement'].input_invalid ")).isDisplayed());
    }
}
