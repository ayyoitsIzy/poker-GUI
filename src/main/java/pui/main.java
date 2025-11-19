package pui;

import java.awt.HeadlessException;
import java.util.*;
import java.util.concurrent.ExecutionException;

import javax.swing.JOptionPane;

public class main {
    public static void main(String[] args) {
        ActionLog actionlog = new ActionLog();
        GUI gui = new GUI(actionlog);
        List<Card> alicehand = new ArrayList<>();
        alicehand.add(new Card(Card.Rank.ACE, Card.Suit.DIAMONDS));
        alicehand.add(new Card(Card.Rank.TWO, Card.Suit.HEARTS));
        Player player = new Player("Alice", alicehand, 4000);

        List<Card> bobhand = new ArrayList<>();
        bobhand.add(new Card(Card.Rank.JACK, Card.Suit.DIAMONDS));
        bobhand.add(new Card(Card.Rank.KING, Card.Suit.HEARTS));
        Player player2 = new Player("Bob", bobhand, 3000);

        List<Card> community = new ArrayList<>();
        community.add(new Card(Card.Rank.ACE, Card.Suit.HEARTS));
        community.add(new Card(Card.Rank.THREE, Card.Suit.HEARTS));
        community.add(new Card(Card.Rank.JACK, Card.Suit.DIAMONDS));
        community.add(new Card(Card.Rank.THREE, Card.Suit.HEARTS));
        community.add(new Card(Card.Rank.JACK, Card.Suit.DIAMONDS));

        ArrayList<Integer> pot = new ArrayList<>();
        pot.add(3000);
        pot.add(1200);
        pot.add(1200);


            try {
                // Alice's turn
                PlayerAction action1;
                while (true) {
                    action1 = gui.setTXHM(player, community,pot,false).get(); 
                    int amount = action1.getAmount();
                    if (action1.getType() == ActionType.BET || action1.getType() == ActionType.RAISE) {
                        if (amount > player.getChipstack()) {
                            JOptionPane.showMessageDialog(null, "You cannot bet more than your chip stack!", "Invalid Bet", JOptionPane.WARNING_MESSAGE);
                            continue; 
                        }
                    }
                    break;
                }
                actionlog.addValidaction(player, action1);
                // Bob's turn
                PlayerAction action2;
                while (true) {
                    action2 = gui.setTXHM(player2, community,pot, true).get(); 
                    int amount = action2.getAmount();
                    if (action2.getType() == ActionType.BET || action2.getType() == ActionType.RAISE) {
                        if (amount > player.getChipstack()) {
                            JOptionPane.showMessageDialog(null, "You cannot bet more than your chip stack!", "Invalid Bet", JOptionPane.WARNING_MESSAGE);
                            continue; 
                        }
                    }
                    break;
                }
                actionlog.addValidaction(player2, action2);
                // Alice's next turn
                PlayerAction action3;
                while (true) {
                    action3 = gui.setTXHM(player, community,pot,true).get(); 
                    int amount = action3.getAmount();
                    if (action3.getType() == ActionType.BET || action3.getType() == ActionType.RAISE) {
                        if (amount > player.getChipstack()) {
                            continue; 
                        }
                    }
                    break;
                }

                actionlog.addValidaction(player, action3);
                System.out.println("Test complete");

            } catch (HeadlessException | InterruptedException | ExecutionException e) {
            }
       
    }
}
