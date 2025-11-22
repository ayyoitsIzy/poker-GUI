package pui;

import java.awt.HeadlessException;
import java.util.*;
import java.util.concurrent.ExecutionException;

import javax.swing.JOptionPane;

public class demo {
    public static void main(String[] args) {
        Map<Player,PlayerAction> actionlog = new HashMap<>() ;
        GUI gui = new GUI();
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
            try {
                gui.setCommunitycard(community);
                gui.setPot(pot);
                gui.setActionlog(actionlog);
                while (true) { 
                String choice = gui.setMainmenu().get();
                // Alice's turn
                switch (choice) {
                    case "1":
                        gui.setTitle("texas Hold em!");
                        break;
                    case "2":
                        gui.setTitle("Omaha!");
                        break;
                    case "3":
                        gui.setTitle("Five card Drawn!");
                        break;
                    default:
                        throw new AssertionError();
                };
                gui.revealCommunitycard(3);
                PlayerAction action1;
                while (true) {
                    action1 = gui.setGUI(player,false).get(); 
                    int amount = action1.getAmount();
                    if (action1.getType() == ActionType.BET || action1.getType() == ActionType.RAISE) {
                        if (amount > player.getChipstack()) {
                            JOptionPane.showMessageDialog(null, "You cannot bet more than your chip stack!", "Invalid Bet", JOptionPane.WARNING_MESSAGE);
                            continue; 
                        }
                    }
                    break;
                }
                actionlog.put(player,action1);//add valid action to action log
      
                // Bob's turn
                PlayerAction action2;
                while (true) {
                    action2 = gui.setGUI(player2,  true).get(); 
                    int amount = action2.getAmount();
                    if (action2.getType() == ActionType.BET || action2.getType() == ActionType.RAISE) {
                        if (amount > player.getChipstack()) {
                            JOptionPane.showMessageDialog(null, "You cannot bet more than your chip stack!", "Invalid Bet", JOptionPane.WARNING_MESSAGE);
                            continue; 
                        }
                    }
                    break;
                }
                 actionlog.put(player2,action2);//add valid action to action log
                gui.revealCommunitycard();
                pot.add(30000);
                //new turn
                // Alice's new turn
                actionlog.clear();//newturn thus clear action log
                PlayerAction action3;
                while (true) {
                    action3 = gui.setGUI(player, true).get(); 
                    int amount = action3.getAmount();
                    if (action3.getType() == ActionType.BET || action3.getType() == ActionType.RAISE) {
                        if (amount > player.getChipstack()) {
                            continue; 
                        }
                    }
                    break;
                }
                 actionlog.put(player,action3);
                 // Bob's new turn
                 PlayerAction action4;
                while (true) {
                    action4 = gui.setGUI(player2, true).get(); 
                    int amount = action4.getAmount();
                    if (action4.getType() == ActionType.BET || action4.getType() == ActionType.RAISE) {
                        if (amount > player2.getChipstack()) {
                            continue; 
                        }
                    }
                    break;
                }
                 actionlog.put(player2,action4);
                 //Concealed the Conclusion
                 gui.resetinfo();
                 Map<Player,Integer> Distributed = new HashMap<>();
                 Distributed.put(player, 3000);
                 Distributed.put(player2, 3000);
                 gui.setResult(Distributed).get();
                System.out.println("Test complete");
              }
            } catch (HeadlessException | InterruptedException | ExecutionException e) {
            }
       
    }
}
