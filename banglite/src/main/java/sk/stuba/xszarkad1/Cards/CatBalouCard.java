package sk.stuba.xszarkad1.Cards;

import sk.stuba.xszarkad1.Bang;
import sk.stuba.xszarkad1.Player;

import java.util.Scanner;

public class CatBalouCard extends Card {

    public CatBalouCard() {
        name = "Cat Balou";
    }
    
    @Override
    public void play(Bang bang) {
        Player target = bang.targetPlayer(bang.getPlayerOnTurn());
        System.out.println("Do you want to take a card from " + target + "'s [0] hand or [1] board?");
        System.out.println(target + "'s board:" + target.board.cards);
        int choice;
        do {
            choice = bang.readNumber();
        } while (choice<0 && choice<2);
        if (choice==0){
            int randIndex=bang.removeRandInHand(target);
            bang.targetDiscardsFromHand(target, randIndex);
        }
        if (choice==1){
            int randIndex=bang.removeRandInBoard(target);
            bang.targetDiscardsFromBoard(target, randIndex);
        }
        System.out.println("A random card discarded.");
    }
    
}
