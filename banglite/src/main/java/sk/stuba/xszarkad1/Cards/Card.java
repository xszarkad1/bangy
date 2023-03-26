package sk.stuba.xszarkad1.Cards;

import sk.stuba.xszarkad1.Bang;
import java.util.Random;


public abstract class Card {
    protected String name;
    
    public abstract void play(Bang bang);
    // public abstract void activate(); <= create BlueCard extends Card
    
    public String getName(){
        return name;
    }
    
    @Override
    public String toString(){
        return name;
    }

    public int randomIntGen(int range){
        Random rand = new Random();
        int randomInt= rand.nextInt(range); //from 0 to range-1\
        return randomInt;
    }
}