package Utils;

import java.util.Properties;

public class CalculatorProperties {


    public static final String PATH_TO_PROPERTIES = "src/test/resources/test";

    static private Properties testProperty = new Properties();

    static {
        try {
            String propertyName = System.getProperty("");
            testProperty.load(CalculatorProperties.class.getClassLoader().getResourceAsStream(propertyName + ".properties"));


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String propertyName) {

        return testProperty.getProperty(propertyName);
    }

    public static String getProperty(String propertyName, String defaultValue) {
        return testProperty.getProperty(propertyName, defaultValue);
    }


}
