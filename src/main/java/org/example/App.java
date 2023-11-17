import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main(String[] args) {
        try {
            // file pdf path
            String filePath = "testFile.pdf";

            // string data
            String pdfText = readPDF(filePath);

            // hash data
            String sha256Hash = calculateSHA256(pdfText);

            // base64 data
            String base64Hash = encodeBase64(sha256Hash);

            System.out.println("SHA-256 Hash: " + sha256Hash);
            System.out.println("Base64 Hash: " + base64Hash);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Return string data of PDF file
    private static String readPDF(String filePath) throws IOException {
        PDDocument document = PDDocument.load(new File(filePath));
        PDFTextStripper pdfTextStripper = new PDFTextStripper();
        String text = pdfTextStripper.getText(document);
        document.close();
        return text;
    }

    // Return hash data
    private static String calculateSHA256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Return base64 data
    private static String encodeBase64(String input) {
        return Base64.getEncoder().encodeToString(input.getBytes(StandardCharsets.UTF_8));
    }
}
