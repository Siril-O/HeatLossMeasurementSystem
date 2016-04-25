package ua.heatloss;


import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {

    public static void main(String... args) {
        ConfigurableApplicationContext repositoryContext = new ClassPathXmlApplicationContext(
                "repositoryContext.xml");

        ConfigurableApplicationContext context = new ClassPathXmlApplicationContext(
                new String[]{"appContext.xml"}, repositoryContext);
    }
}
