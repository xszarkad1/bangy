package sk.stuba.xszarkad1.Cards;
import sk.stuba.xszarkad1.Bang;
import sk.stuba.xszarkad1.Cards.*;

public class StagecoachCard extends Card {

    public StagecoachCard() {
        name = "Stagecoach";
    }
    
    @Override
    public void play(Bang bang) {
        bang.playerDrawsFromDeck();
        bang.playerDrawsFromDeck();
    }
    
}
