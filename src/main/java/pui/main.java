package pui;

import java.util.*;

public class main {
    public static void main(String[] args) {
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

        // Run game logic in a separate thread
       
            try {
                // Alice's turn
                String actionAlice = gui.setTXHM(player, community, true).get();
                System.out.println("Alice pressed: " + actionAlice);

                // Bob's turn
                String actionBob = gui.setTXHM(player2, community, true).get();
                System.out.println("Bob pressed: " + actionBob);
                player.setChipstack(300);
                String actionAlice2 = gui.setTXHM(player, community, true).get();
                System.out.println("Alice pressed: " + actionAlice);

                System.out.println("Test complete");
            } catch (Exception e) {
                e.printStackTrace();
            }
       
    }
}
