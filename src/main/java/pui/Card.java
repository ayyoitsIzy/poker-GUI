package pui;

public class Card {
    enum Suit{
      HEARTS,
      DIAMONDS,
      CLUBS,
      SPADES
    }

    enum Rank{
        TWO,
        THREE,
        FOUR,
        FIVE,
        SIX,
        SEVEN,
        EIGHT,
        NINE,
        TEN,
        JACK,
        QUEEN,
        KING,
        ACE,
    }
    private final Rank rank;
    private final Suit suit;
    Card(Rank rank,Suit suit){
        this.rank = rank;
        this.suit = suit;
    }
    
    //constructor for demo purpose

    public Rank getrank(){return this.rank;}
    public Suit getSuit(){return this.suit;}
}
