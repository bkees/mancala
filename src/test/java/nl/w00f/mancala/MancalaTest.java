package nl.w00f.mancala;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MancalaTest
{
   @Test
   public void testChooseLastMarbleInPlayersRowWhichPlayer()
   {
      Mancala mancala = Mancala.start();

      mancala.choose(0);
   }

   @Test
   public void testChooseLastMarbleInPlayersMancalaWhichPlayer()
   {
      Mancala mancala = Mancala.start();

      mancala.choose(2);

      assertEquals("Wrong player has the turn", Player.PLAYER1, mancala.getPlayerTurn());
   }

   @Test
   public void testChooseLastMarbleInOpponentsRowWhichPlayer()
   {
      Mancala mancala = Mancala.start();

      mancala.choose(3);

      assertEquals("Wrong player has the turn", Player.PLAYER2, mancala.getPlayerTurn());
   }

   @Test
   public void testChooseLastMarbleInPlayersRowInEmptyPit()
   {
      Mancala mancala = Mancala.start();

      mancala.choose(5); // player 1
      mancala.choose(2); // player 2

      // end in empty pit on own row. get marbles from opponents pit.
      mancala.choose(1); // player 1

      assertEquals("Mancala pit count is incorrect", 7, mancala.getBoard().getMancalaMarbles(Row.getRow(Player.PLAYER1)));
   }

   @Test
   public void testFinishGame()
   {
      Mancala mancala = Mancala.start();

      mancala = setMovesToEndableGame(mancala);
      mancala.choose(5);

      assertEquals("Game should be ended", GameStatus.FINISHED, mancala.getGameStatus());
   }

   @Test(expected = IllegalStateException.class)
   public void testFinishGameButStillChoose()
   {
      Mancala mancala = Mancala.start();

      mancala = setMovesToEndableGame(mancala);
      mancala.choose(5);
      mancala.choose(2);
   }

   /**
    * @param mancala
    * @return A mancala game in which player 1 only has stones left in pit 5 (last pit in row).
    */
   private Mancala setMovesToEndableGame(Mancala mancala)
   {
      mancala.choose(0);
      mancala.choose(0);
      mancala.choose(1);
      mancala.choose(2);
      mancala.choose(0);
      mancala.choose(3);
      mancala.choose(0);
      mancala.choose(4);
      mancala.choose(0);
      return mancala;
   }

   @Test
   public void testChooseLastMarblePassingOpponentsRowEndingInPlayersRow()
   {
   }
}
