package sk.stuba.xszarkad1.Cards;

import sk.stuba.xszarkad1.Bang;

public class PrisonCard extends BlueCard {
    
    public PrisonCard() {
        name = "Prison";
    }

    @Override
    public void play(Bang bang) {
    }
    
    @Override
    public void activate() {
        // 1/4 chance to escape prison
        // skip turn or escape prison, then discard this card
       int randomInt=randomIntGen(4);
        if (randomInt==0){
            System.out.println("They have used all their wit and cunning and thus managed to escape from prison.");
        }
        else{
            //PASS NO DISCARD PHASE
        }
    }
}

