import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import org.bson.Document;

import java.util.ArrayList;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

public class LoginPage implements ActionListener {
    private JButton back, login;
    private JFrame frame;
    private JTextField username;
    private JPasswordField pass;


    public LoginPage() {
        frame = new JFrame("Login Page");
        frame.setBounds(360, 60, 500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        //frame.setVisible(true);

        try {
            frame.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("C:\\Users\\mario\\Desktop\\NRDBproject\\pictures\\5.jpeg")))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        frame.pack();

        JLabel field1 = new JLabel("Login to access your music! ");
        field1.setFont(new Font("Arial", Font.PLAIN, 24));
        field1.setSize(600, 30);
        field1.setLocation(105, 10);
        frame.add(field1);

        JLabel field2 = new JLabel("Username");
        field2.setFont(new Font("Arial", Font.PLAIN, 19));
        field2.setSize(600, 30);
        field2.setLocation(50, 80);
        frame.add(field2);

        username = new JTextField();
        username.setFont(new Font("Arial", Font.PLAIN, 15));
        username.setSize(150, 20);
        username.setLocation(50, 123);
        frame.add(username);

        JLabel field3 = new JLabel("Password: ");
        field3.setFont(new Font("Arial", Font.PLAIN, 19));
        field3.setSize(300, 30);
        field3.setLocation(50, 150);
        frame.add(field3);

        pass = new JPasswordField();
        pass.setFont(new Font("Arial", Font.PLAIN, 15));
        pass.setSize(150, 20);
        pass.setLocation(50, 183);
        frame.add(pass);


        //login button
        login = new JButton("Login");
        login.setSize(150, 25);
        login.setLocation(175, 290);
        login.addActionListener(this);
        frame.add(login);


        // back button
        back = new JButton("Back");
        back.setSize(150, 25);
        back.setLocation(175, 350);
        back.addActionListener(this);
        frame.add(back);

        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == login) {

            boolean cs =false;
            MongoClient mongoClient = MongoClients.create("mongodb://127.0.0.1:27017/?directConnection=true&serverSelectionTimeoutMS=2000&appName=mongosh+1.3.0");
            MongoDatabase db = mongoClient.getDatabase("sampleDB");

            MongoCollection<Document> collection = db.getCollection("users");
            try (MongoCursor<Document> cur = collection.find().iterator()) {
                while (cur.hasNext()) {
                    var doc = cur.next();
                    var temp = new ArrayList<>(doc.values());
                    if (username.getText().equals(temp.get(1).toString()) && temp.get(3).equals(new String(pass.getPassword()))) {

                        cs=true;
                        break;
                    }
                }
            }

            if (!cs) {
                ErrorWindow errorWindow = new ErrorWindow();
            }
            if (cs) {
                frame.setVisible(false);
                ArrayList<String> a = new ArrayList<String>();
                ArrayList<String> b = new ArrayList<String>();
                MongoCollection<Document> music = db.getCollection(username.getText());

                try (MongoCursor<Document> cur = music.find().iterator()) {
                    while (cur.hasNext()) {
                        var doc = cur.next();
                        var temp = new ArrayList<>(doc.values());
                        a.add(temp.get(1).toString());
                        b.add(temp.get(2).toString());
                    }
                }

                Main mp = new Main(a,b,username.getText().toString());
        }
    }

    if (e.getSource() == back) {
        frame.setVisible(false);
        FirstPage fp = new FirstPage();
    }
}

}
