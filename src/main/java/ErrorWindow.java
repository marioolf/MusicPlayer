import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ErrorWindow {

    public ErrorWindow() {
        String message = "\"Error\"\n" + "Wrong username or pasword\n";
        JOptionPane.showMessageDialog(new JFrame(), message, "Error",
                JOptionPane.ERROR_MESSAGE);
    }
}


