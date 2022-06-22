import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class RegisterPage implements ActionListener {
    private JButton back, register;
    private JFrame frame;
    private JTextField username, email;
    private JPasswordField pass;

    public RegisterPage() {
        frame = new JFrame("RegisterPage");
        frame.setBounds(360, 60, 500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        try {
            frame.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("C:\\Users\\mario\\Desktop\\NRDBproject\\pictures\\5.jpeg")))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        frame.pack();

        JLabel field1 = new JLabel("Register to the Music Player! ");
        field1.setFont(new Font("Arial", Font.PLAIN, 24));
        field1.setSize(350, 30);
        field1.setLocation(105, 10);
        frame.add(field1);

        JLabel field2 = new JLabel("Username");
        field2.setFont(new Font("Arial", Font.PLAIN, 19));
        field2.setSize(100, 30);
        field2.setLocation(50, 80);
        frame.add(field2);

        username = new JTextField();
        username.setFont(new Font("Arial", Font.PLAIN, 15));
        username.setSize(150, 20);
        username.setLocation(50, 123);
        frame.add(username);


        JLabel field3 = new JLabel("Email address");
        field3.setFont(new Font("Arial", Font.PLAIN, 19));
        field3.setSize(300, 30);
        field3.setLocation(50, 150);
        frame.add(field3);

        email = new JTextField();
        email.setFont(new Font("Arial", Font.PLAIN, 15));
        email.setSize(150, 20);
        email.setLocation(50, 183);
        frame.add(email);

        JLabel field4 = new JLabel("Password");
        field4.setFont(new Font("Arial", Font.PLAIN, 19));
        field4.setSize(600, 30);
        field4.setLocation(50, 220);
        frame.add(field4);

        pass = new JPasswordField();
        pass.setFont(new Font("Arial", Font.PLAIN, 15));
        pass.setSize(150, 25);
        pass.setLocation(50, 260);
        frame.add(pass);

        register = new JButton("Register");
        register.setSize(150, 25);
        register.setLocation(175, 310);
        frame.add(register);
        register.addActionListener(this);

        back = new JButton("Back");
        back.setSize(150, 25);
        back.setLocation(175, 375);
        frame.add(back);
        back.addActionListener(this);

        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if ((e.getSource() == register) && (!username.getText().isEmpty()) && (!email.getText().isEmpty()) && (!String.valueOf(pass.getPassword()).isEmpty())) {
            frame.setVisible(false);
            ArrayList<String> data = new ArrayList<>();
            data.add(username.getText());
            data.add(email.getText());
            data.add(String.valueOf(pass.getPassword()));
            InsertData insert = new InsertData(data);
            FirstPage fp = new FirstPage();
        }
        if (e.getSource() == back) {
            frame.setVisible(false);
            FirstPage fp = new FirstPage();

        }
    }

}
