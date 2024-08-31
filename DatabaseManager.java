import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date; 
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import javax.swing.JPasswordField;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;


public class DatabaseManager {
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/User_Management_App";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Abhiram@2004";
    private static final String AES_KEY = "aesEncryptionKey";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
    }

    public static void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS users ("
                + "id INT AUTO_INCREMENT PRIMARY KEY,"
                + "Username VARCHAR(255) NOT NULL,"
                + "Email VARCHAR(255) NOT NULL,"
                + "Password VARCHAR(255) NOT NULL"
                + ");";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            String encoded = Base64.getEncoder().encodeToString(hash);
            return encoded;
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Error while hashing password: " + e.getMessage());
            return null;
        }
    }

    private static byte[] encrypt(String strToEncrypt) {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            SecretKeySpec secretKey = new SecretKeySpec(AES_KEY.getBytes(), "AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return cipher.doFinal(strToEncrypt.getBytes());
        } catch (Exception e) {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }

    private static String decrypt(byte[] strToDecrypt) {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            SecretKeySpec secretKey = new SecretKeySpec(AES_KEY.getBytes(), "AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(strToDecrypt));
        } catch (Exception e) {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }

    public static boolean checkUser(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String storedPassword = rs.getString("Password");
                String decryptedPassword = decrypt(Base64.getDecoder().decode(storedPassword));
                return password.equals(decryptedPassword);
            }
            return false;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static void insertUser(String username, String email, String password) {
        String sql = "INSERT INTO users(Username, Email, Password) VALUES(?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            byte[] encryptedPassword = encrypt(hashPassword(password));
            String encryptedPasswordBase64 = Base64.getEncoder().encodeToString(encryptedPassword);
            pstmt.setString(1, username);
            pstmt.setString(2, email);
            pstmt.setString(3, encryptedPasswordBase64);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void updateUser(String username, String firstName, String lastName, String mobileNumber, String gender, Date dob) {
        String sql = "UPDATE users SET first_name = ?, last_name = ?, mobile_number = ?, gender = ?, dob = ? WHERE Username = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, mobileNumber);
            pstmt.setString(4, gender);
            pstmt.setDate(5, new java.sql.Date(dob.getTime())); 
            pstmt.setString(6, username);
            
            int rowsUpdated = pstmt.executeUpdate();
            System.out.println("Rows updated: " + rowsUpdated); 
        } catch (SQLException e) {
            System.out.println("Error updating user: " + e.getMessage()); 
            e.printStackTrace(); 
        }
    }

    public static ArrayList<UserInfoPage.User> getAllUsers() {
        ArrayList<UserInfoPage.User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String mobileNumber = rs.getString("mobile_number");
                String email = rs.getString("email");
                String dob = rs.getString("dob"); // Assuming dob is stored as a string in the database
                String gender = rs.getString("gender"); // Assuming gender is stored as a string in the database
                // Create UserInfoPage.User object and add it to the list
                users.add(new UserInfoPage.User(firstName, lastName, mobileNumber, email, dob, gender));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
}

