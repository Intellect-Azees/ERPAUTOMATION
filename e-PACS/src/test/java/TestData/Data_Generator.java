package TestData;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import com.github.javafaker.Faker;
import com.ibm.icu.text.Transliterator;

public class Data_Generator {
	static Faker faker = new Faker();
	static Random random = new Random();
//    public String getFirtName() 
//    {
//    	return faker.name().firstName();
//    }
    
	public String getFirstName() {
	    Faker faker = new Faker(new Locale("ta-IN"));

	    // Tamil → Latin transliteration
	    Transliterator toLatin = Transliterator.getInstance("Tamil-Latin");

	    // Generate Tamil name and convert to English letters
	    String tamilName = faker.name().fullName();
	    String englishName1 = toLatin.transliterate(tamilName);

	    return englishName1;
	}
//    public String getLastName() 
//    {
//    	return faker.name().lastName();
//    }
    public String getLastName() 
    {
    	Faker faker = new Faker(new Locale("ta-IN"));

	    // Tamil → Latin transliteration
	    Transliterator toLatin = Transliterator.getInstance("Tamil-Latin");

	    // Generate Tamil name and convert to English letters
	    String tamilName = faker.name().firstName();
	    String englishName = toLatin.transliterate(tamilName);

	    return englishName;
    }
    public String getMemberDOB()
    {
    	Date dob = faker.date().birthday(18, 50);
        SimpleDateFormat sample = new SimpleDateFormat("dd/MM/yyyy");
        return sample.format(dob);
    }
    public String houseNO()
    {
    	return faker.address().buildingNumber();
    }
    public String phoneNo()
    {
    	int firstDigit = 9;
    	long number = 1000000000L + random.nextInt(900000000);
    	String s = firstDigit + Long.toString(number);
    	return  s;
    }
    public String emailAddress()
    {
    	return faker.internet().emailAddress();
    }
    public String getAadharNo()
    {
    	AadhaarGenerator aadhar=new AadhaarGenerator();
    	return aadhar.generateAadhaar();
    }
    public String getGender()
    {
    	return faker.demographic().sex();
    }
    public String getSurveyNumber() 
    {
    	int mainNumber = faker.number().numberBetween(1, 999);          
        String letterPart = faker.letterify("?").toUpperCase();        
        int subNumber = faker.number().numberBetween(1, 9);
        return mainNumber + letterPart + "/" + subNumber;
    }
}
