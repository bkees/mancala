package nl.w00f;

import static nl.w00f.mancala.GameStatus.FINISHED;
import static nl.w00f.mancala.GameStatus.OK;

import java.util.Scanner;

import nl.w00f.mancala.Board;
import nl.w00f.mancala.GameStatus;
import nl.w00f.mancala.Mancala;
import nl.w00f.mancala.Row;

class App
{
   public static void main(String[] args)
   {
      Mancala mancala = Mancala.start();
      GameStatus gameStatus = mancala.getGameStatus();

      Scanner scanner = new Scanner(System.in);

      printBoard(mancala.getBoard());

      while (gameStatus != FINISHED)
      {
         if (gameStatus != OK)
         {
            // Print what is wrong
            System.out.println(gameStatus.getDescription());
         }

         System.out.printf("Turn %s (0-5): ", mancala.getPlayerTurn());
         String turn = scanner.nextLine();

         try
         {
            gameStatus = mancala.choose(Integer.parseInt(turn));
            printBoard(mancala.getBoard());
         }
         catch (NumberFormatException nfe)
         {
            System.out.println("Please enter a valid integer");
         }
      }
   }

   private static void printBoard(Board board)
   {
      System.out.printf("           Player 2\n    [");

      for (int i = board.getSize() - 1; i >= 0; i--)
      {
         System.out.printf("%2d ", i);
      }

      // print marbles in mandala pit for Player 2
      System.out.printf("]\n\n(%2d) ", board.getMancalaMarbles(Row.ROW2));

      for (int i = board.getSize() - 1; i >= 0; i--)
      {
         System.out.printf("%2d ", board.getMarbles(Row.ROW2, i));
      }

      System.out.printf("\n     ");

      for (int i = 0; i < board.getSize(); i++)
      {
         System.out.printf("%2d ", board.getMarbles(Row.ROW1, i));
      }

      // print marbles in mandala pit for Player 1
      System.out.printf("(%2d) \n\n    [", board.getMancalaMarbles(Row.ROW1));

      for (int i = 0; i < board.getSize(); i++)
      {
         System.out.printf("%2d ", i);
      }

      System.out.println("]\n           Player 1\n");
   }
}