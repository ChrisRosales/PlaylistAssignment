import java.util.*;

//important notes:
// - I made the whole class 0 based for lists and changed position in the output by a -1 to accomodate the Playlist and SongRecord classes
public class initializer {
  private static Playlist[] p = new Playlist[50];
  private static int sizeOfPlayList = 0;
  private static int playlistSelected = 0;
  private static Scanner input = new Scanner(System.in);
  private static boolean flag = false;
  private static String posPrint = "Enter the position: ";

  public static void menu() {
    System.out.println(
        "\nA) Add Song\nB) Print Songs by Artist\nG) Get Song\nR) Remove Song\nP) Print All Songs\nS) Size\nN) Create new Playlist (and set as current)\nV) Change current Playlist\nC) Copy current Playlist into a new Playlist\nE) Compare songs in current Playlist to another Playlist\nD) Display all Playlist names\nZ) Play a song in the Playlist\nQ) Quit\n");
    System.out.print("Select a menu option: ");
  }

  public static void optionA() {
    // adding a song
    try {
      if (p[playlistSelected] == null && sizeOfPlayList < 50) {
        p[playlistSelected] = new Playlist();
        System.out.println("What would you like to name this Playlist");
        String name = input.nextLine();
        p[playlistSelected].setPlaylistName(name);
        sizeOfPlayList++;

      }
      flag = false;
      System.out.print("\nEnter the song title: ");
      String title = input.nextLine();
      System.out.print("Enter the song artist: ");
      String artist = input.nextLine();
      System.out.print("Enter the song length (minutes): ");
      int minutes = input.nextInt();
      input.nextLine();
      if (minutes > 59 || minutes < 0) {
        System.out.println("\nMinutes must be in the range between 0-59");
        flag = true;
        throw new IllegalArgumentException();
      }
      System.out.print("Enter the song length(seconds): ");
      int seconds = input.nextInt();
      input.nextLine();
      if (seconds > 59 || seconds < 0) {
        System.out.println("\nSeconds must be in the range between 0-59");
        flag = true;
        throw new IllegalArgumentException();
      }
      System.out.print(posPrint);
      int position = input.nextInt();
      input.nextLine();
      p[playlistSelected].addSong(new SongRecord(title, artist, minutes, seconds), position - 1);
      System.out.println("Song added: " + title + " By " + artist);
    } catch (InputMismatchException |

        IllegalArgumentException e) {
      if (!flag) {
        System.out.println("\nYou need to type in an Integer for in the song length option!\n");
        input.nextLine();
      }
      optionA();
    }
  }

  public static void optionB() {
    System.out.print("\nEnter the artist: ");
    String artist = input.nextLine();
    Playlist searchArtist = Playlist.getSongsByArtist(p[playlistSelected], artist);
    if (searchArtist.isEmpty()) {
      System.out.println("No songs match your search");
    } else {
      searchArtist.printAllSongs();
    }
  }

  public static void optionG() {
    try {
      System.out.print("\n" + posPrint);
      int position = input.nextInt();
      input.nextLine();
      p[playlistSelected].searchSong(position - 1);
    } catch (InputMismatchException e) {
      System.out.println("The Song must be a number 0-50!");
      optionG();
    }
  }

  public static void optionR() {
    try {
      System.out.print("\n" + posPrint);
      int position = input.nextInt();
      input.nextLine();
      p[playlistSelected].removeSong(position - 1);
      System.out.println("Song removed at position " + position);
    } catch (InputMismatchException e) {
      System.out.println("The song must be a number 0-50!");
      optionR();
    }
  }

  public static void optionP() {
    System.out.println();
    p[playlistSelected].printAllSongs();
  }

  public static void optionS() {
    // Get size
    System.out.println("\nThere are " + p[playlistSelected].size() + " song(s) in the current playlist.");
  }

  public static void optionQ() {
    System.exit(0);
  }

  public static void optionN() {
    if (sizeOfPlayList == 50)
      System.out.println("Cannot add anymore playlists!");
    else {
      sizeOfPlayList++;
      System.out.print("Name your playlist: ");
      String name = input.nextLine();
      p[sizeOfPlayList - 1] = new Playlist();
      p[sizeOfPlayList - 1].setPlaylistName(name);
      playlistSelected = sizeOfPlayList - 1; // now we set the new created playlist to the current
      System.out.println("Playlist " + p[sizeOfPlayList - 1].getPlaylistName()
          + " has been created and has been set as your current Playlist!");
    }
  }

  public static void optionV() {
    if (sizeOfPlayList <= 1)
      System.out.println("You have no playlist to choose from!");
    else {
      optionD();
      System.out.print("Select an option: ");
      try {
        flag = false;
        int option = input.nextInt();
        input.nextLine();
        if (option - 1 < 0 || option - 1 >= sizeOfPlayList) {
          flag = true;
          throw new IllegalArgumentException();
        }
        playlistSelected = option - 1;
        System.out.println("\nPlaylist " + p[playlistSelected].getPlaylistName() + " is now selected");
      } catch (InputMismatchException | IllegalArgumentException e) {
        if (flag)
          System.out.println("Option must be between 0 and " + (sizeOfPlayList - 1));
        else
          System.out.println("Option must be an Integer!");
      }
    }
  }

  public static void optionC() {
    if (sizeOfPlayList < 1)
      System.out.println("You have do not have at least one playlist to merge!");
    else {
      try {
        flag = false;
        optionD();
        System.out.print("\nSelect the playlist you would like to copy " + p[playlistSelected] + " onto: ");
        int option = input.nextInt();
        input.nextLine();
        if (option - 1 < 0 || option - 1 >= sizeOfPlayList) {
          flag = true;
          throw new IllegalArgumentException();
        }
        Playlist copy = Playlist.union(p[playlistSelected], p[option - 1]);
        String temp = p[option - 1].getPlaylistName();
        p[option - 1] = copy;
        p[option - 1].setPlaylistName(temp);
        System.out.println("\nPlaylist " + p[playlistSelected].getPlaylistName() + " has been copied into "
            + p[option - 1].getPlaylistName());
      } catch (IllegalArgumentException | InputMismatchException e) {
        if (flag)
          System.out.println("\nOption must be between 0 and " + (sizeOfPlayList - 1));
        else
          System.out.println("\nOption must be an Integer!");
      }
    }
  }

  public static void optionE() {
    if (sizeOfPlayList <= 1)
      System.out.println("\nYou must have at least two Playlists in order to compare!");
    else {
      try {
        flag = false;
        optionD();
        System.out
            .print("\nWhich playlist would you like to compare " + p[playlistSelected].getPlaylistName() + " to? ");
        int option = input.nextInt();
        input.nextLine();
        if (option - 1 == playlistSelected || option - 1 < 0 || option - 1 >= sizeOfPlayList) {
          flag = true;
          throw new IllegalArgumentException();
        }
        if (p[playlistSelected].equals(p[option - 1]))
          System.out.println("\nPlaylist " + p[playlistSelected].getPlaylistName() + " is equal to "
              + p[playlistSelected].getPlaylistName());
        else {
          System.out.println("\nPlaylist " + p[playlistSelected].getPlaylistName() + " is not equal to "
              + p[playlistSelected].getPlaylistName());
        }
      } catch (IllegalArgumentException | InputMismatchException e) {
        if (flag)
          System.out.println(
              "Option must be between 0 and " + (sizeOfPlayList - 1) + " and you cannot choose your own playlist");
        else
          System.out.println("Option must be an Integer!");
      }
    }
  }

  public static void optionD() {
    for (int i = 0; i < sizeOfPlayList; i++) {
      System.out.println((i + 1) + ")\t" + p[i].getPlaylistName());
    }
  }

  public static void optionZ() {

  }

  public static void main(String[] args) {
    while (true) {
      menu();
      String option = input.nextLine();
      if (option.equals("A"))
        optionA();
      else if (option.equals("B"))
        optionB();
      else if (option.equals("G"))
        optionG();
      else if (option.equals("R"))
        optionR();
      else if (option.equals("P"))
        optionP();
      else if (option.equals("S"))
        optionS();
      else if (option.equals("N"))
        optionN();
      else if (option.equals("V"))
        optionV();
      else if (option.equals("C"))
        optionC();
      else if (option.equals("E"))
        optionE();
      else if (option.equals("D"))
        optionD();
      else if (option.equals("Z"))
        optionZ();
      else if (option.equals("Q"))
        optionQ();
      else {
        System.out.println("That is not one of the inputs listed!");
      }
    }
  }
}
