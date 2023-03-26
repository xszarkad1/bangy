package sk.stuba.xszarkad1.Cards;

import sk.stuba.xszarkad1.Bang;
import java.util.ArrayList;
import sk.stuba.xszarkad1.Player;

//import java.lang.reflect.Array;

public class IndiansCard extends Card {
    
    public IndiansCard(){
        name = "Indians";
    }

    @Override
    public void play(Bang bang) {
        Player playerOnTurn = bang.getPlayerOnTurn();
        ArrayList<Player> players=bang.getAlivePlayers();
        for (Player target : players) {
            if (target == playerOnTurn) {
                continue;
            }
            else{

                boolean hasUsedBang = bang.forceBangCard(target);
                if (hasUsedBang==true) {
                    System.out.println(target+"has used Bang.");
                }
                else{
                    target.changeHealthBy(-1, "was attacked by Indians.");
                }
            }
        }
    }
}