import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RegisterPage extends JFrame {

    public RegisterPage() {
        setTitle("Home");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 400);
        setLocationRelativeTo(null);
        setResizable(false);

        ImageIcon icon = new ImageIcon("icon.jpg");
        setIconImage(icon.getImage());

        ImageIcon backgroundIcon = new ImageIcon(""); 
        JLabel backgroundLabel = new JLabel(backgroundIcon);
        backgroundLabel.setBounds(0, 0, 700, 400);
        backgroundLabel.setLayout(null); 

        JLabel titlelabel = new JLabel("User Management Application");
        titlelabel.setBounds(150, 40, 600, 40);
        titlelabel.setFont(new Font("Arial", Font.PLAIN, 30));

        JButton signUpButton = new JButton("Sign Up");
        signUpButton.setBounds(150, 150, 150, 30); 

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
                dispose(); // Close current frame
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        new SignUpPage(); 
                    }
                });
            }
        });

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(400, 150, 150, 30); 
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
                dispose(); // Close current frame
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        new LoginPage(); 
                    }
                });
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

        JButton adminButton = new JButton("Admin");
        adminButton.setBounds(275, 200, 150, 30); // Adjust position and size as needed

        adminButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close current frame
                // Open admin login window
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        new AdminLoginPage();
                    }
                });
            }
        });

        adminButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                adminButton.setBackground(Color.RED); 
                adminButton.setForeground(Color.WHITE); 
                adminButton.setFont(new Font("Arial", Font.BOLD, 14)); 
            }

            @Override
            public void mouseExited(MouseEvent e) {
                adminButton.setBackground(UIManager.getColor("Button.background")); 
                adminButton.setForeground(UIManager.getColor("Button.foreground")); 
                adminButton.setFont(new Font("Arial", Font.PLAIN, 12)); 
            }
        });

        backgroundLabel.add(titlelabel);
        backgroundLabel.add(signUpButton);
        backgroundLabel.add(loginButton);
        backgroundLabel.add(adminButton);
        

        setContentPane(backgroundLabel);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new RegisterPage();
            }
        });
    }
}
