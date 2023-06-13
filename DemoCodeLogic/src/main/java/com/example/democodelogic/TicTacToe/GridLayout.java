package com.example.democodelogic.TicTacToe;

public class GridLayout {
    public static void show(int[][] grid){
        for (int i = 0 ; i < grid.length ; i ++){
            for (int j = 0 ; j < grid[0].length ; j ++){
                if(grid[i][j] == raw.circle)
                    System.out.print(" O");
                else if(grid[i][j] == raw.cross)
                    System.out.print(" X");
                else
                    System.out.print(" -");
            }
            System.out.println();
        }
    }
}
