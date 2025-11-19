package pui;

import java.util.ArrayList;
import java.util.List;

public class ActionLog {
    List<PlayerAction> logList;
    List<Player> playersList;

    public ActionLog() {
    logList = new ArrayList<>();
    playersList = new ArrayList<>();
    }

    public void addValidaction(Player player,PlayerAction action){
        logList.add(action);
        playersList.add(player);
    }

    public List<PlayerAction> getLog (){
        return logList;
    }

    public List<Player> getPlayers(){
        return playersList;
    }

    public void clearLog() {
        logList.clear();
        playersList.clear();
    }
    
}
