import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import com.toedter.calendar.JDateChooser;
import java.util.Date;

public class ProfilePage extends JFrame {
    private JTextField FirstNameField;
    private JTextField LastNameField;
    private JTextField MobileNumberField;
    private JComboBox<String> genderComboBox;
    private String username;
    private com.toedter.calendar.JDateChooser dobChooser;
    

    public ProfilePage(String username) {
        this.username = username;
        setTitle("Profile");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 900);
        setLocationRelativeTo(null);
        setResizable(false);

        ImageIcon icon = new ImageIcon("icon.jpg");
        setIconImage(icon.getImage());

        ImageIcon backgroundIcon = new ImageIcon(""); 
        JLabel backgroundLabel = new JLabel(backgroundIcon);
        backgroundLabel.setLayout(null);

        JLabel titlelabel = new JLabel("Profile");
        titlelabel.setBounds(300, 50, 100, 40);
        titlelabel.setFont(new Font("Arial", Font.PLAIN, 30));

        JLabel FirstNameLabel = new JLabel("First Name:");
        FirstNameLabel.setBounds(200, 150, 200, 30); 
        FirstNameLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        FirstNameField = new JTextField();
        FirstNameField.setBounds(350, 150, 200, 30); 

        JLabel LastNameLabel = new JLabel("Last Name:");
        LastNameLabel.setBounds(200, 200, 200, 30); 
        LastNameLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        LastNameField = new JTextField();
        LastNameField.setBounds(350, 200, 200, 30); 

        JLabel MobileNumberLabel = new JLabel("Mobile Number:");
        MobileNumberLabel.setBounds(200, 250, 200, 30); 
        MobileNumberLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        MobileNumberField = new JTextField();
        MobileNumberField.setBounds(350, 250, 200, 30); 

        JLabel GenderLabel = new JLabel("Gender:");
        GenderLabel.setBounds(200, 300, 200, 30); 
        GenderLabel.setFont(new Font("Arial", Font.PLAIN, 20));

        String[] genderOptions = {"Male", "Female"};
        genderComboBox = new JComboBox<>(genderOptions);
        genderComboBox.setBounds(350, 300, 200, 30);

        JLabel dobLabel = new JLabel("Date of Birth:");
        dobLabel.setBounds(200, 350, 200, 30);
        dobLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        dobChooser = new JDateChooser();
        dobChooser.setBounds(350, 350, 150, 30);

        JButton SaveButton = new JButton("Save");
        SaveButton.setBounds(150, 400, 100, 30); 
        SaveButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                SaveButton.setBackground(Color.RED); 
                SaveButton.setForeground(Color.WHITE); 
                SaveButton.setFont(new Font("Arial", Font.BOLD, 14)); 
            }

            @Override
            public void mouseExited(MouseEvent e) {
                SaveButton.setBackground(UIManager.getColor("Button.background")); 
                SaveButton.setForeground(UIManager.getColor("Button.foreground")); 
                SaveButton.setFont(new Font("Arial", Font.PLAIN, 12)); 
            }
        });
        SaveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String firstName = FirstNameField.getText();
                String lastName = LastNameField.getText();
                String mobileNumber = MobileNumberField.getText();
                String gender = (String) genderComboBox.getSelectedItem();
                Date dob = dobChooser.getDate();
        
                // Update user information in the database
                updateUser(username, firstName, lastName, mobileNumber, gender, dob);
        
                // Show message
                JOptionPane.showMessageDialog(ProfilePage.this,
                        "Profile updated successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        JButton backButton = new JButton("Back");
        backButton.setBounds(300, 400, 100, 30);
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
                new LoginPage(); 
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

        MobileNumberField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!(Character.isDigit(c) || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE)) {
                    e.consume();
                }
            }
        });

        backgroundLabel.add(titlelabel);
        backgroundLabel.add(FirstNameLabel);
        backgroundLabel.add(FirstNameField);
        backgroundLabel.add(LastNameLabel);
        backgroundLabel.add(LastNameField);
        backgroundLabel.add(MobileNumberLabel);
        backgroundLabel.add(MobileNumberField);
        backgroundLabel.add(GenderLabel);
        backgroundLabel.add(genderComboBox);
        backgroundLabel.add(dobLabel);
        backgroundLabel.add(dobChooser);
        backgroundLabel.add(SaveButton);
        backgroundLabel.add(backButton);

        setContentPane(backgroundLabel);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setVisible(true);
    }

    private void updateUser(String username, String firstName, String lastName, String mobileNumber, String gender, Date dob) {
        DatabaseManager.updateUser(username, firstName, lastName, mobileNumber, gender, dob);
        dispose();
    } 
}
