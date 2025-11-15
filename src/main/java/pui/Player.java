package pui;

import java.util.List;


public class Player {
    String name;
    int chipStack;
    List <Card> holeCard;
    boolean isFolded;

    public Player(String name,List<Card> holdCards,int chipStack) {
        isFolded = false;
        this.holeCard = holdCards;
        this.chipStack = chipStack;
        this.name = name;
    }
    public void fold(){
        isFolded = true;
    }
    public List<Card> getHand(){
        return holeCard;
    }
    public int getChipstack(){
        return chipStack;
    }
    public void setChipstack(int amount){
        this.chipStack = amount;
    }
}
