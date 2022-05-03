import java.util.LinkedList;
/**
 * This class is responsible for the operations of the whole game *
 * @author (Sanskar Raval)
 * @version (2022-04-06)
 */
public class GameLogic implements GameLogicable
{
    //Instance variables
    int gameNumber;
    int stateNumber;
    int playerWins;
    int CPUwins;
    String playerName = "Sanskar";
    String cpuName;
    
    //----------------------------- 
    boolean changeAI = false; // Change this variable to false to use the Smart CPU AI.
    //----------------------------
    
    // Linked List to store the discarded cards.
    LinkedList<Cardable> discard1 = new LinkedList<Cardable>();
    Hand cpuHand;
    Hand humanHand;
    Deckable d;
    // Constructor
    public GameLogic()
    {
        gameNumber = 1;
        stateNumber = 1;
        playerWins = 0;
        CPUwins = 0;
        cpuHand = new Hand();
        humanHand = new Hand();
        d = new Deck();
        d.shuffle();
        for(int i = 0; i<5;i++)
        {
            cpuHand.hand.add(d.drawACard(false));
            humanHand.hand.add(d.drawACard(false));
        }    
    }

    //Methods:
    public Handable getCPUHand()  //Returns the hand (Handable) of the CPU player.
    {
        return cpuHand;
    }

    public Handable getHumanHand()  //Returns the hand (Handable) of the human player.
    {
        return humanHand;
    }

    public boolean nextState(String[] messages)  //The GUI will call this method to proceed to the next stage/state of the game. The String[] parameter is an empty array, which the method can fill up with messages that will be displayed in the GUI to describe the current state of the game. The size of this array is determined by the number of lines that can be displayed in the GUI, and this is stored in PokerTableDisplay.NUM_MESSAGE_ROWS. It is set to 4 for this assignment (4 lines max). Leaves empty rows (where nothing should be displayed) to null.
    {
        if(stateNumber == 1)
        {
            humanHand.showAllCards();
            messages[0] = "Beginning of Game " + gameNumber;
            messages[1] = playerName +", Choose which cards to discard";
            messages[2] = "and click on the proceed button.";
            stateNumber++;
        }
        else if(stateNumber == 2)
        {
            discard1 = humanHand.discard();
            if(changeAI)
            {
                cpuName = "Dumb CPU";
            }
            else
            {
                cpuName = "Smart CPU";
            }
            messages[0] = playerName +" has discarded cards.";
            messages[1] = cpuName+" is thinking...";
            stateNumber++;
        }
        else if(stateNumber == 3)
        {
            if(changeAI)
            {
                discard1 = cpuHand.dumbCPUDiscard();
            }
            else
            {
                discard1 = cpuHand.smartCPUDiscard();
            }
            messages[0] = cpuName+" has discarded cards.";
            messages[1] = "Each player will be dealt with same number of cards they discarded.";
            stateNumber++;
        }
        else if(stateNumber == 4)
        {
            humanHand.draw(d,true);
            cpuHand.draw(d,false);
            messages[0] = "Each player has been dealt with new cards.";
            messages[1] = "Click in Proceed to see the winner!";
            stateNumber++;      
        }
        else if(stateNumber == 5)
        {
            cpuHand.showAllCards();
            messages[0] = cpuName +" has: " + cpuHand.evaluateHand();
            messages[1] = playerName +" has : " + humanHand.evaluateHand();
            if(humanHand.compareTo(cpuHand) == 1)
            {
                messages[2] = playerName +" Wins!!";
                playerWins++;
            }
            else if(humanHand.compareTo(cpuHand) == 0)
            {
                messages[2] = "Its a draw!!";
            }
            else
            {
                messages[2] = cpuName +" wins!!";
                CPUwins++;
            }
            messages[3] = playerName +" has won "+ playerWins +" games. "+ cpuName +" has won "+ CPUwins +" games.";
            stateNumber++;
        }
        else if(stateNumber == 6)
        {
            d.returnToDeck(discard1);
            d.returnToDeck(humanHand.returnCards());
            d.returnToDeck(cpuHand.returnCards());
            messages[0] = "Click Proceed to play a new Game!";     
            resetGame();
        }
        return true;
    }
    
    // This method is called when the game reaches 6th state.
    // It deals the hands for a new game and changes the state to 1.
    private void resetGame()
    {
        int count = 0;
        d.shuffle();
        while(count < 5)
        {
            cpuHand.hand.add(d.drawACard(false));
            humanHand.hand.add(d.drawACard(false));
            count++;
        }
        gameNumber++;
        stateNumber = 1;
    }
}
