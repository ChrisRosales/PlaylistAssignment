import java.util.Arrays;

//Important notes
// - I made a clear() method that sorts the arryay in a manner where there are no null between the SongRecord objects, this is so we don't have to loop through capacity each time but instead we can just loop through the size() (which is how many objects are in the array)
public class Playlist {
  private static final int CAPACITY = 50;
  private SongRecord[] list = new SongRecord[CAPACITY];
  private int manyItems;
  private String playlistName;
  // now these two arrays are if we want to remove a song but that song either has
  // a max length for title, a max length for artist, or both, to prevent a nested
  // for
  // loop from occuring to find the new max, we use arrays.sort (which is in
  // o(nlog(n))) average case.
  private int[] totalTitleLengths = new int[CAPACITY];
  private int[] totalArtistLengths = new int[CAPACITY];
  private int counter = 0;// for the arrays^^
  protected static int maxTitleLength = 10; // 10 is the default length
  protected static int maxArtistLength = 10; // 10 is also the default length
  private int artistSize;
  private int titleSize;
  private String formattedResult = "%-8s %-" + Integer.toString(maxTitleLength) + "s %-"
      + Integer.toString(maxArtistLength) + "s %-5s %n"; // the title and artist can supply a max
  // of 25 characters each while length can
  // supply a max of 5

  public Playlist(SongRecord[] songList) {
    this.list = songList;
  }

  public Playlist() {
    // to add a song from an empty constructor you can do
    // Playlist p = new Playlist();
    // p.addSong(new SongRecord("title" , "artist" , 5, 15), 0);
    manyItems = 0;
  }

  public Playlist clone() { // returning a deep copy
    Playlist copy = new Playlist();
    int y = 0;
    for (SongRecord i : list) {
      // copy.addSong((SongRecord) i.clone(), y); // is this right? for utilizing the
      // clone method
      if (i != null) {
        System.out.println(i);
        copy.addSong(i, y);
      }
      y++;
    }
    return (Playlist) copy; // why do we need to type cast?
  }

  public boolean equals(Object obj) {
    if (obj instanceof Playlist) {
      Playlist p = (Playlist) obj;
      if (this.size() == p.size()) {
        for (int i = 0; i < this.size(); i++) {
          SongRecord song1 = this.getSong(i);
          SongRecord song2 = p.getSong(i);
          if (!(song1.equals(song2))) {
            return false;
          }
        }
        return true;
      }
      return false;
    }
    System.out.println("Object is not an instance of Playlist");
    return false;
  }

  public int size() {
    return manyItems;
  }

  public void addSong(SongRecord song, int position) {
    // make sure to check capacity before adding
    // if there is already an element in for position , insert it
    try {
      if (list[position] != null && this.size() + 1 < CAPACITY) { // creating an open space to insert song if one
                                                                  // already
                                                                  // exists in its position
        SongRecord[] copy = list.clone(); // we have create a clone, otherwise we will receive an exception
        for (int i = position; i < CAPACITY - 1; i++) {
          list[i + 1] = copy[i];
        }
      }
      list[position] = song;
      manyItems++;
      if (list[position].getSongTitle().length() > maxTitleLength) {
        maxTitleLength = list[position].getSongTitle().length();
        totalTitleLengths[counter] = list[position].getSongTitle().length();
        titleSize++;
      }
      if (list[position].getSongArtist().length() > maxArtistLength) {
        maxArtistLength = list[position].getSongArtist().length();
        totalArtistLengths[counter] = list[position].getSongArtist().length();
        artistSize++;
      }
    } catch (IndexOutOfBoundsException e) {
      System.out
          .println("Index is not in the range of 0-50! Check if you have reached the Maximum Capacity and try again.");
    }
    this.clear();
  }

  public void removeSong(int position) {
    try {
      if (list[position] == null)
        throw new RuntimeException();
    } catch (RuntimeException e) {
      System.out.println("There's no song to remove here!");
      initializer.main(null);
    }
    if (list[position].getSongTitle().length() == maxTitleLength && titleSize > 1) {
      totalTitleLengths[totalTitleLengths.length - 1] = 0;
      Arrays.sort(totalTitleLengths);
      maxTitleLength = totalTitleLengths[totalTitleLengths.length - 1];
      titleSize--;
      // then we get the new max length title
    } else if (list[position].getSongTitle().length() == maxTitleLength && titleSize == 1) {
      maxTitleLength = 10; // reset to default
      totalTitleLengths[totalTitleLengths.length - 1] = 0;
    }
    if (list[position].getSongArtist().length() == maxArtistLength && artistSize > 1) {
      totalArtistLengths[totalArtistLengths.length - 1] = 0;
      Arrays.sort(totalArtistLengths);
      maxArtistLength = totalArtistLengths[totalArtistLengths.length - 1];
      artistSize--;
      // then we get the new max length for the artist
    } else if (list[position].getSongArtist().length() == maxArtistLength && artistSize == 1) {
      maxArtistLength = 10; // reset back to default
      totalArtistLengths[totalArtistLengths.length - 1] = 0;
    }
    list[position] = null;
    manyItems--;
    this.clear();
  }

  public void setPlaylistName(String newName) {
    playlistName = newName;
  }

  public String getPlaylistName() {
    return playlistName;
  }

  private SongRecord getSong(int position) {
    if (list[position] == null)
      throw new RuntimeException("There is no song to get!");
    return list[position];
  }

  public void searchSong(int position) {
    try {
      if (list[position] == null)
        throw new NullPointerException();
    } catch (NullPointerException e) {
      System.out.println("No song in this position!");
      initializer.main(null);
    }
    Playlist temp = new Playlist();
    temp.addSong(list[position], 0);
    temp.printAllSongs();
  }

  public void printAllSongs() {
    System.out.println(this.toString());
  }

  public static Playlist getSongsByArtist(Playlist originalList, String artist) {
    Playlist newList = new Playlist();
    int y = 0;
    for (int i = 0; i < originalList.size(); i++) {
      if (originalList.getSong(i).getSongArtist().equals(artist)) {
        newList.addSong(originalList.getSong(i), y);
        y++;
      }
    }
    return newList;
  }

  // public int[] overridePosition() {

  // }

  private void clear() { // clearing the null's that are in between SongRecords in the array
    SongRecord[] temp = new SongRecord[CAPACITY];
    int y = 0;
    for (int i = 0; i < list.length; i++) {
      if (list[i] != null) {
        temp[y] = this.getSong(i);
        y++;
      }
    }
    list = temp.clone(); // might need to type cast this
  }

  public String toString() { // the title and artist can supply a max of 25 characters each while length can
                             // supply a max of 5
    String result = String.format(this.updateFormatting(maxTitleLength, maxArtistLength), "Song", "Title", "Artist",
        "Length"); // x amount of
    // spaces after
    // initial letter
    result += "-".repeat(17 + maxTitleLength + maxArtistLength) + "\n";
    for (int i = 0; i < this.size(); i++) {
      result += String.format("%-9s", i + 1);
      result += list[i].toStringIntegrated();
    }
    return result;
  }

  public boolean isEmpty() {
    return (this.size() == 0);
  }

  public String updateFormatting(int maxTitleLength, int maxArtistLength) {
    formattedResult = "%-8s %-" + Integer.toString(maxTitleLength) + "s %-" + Integer.toString(maxArtistLength)
        + "s %-5s %n";
    return formattedResult;
  }

  public static Playlist union(Playlist p1, Playlist p2) {
    if (p1.size() + p2.size() >= 50) {
      System.out.println("Cannot merge playlists because it will surpass the capacity of 50!");
      initializer.main(null);
    }
    Playlist answer = new Playlist();
    System.arraycopy(p1.list, 0, answer.list, 0, p1.manyItems);
    System.arraycopy(p2.list, 0, answer.list, p1.manyItems, p2.manyItems);
    answer.manyItems = p1.manyItems + p2.manyItems;
    return answer;
  }
}
