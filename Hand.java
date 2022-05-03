//-----------------------------------------
// NAME        : Sanskar Raval
// STUDENT NUMBER    : 7908184
// COURSE        : COMP 2150
// INSTRUCTOR    : Olivier 
// ASSIGNMENT    : assignment 3
// QUESTION    : question 1      
// 
// REMARKS: This class implements Handable and TestableHand.
//          It is responsible for evaluating and comparing two hands.
//
//
//-----------------------------------------
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
/**
 * Write a description of class Hand here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Hand implements Handable,TestableHand
{   
    LinkedList<Cardable> hand;
    LinkedList<Cardable> discarded = new LinkedList<Cardable>();
    LinkedList<Cardable> returnedHand = new LinkedList<Cardable>();
    LinkedList<Cardable> sorted = new LinkedList<Cardable>();
    LinkedList<Cardable>otherSorted = new LinkedList<Cardable>();
    
    Card card0,card1,card2,card3,card4;
    //Constructor
    public Hand()
    {
        hand = new LinkedList<Cardable>();
    }
    //Methods:
    public Cardable getCard(int i)  //Returns the ith Cardable element of the hand.
    {
        Cardable returnCard = null;
        if(i >=0 && i <=5)
        {
            if(hand.size()>0)
            {
                returnCard = hand.get(i);
            }
        }
        return returnCard;
    }

    public void draw(Deckable d, boolean faceUp)  //This should be called after the discard method (see below), and replace all discarded cards with cards drawn from the Deckable sent as a parameter (faceUp determines if the drawn cards should have the face up or down). 
    {
        int count = 0;
        int nullFound = 0;
        int max = discarded.size();
        while(count < HAND_SIZE)
        {
            if(hand.get(count)==null)
            {
                hand.set(count,d.drawACard(faceUp));
                nullFound++;
            }
            count++;
        }
    }

    public void showAllCards()  //Flips all cards (Cardables) of the hand so that they face up. Useful for the showdown.
    {
        for(int i = 0; i<HAND_SIZE; i++)
        {
            hand.get(i).setFaceUp(true);
        }
    }

    public LinkedList<Cardable> discard()  //This method discards from the hand all the Cardables that have been selected  (that have selected state = true). The method returns a LinkedList<Cardable> containing all the cards that have been discarded from this hand.
    {
        for(int i = 0; i<HAND_SIZE;i++)
        {
            if(hand.get(i).getSelected())
            {
                discarded.add(hand.get(i));
                hand.set(i,null);
            }
        }
        return discarded;
    }

    public LinkedList<Cardable> returnCards()  //This method will be called at the end of a round to empty the hand (discard the full hand, not considering the selected state). It returns a LinkedList<Cardable> containing all the cards that were in the hand.
    {
        while(hand.size() != 0)
        {
            returnedHand.add(hand.remove());
        }
        return returnedHand;
    }
    
    public void addCards(Cardable[] cards)
    {
        for(int i = 0; i < cards.length; i++)
        {
            hand.add(i,cards[i]);
        }
    }
    
    public LinkedList<Cardable> dumbCPUDiscard()
    {
        Random rand = new Random();// Instance of random class.
        int random = rand.nextInt(HAND_SIZE);
        discarded.add(hand.get(random));
        hand.set(random,null);
        return discarded;
    }

    public LinkedList<Cardable> smartCPUDiscard()
    {
        sorted = sortByRank(hand);
        Card[] c = new Card[5];
        for(int i = 0; i<c.length;i++)
        {
            c[i] = (Card)sorted.get(i);
        }
        if(this.straightFlush() == 1){}
        else if(this.fourOfaKind() == 1)
        {
            int this4Pair = -1; // Set of 4 cards in out hand.
            int thisK     = -1;// Kicker in our hand.
            if(c[0].getRank() == c[1].getRank())
            {
                this4Pair = 0;
                thisK = 4;
            }
            else
            {
                this4Pair = 1;
                thisK = 0;
            }
            discarded.add(hand.get(thisK));
            hand.set(thisK,null);
        }
        else if(this.fullHouse() == 1){}
        else if(this.flush() == 1){}
        else if(this.straight() == 1){}
        else if(this.threeOfaKind() == 1)
        {
            int thisTri3k = -1;           
            int tOddHigh = -1;
            int tOddLow = -1;
            // Checking the position of the tri set, high and low
            if(c[0].getRank() == c[2].getRank())
            {
                thisTri3k = 0;
                tOddLow = 3;
                tOddHigh = 4;
            }
            else if(c[1].getRank() == c[3].getRank())
            {
                tOddLow = 0;
                thisTri3k = 1;
                tOddHigh = 4;
            }
            else if(c[2].getRank() == c[4].getRank())
            {
                thisTri3k = 2;
                tOddHigh = 1;
                tOddLow = 0;
            }
            discarded.add(hand.get(tOddLow));
            discarded.add(hand.get(tOddHigh));

            hand.set(tOddLow,null);
            hand.set(tOddHigh,null);
        }
        else if(this.twoPair() == 1)
        {
            int thisPair1 = -1;
            int thisPair2 = -1;
            int thisKicker = -1;
            if(c[0].getRank() == c[1].getRank())
            {
                thisPair1 = 0;
                if(c[2].getRank() == c[3].getRank())
                {
                    thisPair2 = 2;
                    thisKicker = 4;
                }
                else if(c[3].getRank() == c[4].getRank())
                {
                    thisPair2 = 3;
                    thisKicker = 2;
                }
            }
            else
            {
                thisPair1 = 1;
                thisPair2 = 3;
                thisKicker = 0;
            }
            discarded.add(hand.get(thisKicker));
            hand.set(thisKicker,null);
        }
        else if(this.pair() == 1)
        {
            int thisPair = -1;
            int thisHigh = -1;
            int thisMid  = -1;
            int thisLow  = -1;

            if(c[0].getRank() == c[1].getRank())
            {
                thisPair = 0;
                thisHigh = 4;
                thisMid = 3;
                thisLow = 2;
            }
            else if(c[1].getRank() == c[2].getRank())
            {
                thisPair = 1;
                thisHigh = 4;
                thisMid = 3;
                thisLow = 0;
            }
            else if(c[2].getRank() == c[3].getRank())
            {
                thisPair = 2;
                thisHigh = 4;
                thisMid = 1;
                thisLow = 0;
            }
            else if(c[3].getRank() == c[4].getRank())
            {
                thisPair = 3;
                thisHigh = 2;
                thisMid = 1;
                thisLow = 0;
            }
            
            discarded.add(hand.get(thisLow));
            hand.set(thisLow,null);
        }
        else
        {
            discarded.add(hand.get(0));
            discarded.add(hand.get(1));
            hand.set(1,null);
            hand.set(0,null);
        }
        return discarded;
    }
    
    private void sortHand()
    {
        sorted = sortByRank(hand);
        card0 = (Card)sorted.get(0);
        card1 = (Card)sorted.get(1);
        card2 = (Card)sorted.get(2);
        card3 = (Card)sorted.get(3);
        card4 = (Card)sorted.get(4);
    }
    
    public String evaluateHand()  //This method evaluates what is in the hand,
    {
        String result = "";
        if(this.straightFlush() == 1)
        {
            result += "Straight Flush";
        }
        else if(this.fourOfaKind() == 1)
        {
            result += "Four Of A Kind";
        }
        else if(this.fullHouse() == 1)
        {
            result += "Full House";
        }
        else if(this.flush() == 1)
        {
            result += "Flush";
        }
        else if(this.straight() == 1)
        {
            result += "Straight";
        }
        else if(this.threeOfaKind() == 1)
        {
            result += "Three Of A Kind";
        }
        else if(this.twoPair() == 1)
        {
            result += "Two Pairs";
        }
        else if(this.pair() == 1)
        {
            result += "a Pair";
        }
        else
        {
            int hCard = this.highCard();
            String face = "";
            if(hCard == 11)
            {
                face += "J";
            }
            else if(hCard == 12)
            {
                face += "Q";
            }
            else if(hCard == 13)
            {
                face += "K";
            }
            else if(hCard == 14)
            {
                face += "A";
            }
            else
            {
                face += hCard;
            }
            result += "a high card " + face; 
        }
        return result;
    }

    public int compareTo(Handable otherHand)
    {
        // Invalid return value
        int retVal = 2;
        // Casting the hand instance.
        Hand other = (Hand)otherHand;
        //Sorting the hands.
        sorted = sortByRank(hand);
        otherSorted = sortByRank(other.hand);
        // Creating an array and filling it
        Card[] o = new Card[5];
        Card[] c = new Card[5];       
        for(int i = 0; i<c.length;i++)
        {
            c[i] =(Card)sorted.get(i);
            o[i] = (Card)otherSorted.get(i);
        }
        
        // If both are straight flushes
        // Then we break the tie by comparing the higest card
        if(this.straightFlush() == 1)
        {
            if(other.straightFlush() ==1)
            {
                //Comparing the highest card
                if(c[4].getRank() >= o[4].getRank())
                {
                    retVal = 1;
                }
                // If they are equal we chack the Suit Rank of the card
                else if(c[4].getRank() == o[4].getRank())
                {
                    if(c[4].getSuitRank() > o[4].getSuitRank())
                    {
                        retVal = 1;
                    }
                    // If the ranks are also same then we have a tie.
                    else if(c[4].getSuitRank() == o[4].getSuitRank())
                    {
                        retVal = 0;
                    }
                }
                else
                {
                    retVal = -1;
                }
            }
            else
            {
                retVal = 1;
            }
        }
        // Four Of a Kind
        else if(this.fourOfaKind() == 1)
        {
            // lose condition.
            if(other.straightFlush() == 1)
            {// We lose
                retVal = -1;
            }
            // Else if other player also has a 4 of a kind, then there is a tie
            else if(other.fourOfaKind() ==1)
            {
                int this4Pair = -1; // Set of 4 cards in out hand.
                int thisK = -1;// Kicker in our hand.
                int other4Pair = -1;// Set of 4 cards in other hand.
                int otherK = -1;// Kicker in other hand.
                // We point out the location where we spot the 4 set of cards and a kicker
                if(c[0].getRank() == c[1].getRank())
                {
                    this4Pair = 0;
                    thisK = 4;
                }
                else
                {
                    this4Pair = 1;
                    thisK = 0;
                }

                if(o[0].getRank() == o[1].getRank())
                {
                    other4Pair = 0;
                    otherK = 4;
                }
                else
                {
                    other4Pair = 1;
                    otherK = 0;
                }
                // We check the ranks of the set of 4 cards in our and the other hand.
                if(c[this4Pair].getRank() > o[other4Pair].getRank())
                {
                    retVal = 1;
                }
                else if(c[this4Pair].getRank() == o[other4Pair].getRank())
                {
                    // If the 4 pair is equal, we check the value of the kicker.
                    if(c[thisK].getRank() > o[otherK].getRank())
                    {
                        retVal = 1;
                    }
                    else if(c[thisK].getRank() == o[otherK].getRank())
                    {
                        retVal = 0; // We have a draw.
                    }
                    else
                    {
                        retVal = -1;
                    }
                }
                else
                {
                    retVal = -1;
                }
            }
            // Win Condition
            else
            {
                retVal = 1;
            }
        }
        //FullHouse
        else if(this.fullHouse() == 1)
        {
            // lose conditons
            if(other.straightFlush() == 1)
            {
                retVal = -1;
            }
            else if (other.fourOfaKind() == 1)
            {
                retVal = -1;
            }
            // Tie contiton
            else if(other.fullHouse() == 1)
            {              
                int thisPairFH = -1; // location of Pair of our hand
                int thisTriFH = -1; // location of trio of our hand
                // We point out the location where we spot the 3 set of cards and a pair
                if(c[1].getRank() == c[2].getRank())
                {
                    thisPairFH = 3;
                    thisTriFH = 0;
                }
                else if(c[1].getRank() != c[2].getRank())
                {
                    thisPairFH = 0;
                    thisTriFH = 2;
                }

                int otherPairFH = -1;// location of Pair of other hand
                int otherTriFH = -1;// location of trio of other hand
                if(o[1].getRank() == o[2].getRank())
                {
                    otherPairFH = 3;
                    otherTriFH = 0;
                }
                else if(o[1].getRank() != o[2].getRank())
                {
                    otherPairFH = 0;
                    otherTriFH = 2;
                }

                // Checking the tri rank
                if(c[thisTriFH].getRank() > o[otherTriFH].getRank())
                {
                    retVal = 1;
                }
                // If the set of Tri is equal
                else if(c[thisTriFH].getRank() == o[otherTriFH].getRank())
                {
                    // We check the pair rank
                    if(c[thisPairFH].getRank() > o[otherPairFH].getRank())
                    {
                        retVal = 1;
                    }
                    // If its equal, we have a tie
                    else if(c[thisPairFH].getRank() == o[otherPairFH].getRank())
                    {
                        retVal = 0;
                    }
                    else
                    {
                        retVal = -1;
                    }
                }
                else
                {
                    retVal = -1;
                }
            }
            // Win Condition.
            else
            {
                retVal = 1;
            }
        }
        // Flush
        else if(this.flush() == 1)
        {
            // Lose Constitons
            if(other.straightFlush() == 1)
            {
                retVal = -1;
            }
            else if (other.fourOfaKind() == 1)
            {
                retVal = -1;
            }
            else if(other.fullHouse() == 1)
            {
                retVal = -1;
            }
            // Tie condition.
            else if(other.flush() == 1)
            {
                //We break the tie by comparing the high values of the other hand.
                if(c[4].getRank() > o[4].getRank())
                {
                    retVal = 1;
                }
                // If they are equal
                else if(c[4].getRank() == o[4].getRank())
                {
                    // We compare the lower ranks, and so on.
                    if(c[3].getRank() > o[3].getRank())
                    {
                        retVal = 1;
                    }
                    else if(c[3].getRank() == o[3].getRank())
                    {
                        if(c[2].getRank() > o[2].getRank())
                        {
                            retVal = 1;
                        }
                        else if(c[2].getRank() == o[2].getRank())
                        {
                            if(c[1].getRank() > o[1].getRank())
                            {
                                retVal = 1;
                            }
                            else if(c[1].getRank() == o[1].getRank())
                            {
                                if(c[0].getRank() > o[0].getRank())
                                {
                                    retVal = 1;
                                }
                                else if(c[0].getRank() == o[0].getRank())
                                {
                                    retVal = 0;
                                }
                                else
                                {
                                    retVal = -1;
                                }
                            }//c[1]=o[1]
                            else
                            {
                                retVal = -1;
                            }
                        }//c[2] = o[2]
                        else
                        {
                            retVal = -1;
                        }
                    }//c[3] = o[3]
                    else
                    {
                        retVal = -1;
                    }
                }//c[4] = o[4]
                else
                {
                    retVal = -1;
                }
            }//else if
            else
            {
                retVal = 1;
            }
        }
        // Straight
        else if(this.straight() == 1)
        {
            // Lose Constitons
            if(other.straightFlush() == 1)
            {
                retVal = -1;
            }
            else if (other.fourOfaKind() == 1)
            {
                retVal = -1;
            }
            else if(other.fullHouse() == 1)
            {
                retVal = -1;
            }
            else if(other.flush() == 1)
            {
                retVal = -1;
            }
            // Tie Conditon
            else if(other.straight() == 1)
            {
                // We break the tie by comparing the 1st and 2nd highest ranking cards.
                if(c[4].getRank() > o[4].getRank())
                {
                    retVal = 1;
                }
                else if(c[4].getRank() == o[4].getRank())
                {
                    if(c[3].getRank() > o[3].getRank())
                    {
                        retVal = 1;
                    }
                    else
                    {
                        retVal = -1;
                    }
                }
                else
                {
                    retVal = -1;
                }
            }
            //Win Conditions
            else
            {
                retVal = 1;
            }
        }
        else if(this.threeOfaKind() == 1)
        {
            // Lose Constitons
            if(other.straightFlush() == 1)
            {
                retVal = -1;
            }
            else if (other.fourOfaKind() == 1)
            {
                retVal = -1;
            }
            else if(other.fullHouse() == 1)
            {
                retVal = -1;
            }
            else if(other.flush() == 1)
            {
                retVal = -1;
            }
            else if(other.straight() == 1)
            {
                retVal = -1;
            }
            // Tie condition
            else if(other.threeOfaKind() == 1)
            {
                int thisTri3k = -1; // This hands trio location.
                int otherTri3k = -1;// Other hands trio location.
                int tOddHigh = -1; // This hands odd high location.
                int tOddLow = -1; // This hands odd low location.
                int oOddHigh = -1;// Other hands odd high location.
                int oOddLow = -1;// Other hands odd high location.
                // Checking the position of the tri set, high and low
                if(c[0].getRank() == c[2].getRank())
                {
                    thisTri3k = 0;
                    tOddLow = 3;
                    tOddHigh = 4;
                }
                else if(c[1].getRank() == c[3].getRank())
                {
                    tOddLow = 0;
                    thisTri3k = 1;
                    tOddHigh = 4;
                }
                else if(c[2].getRank() == c[4].getRank())
                {
                    thisTri3k = 2;
                    tOddHigh = 1;
                    tOddLow = 0;
                }
                // Checking the position of the tri set, high and low
                if(o[0].getRank() == o[2].getRank())
                {
                    otherTri3k = 0;
                    oOddLow = 3;
                    oOddHigh = 4;
                }
                else if(o[1].getRank() == o[3].getRank())
                {
                    otherTri3k = 1;
                    oOddLow = 0;
                    oOddHigh = 4;
                }
                else if(o[2].getRank() == o[4].getRank())
                {
                    otherTri3k = 2;
                    oOddLow = 0;
                    oOddHigh = 1;
                }

                // Checking the rank of three equal cards
                if(c[thisTri3k].getRank() > o[otherTri3k].getRank())
                {
                    retVal = 1;
                }
                // If they are equal
                else if(c[thisTri3k].getRank() == o[otherTri3k].getRank())
                {
                    // Check the next high rank.
                    if(c[tOddHigh].getRank() > o[oOddHigh].getRank())
                    {
                        retVal = 1;
                    }
                    // If thats equal then
                    else if(c[tOddHigh].getRank() == o[oOddHigh].getRank())
                    {
                        // Check the low rank 
                        if(c[tOddLow].getRank() > o[oOddLow].getRank())
                        {
                            retVal = 1;
                        } 
                        else if(c[tOddLow].getRank() == o[oOddLow].getRank())
                        {
                            retVal = 0;
                        }
                        else
                        {
                            retVal = -1;
                        }
                    }
                    else
                    {
                        retVal = -1;
                    }
                }
                else
                {
                    retVal = -1;
                }
            }
            // Win condition.
            else
            {
                retVal = 1;
            }
        }
        // Two Pair
        else if(this.twoPair() == 1)
        {
            // Lose Constitons
            if(other.straightFlush() == 1)
            {
                retVal = -1;
            }
            else if (other.fourOfaKind() == 1)
            {
                retVal = -1;
            }
            else if(other.fullHouse() == 1)
            {
                retVal = -1;
            }
            else if(other.flush() == 1)
            {
                retVal = -1;
            }
            else if(other.straight() == 1)
            {
                retVal = -1;
            }
            else if(other.threeOfaKind() == 1)
            {
                retVal = -1;
            }
            // Tie condition.
            else if(other.twoPair() == 1)
            {
                int thisPair1 = -1;
                int thisPair2 = -1;
                int thisKicker = -1;
                int otherPair1 = -1;
                int otherPair2 = -1;
                int otherKicker = -1;

                if(c[0].getRank() == c[1].getRank())
                {
                    thisPair1 = 0;
                    if(c[2].getRank() == c[3].getRank())
                    {
                        thisPair2 = 2;
                        thisKicker = 4;
                    }
                    else if(c[3].getRank() == c[4].getRank())
                    {
                        thisPair2 = 3;
                        thisKicker = 2;
                    }
                }
                else
                {
                    thisPair1 = 1;
                    thisPair2 = 3;
                    thisKicker = 0;
                }
                if(o[0].getRank() == o[1].getRank())
                {
                    otherPair1 = 0;
                    if(o[2].getRank() == o[3].getRank())
                    {
                        otherPair2 = 2;
                        otherKicker = 4;
                    }
                    else if(o[3].getRank() == o[4].getRank())
                    {
                        otherPair2 = 3;
                        otherKicker = 2;
                    }
                }
                else
                {
                    otherPair1 = 1;
                    otherPair2 = 3;
                    otherKicker = 0;
                }
                // Comparing the highest pair
                if(c[thisPair2].getRank() > o[otherPair2].getRank())
                {
                    retVal = 1;
                }
                // If the highest pair is equal
                else if(c[thisPair2].getRank() == o[otherPair2].getRank())
                {
                    // Compare the low pair.
                    if(c[thisPair1].getRank() > o[otherPair1].getRank())
                    {
                        retVal = 1;
                    }
                    // If its equal
                    else if(c[thisPair1].getRank() == o[otherPair1].getRank())
                    {
                        // Compare the Kicker
                        if(c[thisKicker].getRank() > o[otherKicker].getRank())
                        {
                            retVal = 1;
                        }
                        else if (c[thisKicker].getRank() == o[otherKicker].getRank())
                        {
                            retVal = 0;
                        } // kicker
                        else
                        {
                            retVal = -1;
                        }
                    } // low pair
                    else
                    {
                        retVal = -1;
                    }
                }
                else
                {
                    retVal = -1;
                }
            }

            // Win Condition
            else
            {
                retVal = 1;
            }
        }
        // Pair
        else if(this.pair() == 1)
        {
            // Lose Constitons
            if(other.straightFlush() == 1)
            {
                retVal = -1;
            }
            else if (other.fourOfaKind() == 1)
            {
                retVal = -1;
            }
            else if(other.fullHouse() == 1)
            {
                retVal = -1;
            }
            else if(other.flush() == 1)
            {
                retVal = -1;
            }
            else if(other.straight() == 1)
            {
                retVal = -1;
            }
            else if(other.twoPair() == 1)
            {
                retVal = -1;
            }
            // Tie condition.
            else if(other.pair() == 1)
            {   
                int thisPair = -1;
                int thisHigh = -1;
                int thisMid  = -1;
                int thisLow  = -1;

                int otherPair  = -1;
                int otherHigh = -1;
                int otherMid = -1;
                int otherLow = -1;

                if(c[0].getRank() == c[1].getRank())
                {
                    thisPair = 0;
                    thisHigh = 4;
                    thisMid = 3;
                    thisLow = 2;
                }
                else if(c[1].getRank() == c[2].getRank())
                {
                    thisPair = 1;
                    thisHigh = 4;
                    thisMid = 3;
                    thisLow = 0;
                }
                else if(c[2].getRank() == c[3].getRank())
                {
                    thisPair = 2;
                    thisHigh = 4;
                    thisMid = 1;
                    thisLow = 0;
                }
                else if(c[3].getRank() == c[4].getRank())
                {
                    thisPair = 3;
                    thisHigh = 2;
                    thisMid = 1;
                    thisLow = 0;
                }

                if(o[0].getRank() == o[1].getRank())
                {
                    otherPair = 0;
                    otherHigh = 4;
                    otherMid = 3;
                    otherLow = 2;
                }
                else if(o[1].getRank() == o[2].getRank())
                {
                    otherPair = 1;
                    otherHigh = 4;
                    otherMid = 3;
                    otherLow = 0;
                }
                else if(o[2].getRank() == o[3].getRank())
                {
                    otherPair = 2;
                    otherHigh = 4;
                    otherMid = 1;
                    otherLow = 0;
                }
                else if(o[3].getRank() == o[4].getRank())
                {
                    otherPair = 3;
                    otherHigh = 2;
                    otherMid = 1;
                    otherLow = 0;
                }

                if(c[thisPair].getRank() > o[otherPair].getRank())
                {
                    retVal = 1;
                }
                else if(c[thisPair].getRank() == o[otherPair].getRank())
                {
                    if(c[thisHigh].getRank() > o[otherHigh].getRank())
                    {
                        retVal = 1;
                    }
                    else if(c[thisHigh].getRank() == o[otherHigh].getRank())
                    {
                        if(c[thisMid].getRank() > o[otherMid].getRank())
                        {
                            retVal = 1;
                        }
                        else if(c[thisMid].getRank() == o[otherMid].getRank())
                        {
                            if(c[thisLow].getRank() > o[otherLow].getRank())
                            {
                                retVal = 1;
                            }
                            else if(c[thisLow].getRank() == o[otherLow].getRank())
                            {
                                retVal = 0;
                            }
                            else
                            {
                                retVal = -1;
                            }
                        }
                        else
                        {
                            retVal = -1;
                        }
                    }
                    else
                    {
                        retVal = -1;
                    }
                }
                else
                {
                    retVal = -1;
                }
            }
            // Win Condition.
            else
            {
                retVal = 1;
            }
        }
        else
        {
            // We have a high Card.
            // Lose Constitons
            if(other.straightFlush() == 1)
            {
                retVal = -1;
            }
            else if (other.fourOfaKind() == 1)
            {
                retVal = -1;
            }
            else if(other.fullHouse() == 1)
            {
                retVal = -1;
            }
            else if(other.flush() == 1)
            {
                retVal = -1;
            }
            else if(other.straight() == 1)
            {
                retVal = -1;
            }
            else if(other.twoPair() == 1)
            {
                retVal = -1;
            }
            else if(other.pair() ==1)
            {
                retVal = -1;
            }
            // If both have high card
            else
            {
                // Comparing high card
                if(c[4].getRank() > o[4].getRank())
                {
                    retVal = 1;
                }
                // If the high card is equal then compare the next high card. and so on.
                else if(c[4].getRank() == o[4].getRank())
                {
                    if(c[3].getRank() > o[3].getRank())
                    {
                        retVal = 1;
                    }
                    else if(c[3].getRank() == o[3].getRank())
                    {
                        if(c[2].getRank() > o[2].getRank())
                        {
                            retVal = 1;

                        }
                        else if(c[2].getRank() == o[2].getRank())
                        {
                            if(c[1].getRank() > o[1].getRank())
                            {
                                retVal = 1;
                            }
                            else if(c[1].getRank() == o[1].getRank())
                            {
                                if(c[0].getRank() > o[0].getRank())
                                {
                                    retVal = 1;
                                }
                                else if(c[0].getRank() == o[0].getRank())
                                {
                                    retVal = 0;
                                }
                                else
                                {
                                    retVal = -1;
                                }
                            }
                            else
                            {
                                retVal = -1;
                            }
                        }
                        else
                        {
                            retVal = -1;
                        }
                    }
                    else
                    {
                        retVal = -1;
                    }
                }
                else
                {
                    retVal = -1;
                }
            }
        }
        return retVal;
    }

    public int straightFlush()
    {
        int retVal = 0;
        sortHand();
        if(this.flush() == 1 && this.straight() ==1)
        {
            retVal = 1;
        }
        // Edge case
        if(card0.getRank() == 5 && card1.getRank() == 4 && card2.getRank() == 3
        && card3.getRank() == 2 && card4.getRank() == 14)
        {
            retVal = 1;
        }
        return retVal;
    }

    public int fourOfaKind()
    {
        int retVal = -1;
        sortHand();
        // Checks if the first and the third hand is equal and and second and the fourth hand is equal.
        if(card0.getRank() == card1.getRank() && card1.getRank() == card2.getRank() && card2.getRank() == card3.getRank())
        {
            retVal = 1;
        }
        else if (card1.getRank() == card2.getRank() && card2.getRank() == card3.getRank() && card3.getRank() == card4.getRank())
        {
            retVal = 1;
        }
        else
        {
            retVal = 0;
        }
        return retVal;
    }

    public int fullHouse()
    {
        int retVal = -1;
        sortHand();
        // Checks if there are three of a kind in a hand.
        if(card0.getRank() == card1.getRank() && card1.getRank() == card2.getRank()
        && card3.getRank() == card4.getRank())
        {
            retVal = 1;
        }
        else if(card0.getRank() == card1.getRank() && card2.getRank() == card3.getRank()
        && card3.getRank() == card4.getRank())
        {
            retVal = 1;
        }
        else
        {
            retVal = 0;
        }
        return retVal;
    }

    public int flush()
    {
        int retVal = 1;
        // Checks if the five cards have the same suit.
        for(int i = 1; i < HAND_SIZE; i++)
        {
            if(hand.get(0).getSuit() != hand.get(i).getSuit())
            {
                retVal = 0;
            }
        }
        return retVal;
    }

    public int straight()
    {
        int retVal = 1;
        // Checks if the five cards are in the same sequence or not.
        for(int j = 1; j < HAND_SIZE; j++)
        {
            Card curr = (Card)hand.get(j-1);
            Card next = (Card)hand.get(j);
            if(curr.getRank() != (next.getRank()-1))
            {
                retVal = 0;
            }
        }
        return retVal;
    }

    public int threeOfaKind()
    {
        int retVal = 0;
        sortHand();
        // Checking the 1st and 2nd card are equal and 2nd and 3rd cards are equal.
        if(card0.getRank() == card1.getRank() && card1.getRank() == card2.getRank())
        {
            retVal = 1;
        }
        else if(card1.getRank() == card2.getRank() && card2.getRank() == card3.getRank())
        {
            retVal = 1;
        }
        else if(card2.getRank() == card3.getRank() && card3.getRank() == card4.getRank())
        {
            retVal = 1;
        }
        return retVal;
    }

    public int twoPair()
    {
        int retVal = 0;
        sortHand();
        // Checking the cases where two pair can exist.
        if(card0.getRank() == card1.getRank() && card2.getRank() == card3.getRank())
        {
            retVal = 1;
        }
        else if(card0.getRank() == card1.getRank() && card3.getRank() == card4.getRank())
        {
            retVal = 1;
        }
        else if(card1.getRank() == card2.getRank() && card3.getRank() == card4.getRank())
        {
            retVal = 1;
        }
        return retVal;
    }

    public int pair()
    {
        int retVal = 0;
        sortHand();
        // Checking for a pair.
        if(card0.getRank() == card1.getRank() || card1.getRank() == card2.getRank()
        || card2.getRank() == card3.getRank()||card3.getRank() == card4.getRank())
        {
            retVal = 1;
        }
        return retVal;
    }

    public int highCard()
    {
        int highCardValue = 0;

        sorted = sortByRank(hand);
        Card curr = null;
        for(int i = 0; i<HAND_SIZE;i++)
        {
            curr = (Card)sorted.get(i);
            if(curr.getRank() > highCardValue)
            {
                highCardValue = curr.getRank();
            }
        }
        return highCardValue;
    }
    
    // Overriding the sort method to sort the linked list.
    public LinkedList<Cardable>sortByRank(LinkedList<Cardable> hand)
    {
        Collections.sort(hand, new Comparator<Cardable>()
            {
                @Override
                public int compare(Cardable o1, Cardable o2)
                {
                    Card c1 = (Card)o1;
                    Card c2 = (Card)o2;
                    Integer c1Value = c1.getRank();
                    Integer c2Value = c2.getRank();
                    return c1Value.compareTo(c2Value);
                }
            });
        return hand;
    }
}
