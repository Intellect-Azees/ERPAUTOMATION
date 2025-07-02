package Utilities;

import org.openqa.selenium.WebElement;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class HandelCaptcha {
    private WebElement captchaElement;
    public String text;

    public HandelCaptcha(WebElement captchaElement) {
        this.captchaElement = captchaElement;
    }

    public Boolean captcha() {
        if (captchaElement != null) {
            try {
                // Extract the src attribute
            	String tempImg = captchaElement.getAttribute("src");

                if (tempImg == null || tempImg.isEmpty()) {
                    throw new IOException("Captcha src attribute is empty");
                }

                String[] parts = tempImg.split(","); 
                String imageData = parts.length > 1 ? parts[1] : parts[0];
                byte[] imageBytes = Base64.getDecoder().decode(imageData); 
                ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
                BufferedImage bufferedImage = ImageIO.read(bis);

                Tesseract tesseract = new Tesseract();
                String tessdataPath = "D:\\aa\\e-PACS\\src\\main\\resources\\tessdata";
                System.setProperty("TESSDATA_PREFIX", tessdataPath);
                tesseract.setDatapath(tessdataPath);  
                tesseract.setLanguage("eng");  
                text = tesseract.doOCR(bufferedImage);
                return true;
            } catch (IOException | TesseractException e) {
                e.printStackTrace();
                return false;
            }   
        } else {
            return false;
        }
    }
}
