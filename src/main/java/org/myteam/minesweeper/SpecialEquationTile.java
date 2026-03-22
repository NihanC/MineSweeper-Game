package org.myteam.minesweeper;

import java.util.Random;

//basically ends the game for anyone that doesn't know basic math
public class SpecialEquationTile extends Tile {
    public SpecialEquationTile(int r, int c) {
        super(r, c);
    }
    private int num1;
    private int num2;
    private String equation;

    @Override
    public void open(Board board, Game game) {
        if (!flagged && !revealed) {
            revealed = true;
            Random r= new Random();
            int i=0;
            int j=0;
            if(board.getLevel()== Level.EASY){
                i = r.nextInt(1,10);
                j = r.nextInt(1, 10);
            }
            else if(board.getLevel()== Level.MEDIUM){
                i = r.nextInt(10, 100);
                j = r.nextInt(10, 100);
            }
            else{
                i = r.nextInt(100, 200);
                j = r.nextInt(100, 200);
            }
            num1=i;
            num2=j;
            generateEquation(); // FIX: was never called, leaving equation as null
        }
    }

    public void generateEquation(){
        Random r = new Random();
        int i = r.nextInt(2);
        if(i==1){
            equation = "sum";
        }
        else{
            equation = "minus";
        }
    }

    public int getNum1(){return num1;}
    public int getNum2(){return num2;}
    public String getEquation(){return equation;}
}