import java.util.HashMap;
import java.util.ArrayList;

public class Board {

	private char[][] puzzle;
	private String[] wordLocations;
	private String start;
	private String end;
	private double totalTime;
	private HashMap<Character,ArrayList> letters = new HashMap<Character,ArrayList>();
	private int currIndex;

	public Board(char[][] puzzle,int wordAmount,HashMap letters){
		this.puzzle = puzzle;
		this.letters = letters;
		wordLocations = new String[wordAmount];
	}

	public void findWords(String[] words){
		double startTime = System.nanoTime();
		for(int i = 0;i<words.length;i++){
			currIndex = i;
			searchWordHash(words[i]);
		}
		double endtime = System.nanoTime();
		totalTime = (endtime - startTime)/1000000;
	}

	public void searchWordHash(String word) {
		int row = 0;
		int column = 0;
		ArrayList<Coordinate> locations;

		if( (locations = letters.get(word.charAt(0))) != null){
			for(Coordinate location:locations){
				row = location.row;
				column = location.column;
				lookDirections(word, row, column);
			}
		}
	}

	public void lookDirections(String word, int row, int column){
		lookDirection(word, row, column,0,1);
		lookDirection(word, row, column,0,-1);
		lookDirection(word, row, column, 1, 0);
		lookDirection(word, row, column, -1, 0);
		lookDirection(word, row, column, 1, 1);
		lookDirection(word, row, column, 1, -1);
		lookDirection(word, row, column, -1, -1);
		lookDirection(word, row, column, -1, 1);
	}

	public void lookDirection(String word, int row, int column,int rowIncrease, int columnIncrease){
		if(!hasLastLetter(word,row,column,rowIncrease,columnIncrease)) return;
		start = "(" + (row+1) + "," + (column+1) + ")";

		for(int i = 1;i<word.length()-1;i++){
			row += rowIncrease; column += columnIncrease;
			if(!(word.charAt(i) == puzzle[row][column])) return;
		}
		row += rowIncrease; column += columnIncrease;
		end = ("(" + (row+1) + "," + (column+1) + ")");
		if(wordLocations[currIndex] == null){
			System.out.println("Found word: " + word + " at: " + row + "," + column + " with currIndex: " + currIndex);
			wordLocations[currIndex] = "[" + start + "-" + end + "]";
		}
		else {
			System.out.println("Found word: " + word + " at: " + row + "," + column + " with currIndex: " + currIndex);
			wordLocations[currIndex] += "[" + start + "-" + end + "]";
		}
	}

	//checks if last letter exist and as a bi-effect checks if  the word is out of bounds
	public boolean hasLastLetter(String word, int row, int column, int rowIncrease, int columnIncrease){
		int lastCharIndex = word.length() - 1;
		int rowLetterIndex = row + (rowIncrease * lastCharIndex);
		int columnLetterIndex = column + (columnIncrease * lastCharIndex);

		if(letterInBounds(rowLetterIndex,columnLetterIndex)){
			if(word.charAt(lastCharIndex) == puzzle[rowLetterIndex][columnLetterIndex]) return true;
		}
		return false;
	}

	public boolean letterInBounds(int rowLetterIndex, int columnLetterIndex){
		return (puzzle.length>rowLetterIndex
			 && puzzle.length>columnLetterIndex
			 && rowLetterIndex>=0
			 && columnLetterIndex>=0);
	}

	public String[] getWordLocations(){
		return wordLocations;
	}

	public double getTotalTime(){
		return totalTime;
	}
}
