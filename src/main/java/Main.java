import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import java.util.List;
import java.util.ArrayList;

import javazoom.jl.player.Player;
import org.bson.Document;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class Main implements ActionListener{

    //JFrame variables
    private JFrame frame;
    private JLabel songName;
    private JButton addSong, play, pause, resume, stop;
    private JPanel playerPanel, controlPanel;
    private JList list;
    private JFileChooser fileChooser;
    private JScrollPane scrollpane;

    //variables related to the list of songs
    private List<String> categories = new ArrayList<String>();
    private List<String> categories2 = new ArrayList<String>();
    private String username;
    private int selectedIndex = 0;

    //variables related to adding new songs
    private FileInputStream fileInputStream;
    private BufferedInputStream bufferedInputStream;
    private File myFile = null;
    private String filename, filePath;

    //variables related to music player thread
    private long totalLength, pauseLength;
    private Player player;
    private Thread playThread, resumeThread;

    //variables related to the database
    private MongoClient mongoClient;
    private MongoDatabase db;
    private MongoCollection col;


    public Main(List<String> a, List<String> b, String c) {

        //initializing the variables which will be used to create the list
        categories = a;
        categories2 = b;
        username = c;

        //initializing user interface
        initUI();

        //setting up the action listeners
        addActionEvents();

        //creating threads

        //setting up the database
        mongoClient = MongoClients.create("mongodb://127.0.0.1:27017/?directConnection=true&serverSelectionTimeoutMS=2000&appName=mongosh+1.3.0");
        db = mongoClient.getDatabase("sampleDB");
        col = db.getCollection(c.toString());

    }

    public void initUI() {

        songName = new JLabel("", SwingConstants.CENTER);

        addSong = new JButton("Add Song");

        playerPanel = new JPanel(); //Music Selection Panel
        controlPanel = new JPanel(); //Control Selection Panel

        ImageIcon iconPlay = new ImageIcon("C:\\Users\\mario\\Desktop\\NRDBproject\\pictures\\1.jpeg");
        Image image = iconPlay.getImage();
        Image newimg = image.getScaledInstance(30,30,java.awt.Image.SCALE_SMOOTH);
        iconPlay = new ImageIcon(newimg);

        ImageIcon iconPause = new ImageIcon("C:\\Users\\mario\\Desktop\\NRDBproject\\pictures\\4.jpeg");
        Image image1 = iconPause.getImage();
        Image newimg1 = image1.getScaledInstance(30,30,java.awt.Image.SCALE_SMOOTH);
        iconPause = new ImageIcon(newimg1);

        ImageIcon iconResume = new ImageIcon("C:\\Users\\mario\\Desktop\\NRDBproject\\pictures\\2.jpeg");
        Image image2 = iconResume.getImage();
        Image newimg2 = image2.getScaledInstance(30,30,java.awt.Image.SCALE_SMOOTH);
        iconResume = new ImageIcon(newimg2);

        ImageIcon iconStop = new ImageIcon("C:\\Users\\mario\\Desktop\\NRDBproject\\pictures\\3.png");
        Image image3 = iconStop.getImage();
        Image newimg3 = image3.getScaledInstance(30,30,java.awt.Image.SCALE_SMOOTH);
        iconStop = new ImageIcon(newimg3);

        play = new JButton(iconPlay);
        pause = new JButton(iconPause);
        resume = new JButton(iconResume);
        stop = new JButton(iconStop);

        playerPanel.setLayout(new GridLayout(3, 1));
        playerPanel.add(addSong);
        playerPanel.add(songName);

        controlPanel.setLayout(new GridLayout(1, 4));
        controlPanel.add(play);
        controlPanel.add(pause);
        controlPanel.add(resume);
        controlPanel.add(stop);

        play.setBackground(Color.WHITE);
        pause.setBackground(Color.WHITE);
        resume.setBackground(Color.WHITE);
        stop.setBackground(Color.WHITE);

        frame = new JFrame();

        list = new JList(categories.toArray());
        list.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);

        scrollpane = new JScrollPane(list);

        frame.setTitle("Music Player");
        frame.setBackground(Color.white);
        frame.setBounds(360,60,500,500);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(scrollpane,BorderLayout.CENTER);
        frame.add(playerPanel, BorderLayout.NORTH);
        frame.add(controlPanel, BorderLayout.SOUTH);

        //action listener of the list
        list.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent evt) {
                jList1ValueChanged(evt);
            }
        });
    }

    private void jList1ValueChanged(javax.swing.event.ListSelectionEvent evt) {

        if(!evt.getValueIsAdjusting()){
            selectedIndex = list.getSelectedIndex();
        }

        filename = categories.get(selectedIndex);
        filePath = categories2.get(selectedIndex);
        songName.setText("File Selected : " + filename);

    }

    public void addActionEvents() {

        addSong.addActionListener(this);
        play.addActionListener(this);
        pause.addActionListener(this);
        resume.addActionListener(this);
        stop.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource().equals(addSong)) {
            fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("C:\\Users"));
            fileChooser.setDialogTitle("Select Mp3");
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.setFileFilter(new FileNameExtensionFilter("Mp3 files", "mp3"));
            if (fileChooser.showOpenDialog(addSong) == JFileChooser.APPROVE_OPTION) {
                String filename1;
                File myFile1 = fileChooser.getSelectedFile();
                filename1 = fileChooser.getSelectedFile().getName();

                categories.add(filename1);
                categories2.add(myFile1.getPath().toString());

                Document sampleDoc = new Document("nev", filename1).append("path", myFile1.getPath().toString());
                col.insertOne(sampleDoc);

                frame.setVisible(false);
                Main mp = new Main(categories, categories2, username);
            }
        }

        if (e.getSource().equals(play)) {
            if (filename != null) {
                playThread = new Thread(runnablePlay);
                playThread.start();
                songName.setText("Now playing : " + filename);
            } else {
                songName.setText("No File was selected!");
            }
        }
        if (e.getSource().equals(pause)) {
            if (player != null && filename != null) {
                try {
                    pauseLength = fileInputStream.available();
                    player.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }

        if (e.getSource().equals(resume)) {

            if (filename != null) {
                resumeThread = new Thread(runnableResume);
                resumeThread.start();
            }
            else {
                songName.setText("No File was selected!");
            }
        }

        if (e.getSource().equals(stop)) {

            if (player != null) {
                player.close();
                songName.setText("");
            }

        }

    }

    Runnable runnablePlay = new Runnable() {

        @Override
        public void run() {
            try {
                File file1 = new File(categories2.get(selectedIndex));
                fileInputStream = new FileInputStream(file1);
                bufferedInputStream = new BufferedInputStream(fileInputStream);
                player = new Player(bufferedInputStream);
                totalLength = fileInputStream.available();
                player.play();//starting music
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    Runnable runnableResume = new Runnable() {
        @Override
        public void run() {
            try {
                File file1 = new File(categories2.get(selectedIndex));
                fileInputStream = new FileInputStream(file1);
                bufferedInputStream = new BufferedInputStream(fileInputStream);
                player = new Player(bufferedInputStream);
                fileInputStream.skip(totalLength - pauseLength);
                player.play();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
}