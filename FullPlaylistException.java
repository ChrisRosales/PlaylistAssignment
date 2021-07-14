/**
 * This class represents the FullPlaylistException that represents if a Playlist
 * has reached its maximum capacity of 50, it also catch if the Playlist list
 * reaches its maximum capacity of 50
 * 
 * @author Christopher Rosales
 * @ID #114328928
 * @Assginment #1, Playlist and Song Record
 */
public class FullPlaylistException extends Exception {

  /**
   * Description: FullPlaylistException constructor to display the error message
   *
   * @param message The message of the error
   */
  public FullPlaylistException(String message) {
    super(message);
  }
}
