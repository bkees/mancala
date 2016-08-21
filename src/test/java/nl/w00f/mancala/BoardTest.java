package nl.w00f.mancala;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class BoardTest
{
   @Test
   public void testInitBoard()
   {
      Board board = Board.init(6, 4);
      System.out.print(board.toString());
   }

   @Test
   public void testGetSetMarbles()
   {
      Board board = Board.init(6, 4);
      int marbles = board.getMarbles(Row.ROW1, 0);

      assertEquals("Initial marble incorrect", 4, marbles);

      board.setMarbles(Row.ROW1, 0, ++marbles);

      marbles = board.getMarbles(Row.ROW1, 0);

      assertEquals("Marble count incorrect", 5, marbles);
   }

   @Test
   public void testIsRowEmpty()
   {
      Board board = Board.init(6, 0);

      assertEquals("Row should be empty", true, board.isRowEmpty(Row.ROW1));
   }

   @Test
   public void testIsRowNotEmpty()
   {
      Board board = Board.init(6, 1);

      assertEquals("Row should not be empty", false, board.isRowEmpty(Row.ROW1));
   }

   @Test
   public void testCountMarblesInRow()
   {
      Board board = Board.init(6, 1);

      assertEquals("Number of marbles is incorrect", 6, board.countMarblesInRow(Row.ROW1));
   }

   @Test
   public void testClearRow()
   {
      Board board = Board.init(6, 1);
      board.clearRow(Row.ROW1);
      assertEquals("Number of marbles is incorrect", 0, board.countMarblesInRow(Row.ROW1));
   }
}
