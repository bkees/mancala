package nl.w00f.mancala;

public enum GameStatus
{
   CHOSE_EMPTY_PIT("You chose an empty pit"), OK("Everything is OK"), INVALID_PIT_NUMBER(
         "The number you entered is not a valid pit number"), FINISHED("The game has finished");

   private final String description;

   /**
    * @param description Description of the game status
    */
   GameStatus(String description) {
      this.description = description ;
   }

   /**
    * @return Description of the game status
    */
   public String getDescription()
   {
      return this.description;
   }
}
