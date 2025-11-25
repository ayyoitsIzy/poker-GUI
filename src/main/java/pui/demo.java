package pui;

import java.awt.HeadlessException;
import java.util.*;
import java.util.concurrent.ExecutionException;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class demo {
    public static void main(String[] args) {
        Map<Player,PlayerAction> actionlog = new HashMap<>() ;
        GUI gui = new GUI();
        

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
                Map<String,Integer> playeranswer = gui.setPlayerInfo().get();
                List<Player> playerlist = new LinkedList<>();
                for (Map.Entry<String,Integer> name: playeranswer.entrySet()){
                   List<Card> hand = new ArrayList<>();
                    hand.add(new Card(Card.Rank.ACE, Card.Suit.DIAMONDS));
                    hand.add(new Card(Card.Rank.TWO, Card.Suit.HEARTS));
                    hand.add(new Card(Card.Rank.ACE, Card.Suit.DIAMONDS));
                    hand.add(new Card(Card.Rank.TWO, Card.Suit.HEARTS));
                    playerlist.add(new Player(name.getKey(), hand, name.getValue()));
                 }


              


                Player player = playerlist.get(0);
                Player player2 = playerlist.get(1);

                 // Player1turn
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
                gui.Cover("next player turn").get(); 
                actionlog.put(player,action1);//add valid action to action log
                 // Player2turn
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
                gui.Cover("new turn").get();
                //new turn
                // player1 new turn
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
                gui.Cover("next player turn").get(); 
                 // player2 new turn
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
                    List<Card> AliceCards = new ArrayList<>();
                    gui.Cover("Play end player 1 chose omaha card").get();
                    AliceCards.addAll(gui.setOmaha1(player).get());
                    AliceCards.addAll(gui.setOmaha2(player).get());
                    for (Card card : AliceCards) {
                        System.out.println(card.getSuit().name());
                        System.out.println(card.getrank().name());
                    }


                    //omaha choose card
                    gui.Cover("Next player omaha chose").get();
                    gui.setOmaha1(player2).get();
                    gui.setOmaha2(player2).get();





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
