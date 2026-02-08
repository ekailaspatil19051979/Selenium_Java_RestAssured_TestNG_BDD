package utils;

import com.github.javafaker.Faker;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

public class FakerUtil {
    private static final Faker faker = new Faker();

    public static String getFirstName() {
        return faker.name().firstName();
    }

    public static String getLastName() {
        return faker.name().lastName();
    }

    public static String getEmail() {
        return faker.internet().emailAddress();
    }

    public static String getPhoneNumber() {
        return faker.phoneNumber().cellPhone();
    }

    public static int getRandomPrice(int min, int max) {
        return faker.number().numberBetween(min, max);
    }

    public static String getFutureDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(faker.date().future(30, TimeUnit.DAYS));
    }

    public static String getPastDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(faker.date().past(30, TimeUnit.DAYS));
    }

    public static String getRandomAdjective() {
        return faker.lorem().word();
    }
}
