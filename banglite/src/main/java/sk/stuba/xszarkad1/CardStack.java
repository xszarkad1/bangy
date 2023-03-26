package sk.stuba.xszarkad1;
import java.util.ArrayList;
import java.util.Collections;
import sk.stuba.xszarkad1.Cards.Card;

public class CardStack {
    public ArrayList<Card> cards = new ArrayList<>();
    
    public void addCard(Card card){
        cards.add(card);
    }
    
    public Card getCard(int i){
        return cards.get(i);
    }
    
    public void removeCard(Card card){
        cards.remove(card);
    }
    
    public boolean removeCardWithName(String name){
        for (Card card : cards){
            if (card.getName().equals(name)){
                cards.remove(card);
                return true;
            }
        }
        return false;
    }
    
    public void shuffle(){
        Collections.shuffle(cards);
    }
    
    @Override
    public String toString(){
        String str = "";
        int i = 0;
        for (Card card : cards){
            str += "[" + i++ + "]" + card + "  ";
        }
        return str;
    }
}
