package security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordUtil {

    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(password.getBytes());

            // Chuyển byte thành hex string
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b)); // 2 chữ số thập lục phân
            }
            return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not found!", e);
        }
    }
    public static void main(String[] args) {
        String original = "123"; // password gốc
        String hashed = hashPassword(original);
        System.out.println("Mã hóa SHA-256 của '123':");
        System.out.println(hashed);
    }
}

