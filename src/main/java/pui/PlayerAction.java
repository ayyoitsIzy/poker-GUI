package pui;


enum ActionType{
    FOLD,
    CHECK,
    CALL,
    BET,
    RAISE,
}


public class PlayerAction {
    ActionType type;
    int amount;

    public PlayerAction(ActionType type,int Amount) {
        this.type = type;
        this.amount = Amount;        
    }
    public PlayerAction(ActionType type) {
        this.type = type;     
    }
    
}
