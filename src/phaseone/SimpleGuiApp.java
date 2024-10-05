import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimpleGuiApp {
    public static void main(String[] args) {
        // Create a new JFrame (window)
        JFrame frame = new JFrame("Simple GUI with Button");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        
        // Create a new button
        JButton button = new JButton("Click Me!");

        // Add action listener to the button
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Button clicked!");
            }
        });
        
        // Create a layout and add the button to the frame
        frame.setLayout(new FlowLayout());
        frame.add(button);
        
        // Make the frame visible
        frame.setVisible(true);
    }
}
