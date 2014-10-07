import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.ArrayList;

public class PuzzleReader {
	private char[][] puzzle;
	private String[] words;
	private String[] wordLocations;
	private Scanner scanner = null;
	private double totalTime;
	private HashMap<Character,ArrayList> lettersHash = new HashMap<Character,ArrayList>();
	public PuzzleReader(){
		try{
			scanner = new Scanner(new File("input.txt"));
		}
		catch(FileNotFoundException e){
			System.out.println("File not found");
		}
		finally {
			if(scanner !=null){
				fillPuzzle();
				printPuzzle();
				fillWords();
				scanner.close();

				solvePuzzle();
				printSolution();
			}
			else System.exit(0);
		}
	}

	public void fillPuzzle(){
		String[] letters = scanner.nextLine().split(" ");
		int size = letters.length;
		char letter;
		puzzle = new char[size][size];
		for(int i = 0;i<size;i++){
			for(int j = 0;j<size;j++){
				letter = letters[j].charAt(0);
				puzzle[i][j] = letter;
				saveLetterLocations(letter,i,j);
			}
			letters = scanner.nextLine().split(" ");
		}
	}

	public void fillWords(){
		String line = scanner.nextLine();
		words = line.split(" ");
	}

	public void saveLetterLocations(char letter,int i, int j){
		ArrayList<Coordinate> letterLocations;

		try{
			if((letterLocations = lettersHash.get(letter)) == null) {
				lettersHash.put(letter,new ArrayList());
				letterLocations = lettersHash.get(letter);
				letterLocations.add(new Coordinate(i,j));
			}

			letterLocations.add(new Coordinate(i,j));
		}catch (Exception e){}
	}

	public void solvePuzzle(){
		Board board = new Board(puzzle,words.length,lettersHash);
		board.findWords(words);
		wordLocations = board.getWordLocations();
		totalTime = board.getTotalTime();
	}

	public void printPuzzle(){
		System.out.println("<--Puzzle-->");
		for(int i = 0;i<puzzle.length;i++){
			for(int j = 0;j<puzzle.length;j++){
				System.out.print(puzzle[i][j]);
			}
			System.out.println();
		}
		System.out.println();
	}

	public void printSolution(){
		System.out.println("<--Solution-->");
		for(int i = 0;i<words.length;i++){
			if(wordLocations[i] == null) System.out.printf("%-24s%s%s\n",words[i],":","NOT FOUND!");
			else System.out.printf("%-24s%s%s\n",words[i],":",wordLocations[i]);
		}
		System.out.println("\nTotaltime: " + totalTime + "ms");
	}
}
