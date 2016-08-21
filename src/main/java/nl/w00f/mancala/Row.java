package nl.w00f.mancala;

public enum Row
{
   ROW1(0), ROW2(1);
   public final int number;

   Row(int i)
   {
      number = i;
   }

   public Row opposite()
   {
      return this == ROW1 ? ROW2 : ROW1;
   }

   public static Row getRow(Player player)
   {
      return player == Player.PLAYER1 ? ROW1 : ROW2;
   }
}
