package view;

import dao.UserDAO;
import model.User;
import security.PasswordUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.security.MessageDigest;

public class RegisterView extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnSubmit, btnBack;

    public RegisterView() {
        setTitle("Đăng ký tài khoản");
        setSize(400, 230);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(20, 30, 20, 30));
        mainPanel.setBackground(Color.WHITE);

        JLabel title = new JLabel("TẠO TÀI KHOẢN MỚI", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setBorder(new EmptyBorder(0, 0, 20, 0));
        mainPanel.add(title, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        formPanel.setBackground(Color.WHITE);
        formPanel.add(new JLabel("Tên đăng nhập:"));
        txtUsername = new JTextField();
        formPanel.add(txtUsername);
        formPanel.add(new JLabel("Mật khẩu:"));
        txtPassword = new JPasswordField();
        formPanel.add(txtPassword);
        mainPanel.add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        btnSubmit = new JButton("Đăng ký");
        btnBack = new JButton("Trở về");
        btnSubmit.setPreferredSize(new Dimension(120, 30));
        btnBack.setPreferredSize(new Dimension(120, 30));
        buttonPanel.add(btnSubmit);
        buttonPanel.add(btnBack);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // Xử lý nút đăng ký
        btnSubmit.addActionListener(e -> {
            // ✅ Lấy thông tin từ người dùng nhập
            String username = txtUsername.getText().trim();
            String rawPassword = new String(txtPassword.getPassword()).trim();

            // ✅ Kiểm tra rỗng
            if (username.isEmpty() || rawPassword.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin.");
                return;
            }

            // 🔐 Mã hóa mật khẩu bằng SHA-256
            String hashedPassword = PasswordUtil.hashPassword(rawPassword);

            // ✅ Tạo user với mật khẩu đã mã hóa
            User newUser = new User(username, hashedPassword);
            boolean success = UserDAO.register(newUser);

            if (success) {
                JOptionPane.showMessageDialog(this, "🎉 Đăng ký thành công!");
                dispose(); // đóng form đăng ký
                new LoginView(); // quay về form đăng nhập
            } else {
                JOptionPane.showMessageDialog(this, "❌ Đăng ký thất bại. Tài khoản có thể đã tồn tại.");
            }
        });


        // Xử lý nút trở về
        btnBack.addActionListener(e -> dispose());

        setVisible(true);
    }
}