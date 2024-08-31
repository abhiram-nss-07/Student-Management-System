import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.regex.Pattern;

public class SignUpPage extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField emailField;
    private JButton eyeButton; 
    private boolean passwordVisible = false;

    public SignUpPage() {
        setTitle("SignUp");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 400);
        setLocationRelativeTo(null);
        setResizable(false);

        ImageIcon icon = new ImageIcon("icon.jpg");
        setIconImage(icon.getImage());

        ImageIcon backgroundIcon = new ImageIcon(""); 
        JLabel backgroundLabel = new JLabel(backgroundIcon);
        backgroundLabel.setLayout(null); 

        JLabel titlelabel = new JLabel("Sign Up");
        titlelabel.setBounds(270, 40, 150, 40);
        titlelabel.setFont(new Font("Arial", Font.PLAIN, 30));

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(200, 120, 100, 30); 
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        usernameField = new JTextField();
        usernameField.setBounds(300, 120, 150, 30); 

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(200, 170, 100, 30); 
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        emailField = new JTextField();
        emailField.setBounds(300, 170, 150, 30); 

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(200, 220, 100, 30); 
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        passwordField = new JPasswordField();
        passwordField.setBounds(300, 220, 150, 30); 

        JButton signUpButton = new JButton("Sign Up");
        signUpButton.setBounds(200, 300, 100, 30); 

        signUpButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                signUpButton.setBackground(Color.RED); 
                signUpButton.setForeground(Color.WHITE); 
                signUpButton.setFont(new Font("Arial", Font.BOLD, 14)); 
            }

            @Override
            public void mouseExited(MouseEvent e) {
                signUpButton.setBackground(UIManager.getColor("Button.background")); 
                signUpButton.setForeground(UIManager.getColor("Button.foreground")); 
                signUpButton.setFont(new Font("Arial", Font.PLAIN, 12)); 
            }
        });


        signUpButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText(); 
                String email = emailField.getText(); 
                String password = new String(passwordField.getPassword());
                
                if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    shakeFrame();
                    JOptionPane.showMessageDialog(SignUpPage.this,
                            "Please fill in all fields.",
                            "Registration Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                } 
                
                if (username.length() < 8) {
                    shakeFrame();
                    JOptionPane.showMessageDialog(SignUpPage.this,
                            "Username must be at least 8 characters long.",
                            "Username Validation Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                if (!isValidEmail(email)) {
                    shakeFrame();
                    JOptionPane.showMessageDialog(SignUpPage.this,
                            "Please enter a valid email address.",
                            "Email Validation Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                if (!isValidPassword(password)) {
                    shakeFrame();
                    JOptionPane.showMessageDialog(SignUpPage.this,
                            "Password must contain at least one uppercase letter, one lowercase letter, one digit, one special character, and be at least 8 characters long.",
                            "Password Validation Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
        
                try {
                    DatabaseManager.insertUser(username, email, password);
                    JOptionPane.showMessageDialog(SignUpPage.this,
                            "Sign-up successful!",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                    dispose(); 
                    
                    new RegisterPage().setVisible(true);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(SignUpPage.this,
                            "An error occurred during sign-up. Please try again.",
                            "Registration Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        
        JButton backButton = new JButton("Back");
        backButton.setBounds(400, 300, 100, 30);
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
        eyeButton.setBounds(460, 220, 30, 30);
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
        backgroundLabel.add(emailLabel);
        backgroundLabel.add(emailField);
        backgroundLabel.add(passwordLabel);
        backgroundLabel.add(passwordField);
        backgroundLabel.add(signUpButton);
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

    public boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    
        Pattern pattern = Pattern.compile(emailRegex);
    
        return pattern.matcher(email).matches();
    }

    public boolean isValidPassword(String password) {
        String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";

        Pattern pattern = Pattern.compile(passwordRegex);

        return pattern.matcher(password).matches();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new SignUpPage();
            }
        });
    }
}
