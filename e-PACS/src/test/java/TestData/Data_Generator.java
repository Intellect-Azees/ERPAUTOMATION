package TestData;

import java.util.Date;
import java.util.Random;

import com.github.javafaker.Faker;

public class Data_Generator {
	static Faker faker = new Faker();
	static Random random = new Random();
    public static String getFirtName() 
    {
    	return faker.name().firstName();
    }
    public static String getLastName() 
    {
    	return faker.name().lastName();
    }

    public static String generateAadharNumber() {
        StringBuilder aadhar = new StringBuilder();
        for (int i = 0; i < 12; i++) {
            aadhar.append(random.nextInt(10)); 
        }
        return aadhar.toString();
    }
    public static String getMemberDOB()
    {
    	Date dob=faker.date().birthday(18, 99);
    	return dob.toString();
    }
}
