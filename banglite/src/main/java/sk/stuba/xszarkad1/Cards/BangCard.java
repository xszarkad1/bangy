package sk.stuba.xszarkad1.Cards;

import sk.stuba.xszarkad1.Bang;
import sk.stuba.xszarkad1.Player;
import sk.stuba.xszarkad1.Cards.BarrelCard;

public class BangCard extends Card {

    public BangCard() {
        name = "Bang";
    }
    
    @Override
    public void play(Bang bang) {
        Player target = bang.targetPlayer(bang.getPlayerOnTurn());
        boolean hasBarrelOnBoard = bang.hasCardOnBoard("BarrelCard", target);
        int randomInt=randomIntGen(4);

        if(hasBarrelOnBoard==true && randomInt>0){
            System.out.println(target+" couldn't reach the barrel in time.");
            // cancer, im leaving it here, so you appreciate the genius of the finished code
            // bang.targetDiscardsFromBoard(target, target.board.cards.indexOf(name.equals("Barrel")));
        }
        if(hasBarrelOnBoard==true && randomInt==0){
            System.out.println(target+" has managed to duck behind a barrel just in time. They took no damage.");
            target.board.removeCardWithName("Barrel");
            return;
        }
        boolean hasUsedMiss = bang.forceMissCard(target);
        if (hasUsedMiss) {
                System.out.println(target + " has used Miss!");
                return;
            }
        target.changeHealthBy(-1, " was shot");
        if (target.getLife() < 1) {
            // check living players
            bang.checkForEndGame();
        }
    }
}
