package nl.w00f.mancala;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Board
{
   private List<List<Integer>> rows;
   private List<Integer> mancalas;

   private int size = 0;

   private Board()
   {
   }

   /**
    * Initialize a Mancala board
    * @param numberOfPits The number of pits for each player
    * @param numberOfMarbles The number of marbles put in each pit
    * @return The created board
    */
   public static Board init(int numberOfPits, int numberOfMarbles)
   {
      Board board = new Board();
      board.rows = new ArrayList<>();
      board.size = numberOfPits;

      for (Row row : Row.values())
      {
         board.rows.add(new ArrayList<>());

         for (int i = 0; i < 6; i++)
         {
            board.rows.get(row.number).add(numberOfMarbles);
         }

         board.mancalas = new ArrayList<>();
         board.mancalas.add(0);
         board.mancalas.add(0);
      }

      return board;
   }

   /**
    * Creates a copy of the board object
    * @param board object from which to take a copy of
    * @return copies Board object
    */
   public static Board init(Board board)
   {
      Board newBoard = new Board();

      newBoard.rows = new ArrayList<>();
      newBoard.mancalas = new ArrayList<>();
      newBoard.size = board.getSize();

      for (Row row : Row.values())
      {
         List<Integer> pitCounts = board.rows.get(row.number);
         newBoard.rows.add(row.number, new ArrayList<>(pitCounts));

         newBoard.mancalas.add(row.number, board.mancalas.get(row.number));
      }

      return board;
   }

   @Override
   public String toString()
   {
      String board = "";
      for (int i = 0; i < rows.size(); i++)
      {
         board += "row" + i + " ";
         for (int pit : rows.get(i))
         {
            board += pit + " ";
         }
         board += "(m) " + mancalas.get(i);

         board += "\n";
      }

      return board;
   }

   /**
    * Gets the number of marbles put in a specific pit
    * @param row The row
    * @param number The pit number
    * @return The number of marbles
    */
   public int getMarbles(Row row, int number)
   {
      // TODO: Throw IllegalArgumentException when number is not valid
      return rows.get(row.number).get(number);
   }

   /**
    * @param number number of the pit
    * @return true if number is a valid pit
    */
   boolean isValidPit(int number)
   {
      return number >= 0 && number < getSize();
   }

   /**
    * Gets the number of marbles in the mancala for a specific row.
    * @param row The row
    * @return The number of marbles
    */
   public int getMancalaMarbles(Row row)
   {
      return mancalas.get(row.number);
   }

   /**
    * Increases the mancala number of marbles by count
    * @param row The row
    * @param count The number of marbles to add to mancala marble count
    */
   void addMancalaMarbles(Row row, int count)
   {
      mancalas.set(row.number, mancalas.get(row.number) + count);
   }

   /**
    * Sets the pit number of marbles by count
    * @param row The row
    * @param number The pit number.
    * @param count The number of marbles to set
    */
   void setMarbles(Row row, int number, int count)
   {
      rows.get(row.number).set(number, count);
   }

   /**
    * Increases the pit number of marbles by count
    * @param row The row
    * @param number The pit number
    * @param count The number of marbles to add
    */
   void addMarbles(Row row, int number, int count)
   {
      rows.get(row.number).set(number, rows.get(row.number).get(number) + count);
   }

   /**
    * @return The size of the board
    */
   public int getSize()
   {
      return size;
   }

   /**
    * @param row Row which to check if it's empty
    * @return Returns true if row is empty.
    */
   boolean isRowEmpty(Row row)
   {
      return rows.get(row.number).stream().noneMatch(m -> m > 0);
   }

   /**
    * @param row Row which to count the marbles
    * @return Returns the number of marbles in row
    */
   int countMarblesInRow(Row row)
   {
      return rows.get(row.number).stream().reduce(0, (a, b) -> a + b);
   }

   /**
    * @param row Clears the row
    */
   void clearRow(Row row)
   {
      IntStream.range(0, getSize()).forEach(n -> rows.get(row.number).set(n, 0));
   }

   /**
    * @param number The number of the pit
    * @return The number of the opposite pit
    */
   int oppositePit(int number)
   {
      return getSize() - 1 - number;
   }
}
