package pui;
import javax.swing.*;
import java.awt.*;

public class ResponsiveUI {
    public static void main(String[] args) {

        // Always start Swing on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Responsive UI Example");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 300);

            // CardLayout for switching components
            CardLayout cardLayout = new CardLayout();
            JPanel cardPanel = new JPanel(cardLayout);

            // -------- Panel 1 --------
            JPanel panel1 = new JPanel();
            panel1.setBackground(Color.LIGHT_GRAY);
            panel1.add(new JLabel("This is Panel 1"));
            JButton gotoPanel2 = new JButton("Go to Panel 2");
            panel1.add(gotoPanel2);

            // -------- Panel 2 --------
            JPanel panel2 = new JPanel();
            panel2.setBackground(Color.CYAN);
            panel2.add(new JLabel("This is Panel 2"));
            JButton gotoPanel1 = new JButton("Go back to Panel 1");
            panel2.add(gotoPanel1);

            // Add to cardPanel
            cardPanel.add(panel1, "panel1");
            cardPanel.add(panel2, "panel2");

            // Button actions
            gotoPanel2.addActionListener(e -> {
                System.out.println("Switching to Panel 2...");
                cardLayout.show(cardPanel, "panel2");
            });

            gotoPanel1.addActionListener(e -> {
                System.out.println("Switching to Panel 1...");
                cardLayout.show(cardPanel, "panel1");
            });

            frame.add(cardPanel);
            frame.setVisible(true);
        });
    }
}