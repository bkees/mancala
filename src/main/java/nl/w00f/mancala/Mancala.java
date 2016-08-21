package nl.w00f.mancala;

import static nl.w00f.mancala.GameStatus.*;
import static nl.w00f.mancala.GameStatus.CHOSE_EMPTY_PIT;
import static nl.w00f.mancala.GameStatus.FINISHED;
import static nl.w00f.mancala.GameStatus.INVALID_PIT_NUMBER;
import static nl.w00f.mancala.GameStatus.OK;

public class Mancala
{
   private Board board;
   private GameStatus gameStatus;

   /**
    * @return The player which turn it is
    */
   public Player getPlayerTurn()
   {
      return playerTurn;
   }

   private Player playerTurn = Player.PLAYER1;

   private Mancala()
   {
   }

   /**
    * @return A started Mancala game
    */
   public static Mancala start()
   {
      Mancala mancala = new Mancala();
      mancala.board = Board.init(6, 4);
      mancala.gameStatus = OK;

      return mancala;
   }

   /**
    * @return A copy of the board related to this Mancala game
    */
   public Board getBoard()
   {
      return Board.init(board);
   }

   /**
    * @param number The pit number starting with 0
    * @return Return the status of the game
    */
   public GameStatus choose(int number)
   {
      Player player = playerTurn;

      if (gameStatus == FINISHED)
      {
         throw new IllegalStateException("Still playing while game has ended.");
      }

      if (getBoard().isValidPit(number))
      {
         gameStatus = OK;
         int currentPit = number;
         Row playersRow = Row.getRow(player);

         // default is to switch turn to opponent
         playerTurn = player.opponent();

         // get marbles
         int pickedMarbles = board.getMarbles(playersRow, currentPit);

         if (pickedMarbles == 0)
         {
            gameStatus = CHOSE_EMPTY_PIT;
            playerTurn = player; // same player can have another try
         }
         else
         {
            board.setMarbles(playersRow, number, 0);
            currentPit++;
            distributeMarbles(player, currentPit, pickedMarbles);
         }

         if (getBoard().isRowEmpty(playersRow))
         {
            gameStatus = finishGame(playersRow);
         }
      }
      else
      {
         gameStatus = INVALID_PIT_NUMBER;
      }

      return gameStatus;
   }

   /**
    * A game is always ended by counting opponents marbles and adding result to their Mancala pit
    * @param playersRow Empty row of the player, resulting in the end of the game.
    * @return Status of the game
    */
   private GameStatus finishGame(Row playersRow)
   {
      Row opponentsRow = playersRow.opposite();

      int marbleCount = getBoard().countMarblesInRow(opponentsRow);
      board.addMancalaMarbles(opponentsRow, marbleCount);

      getBoard().clearRow(opponentsRow);

      return FINISHED;
   }

   /**
    * @param player The player for which marbles need to be distributed
    * @param currentPit The pit where the first marble needs to be put
    * @param pickedMarbles The number of marbles which needs to be distributed
    */
   private void distributeMarbles(Player player, int currentPit, int pickedMarbles)
   {
      Row playersRow = Row.getRow(player);

      Row currentRow = playersRow;

      while (pickedMarbles > 0)
      {
         // check if we exceeded the current row
         // Then we are at the Mancala pit of player of opponent and maybe we have to switch to other side.
         if (currentPit >= board.getSize())
         {
            // marble should be put in players mancala pit
            if (playersRow == currentRow)
            {
               pickedMarbles--;
               board.addMancalaMarbles(playersRow, 1);

               // if last marble in players mancala, the player can have another turn
               if (pickedMarbles == 0)
               {
                  playerTurn = player;
               }
            }

            // switch to other side.
            if (pickedMarbles != 0)
            {
               currentRow = currentRow.opposite();
               currentPit = 0;
            }
         }
         else
         {
            pickedMarbles--;
            // If last marble is played in an empty pit on the own row
            // then we get the marbles from the opposite pit.
            if (pickedMarbles == 0 && playersRow == currentRow && board.getMarbles(currentRow, currentPit) == 0)
            {
               // get marbles from opposite pit and add 1 for marble played
               int grabbed = 1 + board.getMarbles(playersRow.opposite(), board.oppositePit(currentPit));

               // set opposite pit marbles to 0
               board.setMarbles(playersRow.opposite(), board.oppositePit(currentPit), 0);
               board.addMancalaMarbles(playersRow, grabbed);
            }
            else
            {
               board.addMarbles(currentRow, currentPit, 1);
            }
            playerTurn = player.opponent();
            currentPit++;
         }
      }
   }

   /**
    * @return The gamestatus
    */
   public GameStatus getGameStatus()
   {
      return gameStatus;
   }
}
