
/**
 * Write a description of class Card here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Card implements Cardable
{
    //public enum Suit {HEART, DIAMOND, SPADE, CLUB};
    int value;
    Suit mySuit;
    boolean selected = false;
    boolean faceUp = false;
    //Constructor
    public Card(int value, Suit mySuit)
    {
        this.value = value;
        this.mySuit = mySuit;
    }
    //Methods:
    public boolean getSelected() //Returns the selected state: selected (true) or not selected (false).
    { 
        return selected;
    }
    public boolean getFaceUp()  //Returns true if the face is up, false if it is facing down.
    {
        return faceUp;
    }
    public Suit getSuit()  //Returns the suit.
    {
        return mySuit;
    }
    public void switchSelectedState()  //Switches the selected state: if it was true it becomes false and vice versa.
    {
        if (selected == true)
        {
            selected = false;
        }
        else
        {
            selected = true;
        }
    }
    public void resetSelected()  //Sets selected state to false (the default state).
    {
        selected = false;
    }
    public void setFaceUp(boolean faceUp)
    {
        this.faceUp = faceUp;
    }
    public int getRank()
    {
        return this.value;
    }
    public int getSuitRank()
    {
        int retVal = 0;
        if(this.mySuit == Suit.SPADE)
        {
            retVal = 4;
        }
        else if(this.mySuit == Suit.HEART)
        {
            retVal = 3;
        }
        else if(this.mySuit == Suit.DIAMOND)
        {
            retVal = 2;
        }
        else if(this.mySuit == Suit.CLUB)
        {
            retVal = 1;
        }
        return retVal;
    }
    public String toString()
    {
        String suit = "";
        String face = "";
        if(value == 11)
        {
            face += "J";
        }
        else if(value == 12)
        {
            face += "Q";
        }
        else if(value == 13)
        {
            face += "K";
        }
        else if(value == 14)
        {
            face += "A";
        }
        else
        {
            face += value;
        }
        if(mySuit == Suit.HEART)
        {
            suit += "\u2665";
        }
        else if(mySuit == Suit.DIAMOND)
        {
            suit += "\u2666";
        }
        else if(mySuit == Suit.SPADE)
        {
            suit += "\u2660";
        }
        else if(mySuit == Suit.CLUB)
        {
            suit += "\u2663";
        }
        return face +" "+ suit;
    }
}
