package com.example.democodelogic.TicTacToe;

public class three_Logics {
    private int[][] grid ;
    public three_Logics(int[][] grid) {
        this.grid = grid ;
    }
    public int CheckRow(){
        for (int i = 0 ; i < 3 ; i ++){
            for (int j = 0 ; j <= 0 ; j ++){
                if(grid[i][j] == grid[i][j+1] && grid[i][j+1] == grid[i][j+2] && grid[i][j] != -1)
                    return grid[i][j] ;
            }
        }
        return -1 ;
    }
    public int CheckCol(){
        for (int j = 0 ; j < 3 ; j ++){
            for (int i = 0 ; i <= 0 ; i ++){
                if(grid[i][j] == grid[i+1][j] && grid[i+1][j] == grid[i+2][j] && grid[i][j] != -1)
                    return grid[i][j] ;
            }
        }
        return -1 ;
    }
    public int CheckDiagonal(){
        for (int i = 0 ; i < 3 ; i ++){
            if(i == 0){
                if(grid[i][i] == grid[i+1][i+1] && grid[i+1][i+1] == grid[i+2][i+2] && grid[i][i] != -1)
                    return grid[i][i] ;
            }
            if(i == 2){
                if(grid[i][0] == grid[i-1][1] && grid[i-1][1] == grid[i-2][2] && grid[i][0] != -1)
                    return grid[i][0] ;
            }
        }
        return -1 ;
    }
}