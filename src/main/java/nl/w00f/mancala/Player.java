package nl.w00f.mancala;

public enum Player
{
   PLAYER1("Player 1"), PLAYER2("Player 2");

   private final String description;

   Player(String description)
   {
      this.description = description;
   }

   public Player opponent()
   {
      return this == PLAYER1 ? PLAYER2 : PLAYER1;
   }

   @Override
   public String toString()
   {
      return description;
   }
}
