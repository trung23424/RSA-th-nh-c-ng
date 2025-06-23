package client;

import view.ChatRoomView;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import security.MyKeyManager;
import javax.swing.SwingUtilities;
import security.EncryptionUtil;
import security.MyKeyManager;
import java.security.PrivateKey;


public class SocketClientHandler implements Runnable {
    private BufferedReader in;
    private ChatRoomView chatView;
    private String currentUser;

    public SocketClientHandler(BufferedReader in, ChatRoomView chatView, String currentUser) {
        this.in = in;
        this.chatView = chatView;
        this.currentUser = currentUser;
    }

    @Override
    public void run() {
        try {
            String serverMessage;
            while ((serverMessage = in.readLine()) != null) {
                // KIỂM TRA VÀ GIẢI MÃ TIN NHẮN RSA
                if (serverMessage.startsWith("/rsamsg ")) {
                    String[] parts = serverMessage.split(" ", 3);
                    if (parts.length == 3) {
                        String sender = parts[1];
                        String encryptedBase64 = parts[2];
                        System.out.println("Base64 nhận được: " + encryptedBase64);
                        try {
                            byte[] encryptedBytes = Base64.getDecoder().decode(encryptedBase64);
                            byte[] decryptedBytes = security.EncryptionUtil.decrypt(encryptedBytes, MyKeyManager.getPrivateKey());
                            String originalMessage = new String(decryptedBytes, StandardCharsets.UTF_8);


                            // Hiển thị tin nhắn đã giải mã
                            String finalMessage = sender + ": " + originalMessage;
                            boolean isMine = sender.equals(currentUser);
                            SwingUtilities.invokeLater(() -> chatView.addMessage(finalMessage, isMine));

                        } catch (Exception e) {
                            System.err.println("❌ Lỗi giải mã tin nhắn từ " + sender);
                            e.printStackTrace();
                        }
                    }
                } 
                else if (serverMessage.startsWith("/sendfile ")) {
                    String[] parts = serverMessage.split(" ", 3);
                    if (parts.length == 3) {
                        String sender = parts[1];
                        String fileName = parts[2];

                        String message = "📁 " + sender + " đã gửi file: " + fileName + " – [Tải về]";
                        boolean isMine = sender.equals(currentUser);
                        SwingUtilities.invokeLater(() -> chatView.addMessage(message, isMine));
                     // ✅ THÊM VÀO ĐÂY: xử lý cho phép click để tải
                        chatView.attachDownloadAction(message);
                    }
                }
                else {
                    // Xử lý các tin nhắn hệ thống/thông thường khác
                    final String finalMsg = serverMessage;
                    boolean isMine = finalMsg.startsWith(currentUser + ":");
                    SwingUtilities.invokeLater(() -> chatView.addMessage(finalMsg, isMine));
                }
            }
        } catch (IOException e) {
            System.err.println("Mất kết nối server: " + e.getMessage());
        }
    }
}