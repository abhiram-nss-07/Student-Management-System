import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class AdminLoginPage extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton eyeButton; 
    private boolean passwordVisible = false;

    public AdminLoginPage() {
        setTitle("Admin Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 400);
        setLocationRelativeTo(null);
        setResizable(false);

        // Set icon for the frame
        ImageIcon icon = new ImageIcon("icon.jpg");
        setIconImage(icon.getImage());

        JLabel titlelabel = new JLabel("Admin");
        titlelabel.setBounds(300, 50, 100, 40);
        titlelabel.setFont(new Font("Arial", Font.PLAIN, 30));

        // JLabel for username
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(200, 180, 80, 30);
        usernameField = new JTextField();
        usernameField.setBounds(300, 180, 200, 30);

        // JLabel for password
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(200, 220, 80, 30);
        passwordField = new JPasswordField();
        passwordField.setBounds(300, 220, 200, 30);

        // JButton for back
        JButton backButton = new JButton("Back");
        backButton.setBounds(200, 280, 100, 30);
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new RegisterPage();
            }
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

        // JButton for login
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(400, 280, 100, 30);
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                // Check admin credentials
                boolean isAdmin = checkAdminCredentials(username, password);

                if (isAdmin) {
                    // If admin credentials are correct, open the user info page
                    ArrayList<UserInfoPage.User> users = DatabaseManager.getAllUsers();
                    dispose();
                    showUserInfoPage(users);
                } else {
                    shakeFrame();
                    // If admin credentials are incorrect, show an error message
                    JOptionPane.showMessageDialog(AdminLoginPage.this,
                            "Invalid username or password!",
                            "Login Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        loginButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                loginButton.setBackground(Color.RED); 
                loginButton.setForeground(Color.WHITE); 
                loginButton.setFont(new Font("Arial", Font.BOLD, 14)); 
            }

            @Override
            public void mouseExited(MouseEvent e) {
                loginButton.setBackground(UIManager.getColor("Button.background")); 
                loginButton.setForeground(UIManager.getColor("Button.foreground")); 
                loginButton.setFont(new Font("Arial", Font.PLAIN, 12)); 
            }
        });

        ImageIcon eyeIconClosed = new ImageIcon("eye_closed.png"); 
        ImageIcon eyeIconOpen = new ImageIcon("eye_open.png"); 

        // Resize eye icons
        Image imgClosed = eyeIconClosed.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        Image imgOpen = eyeIconOpen.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);

        final ImageIcon finalEyeIconClosed = new ImageIcon(imgClosed); 
        final ImageIcon finalEyeIconOpen = new ImageIcon(imgOpen); 


        eyeButton = new JButton();
        eyeButton.setIcon(finalEyeIconClosed); 
        eyeButton.setBounds(520, 220, 30, 30);
        eyeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                passwordVisible = !passwordVisible; 
                if (passwordVisible) {
                    eyeButton.setIcon(finalEyeIconOpen); 
                    passwordField.setEchoChar((char) 0); 
                } else {
                    eyeButton.setIcon(finalEyeIconClosed); 
                    passwordField.setEchoChar('\u2022'); 
                }
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

        // Add components to the frame
        setLayout(null);
        add(titlelabel);
        add(usernameLabel);
        add(usernameField);
        add(passwordLabel);
        add(passwordField);
        add(backButton);
        add(loginButton);
        add(eyeButton);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setVisible(true);
    }

    private boolean checkAdminCredentials(String username, String password) {
        return username.equals("AdminLogin") && password.equals("Admin@123");
    }

    private void showUserInfoPage(ArrayList<UserInfoPage.User> users) {
        // Create and display the UserInfoPage
        SwingUtilities.invokeLater(() -> {
            UserInfoPage userInfoPage = new UserInfoPage(users);
            userInfoPage.setVisible(true);
        });
    }

    private void shakeFrame() {
        final int SHAKE_DISTANCE = 3;
        final int SHAKE_CYCLES = 3;
        final int SHAKE_DURATION = 40; 

        Point originalLocation = getLocation();

        for (int i = 0; i < SHAKE_CYCLES; i++) {
            try {
                setLocation(originalLocation.x + SHAKE_DISTANCE, originalLocation.y);
                Thread.sleep(SHAKE_DURATION);
                setLocation(originalLocation.x - SHAKE_DISTANCE, originalLocation.y);
                Thread.sleep(SHAKE_DURATION);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        setLocation(originalLocation);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new AdminLoginPage();
            }
        });
    }
}
