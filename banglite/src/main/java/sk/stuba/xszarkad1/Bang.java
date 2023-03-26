package sk.stuba.xszarkad1;

import java.util.ArrayList;
import java.util.Scanner;
import sk.stuba.xszarkad1.Cards.*;
import java.util.Random;

public final class Bang {
    
    private Player playerOnTurn;
    private ArrayList<Player> players = new ArrayList();
    
    private CardStack deck;
    private CardStack discard;
    
    private Scanner scanner;
    
    // constructor
    public Bang(){
        deck = new CardStack();
        discard = new CardStack();
        
        int playerCount = 0;
        boolean isOutOfRange;
        do{
            System.out.print("Define player count: ");
            scanner = new Scanner(System.in);
            
            playerCount = readNumber();
            
            isOutOfRange = playerCount < 2 || playerCount > 4;
                
            if (isOutOfRange)
                System.out.println("Game can be played by 2-4 people. Choose again.");
            
        } while (isOutOfRange);
        
        
        for (int i = 0; i < playerCount; i++)
            players.add(new Player(i + 1));
        
        // generate cards
        deck.addCard(new BarrelCard());
        deck.addCard(new BarrelCard());
        deck.addCard(new BarrelCard());
        deck.addCard(new BarrelCard());
        deck.addCard(new BarrelCard());
        deck.addCard(new BarrelCard());
        deck.addCard(new BarrelCard());
        deck.addCard(new BarrelCard());
        deck.addCard(new BarrelCard());
        deck.addCard(new BarrelCard());
        deck.addCard(new BarrelCard());
        deck.addCard(new BarrelCard());
        deck.addCard(new BarrelCard());

        deck.addCard(new DynamiteCard());
        deck.addCard(new BarrelCard());
        for (int i = 0; i < 30; i++)
            deck.addCard(new BangCard());
        for (int i = 0; i < 15; i++)
            deck.addCard(new MissCard());
        for (int i = 0; i < 8; i++)
            deck.addCard(new BeerCard());
        for (int i = 0; i < 6; i++)
            deck.addCard(new CatBalouCard());
        for (int i = 0; i < 4; i++)
            deck.addCard(new StagecoachCard());
        for (int i = 0; i < 3; i++)
            deck.addCard(new PrisonCard());
        for (int i = 0; i < 2; i++)
            deck.addCard(new IndiansCard());
        
        deck.shuffle();
        
        // start game
        playerOnTurn = players.get(0);
        playGame();
    }
    
    public void playGame(){
        
//        // notice other players to look away
//            System.out.println(playerOnTurn + " begins their turn. Everyone else look away! Press enter.");
//            scanner.nextLine();
        
        // deal cards
        for (int i = 0; i < 4; i++){
            for (Player player : players){
                playerDrawsFromDeck(player);
            }
        }
        
        while(true){
            // player evaluates their effect cards on board
            System.out.println();
            System.out.println(playerOnTurn + " begins their turn.");
           // System.out.println(players.size());
            
            effectPhase();

            // player draws 2 cards
            drawPhase();

            // player plays any number of non-duplicate cards
            playPhase();

            // player discards to his life
            discardPhase();
            
            // move to next living player
            Player next = playerOnTurn;
            do {
                int indexOfPlayer = (players.indexOf(next) + 1) % players.size();
                next = players.get(indexOfPlayer);
            } while(next.getLife() < 1);
            
            playerOnTurn = next;
        }
    }
    
    public void effectPhase(){
        playerOnTurn.evaluateBoard();
    }
    
    public void drawPhase(){
        for (int i = 0; i < 2; i++)
            playerDrawsFromDeck();
        System.out.println(playerOnTurn + " draws two cards for their turn.");
    }
    
    public void playPhase(){
        
        while (!playerOnTurn.hand.cards.isEmpty()){
            int choiceIndex;
        
            while (true) {
                int handSize = playerOnTurn.hand.cards.size();
                System.out.println("Choose a card to play: " + playerOnTurn.hand +
                        "  [" + handSize + "]PASS");
                choiceIndex = readNumber();

                if (choiceIndex == handSize) {
                    break;
                }

                if (choiceIndex < 0 || choiceIndex > handSize) {
                    System.out.println("Invalid card index for discard!");
                    continue;
                }

                if (playerHasOnBoard(playerOnTurn.hand.getCard(choiceIndex))) {
                    System.out.println("You cannot play a duplicate card!");
                    continue;
                }
                if (playerOnTurn.hand.getCard(choiceIndex) instanceof MissCard){
                    System.out.println("Can't play Miss.");
                    continue;
                }

                break;
            }
            if (choiceIndex==playerOnTurn.hand.cards.size())
            {
                //pass
                return;
            }

            Card card = playerOnTurn.hand.cards.get(choiceIndex);

            if (card instanceof BlueCard){
                if (card instanceof PrisonCard){    // prison goes to discard first, then is moved to a player
                    playerDiscards(choiceIndex);
                }
                else
                    playerPlaysToBoard(choiceIndex);
            }
            else{
                playerDiscards(choiceIndex);
            }
            
            card.play(this);
        }
    }
    
    public void discardPhase(){
        int handSize = playerOnTurn.getLife();
        while (playerOnTurn.hand.cards.size() > handSize){
            System.out.println("Your hand: " + playerOnTurn.hand);
            System.out.print("Enter index of card to discard: ");
            int index = readNumber();
            
            if (index < 0 || index >= playerOnTurn.hand.cards.size()){
                System.out.println("Invalid card index for discard!");
                continue;
            }
            
            playerDiscards(index);
        }
    }
    
    public void playerDrawsFromDeck(){
        playerDrawsFromDeck(playerOnTurn);
    }
    
    public void playerDrawsFromDeck(Player player){
        // if deck is empty, shuffle the discard pile in to the deck
        if (deck.cards.isEmpty()){
            deck.cards = discard.cards;
            discard.cards = new ArrayList();
            deck.shuffle();
        }
        
        int cardIndex = deck.cards.size() - 1;
        Card card = deck.cards.get(cardIndex);
        deck.cards.remove(cardIndex);
        player.hand.cards.add(card);
    }
    
    public void playerDiscards(int index){
        Card card = playerOnTurn.hand.cards.get(index);
        playerOnTurn.hand.cards.remove(index);
        discard.cards.add(card);
    }
    public void targetDiscardsFromHand(Player target, int index){
        Card card = target.hand.cards.get(index);
        target.hand.cards.remove(index);
        discard.cards.add(card);
    }
    public void targetDiscardsFromBoard(Player target, int index){
        Card card = target.board.cards.get(index);
        target.board.cards.remove(index);
        discard.cards.add(card);
    }
    
    public void playerPlaysToBoard(int index){
        Card card = playerOnTurn.hand.cards.get(index);
        playerOnTurn.hand.cards.remove(index);
        playerOnTurn.board.cards.add(card);
    }
    
    public int readNumber(){
        int num = -1;
        try
        {
            num = Integer.parseInt(scanner.nextLine());
        }
        catch (NumberFormatException e)
        {
            System.out.println("Please input an integer value.");
        }
        return num;
    }

    private boolean playerHasOnBoard(Card card) {
        if (!(card instanceof BlueCard))    // only blue cards have to be checked
            return false;
        
        boolean has = false;
        for (Card blue : playerOnTurn.board.cards){
            if (blue.getName().equals(card.getName())){
                has = true;
                break;
            }
        }
        return has;
    }
    public boolean hasCardOnBoard(String name, Player target) {
//        if (!(card instanceof BlueCard))    // only blue cards have to be checked
//            return false;
        boolean has = false;
        for (Card blue : target.board.cards){
            if (blue.getName().equals(name));
                has = true;
                break;
            }
        return has;
    }
    
    private void printAlivePlayers(Player ignored){
        String s = "";
        
        int i = -1;
        for (Player player : players){
            i++;
            if (player == ignored || player.getLife() < 1)
                continue;
            s += "[" + i + "]" + player + " ";
        }
        
        System.out.println(s);
    }
    
    public Player targetPlayer(Player ignored){
        while (true){
            System.out.print("Choose your target: ");
            printAlivePlayers(ignored);
            int index = readNumber();
            
            if (index < 0 || index>=players.size() || players.get(index) == ignored || players.get(index).getLife() < 1) {
                System.out.println("Invalid index for target!");
                continue;
            }
            
            return players.get(index);
        }
    }
    
    public void checkForEndGame(){
        // if only one cowboy lives, the game ends
        int livingCount = 0;
        for (Player player : players){
            if (player.getLife() > 0){
                livingCount++;
                if (livingCount > 1) break;
            }
        }
        
        if (livingCount == 1){
            // game ends
            System.out.println();
            System.out.println("GAME OVER!"); //+ players.getLife() < 1 + "has won."
            System.out.println(players);
        }
        System.exit(0); //not pretty i know, it is what it is
    }
    
    public boolean forceMissCard(Player player){
        return player.hand.removeCardWithName("Miss");
    }
    public boolean forceBangCard(Player player){
        return player.hand.removeCardWithName("Bang");
    }
    
    public Player getPlayerOnTurn(){
        return playerOnTurn;
    }
    public ArrayList<Player> getAlivePlayers(){
        ArrayList<Player> alivePlayers =new ArrayList();
        for (Player player : players){
            if (player.getLife() >0){
                alivePlayers.add(player);
            }
        }
        return alivePlayers;
    }

    public int removeRandInHand(Player player){
        Random rand = new Random();
        int randInt =rand.nextInt(player.hand.cards.size());
        return randInt;
    }
    public int removeRandInBoard(Player player){
        Random rand = new Random();
        int randInt =rand.nextInt(player.board.cards.size());
        return randInt;
    }
    //public void getIndexOf(CardStack cardstack){
      //  for(int i=0; i<cardstack)
//    }
    public Player nextPlayer(){
        Player next = playerOnTurn;
        do {
            int indexOfPlayer = (players.indexOf(next) + 1) % players.size();
            next = players.get(indexOfPlayer);
        } while(next.getLife() < 1);

        playerOnTurn = next;
        return playerOnTurn;
    }
}
