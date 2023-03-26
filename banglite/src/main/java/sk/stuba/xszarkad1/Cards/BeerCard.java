package sk.stuba.xszarkad1.Cards;

import sk.stuba.xszarkad1.Player;
import sk.stuba.xszarkad1.Bang;

public class BeerCard extends Card {
    
    public BeerCard(){
        name = "Beer";
    }

    @Override
    public void play(Bang bang) {
        bang.getPlayerOnTurn().changeHealthBy(1, "drank a beer. Newfound energy hits them.");
    }
    
}
