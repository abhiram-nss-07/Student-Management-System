import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class LoginPage extends JFrame {
    JTextField usernameField;
    private JPasswordField passwordField;
    private JButton eyeButton; 
    private boolean passwordVisible = false;

    public LoginPage() {
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 400);
        setLocationRelativeTo(null);
        setResizable(false);

        ImageIcon icon = new ImageIcon("icon.jpg");
        setIconImage(icon.getImage());

        ImageIcon backgroundIcon = new ImageIcon(""); 
        JLabel backgroundLabel = new JLabel(backgroundIcon);
        backgroundLabel.setLayout(null);

        JLabel titlelabel = new JLabel("Login");
        titlelabel.setBounds(300, 50, 100, 40);
        titlelabel.setFont(new Font("Arial", Font.PLAIN, 30));

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(200, 150, 100, 30); 
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        usernameField = new JTextField();
        usernameField.setBounds(300, 150, 150, 30); 

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(200, 200, 100, 30); 
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        passwordField = new JPasswordField();
        passwordField.setBounds(300, 200, 150, 30); 

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(200, 270, 100, 30); 
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

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean loginSuccess = false; 
                if (!loginSuccess) {
                    shakeFrame();
                }
            }
        });

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText(); 
                char[] passwordChars = passwordField.getPassword(); 
                String password = new String(passwordChars);
                
                String hashedPassword = hashPassword(password);
        
                if (DatabaseManager.checkUser(username, hashedPassword)) {
                    dispose();
                    openProfilePage(username); 
                } else {
                    shakeFrame();
                    JOptionPane.showMessageDialog(LoginPage.this,
                            "Invalid Username or Password!",
                            "Login Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JButton backButton = new JButton("Back");
        backButton.setBounds(400, 270, 100, 30);
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
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); 
                new RegisterPage(); 
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

        ImageIcon eyeIconClosed = new ImageIcon("eye_closed.png"); 
        ImageIcon eyeIconOpen = new ImageIcon("eye_open.png"); 

        // Resize eye icons
        Image imgClosed = eyeIconClosed.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        Image imgOpen = eyeIconOpen.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);

        final ImageIcon finalEyeIconClosed = new ImageIcon(imgClosed); 
        final ImageIcon finalEyeIconOpen = new ImageIcon(imgOpen); 


        eyeButton = new JButton();
        eyeButton.setIcon(finalEyeIconClosed); 
        eyeButton.setBounds(460, 200, 30, 30);
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

        backgroundLabel.add(titlelabel);
        backgroundLabel.add(usernameLabel);
        backgroundLabel.add(usernameField);
        backgroundLabel.add(passwordLabel);
        backgroundLabel.add(passwordField);
        backgroundLabel.add(loginButton);
        backgroundLabel.add(backButton);
        backgroundLabel.add(eyeButton);

        setContentPane(backgroundLabel);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setVisible(true);
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

    private String hashPassword(String password) {
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
    

    private boolean checkLogin(String username, String password) {
        return DatabaseManager.checkUser(username, password);
    }

    private void openProfilePage(String username) {
        new ProfilePage(username).setVisible(true); 
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new LoginPage();
            }
        });
    }
}
