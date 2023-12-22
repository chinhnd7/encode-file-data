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
            byte[] bytes = hexStringToByteArray(sha256Hash);
            String base64Hash = encodeToBase64(bytes);

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

    // Chuyển đổi chuỗi hex sang byte array
    private static byte[] hexStringToByteArray(String hexString) {
        int len = hexString.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4)
                    + Character.digit(hexString.charAt(i + 1), 16));
        }
        return data;
    }

    // Encode byte array sang base64
    private static String encodeToBase64(byte[] bytes) {
        byte[] encodedBytes = Base64.getEncoder().encode(bytes);
        return new String(encodedBytes, StandardCharsets.UTF_8);
    }
}
