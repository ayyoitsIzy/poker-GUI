package pui;

import java.awt.*;
import java.awt.event.*;
import java.io.Console;
import java.net.URL;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.swing.*;

public class GUI extends JFrame implements ActionListener {
    private JFrame UI;
    Color pokerGreen;
    ImageIcon icon;
    URL imageUrl;
    JLabel title;
    JButton bet, raise, call, check, fold;
    Font display, titleFont;
    ActionLog actionLog;
    public GUI(ActionLog actionLog) {
        this.actionLog = actionLog;
        imageUrl = GUI.class.getResource("IMG/icon.png");
        icon = new ImageIcon(imageUrl);
        pokerGreen = new Color(0x35654d);
        UI = new JFrame();
        UI.setSize(1366, 768);
        UI.setResizable(false);
        UI.setDefaultCloseOperation(EXIT_ON_CLOSE);
        UI.setTitle("Poker Night!");
        UI.getContentPane().setBackground(pokerGreen);
        UI.setIconImage(icon.getImage());
        UI.setVisible(true);

        bet = buttonmaker("bet");
        raise = buttonmaker("raise");
        call = buttonmaker("call");
        check = buttonmaker("check");
        fold = buttonmaker("fold");

        display = new Font("Helvetica", Font.PLAIN, 25);
        titleFont = new Font("Helvetica", Font.PLAIN, 50);
    }

    public CompletableFuture<PlayerAction> setTXHM(Player player, List<Card> community_card,List<Integer> pot, boolean betted) {
        
        //infra requirement : player info getter(name,holecard,chipstack),card info getter,action type constructor,action type getter(type amount),list of current pot (amount in int) 
        //and game mode to be able to access coummunity card;

        CompletableFuture<PlayerAction> result = new CompletableFuture<>();

        // Title
        title = new JLabel("Texas Hold 'em");
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setVerticalAlignment(JLabel.TOP);
        title.setForeground(new Color(0xF9F6EE));
        title.setFont(titleFont);

        // Panels
        JPanel Top = new JPanel();
        Top.setBackground(Color.gray);
        Top.setBorder(BorderFactory.createEtchedBorder());
        Top.setPreferredSize(new Dimension(5, 100));

        JPanel Aside = new JPanel();
        Aside.setBackground(Color.lightGray);
        Aside.setBorder(BorderFactory.createEtchedBorder());
        Aside.setPreferredSize(new Dimension(200, 5));
        Aside.setLayout(new BoxLayout(Aside, BoxLayout.Y_AXIS));




        JPanel Aside2 = new JPanel();
        Aside2.setBackground(Color.lightGray);
        Aside2.setBorder(BorderFactory.createEtchedBorder());
        Aside2.setPreferredSize(new Dimension(200, 5));
        Aside2.setLayout(new BoxLayout(Aside2, BoxLayout.Y_AXIS));

        JPanel Center = new JPanel();
        Center.setBackground(pokerGreen);
        Center.setPreferredSize(new Dimension(50, 50));
        Center.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 75));

        JPanel Bottom = new JPanel();
        Bottom.setBackground(Color.gray);
        Bottom.setPreferredSize(new Dimension(50, 300));
        Bottom.setLayout(new BorderLayout());

        JPanel PlayerCardPanel = new JPanel();
        PlayerCardPanel.setBorder(BorderFactory.createEtchedBorder());
        PlayerCardPanel.setBackground(pokerGreen);
        PlayerCardPanel.setPreferredSize(new Dimension(50, 150));
        PlayerCardPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 15));

        JPanel ChooseArea = new JPanel();
        ChooseArea.setBackground(Color.DARK_GRAY);
        ChooseArea.setBorder(BorderFactory.createEtchedBorder());
        ChooseArea.setPreferredSize(new Dimension(50, 150));
        ChooseArea.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 15));

        //add action log
        List<Player> playerLog = actionLog.getPlayers();
        List<PlayerAction> playerActions = actionLog.getLog();
        Font AsideFont = new Font("Helvetica", Font.PLAIN, 20);
        for (int i = 0; i < playerActions.size(); i++) {
            JLabel log = new JLabel();
            log.setFont(AsideFont);
            switch (playerActions.get(i).getType()) {
                case BET, RAISE :
                    log.setText(String.format("%s : %s %d $", playerLog.get(i).getName(),playerActions.get(i).getType().name(),playerActions.get(i).getAmount()));
                    break;
                default :
                    log.setText(String.format("%s : %s ", playerLog.get(i).getName(),playerActions.get(i).getType()));
        }
        Aside.add(log);   
    }
        //add pot info
        for (int i = 0; i < pot.size(); i++) {
            JLabel log = new JLabel();
            log.setFont(AsideFont);
            if(i==0){
                log.setText(String.format("Mainpot : %d $",pot.get(i)));
            }else{
                log.setText(String.format("Sidepot %d : %d $",i,pot.get(i)));
            }
            Aside2.add(log);
        }

        // Add community cards
        for (Card card : community_card) {
            JLabel cardimg = new JLabel(getcardIcon(card, 6));
            Center.add(cardimg);
        }

        // Player cards
        JLabel yourcard = new JLabel("Your card");
        yourcard.setForeground(new Color(0xF9F6EE));
        yourcard.setFont(titleFont);
        PlayerCardPanel.add(yourcard);
        for (Card card : player.getHand()) {
            JLabel cardimg = new JLabel(getcardIcon(card, 10));
            PlayerCardPanel.add(cardimg, BorderLayout.CENTER);
        }
        JLabel yourchip = new JLabel(String.format("Your Chip : %d $", player.getChipstack()));
        yourchip.setForeground(new Color(0xF9F6EE));
        yourchip.setFont(display);
        

        Bottom.add(ChooseArea, BorderLayout.SOUTH);
        Bottom.add(PlayerCardPanel, BorderLayout.NORTH);
        Top.add(title);

        // Add buttons
        JTextField field = new JTextField();
        field.setPreferredSize(new Dimension(220, 50));
        field.setFont(new Font("Helvetica", Font.PLAIN, 30));
        ChooseArea.add(bet);
        ChooseArea.add(raise);
        ChooseArea.add(call);
        ChooseArea.add(check);
        ChooseArea.add(fold);
        ChooseArea.add(yourchip);
        ChooseArea.add(field);
        raise.setEnabled(true);
        call.setEnabled(true);
        check.setEnabled(true);
        bet.setEnabled(true);
        if (betted) {
            check.setEnabled(false);
            bet.setEnabled(false);
        }else{
            raise.setEnabled(false);
            call.setEnabled(false);
        }

        // Repaint
        UI.getContentPane().removeAll();
        UI.add(Aside2,BorderLayout.EAST);
        UI.add(Top, BorderLayout.NORTH);
        UI.add(Center, BorderLayout.CENTER);
        UI.add(Bottom, BorderLayout.SOUTH);
        UI.add(Aside, BorderLayout.WEST);
        UI.revalidate();
        UI.repaint();

        bet.addActionListener(e -> {
            try {
                int amount = Integer.parseInt(field.getText());
                result.complete(new PlayerAction(ActionType.BET, amount));
            } catch (NumberFormatException ex) {
                field.setText("Not a Number!");
            }
        });

        raise.addActionListener(e -> {
            try {
                int amount = Integer.parseInt(field.getText());
                result.complete(new PlayerAction(ActionType.RAISE, amount));
            } catch (NumberFormatException ex) {
                field.setText("Not a Number!");
            }
        });
        call.addActionListener(e -> result.complete(new PlayerAction(ActionType.CALL)));
        check.addActionListener(e -> result.complete(new PlayerAction(ActionType.CHECK)));
        fold.addActionListener(e -> result.complete(new PlayerAction(ActionType.FOLD)));

        return result;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
    }

    public ImageIcon getcardIcon(Card card, int scale) {
        ImageIcon cardUrl = new ImageIcon(getCardURL(card));
        Image rawimg = cardUrl.getImage();
        Image scaledImage = rawimg.getScaledInstance(rawimg.getWidth(rootPane) / scale,
                rawimg.getHeight(rootPane) / scale, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }

    private URL getCardURL(Card card) {
        String url = String.format("IMG/Card/%s/%s.png", card.getSuit().name(), card.getrank().name());
        return GUI.class.getResource(url);
    }

    private JButton buttonmaker(String text) {
        JButton button = new JButton(text);
        button.addActionListener(this);
        button.setFont(new Font("Helvetica", Font.PLAIN, 30));
        button.setPreferredSize(new Dimension(220, 50));
        return button;
    }
}
