import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
import java.io.IOException;
import javax.swing.border.Border;

public class FirstPage implements ActionListener{
    private JButton but1,but2;
    private JFrame frame;
    public FirstPage(){
        frame = new JFrame("FirstPage");
        frame.setBounds(360, 60, 500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);  
        //frame.setVisible(true);

        try {
            frame.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("C:\\Users\\mario\\Desktop\\NRDBproject\\pictures\\5.jpeg")))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //frame.pack();
        but1 = new JButton("Login");
        but1.setSize(100, 25);
        but1.setLocation(200, 200);
        but1.setBorder(new RoundBtn(15));
//        but1.setBackground(new Color(49, 4, 61));
//        but1.setForeground(new Color(96, 194, 129));
        but1.addActionListener(this);

        frame.add(but1);

        but2 = new JButton("Register");
        but2.setSize(100, 25);
        but2.setBorder(new RoundBtn(15));
        but2.setLocation(200, 250);
//        but2.setBackground(new Color(49, 4, 61));
//        but2.setForeground(new Color(96, 194, 129));
        but2.addActionListener(this);
        frame.add(but2);

        frame.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == but2) {
            frame.setVisible(false);
            RegisterPage mp = new RegisterPage();
        }
        if (e.getSource() == but1) {
            frame.setVisible(false);
            LoginPage mp = new LoginPage();
        }
    }
}
