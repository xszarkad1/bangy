package sk.stuba.xszarkad1;

import java.util.Scanner;
import sk.stuba.xszarkad1.Cards.BlueCard;
import sk.stuba.xszarkad1.Cards.Card;

public class Player { 
    
    CardStack hand = new CardStack();
   public CardStack board = new CardStack();
    
    private String name;
    private int life = 4;
    
    public Player(int i){
        System.out.print("Give player " + i + " a name: ");
        Scanner scanner = new Scanner(System.in);
        name = scanner.nextLine();
    }
    
    public int getLife(){
        return life;
    }
    
    public void evaluateBoard(){
        for (Card card : board.cards) {
            ((BlueCard)card).activate();    // only bluecards are placed on the board
        }
    }
    
    public void changeHealthBy(int value, String reason){
        life += value;
        System.out.println(this + reason);
        if (life <= 0)
            System.out.println(name + " has perished!");
    }
    
    @Override
    public String toString(){
        return name + "(hp:" + life + ")";
    }
}
