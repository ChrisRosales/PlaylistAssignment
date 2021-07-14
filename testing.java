import java.util.Arrays;

public class testing {
  public static void main(String[] args) {
    Playlist p = new Playlist();
    for (int i = 0; i <= 50; i++) {
      p.addSong(new SongRecord("title", "artist", 1, 23), i);
      System.out.println("song " + (i + 1) + " has been added");
    }
  }
}
