import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class JUnitTests {
    @Test
    public void straightVSThreeOfAKind() 
    {
        Cardable[] cards1 = {new Card(2, Cardable.Suit.CLUB), new Card(2, Cardable.Suit.HEART), new Card(3, Cardable.Suit.CLUB), new Card(4, Cardable.Suit.CLUB), new Card(2, Cardable.Suit.DIAMOND)};
        TestableHand th1 = new Hand();
        th1.addCards(cards1);

        Cardable[] cards2 = {new Card(3, Cardable.Suit.HEART), new Card(4, Cardable.Suit.HEART), new Card(5, Cardable.Suit.HEART), new Card(6, Cardable.Suit.HEART), new Card(7, Cardable.Suit.DIAMOND)};
        TestableHand th2 = new Hand();
        th2.addCards(cards2);

        assertTrue(th1.compareTo(th2) < 0, "Straight beats Three of a kind.");
    }
    
    @Test
    public void testStraightFlush() 
    {
        Cardable[] cards1 = {new Card(5, Cardable.Suit.HEART), new Card(4, Cardable.Suit.HEART), new Card(3, Cardable.Suit.HEART), new Card(2, Cardable.Suit.HEART), new Card(14, Cardable.Suit.HEART)};
        TestableHand th1 = new Hand();
        th1.addCards(cards1);

        Cardable[] cards2 = {new Card(14, Cardable.Suit.HEART), new Card(13, Cardable.Suit.HEART), new Card(12, Cardable.Suit.HEART), new Card(11, Cardable.Suit.HEART), new Card(10, Cardable.Suit.HEART)};
        TestableHand th2 = new Hand();
        th2.addCards(cards2);
        
        assertTrue(th1.compareTo(th2) < 0, "Both StraightFlush , but th2 is higher.");  
    }
    
    @Test
    public void testFourOfAKind() 
    {
        Cardable[] cards1 = {new Card(14, Cardable.Suit.HEART), new Card(14, Cardable.Suit.HEART), new Card(14, Cardable.Suit.HEART), new Card(3, Cardable.Suit.HEART), new Card(14, Cardable.Suit.SPADE)};
        TestableHand th1 = new Hand();
        th1.addCards(cards1);

        Cardable[] cards2 = {new Card(14, Cardable.Suit.HEART), new Card(14, Cardable.Suit.HEART), new Card(14, Cardable.Suit.HEART), new Card(14, Cardable.Suit.HEART), new Card(2, Cardable.Suit.SPADE)};
        TestableHand th2 = new Hand();
        th2.addCards(cards2);
        
        Cardable[] cards3 = {new Card(11, Cardable.Suit.HEART), new Card(11, Cardable.Suit.HEART), new Card(11, Cardable.Suit.HEART), new Card(11, Cardable.Suit.HEART), new Card(2, Cardable.Suit.SPADE)};
        TestableHand th3 = new Hand();
        th3.addCards(cards3);
        
        Cardable[] cards4 = {new Card(14, Cardable.Suit.HEART), new Card(13, Cardable.Suit.HEART), new Card(12, Cardable.Suit.HEART), new Card(11, Cardable.Suit.HEART), new Card(10, Cardable.Suit.HEART)};
        TestableHand th4 = new Hand();
        th4.addCards(cards4);
    
        assertTrue(th1.compareTo(th2) > 0, "Same Quadro, but th1 has higher Kicker");
        assertTrue(th2.compareTo(th3) > 0, "Th2 has higher quadro");
        assertTrue(th3.compareTo(th4) < 0, "Four of a Kind vs Straight Flush");
    }
    
    @Test
    public void testFullHouse() 
    {
        Cardable[] cards1 = {new Card(9, Cardable.Suit.HEART), new Card(8, Cardable.Suit.SPADE), new Card(8, Cardable.Suit.CLUB), new Card(9, Cardable.Suit.HEART), new Card(9, Cardable.Suit.SPADE)};
        TestableHand th1 = new Hand();
        th1.addCards(cards1);

        Cardable[] cards2 = {new Card(9, Cardable.Suit.HEART), new Card(9, Cardable.Suit.HEART), new Card(9, Cardable.Suit.HEART), new Card(14, Cardable.Suit.HEART), new Card(14, Cardable.Suit.SPADE)};
        TestableHand th2 = new Hand();
        th2.addCards(cards2);
        
        Cardable[] cards3 = {new Card(10, Cardable.Suit.HEART), new Card(10, Cardable.Suit.SPADE), new Card(10, Cardable.Suit.DIAMOND), new Card(2, Cardable.Suit.CLUB), new Card(2, Cardable.Suit.SPADE)};
        TestableHand th3 = new Hand();
        th3.addCards(cards3);
        
        Cardable[] cards4 = {new Card(10, Cardable.Suit.HEART), new Card(10, Cardable.Suit.SPADE), new Card(10, Cardable.Suit.DIAMOND), new Card(2, Cardable.Suit.CLUB), new Card(2, Cardable.Suit.SPADE)};
        TestableHand th4 = new Hand();
        th4.addCards(cards4);
        
        Cardable[] cards5 = {new Card(14, Cardable.Suit.HEART), new Card(14, Cardable.Suit.HEART), new Card(14, Cardable.Suit.HEART), new Card(14, Cardable.Suit.HEART), new Card(2, Cardable.Suit.SPADE)};
        TestableHand th5 = new Hand();
        th5.addCards(cards5);

        assertTrue(th1.compareTo(th2) < 0, "Same trio, but th2 has higher other pair.");
        assertTrue(th2.compareTo(th3) < 0, "th3 has higher trio.");
        assertTrue(th3.compareTo(th4) == 0, "Exactly same cards.");
        assertTrue(th4.compareTo(th5) < 0, "Full House vs Four of a Kind");
    }
    
    @Test
    public void testFlush()
    {
        Cardable[] cards1 = {new Card(9, Cardable.Suit.HEART), new Card(8, Cardable.Suit.SPADE), new Card(8, Cardable.Suit.CLUB), new Card(9, Cardable.Suit.HEART), new Card(9, Cardable.Suit.SPADE)};
        TestableHand th1 = new Hand();
        th1.addCards(cards1);
        
        Cardable[] cards4 = {new Card(2, Cardable.Suit.HEART), new Card(4, Cardable.Suit.HEART), new Card(5, Cardable.Suit.HEART), new Card(6, Cardable.Suit.HEART), new Card(8, Cardable.Suit.HEART)};
        TestableHand th4 = new Hand();
        th4.addCards(cards4);
        
        Cardable[] cards3 = {new Card(9, Cardable.Suit.DIAMOND), new Card(8, Cardable.Suit.DIAMOND), new Card(7, Cardable.Suit.DIAMOND), new Card(6, Cardable.Suit.DIAMOND), new Card(3, Cardable.Suit.DIAMOND)};
        TestableHand th3 = new Hand();
        th3.addCards(cards3);
        
        Cardable[] cards2 = {new Card(9, Cardable.Suit.DIAMOND), new Card(8, Cardable.Suit.DIAMOND), new Card(7, Cardable.Suit.DIAMOND), new Card(6, Cardable.Suit.DIAMOND), new Card(4, Cardable.Suit.DIAMOND)};
        TestableHand th2 = new Hand();
        th2.addCards(cards2);
        
        assertTrue(th1.compareTo(th2) > 0, "Full House vs Flush");
        assertTrue(th4.compareTo(th3) < 0, "th3 has higher value card");
        assertTrue(th2.compareTo(th3) > 0, "th2 has higher value card at the end");
        
    }
    
    @Test
    public void testStraight() 
    {
        Cardable[] cards1 = {new Card(4, Cardable.Suit.HEART), new Card(5, Cardable.Suit.HEART), new Card(6, Cardable.Suit.HEART), new Card(7, Cardable.Suit.HEART), new Card(8, Cardable.Suit.DIAMOND)};
        TestableHand th1 = new Hand();
        th1.addCards(cards1);

        Cardable[] cards2 = {new Card(3, Cardable.Suit.HEART), new Card(4, Cardable.Suit.HEART), new Card(5, Cardable.Suit.HEART), new Card(6, Cardable.Suit.HEART), new Card(7, Cardable.Suit.DIAMOND)};
        TestableHand th2 = new Hand();
        th2.addCards(cards2);
        
        Cardable[] cards3 = {new Card(2, Cardable.Suit.HEART), new Card(4, Cardable.Suit.HEART), new Card(5, Cardable.Suit.HEART), new Card(6, Cardable.Suit.HEART), new Card(3, Cardable.Suit.DIAMOND)};
        TestableHand th3 = new Hand();
        th3.addCards(cards3);
        
        //Flush 
        Cardable[] cards4 = {new Card(2, Cardable.Suit.HEART), new Card(4, Cardable.Suit.HEART), new Card(5, Cardable.Suit.HEART), new Card(6, Cardable.Suit.HEART), new Card(3, Cardable.Suit.HEART)};
        TestableHand th4 = new Hand();
        th4.addCards(cards4);

        assertTrue(th1.compareTo(th2) > 0, "Both Straight, but th1 is higher.");
        assertTrue(th3.compareTo(th2) < 0, "Th2 has higher sequence");
        assertTrue(th4.compareTo(th3) > 0, "Flush vs Straight");
    }
    
    @Test
    public void testThreeOfAKind()
    {
        Cardable[] cards1 = {new Card(4, Cardable.Suit.HEART), new Card(5, Cardable.Suit.HEART), new Card(6, Cardable.Suit.HEART), new Card(6, Cardable.Suit.HEART), new Card(6, Cardable.Suit.DIAMOND)};
        TestableHand th1 = new Hand();
        th1.addCards(cards1);
        
        Cardable[] cards2 = {new Card(9, Cardable.Suit.HEART), new Card(9, Cardable.Suit.HEART), new Card(9, Cardable.Suit.HEART), new Card(4, Cardable.Suit.HEART), new Card(5, Cardable.Suit.SPADE)};
        TestableHand th2 = new Hand();
        th2.addCards(cards2);
        
        Cardable[] cards3 = {new Card(9, Cardable.Suit.HEART), new Card(9, Cardable.Suit.HEART), new Card(9, Cardable.Suit.HEART), new Card(10, Cardable.Suit.HEART), new Card(5, Cardable.Suit.SPADE)};
        TestableHand th3 = new Hand();
        th3.addCards(cards3);
        
        Cardable[] cards4 = {new Card(2, Cardable.Suit.HEART), new Card(4, Cardable.Suit.HEART), new Card(5, Cardable.Suit.HEART), new Card(6, Cardable.Suit.HEART), new Card(8, Cardable.Suit.HEART)};
        TestableHand th4 = new Hand();
        th4.addCards(cards4);
        
        Cardable[] cards5 = {new Card(9, Cardable.Suit.HEART), new Card(9, Cardable.Suit.HEART), new Card(9, Cardable.Suit.HEART), new Card(10, Cardable.Suit.HEART), new Card(6, Cardable.Suit.SPADE)};
        TestableHand th5 = new Hand();
        th5.addCards(cards5);

        assertTrue(th1.compareTo(th2) < 0, "th2 has higher trio.");
        assertTrue(th2.compareTo(th3) < 0, "Same trio, th3 has higher odd card");
        assertTrue(th3.compareTo(th5) < 0, "Same trio, th3 has higher lower odd card");
        assertTrue(th4.compareTo(th3) > 0, "Flush vs Three of a kind");
    }
    
    @Test
    public void testTwoPairs()
    {
        Cardable[] cards1 = {new Card(4, Cardable.Suit.SPADE), new Card(4, Cardable.Suit.HEART), new Card(6, Cardable.Suit.CLUB), new Card(8, Cardable.Suit.HEART), new Card(8, Cardable.Suit.DIAMOND)};
        TestableHand th1 = new Hand();
        th1.addCards(cards1);
        
        Cardable[] cards2 = {new Card(6, Cardable.Suit.HEART), new Card(6, Cardable.Suit.HEART), new Card(5, Cardable.Suit.HEART), new Card(4, Cardable.Suit.HEART), new Card(5, Cardable.Suit.SPADE)};
        TestableHand th2 = new Hand();
        th2.addCards(cards2);
        
        Cardable[] cards3 = {new Card(4, Cardable.Suit.SPADE), new Card(4, Cardable.Suit.HEART), new Card(7, Cardable.Suit.CLUB), new Card(8, Cardable.Suit.HEART), new Card(8, Cardable.Suit.DIAMOND)};
        TestableHand th3 = new Hand();
        th3.addCards(cards3);
        
        Cardable[] cards4 = {new Card(5, Cardable.Suit.SPADE), new Card(5, Cardable.Suit.HEART), new Card(7, Cardable.Suit.CLUB), new Card(8, Cardable.Suit.HEART), new Card(8, Cardable.Suit.DIAMOND)};
        TestableHand th4 = new Hand();
        th4.addCards(cards4);
        
        Cardable[] cards5 = {new Card(9, Cardable.Suit.HEART), new Card(9, Cardable.Suit.HEART), new Card(9, Cardable.Suit.HEART), new Card(10, Cardable.Suit.HEART), new Card(6, Cardable.Suit.SPADE)};
        TestableHand th5 = new Hand();
        th5.addCards(cards5);
        
        int r = th1.compareTo(th5);
        
        assertTrue(th1.compareTo(th2) > 0, "th1 has higher First Pair.");
        assertTrue(th1.compareTo(th3) < 0, "Same pairs, higher kicker.");
        assertTrue(th4.compareTo(th3) > 0, "Same first pair, Higher second pair in th4.");
        assertTrue(th1.compareTo(th5) < 0, "Two Pair vs Three of a kind");
    }
    @Test
    public void testPair()
    {
        Cardable[] cards1 = {new Card(4, Cardable.Suit.SPADE), new Card(5, Cardable.Suit.HEART), new Card(6, Cardable.Suit.CLUB), new Card(8, Cardable.Suit.HEART), new Card(8, Cardable.Suit.DIAMOND)};
        TestableHand th1 = new Hand();
        th1.addCards(cards1);
        //th1 > th2
        Cardable[] cards2 = {new Card(6, Cardable.Suit.HEART), new Card(7, Cardable.Suit.HEART), new Card(5, Cardable.Suit.HEART), new Card(4, Cardable.Suit.HEART), new Card(5, Cardable.Suit.SPADE)};
        TestableHand th2 = new Hand();
        th2.addCards(cards2);
        // th3 > th2 (Same pair high oher value)
        Cardable[] cards3 = {new Card(8, Cardable.Suit.HEART), new Card(7, Cardable.Suit.HEART), new Card(5, Cardable.Suit.HEART), new Card(4, Cardable.Suit.HEART), new Card(5, Cardable.Suit.SPADE)};
        TestableHand th3 = new Hand();
        th3.addCards(cards3);
        // th4 > th3 (Two pair vs pair)
        Cardable[] cards4 = {new Card(5, Cardable.Suit.SPADE), new Card(5, Cardable.Suit.HEART), new Card(7, Cardable.Suit.CLUB), new Card(8, Cardable.Suit.HEART), new Card(8, Cardable.Suit.DIAMOND)};
        TestableHand th4 = new Hand();
        th4.addCards(cards4);
        
        assertTrue(th1.compareTo(th2) > 0, "th1 has higher pair.");
        assertTrue(th2.compareTo(th3) < 0, "th3 has same pair as th2, but th3 has higher other card.");
        assertTrue(th3.compareTo(th4) < 0, "Pair vs Two Pair");
        
    }
    @Test
    public void testNothing()
    {
        Cardable[] cards1 = {new Card(4, Cardable.Suit.SPADE), new Card(5, Cardable.Suit.HEART), new Card(6, Cardable.Suit.CLUB), new Card(8, Cardable.Suit.HEART), new Card(14, Cardable.Suit.HEART)};
        TestableHand th1 = new Hand();
        th1.addCards(cards1);
        // th2 > th1
        Cardable[] cards2 = {new Card(4, Cardable.Suit.HEART), new Card(5, Cardable.Suit.SPADE), new Card(6, Cardable.Suit.CLUB), new Card(8, Cardable.Suit.HEART), new Card(13, Cardable.Suit.HEART)};
        TestableHand th2 = new Hand();
        th2.addCards(cards2);
        // th3 > th2 (Same high card, higher second highest card).
        Cardable[] cards3 = {new Card(4, Cardable.Suit.SPADE), new Card(5, Cardable.Suit.HEART), new Card(6, Cardable.Suit.HEART), new Card(9, Cardable.Suit.CLUB), new Card(13, Cardable.Suit.HEART)};
        TestableHand th3 = new Hand();
        th3.addCards(cards3);
        // th4 > th3 (Pair vs highCard)
        Cardable[] cards4 = {new Card(8, Cardable.Suit.HEART), new Card(7, Cardable.Suit.HEART), new Card(5, Cardable.Suit.HEART), new Card(4, Cardable.Suit.HEART), new Card(5, Cardable.Suit.SPADE)};
        TestableHand th4 = new Hand();
        th4.addCards(cards4);
        
        assertTrue(th1.compareTo(th2) > 0, "th1 has higher card.");
        assertTrue(th3.compareTo(th2) > 0, "Same high card, higher second highest card");
        assertTrue(th3.compareTo(th4) < 0, "HighCard vs Pair");
    }
}
