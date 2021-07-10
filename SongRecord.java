import java.util.*;

//The only reason I made SongRecord extend playlist is so I can extend the formattedResult variable in which formats the amount of characters for the title, artist, and length of the song i can fit into the printAll method
public class SongRecord extends Playlist {
  private String title;
  private String artist;
  private int minutes;
  private int seconds;

  public SongRecord(String titleName, String artistName, int minutesLong, int secondsLong) {
    this.title = titleName;
    this.artist = artistName;
    this.minutes = minutesLong;
    this.seconds = secondsLong;
  }

  public SongRecord() {

  }

  public String getSongTitle() {
    return this.title;
  }

  public String getSongArtist() {
    return this.artist;
  }

  public int getMinutesLong() {
    return this.minutes;
  }

  public int getSecondsLong() {
    return this.seconds;
  }

  public String totalSongLength() {
    if (this.seconds < 10)
      return (this.minutes + ":0" + this.seconds);
    return (this.minutes + ":" + this.seconds);
  }

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

  public String toString() {
    return ("Title: " + this.title + "Artist: " + this.artist + "Length: " + totalSongLength());
  }

  public String toStringIntegrated() {
    return String.format(this.updateFormatting(maxTitleLength, maxArtistLength).substring(5), this.title, this.artist,
        this.totalSongLength());
    // return ("\t" + this.title + "\t" + this.artist + "\t" + this.minutes + ":" +
    // this.seconds);
  }

  public void changeSongTitle(String newName) {
    this.title = newName;
  }

  public void changeSongArtist(String newArtist) {
    this.artist = newArtist;
  }

  public void changeMinutesLong(int newMinutes) {
    if (newMinutes < 0)
      throw new IllegalArgumentException("Value is negative!");
    this.minutes = newMinutes;
  }

  public void changeSecondsLong(int newSeconds) {
    if (newSeconds < 0 || newSeconds > 59) {
      throw new IllegalArgumentException("Seconds must be in the range between 0 and 59 seconds!");
    }
    this.seconds = newSeconds;
  }

  public void changeTotalLength(int newMinutes, int newSeconds) {
    changeMinutesLong(newMinutes);
    changeSecondsLong(newSeconds);
  }
}
