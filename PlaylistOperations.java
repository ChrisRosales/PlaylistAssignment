
/**
 *  This class represents the main for running the SongRecord and Playlist classes 
 *  @author Christopher Rosales
 *  @ID #114328928
 *  @Assginment #1, Playlist and Song Record
 */

import java.util.*;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

//important notes:
// - I made the whole class 0 based for lists and changed position in the output by a -1 to accomodate the Playlist and SongRecord classes
public class PlaylistOperations {
  public static final int CAPACITY = 50;
  private static Playlist[] p = new Playlist[CAPACITY];
  private static int sizeOfPlayList = 0;
  private static int playlistSelected = 0;
  private static Scanner input = new Scanner(System.in);
  private static boolean flag = false;
  private static String posPrint = "Enter the position: ";
  private static String filePath = "/Users/chrisrosales/programming/sbuWork/214/homework/assignment1/Songs/";

  // Enter own file path here ^
  /**
   * Description: A method that calls a String to display a menu
   *
   */
  public static void menu() {
    System.out.println(
        "\nA) Add Song\nB) Print Songs by Artist\nG) Get Song\nR) Remove Song\nP) Print All Songs\nS) Size\nN) Create new Playlist (and set as current)\nV) Change current Playlist\nC) Copy current Playlist into a new Playlist\nE) Compare songs in current Playlist to another Playlist\nD) Display all Playlist names\nZ) Play a song in the Playlist\nQ) Quit\n");
    System.out.print("Select a menu option: ");
  }

  /**
   * Description: Adds a song to the playlist
   * 
   * @throws IllegalArgumentException this is to check if the minutes or seconds
   *                                  are past 59 or below 0
   *
   * 
   * @exception InputMismatchException To check if the user inputs a String
   *                                   instead of an int
   *
   */
  public static void optionA() {
    // adding a song
    try {
      if (p[playlistSelected] == null) { // this would be the first playlist of this program
        p[playlistSelected] = new Playlist();
        System.out.print("\nWhat would you like to name this Playlist: ");
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
    } catch (InputMismatchException | IllegalArgumentException e) {
      if (!flag) {
        System.out.println("\nYou need to type in an Integer for in the song length option!\n");
        input.nextLine();
      }
    }
  }

  /**
   * Description: Searches a song by the artist
   *
   */
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

  /**
   * Description: Retrieves a song from a playlist
   *
   * @exception InputMismatchException To check if the user inputs a String
   *                                   instead of an int
   * @exception NullPointerException   If the given input is equal to a null value
   *                                   instead of a SongRecord object
   */
  public static void optionG() {
    if (p[playlistSelected] == null || p[playlistSelected].size() == 0)
      System.out.println("You have no songs to search!");
    else {
      try {
        System.out.print("\n" + posPrint);
        int position = input.nextInt();
        input.nextLine();
        flag = true;
        p[playlistSelected].searchSong(position - 1);
      } catch (InputMismatchException | NullPointerException e) {
        if (flag)
          System.out.println("Your input is not in the song records!");
        else {
          System.out.println("Your input must be an int!");
          input.nextLine();
        }
      }
    }
  }

  /**
   * Description: Removes a song from the selected playlist
   *
   * @exception InputMismatchException To check if the user inputs a String
   *                                   instead of an int
   * @exception NullPointerException   If the given input is equal to a null value
   *                                   instead of a SongRecord object
   */
  public static void optionR() {
    if (p[playlistSelected] == null || p[playlistSelected].size() == 0)
      System.out.println("You have no songs to remove");
    else {
      try {
        System.out.print("\n" + posPrint);
        int position = input.nextInt();
        input.nextLine();
        flag = true;
        p[playlistSelected].removeSong(position - 1);
        System.out.println("Song removed at position " + position);
      } catch (NullPointerException | InputMismatchException e) {
        if (flag)
          System.out.println("No song to remove here!");
        else {
          System.out.println("Your input must be an int");
          input.nextLine();
        }
      }
    }
  }

  /**
   * Description: Prints all of the songs from the selected playlist
   */
  public static void optionP() {
    System.out.println();
    p[playlistSelected].printAllSongs();
  }

  /**
   * Description: Prints the selected playlists' size
   */
  public static void optionS() {
    // Get size
    if (p[playlistSelected] == null)
      System.out.println("You did not create any Playlists yet!");
    else
      System.out.println("\nThere are " + p[playlistSelected].size() + " song(s) in the current playlist.");
  }

  /**
   * Description: Quits the Program
   */
  public static void optionQ() {
    System.exit(0);
  }

  /**
   * Description: Adds a new Playlist to the Playlist list
   *
   * @throws FullPlaylistException To check if we have reached the maximum amount
   *                               of Playlists in the list
   */
  public static void optionN() {
    try {
      if (sizeOfPlayList == 50)
        throw new FullPlaylistException("EXCEEDING CAPACITY");
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
    } catch (FullPlaylistException e) {
      System.out.println("Cannot add anymore Playlists!");
      main(null);
    }
  }

  /**
   * Description: Select a playlist to make current
   * 
   * @exception InputMismatchException To check if the user inputs a String
   *                                   instead of an int
   * @throws IllegalArgumentException To check if the option is between 1 and the
   *                                  size of the Playlist list
   */
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
          System.out.println("Option must be between 1 and " + (sizeOfPlayList));
        else
          System.out.println("Option must be an Integer!");
      }
    }
  }

  /**
   * Description: Copies one playlist onto another
   *
   * @throws IllegalArgumentException To check if the index is between option 1
   *                                  and the size of the playlist list
   * @exception InputMismatchException To check if the user inputs a String
   *                                   instead of an int
   *
   */
  public static void optionC() {
    if (sizeOfPlayList < 1)
      System.out.println("You have do not have at least one playlist to merge!");
    else {
      try {
        flag = false;
        optionD();
        System.out
            .print("\nSelect the playlist you would like to copy " + p[playlistSelected].getPlaylistName() + " onto: ");
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
        if (flag) {
          if (sizeOfPlayList == 1)
            System.out.println("You may only choose option 1!");
          else
            System.out.println("\nOption must be between 1 and " + (sizeOfPlayList));
        } else
          System.out.println("\nOption must be an Integer!");
      }
    }
  }

  /**
   * Description: Compare two playlists
   * 
   * @throws IllegalArgumentException To check if the index is between 1 and the
   *                                  size of the playlist list
   *
   * @exception InputMismatchException To check if the user inputs a String
   *                                   instead of an int
   *
   */
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
              + p[option - 1].getPlaylistName());
        else {
          System.out.println("\nPlaylist " + p[playlistSelected].getPlaylistName() + " is not equal to "
              + p[option - 1].getPlaylistName());
        }
      } catch (IllegalArgumentException | InputMismatchException e) {
        if (flag)
          System.out.println(
              "\nOption must be between 1 and " + (sizeOfPlayList) + " and you cannot choose your own playlist");
        else
          System.out.println("\nOption must be an Integer!");
      }
    }
  }

  /**
   * Description: Display all Playlists
   *
   */
  public static void optionD() {
    if (sizeOfPlayList == 0)
      System.out.println("You have no playlists to display");
    else {
      for (int i = 0; i < sizeOfPlayList; i++) {
        System.out.println((i + 1) + ")\t" + p[i].getPlaylistName());
      }
    }
  }

  /**
   * Description: Plays a song either from a song in a playlist already or from a
   * user inputting the credentials of a song
   *
   * @exception InputMismatchException        To check if the user inputs a String
   *                                          instead of an int
   * @exception IOException                   If the file being read does not
   *                                          exist in the Song folder
   * @exception LineUnavailableException      To check if an unavailable line
   *                                          cannot be opened in a file
   * @exception UnsupportedAudioFileException To check if a file type is not
   *                                          supported for the program
   */
  public static void optionZ() { // method
                                 // can only
                                 // take in
                                 // AIFC,
                                 // AIFF,
                                 // AU,
    // SND and WAVE formats.
    try {
      System.out.println("\n1. Enter the contents of the song file\n2. Retrieve song from selected Playlist\n3. Quit");
      System.out.print("\nSelect a menu option: ");
      int option = input.nextInt();
      input.nextLine();
      String songPath = "";
      SongRecord temp = null;
      if (option == 1) { // both options are creating a temp SongRecord
        System.out.print("Enter the song title: ");
        String title = input.nextLine();
        System.out.print("Enter the song artist: ");
        String artist = input.nextLine();
        System.out.print("Enter the song length (minutes): ");
        int minutes = input.nextInt();
        System.out.print("Enter the song length (seconds): ");
        int seconds = input.nextInt();
        temp = new SongRecord(title, artist, minutes, seconds);
        input.nextLine();
      } else if (option == 2) {
        // search the selected playlist to find any matching audio files
        if (p[playlistSelected] == null || p[playlistSelected].size() == 0) { // null meaning there is no playlist
                                                                              // initialized, >0 means that we created a
                                                                              // playlist with no songs
          System.out.println("You have no songs to retrieve");
          main(null);
        }
        optionP();
        System.out.println("Which song would you like to pick?");
        int position = input.nextInt();
        input.nextLine();
        temp = p[playlistSelected].getSong(position - 1);

      } else if (option == 3) {
        main(null);
      } else {
        System.out.println("You must input an option 1-3!");
        main(null);
      }
      // loop through Songs file directory
      File dir = new File(filePath);
      File[] directoryListing = dir.listFiles();
      if (directoryListing != null) {
        for (File child : directoryListing) {
          String childLowercase = child.getName().toLowerCase();
          if (childLowercase.contains(temp.getSongTitle().toLowerCase())
              && childLowercase.contains(temp.getSongArtist().toLowerCase())) {
            System.out.println("test");
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(child);
            AudioFormat format = audioInputStream.getFormat();
            long frames = audioInputStream.getFrameLength();
            int durationInSeconds = (int) ((frames + 0.0) / format.getFrameRate());
            // now we check for the length
            if (durationInSeconds / 60 == temp.getMinutesLong() && durationInSeconds % 60 == temp.getSecondsLong()) {
              System.out.println("\nThe file has been located!");
              System.out.println("\n" + child.getName()); // temp
              songPath = child.getPath();
              System.out.println("Song Path: " + songPath); // temp
              flag = true;
              break;
            }
          }
        }
        if (!flag) {
          System.out.println("No Songs in the folder match your search!");
          main(null);
        }
      } else {
        System.out.println("There are no Songs in this folder");
        main(null);
      }
      if (!(songPath.equals(filePath))) { // if we found a song from looping through the file directory, we play it
        // creating an AudioInputStream object
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(songPath).getAbsoluteFile());
        // creating a clip reference to manipulate the song
        Clip clip = AudioSystem.getClip();
        // opening an audioInputStream for the clip
        clip.open(audioInputStream);
        // starts the song
        clip.start();
        System.out.println("The song: " + temp.getSongTitle() + " by " + temp.getSongArtist() + " is now playing...");
      }
    } catch (IOException | InputMismatchException | LineUnavailableException | UnsupportedAudioFileException e) {
      System.out.println(
          "There is a problem with your input. Make sure you enter in an integer when it asks for it. Only supported audio files are AIFC, AIFF, AU, SND and WAVE formats.");
      // e.printStackTrace();
      input.nextLine();
    }
  }

  /**
   * Description: Main calls the methods from each option from the menu
   *
   * @param args default for main
   *
   */
  public static void main(String[] args) {
    while (true) {
      menu();
      String option = input.nextLine();
      if (option.equalsIgnoreCase("A"))
        optionA();
      else if (option.equalsIgnoreCase("B"))
        optionB();
      else if (option.equalsIgnoreCase("G"))
        optionG();
      else if (option.equalsIgnoreCase("R"))
        optionR();
      else if (option.equalsIgnoreCase("P"))
        optionP();
      else if (option.equalsIgnoreCase("S"))
        optionS();
      else if (option.equalsIgnoreCase("N"))
        optionN();
      else if (option.equalsIgnoreCase("V"))
        optionV();
      else if (option.equalsIgnoreCase("C"))
        optionC();
      else if (option.equalsIgnoreCase("E"))
        optionE();
      else if (option.equalsIgnoreCase("D"))
        optionD();
      else if (option.equalsIgnoreCase("Z"))
        optionZ();
      else if (option.equalsIgnoreCase("Q"))
        optionQ();
      else {
        System.out.println("That is not one of the inputs listed!");
      }
    }
  }
}
