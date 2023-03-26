package sk.stuba.xszarkad1.Cards;

import sk.stuba.xszarkad1.Bang;
import sk.stuba.xszarkad1.Player;


import java.util.Random;

public class DynamiteCard extends BlueCard {

    // constructor
    public DynamiteCard() {
        name = "Dynamite";
    }

    @Override
    public void play(Bang bang) {

    }
    public void activate(Bang bang) {
        int randInt = randomIntGen(8);
        Player playerOnTurn = bang.getPlayerOnTurn();
        if (randInt == 0) {
            playerOnTurn.changeHealthBy(-3, "was in close proximity to a detonating dynamite.");
            playerOnTurn.board.removeCardWithName("Barrel");
        } else {
            playerOnTurn.board.removeCardWithName("Barrel");
            Player nextPlayer = bang.nextPlayer();
            nextPlayer.board.addCard(this);

        }
    }
}