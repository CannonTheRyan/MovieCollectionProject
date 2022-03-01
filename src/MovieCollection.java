import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class MovieCollection
{
  private ArrayList<Movie> movies;
  private Scanner scanner;

  public MovieCollection(String fileName)
  {
    importMovieList(fileName);
    scanner = new Scanner(System.in);
  }

  public ArrayList<Movie> getMovies()
  {
    return movies;
  }
  
  public void menu()
  {
    String menuOption = "";
    
    System.out.println("Welcome to the movie collection!");
    System.out.println("Total: " + movies.size() + " movies");
    
    while (!menuOption.equals("q"))
    {
      System.out.println("------------ Main Menu ----------");
      System.out.println("- search (t)itles");
      System.out.println("- search (k)eywords");
      System.out.println("- search (c)ast");
      System.out.println("- see all movies of a (g)enre");
      System.out.println("- list top 50 (r)ated movies");
      System.out.println("- list top 50 (h)igest revenue movies");
      System.out.println("- (q)uit");
      System.out.print("Enter choice: ");
      menuOption = scanner.nextLine();
      
      if (!menuOption.equals("q"))
      {
        processOption(menuOption);
      }
    }
  }
  
  private void processOption(String option)
  {
    if (option.equals("t"))
    {
      searchTitles();
    }
    else if (option.equals("c"))
    {
      searchCast();
    }
    else if (option.equals("k"))
    {
      searchKeywords();
    }
    else if (option.equals("g"))
    {
      listGenres();
    }
    else if (option.equals("r"))
    {
      listHighestRated();
    }
    else if (option.equals("h"))
    {
      listHighestRevenue();
    }
    else
    {
      System.out.println("Invalid choice!");
    }
  }

  private void searchTitles()
  {
    System.out.print("Enter a title search term: ");
    String searchTerm = scanner.nextLine();

    // prevent case sensitivity
    searchTerm = searchTerm.toLowerCase();

    // arraylist to hold search results
    ArrayList<Movie> results = new ArrayList<Movie>();

    // search through ALL movies in collection
    for (int i = 0; i < movies.size(); i++)
    {
      String movieTitle = movies.get(i).getTitle();
      movieTitle = movieTitle.toLowerCase();

      if (movieTitle.indexOf(searchTerm) != -1)
      {
        //add the Movie object to the results list
        results.add(movies.get(i));
      }
    }

    // sort the results by title
    sortResults(results);

    // now, display them all to the user
    for (int i = 0; i < results.size(); i++)
    {
      String title = results.get(i).getTitle();

      // this will print index 0 as choice 1 in the results list; better for user!
      int choiceNum = i + 1;

      System.out.println("" + choiceNum + ". " + title);
    }

    System.out.println("Which movie would you like to learn more about?");
    System.out.print("Enter number: ");

    int choice = scanner.nextInt();
    scanner.nextLine();

    Movie selectedMovie = results.get(choice - 1);

    displayMovieInfo(selectedMovie);

    System.out.println("\n ** Press Enter to Return to Main Menu **");
    scanner.nextLine();
  }

  private void sortResults(ArrayList<Movie> listToSort)
  {
    for (int j = 1; j < listToSort.size(); j++)
    {
      Movie temp = listToSort.get(j);
      String tempTitle = temp.getTitle();

      int possibleIndex = j;
      while (possibleIndex > 0 && tempTitle.compareTo(listToSort.get(possibleIndex - 1).getTitle()) < 0)
      {
        listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
        possibleIndex--;
      }
      listToSort.set(possibleIndex, temp);
    }
  }

  private void sortStringResults(ArrayList<String> listToSort)
  {
    for (int j = 1; j < listToSort.size(); j++)
    {
      String temp = listToSort.get(j);
      int possibleIndex = j;
      while (possibleIndex > 0 && temp.compareTo(listToSort.get(possibleIndex - 1)) < 0)
      {
        listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
        possibleIndex--;
      }
      listToSort.set(possibleIndex, temp);
    }
  }

  private void displayMovieInfo(Movie movie)
  {
    System.out.println();
    System.out.println("Title: " + movie.getTitle());
    System.out.println("Tagline: " + movie.getTagline());
    System.out.println("Runtime: " + movie.getRuntime() + " minutes");
    System.out.println("Year: " + movie.getYear());
    System.out.println("Directed by: " + movie.getDirector());
    System.out.println("Cast: " + movie.getCast());
    System.out.println("Overview: " + movie.getOverview());
    System.out.println("User rating: " + movie.getUserRating());
    System.out.println("Box office revenue: " + movie.getRevenue());
  }

  private void searchKeywords()
  {
    System.out.print("Enter a keyword search term: ");
    String searchTerm = scanner.nextLine();
    searchTerm = searchTerm.toLowerCase();

    ArrayList<Movie> results = new ArrayList<Movie>();

    // search through ALL movies in collection
    for (int i = 0; i < movies.size(); i++)
    {
      String movieKeywords = movies.get(i).getKeywords();
      movieKeywords = movieKeywords.toLowerCase();
      if (movieKeywords.indexOf(searchTerm) != -1)
      {
        results.add(movies.get(i));
      }
    }

    sortResults(results);

    for (int i = 0; i < results.size(); i++)
    {
      String title = results.get(i).getTitle();
      int choiceNum = i + 1;
      System.out.println("" + choiceNum + ". " + title);
    }

    System.out.println("Which movie would you like to learn more about?");
    System.out.print("Enter number: ");

    int choice = scanner.nextInt();
    scanner.nextLine();

    Movie selectedMovie = results.get(choice - 1);

    displayMovieInfo(selectedMovie);

    System.out.println("\n ** Press Enter to Return to Main Menu **");
    scanner.nextLine();
  }

  private void searchCast()
  {
    System.out.print("Enter a person to search for (first or last name): ");
    String searchTerm = scanner.nextLine();
    searchTerm = searchTerm.toLowerCase();

    ArrayList<String> results = new ArrayList<>();

    for (int i = 0; i < movies.size(); i++)
    {
      String movieCast = movies.get(i).getCast();
      String[] cast = movieCast.split("\\|");
      for (int j = 0; j < cast.length; j++)
      {
        if (results.indexOf(cast[j]) == -1 && cast[j].toLowerCase().indexOf(searchTerm.toLowerCase()) != -1)
        {
          results.add(cast[j]);
        }
      }
    }

    sortStringResults(results);

    for (int i = 0; i < results.size(); i++)
    {
      String title = results.get(i);
      int choiceNum = i + 1;
      System.out.println("" + choiceNum + ". " + title);
    }

    System.out.println("Which would you like to see all movies for? ");
    System.out.print("Enter number: ");
    String castChosen = results.get(scanner.nextInt()-1);

    ArrayList<Movie> moviesWithCast = new ArrayList<>();

    for (int i = 0; i < movies.size(); i++)
    {
      Movie movie = movies.get(i);
      if (movie.getCast().indexOf(castChosen) != -1)
      {
        moviesWithCast.add(movie);
      }
    }

    sortResults(moviesWithCast);

    for (int i = 0; i < moviesWithCast.size(); i++)
    {
      System.out.println(i+1 + ". " + moviesWithCast.get(i).getTitle());
    }

    System.out.println("Which movie would you like to learn more about? ");
    System.out.print("Enter number: ");

    displayMovieInfo(moviesWithCast.get(scanner.nextInt()-1));

    System.out.println("\n ** Press Enter to Return to Main Menu **");
    scanner.nextLine();
    scanner.nextLine();
  }
  
  private void listGenres()
  {
    ArrayList<String> genres = new ArrayList<>();

    for (int i = 0; i < movies.size(); i++)
    {
      String[] genre = movies.get(i).getGenres().split("\\|");
      for (int j = 0; j < genre.length; j++)
      {
        if (genres.indexOf(genre[j]) == -1)
        {
          genres.add(genre[j]);
        }
      }
    }

    sortStringResults(genres);

    for (int i = 0; i < genres.size(); i++)
    {
      System.out.println(i+1 + ". " + genres.get(i));
    }

    System.out.println("Which would you like to see all movies for? ");
    System.out.print("Enter number: ");
    String genreChosen = genres.get(scanner.nextInt()-1);

    ArrayList<Movie> moviesWithGenre = new ArrayList<>();

    for (int i = 0; i < movies.size(); i++)
    {
      Movie movie = movies.get(i);
      if (movie.getGenres().indexOf(genreChosen) != -1)
      {
        moviesWithGenre.add(movie);
      }
    }

    sortResults(moviesWithGenre);

    for (int i = 0; i < moviesWithGenre.size(); i++)
    {
      System.out.println(i+1 + ". " + moviesWithGenre.get(i).getTitle());
    }

    System.out.println("Which movie would you like to learn more about? ");
    System.out.print("Enter number: ");

    displayMovieInfo(moviesWithGenre.get(scanner.nextInt()-1));

    System.out.println("\n ** Press Enter to Return to Main Menu **");
    scanner.nextLine();
    scanner.nextLine();
  }
  
  private void listHighestRated()
  {
    ArrayList<Movie> highestRatedMovies = new ArrayList<>();
    ArrayList<Integer> usedIndexes = new ArrayList<>();
    for (int i = 0; i < 50; i++)
    {
      double highestRating = 0;
      int index = -1;
      Movie highestMovie;
      for (int j = 0; j < movies.size(); j++)
      {
        if (movies.get(j).getUserRating() > highestRating && usedIndexes.indexOf(j) == -1)
        {
          highestRating = movies.get(j).getUserRating();
          index = j;
        }
      }
      highestMovie = movies.get(index);
      usedIndexes.add(index);
      highestRatedMovies.add(highestMovie);
    }

    for (int i = 0; i < highestRatedMovies.size(); i++)
    {
      System.out.println(i+1 + ". " + highestRatedMovies.get(i).getTitle() + ": " + highestRatedMovies.get(i).getUserRating());
    }

    System.out.println("Which movie would you like to learn more about? ");
    System.out.print("Enter number: ");

    displayMovieInfo(highestRatedMovies.get(scanner.nextInt()-1));

    System.out.println("\n ** Press Enter to Return to Main Menu **");
    scanner.nextLine();
    scanner.nextLine();
  }
  
  private void listHighestRevenue()
  {
    ArrayList<Movie> highestEarningMovies = new ArrayList<>();
    ArrayList<Integer> usedIndexes = new ArrayList<>();
    for (int i = 0; i < 50; i++)
    {
      double highestRevenue = 0;
      int index = -1;
      Movie highestMovie;
      for (int j = 0; j < movies.size(); j++)
      {
        if (movies.get(j).getRevenue() > highestRevenue && usedIndexes.indexOf(j) == -1)
        {
          highestRevenue = movies.get(j).getRevenue();
          index = j;
        }
      }
      highestMovie = movies.get(index);
      usedIndexes.add(index);
      highestEarningMovies.add(highestMovie);
    }

    for (int i = 0; i < highestEarningMovies.size(); i++)
    {
      System.out.println(i+1 + ". " + highestEarningMovies.get(i).getTitle() + ": " + highestEarningMovies.get(i).getRevenue());
    }

    System.out.println("Which movie would you like to learn more about? ");
    System.out.print("Enter number: ");

    displayMovieInfo(highestEarningMovies.get(scanner.nextInt()-1));

    System.out.println("\n ** Press Enter to Return to Main Menu **");
    scanner.nextLine();
    scanner.nextLine();
  }

  private void importMovieList(String fileName)
  {
    try
    {
      FileReader fileReader = new FileReader(fileName);
      BufferedReader bufferedReader = new BufferedReader(fileReader);
      String line = bufferedReader.readLine();

      movies = new ArrayList<Movie>();

      while ((line = bufferedReader.readLine()) != null)
      {
        String[] movieLine = line.split(",");

        String title = movieLine[0];
        String cast = movieLine[1];
        String director = movieLine[2];
        String tagline = movieLine[3];
        String keywords = movieLine[4];
        String overview = movieLine[5];
        int runtime = Integer.parseInt(movieLine[6]);
        String genres = movieLine[7];
        double userRating = Double.parseDouble(movieLine[8]);
        int year = Integer.parseInt(movieLine[9]);
        int revenue = Integer.parseInt(movieLine[10]);

        Movie movie = new Movie(title, cast, director, tagline, keywords, overview, runtime, genres, userRating, year, revenue);

        movies.add(movie);
      }
      bufferedReader.close();
    }
    catch(IOException exception)
    {
      System.out.println("Unable to access " + exception.getMessage());
    }
  }
}