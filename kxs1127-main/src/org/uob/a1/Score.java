package org.uob.a1;

public class Score {
    private int startingScore;
    private int roomsVisited;
    private int puzzlesSolved;
    private final int PUZZLE_VALUE = 10;

    public Score(int startingScore){
        this.startingScore = startingScore;
        this.roomsVisited=0;
        this.puzzlesSolved=0;
    }

    public void visitRoom(){
        roomsVisited++;
    }

    public void solvePuzzle(){
        puzzlesSolved++;
    }

    public int getScore(){
        return startingScore - roomsVisited +(puzzlesSolved * PUZZLE_VALUE);
    }
}