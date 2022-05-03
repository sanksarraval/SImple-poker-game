import java.util.LinkedList;
import java.util.Collections;
/**
 * Deck implements the Deckable interface and creates a deck.
 *
 * @author (Sanskar)
 * @version (2022-04-06)
 */
public class Deck implements Deckable
{
    public static final int numSuits = 4;
    public static final int numSuitCards = 14;
    LinkedList<Cardable> deck;
    public Deck()
    {
        deck = new LinkedList<Cardable>();
        Cardable.Suit mySuit = null;
        // Creating a deck
        for(int i = 0; i < numSuits ;i++)
        {
            for(int j = 2; j <= numSuitCards; j++)
            {
                if(i == 0)
                {
                    mySuit = Cardable.Suit.HEART;
                }
                else if(i == 1)
                {
                    mySuit = Cardable.Suit.DIAMOND;
                }
                else if(i == 2)
                {
                    mySuit = Cardable.Suit.SPADE;
                }
                else if(i == 3)
                {
                    mySuit = Cardable.Suit.CLUB;
                }
                deck.add(new Card(j,mySuit));
            }
        }
    }

    public void shuffle()  //This must shuffle the deck randomly.
    {
        Collections.shuffle(deck);
    }

    public void returnToDeck(LinkedList<Cardable> discarded)  //This must return the cards (Cardables) that were drawn previously (passed as a LinkedList<Cardable> parameter) back to the deck (do not recreate new cards, the same cards that were drawn must go back).
    {
        while(discarded.size() != 0)
        {
            deck.addLast(discarded.remove());
        }
    }

    public Cardable drawACard(boolean faceUp)  //This deals the card (Cardable) that is at the top of the deck, either with the face up (true) or down (false).
    {
        Cardable draw = deck.removeFirst();
        draw.setFaceUp(faceUp);
        return draw;
    }
}
