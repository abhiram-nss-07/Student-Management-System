import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserInfoPage extends JFrame {
    private ArrayList<User> users;

    public UserInfoPage(ArrayList<User> users) {
        this.users = users;

        setTitle("User Information");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 900);
        setLocationRelativeTo(null);
        setResizable(false);

        JLabel titleLabel = new JLabel("User Information");
        titleLabel.setBounds(300, 20, 200, 30);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 20));

        JTextArea userInfoTextArea = new JTextArea();
        userInfoTextArea.setEditable(false);

        // Populate the text area with user information
        for (User user : users) {
            userInfoTextArea.append("First Name: " + user.getFirstName() + "\n");
            userInfoTextArea.append("Last Name: " + user.getLastName() + "\n");
            userInfoTextArea.append("Mobile Number: " + user.getMobileNumber() + "\n");
            userInfoTextArea.append("Email: " + user.getEmail() + "\n");
            userInfoTextArea.append("Date of Birth: " + user.getDob() + "\n");
            userInfoTextArea.append("Gender: " + user.getGender() + "\n");
            userInfoTextArea.append("\n");
        }

        JScrollPane scrollPane = new JScrollPane(userInfoTextArea);
        scrollPane.setBounds(50, 60, 700, 600);

        JButton backButton = new JButton("Back");
        backButton.setBounds(350, 680, 100, 30);
        backButton.addActionListener(e -> {
            dispose();
            new AdminLoginPage();
        });

        backButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                backButton.setBackground(Color.RED);
                backButton.setForeground(Color.WHITE);
                backButton.setFont(new Font("Arial", Font.BOLD, 14));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                backButton.setBackground(UIManager.getColor("Button.background"));
                backButton.setForeground(UIManager.getColor("Button.foreground"));
                backButton.setFont(new Font("Arial", Font.PLAIN, 12));
            }
        });

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                int a = JOptionPane.showConfirmDialog( null, "Do You Want To Close ?");
                if (a == JOptionPane.YES_OPTION) {
                    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                }
            }
        });

        setLayout(null);
        add(titleLabel);
        add(scrollPane);
        add(backButton);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setVisible(true);
    }

    // User class to represent user information
    static class User {
        private String firstName;
        private String lastName;
        private String mobileNumber;
        private String email;
        private String dob;
        private String gender;

        public User(String firstName, String lastName, String mobileNumber, String email, String dob, String gender) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.mobileNumber = mobileNumber;
            this.email = email;
            this.dob = dob;
            this.gender = gender;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public String getMobileNumber() {
            return mobileNumber;
        }

        public String getEmail() {
            return email;
        }

        public String getDob() {
            return dob;
        }

        public String getGender() {
            return gender;
        }
    }

    public static void main(String[] args) {
        // Fetch user data from the database
        ArrayList<User> users = getAllUsers();

        SwingUtilities.invokeLater(() -> new UserInfoPage(users));
    }

    public static ArrayList<User> getAllUsers() {
        ArrayList<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String mobileNumber = rs.getString("mobile_number");
                String email = rs.getString("email");
                String dob = rs.getString("dob");
                String gender = rs.getString("gender");
                users.add(new User(firstName, lastName, mobileNumber, email, dob, gender));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/User_Management_App";
        String username = "root";
        String password = "Abhiram@2004";
        return DriverManager.getConnection(url, username, password);
    }
}
