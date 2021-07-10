import java.util.Arrays;

public class testing {
  public static void main(String[] args) {
    Playlist p = new Playlist();
    p.addSong(new SongRecord("Don't let me fade away", "Wage War", 3, 25), 1);
    p.addSong(new SongRecord("Breaking the mirror", "Wage War", 2, 49), 2);
    Playlist e = p.clone(); // should be a deep copy
    if (p.equals(e)) {
      // checks each song to see if they're equal
      System.out.println("p.equals(e)");
    }
    if (p == e) {
      System.out.println("p == e");
    }
  }
}
