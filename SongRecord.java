
/**
 *  This class represents a SongRecord object class which passes a title, artist, minutes, and seconds or nothing at all for its initialization
 *  @author Christopher Rosales
 *  @ID #114328928
 *  @Assginment #1, Playlist and Song Record
 */
import java.util.*;

//The only reason I made SongRecord extend playlist is so I can extend the formattedResult variable in which formats the amount of characters for the title, artist, and length of the song i can fit into the printAll method
public class SongRecord extends Playlist {
  private String title;
  private String artist;
  private int minutes;
  private int seconds;

  /**
   * Description: The SongRecord constructor initiazes a SongRecord object
   *
   * @param titleName   The title of the song
   * @param artistName  The artist of the song
   * @param minutesLong The number of minutes in the song
   * @param secondsLong The number of seconds in the song
   */
  public SongRecord(String titleName, String artistName, int minutesLong, int secondsLong) {
    this.title = titleName;
    this.artist = artistName;
    this.minutes = minutesLong;
    this.seconds = secondsLong;
  }

  /**
   * Description: Empty Constructor
   *
   */
  public SongRecord() {

  }

  /**
   * Description: Retrieves the title of the song
   *
   * @return Returns the title of the song
   */
  public String getSongTitle() {
    return this.title;
  }

  /**
   * Description: Retrieves the artist of the song
   *
   * @return Returns the artist of the song
   *
   */
  public String getSongArtist() {
    return this.artist;
  }

  /**
   * Description: Retrieves the number of minutes in the song
   *
   * @return Returns the number of minutes in the song
   */
  public int getMinutesLong() {
    return this.minutes;
  }

  /**
   * Description: Retrieves the number of seconds in the song
   *
   * @return Returns the number of seconds in the song
   */
  public int getSecondsLong() {
    return this.seconds;
  }

  /**
   * Description: Retrieves the total song length of the song with keeping in mind
   * to put a 0 in the tens place of seconds if the seconds is <10
   *
   * @return returns the total song length of the song in a string
   */
  public String totalSongLength() {
    if (this.seconds < 10)
      return (this.minutes + ":0" + this.seconds);
    return (this.minutes + ":" + this.seconds);
  }

  /**
   * Description: compares if the contents of one song object is equal to the
   * inputted object
   *
   * @param obj The Song object (assuming it's an instance of the SongRecord
   *            class) being compared to the initial Song object
   *
   * @return True if all the contents are equal False if one or more of the
   *         contents are not equal
   */
  public boolean equals(Object obj) {
    if (obj instanceof SongRecord) {
      SongRecord p = (SongRecord) obj;
      if (!(this.getSongTitle().equals(p.getSongTitle()))) // comparing strings
        return false;
      if (!(this.getSongArtist().equals(p.getSongArtist()))) // comparing strings
        return false;
      if (this.getMinutesLong() != p.getMinutesLong()) // comparing int's
        return false;
      if (this.getSecondsLong() != p.getSecondsLong()) // comparing int's
        return false;
      return true;
    }
    return false;

  }

  /**
   * Description: The toString() method of the SongRecord class
   *
   * @return returns the toString() value of the SongRecord object
   *
   */
  public String toString() {
    return ("Title: " + this.title + "Artist: " + this.artist + "Length: " + totalSongLength());
  }

  /**
   * Description: A bit more complex than the toString method, it updates the
   * formatting of the maxTitleLength and maxArtistLength from the Playlist class
   * so the spacing will be even with the Playlist toString() method
   *
   * @return Returns the toStringIntegrated() format of the SongRecord object
   */
  public String toStringIntegrated() {
    return String.format(this.updateFormatting(maxTitleLength, maxArtistLength).substring(5), this.title, this.artist,
        this.totalSongLength());
  }

  /**
   * Description: Changes the song title of the Song object
   *
   * @param newName The new name of the song object's title
   *
   */
  public void changeSongTitle(String newName) {
    this.title = newName;
  }

  /**
   * Description: Changes the song artist of the Song object
   *
   * @param newArtist The new artist name of the song object's artist
   */
  public void changeSongArtist(String newArtist) {
    this.artist = newArtist;
  }

  /**
   * Description: Changes the number of Minutes of the Song object
   *
   * @param newMinutes The new number of minutes of the song object's Minutes
   *
   */
  public void changeMinutesLong(int newMinutes) {
    if (newMinutes < 0)
      throw new IllegalArgumentException("Value is negative!");
    this.minutes = newMinutes;
  }

  /**
   * Description: Chagnes the seconds long of the Song object
   *
   * @param newSeconds The new number of seconds of the song object's minutes
   */
  public void changeSecondsLong(int newSeconds) {
    if (newSeconds < 0 || newSeconds > 59) {
      throw new IllegalArgumentException("Seconds must be in the range between 0 and 59 seconds!");
    }
    this.seconds = newSeconds;
  }

  /**
   * Description: Changes the total length of the Song object
   *
   * @param newMinutes The new number of minutes of the song object's minutes
   * @param newSeconds The new number of secodns of the song object's seconds
   *
   */
  public void changeTotalLength(int newMinutes, int newSeconds) {
    changeMinutesLong(newMinutes);
    changeSecondsLong(newSeconds);
  }
}
