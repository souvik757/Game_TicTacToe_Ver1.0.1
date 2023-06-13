package com.example.democodelogic.TicTacToe;

public class n_Logics {
    private int row ;
    private int col ;
    private int[][] grid ;
    public n_Logics(int[][] grid){
        this.grid = grid ;
        row = grid.length; ;
        col = grid[0].length ;
    }
    public int CheckRow(){
        for (int i = 0 ; i < row ; i ++){
            for (int j = 0 ; j <= col - 4 ; j ++){
                if(grid[i][j] == grid[i][j+1] && grid[i][j+1] == grid[i][j+2] && grid[i][j+2] == grid[i][j+3]
                        && grid[i][j] != -1)
                    return grid[i][j] ;
            }
        }
        return -1 ;
    }
    public int CheckCol(){
        for (int j = 0 ; j < col ; j ++){
            for (int i = 0 ; i <= row - 4 ; i ++){
                if(grid[i][j] == grid[i+1][j] && grid[i+1][j] == grid[i+2][j] && grid[i+2][j] == grid[i+3][j]
                        && grid[i][j] != -1)
                    return grid[i][j] ;
            }
        }
        return -1 ;
    }
    public int CheckDiagonal(){
        DiagonalChecker obj = new DiagonalChecker() ;
        return obj.CheckDiagonal() ;
    }
    class DiagonalChecker {
        public int CheckDiagonal() {
            for (int i = 0 ; i < row ; i ++){
                for (int j = 0 ; j < col ; j ++){
//                    System.out.println("here");
                    if(valid(i , j , false , true , true , false , grid)) // - if bottom-right valid
                    {
                        // - ? has same value
                        if(Value(i , j , false , true , true , false , grid))
                            return grid[i][j] ;
                    }
                    if(valid(i , j , false , false , true , true , grid))// - if bottom-left valid
                    {
                        // - ? has same value
                        if(Value(i , j , false , false , true , true , grid))
                            return grid[i][j] ;
                    }
                    if(valid(i , j , true , true , false , false , grid))// - if top-right valid
                    {
                        // - ? has same value
                        if(Value(i , j , true , true , false , false , grid))
                            return grid[i][j] ;
                    }
                    if(valid(i , j , true , false , false , true , grid))// - if top-left valid
                    {
                        // - ? has same value
                        if(Value(i , j , true , false , false , true , grid))
                            return grid[i][j] ;
                    }
                }
            }
            return -1;
        }
        private boolean valid(int i , int j , boolean top , boolean right , boolean bottom , boolean left , int[][] grid){
            if(bottom && right){
                if(i+3 < grid.length && j+3 < grid[0].length)
                    return true ;
            }
            if(bottom && left){
                if(i+3 < grid.length && j-3 >= 0)
                    return true ;
            }
            if(top && right){
                if(i-3 >= 0 && j+3 < grid[0].length)
                    return true ;
            }
            if(top && left){
                return i - 3 > 0 && j - 3 >= 0;
            }
            return false ;
        }
        private boolean Value(int i , int j ,boolean top , boolean right , boolean bottom , boolean left , int[][] grid){
            if(bottom && right){
                if(     grid[i][j]     == grid[i+1][j+1] &&
                        grid[i+1][j+1] == grid[i+2][j+2] &&
                        grid[i+2][j+2] == grid[i+3][j+3] &&
                        grid[i][j] != raw.empty)
                    return true ;
            }
            if(bottom && left){
                if(     grid[i][j]     == grid[i+1][j-1] &&
                        grid[i+1][j-1] == grid[i+2][j-2] &&
                        grid[i+2][j-2] == grid[i+3][j-3] &&
                        grid[i][j] != raw.empty)
                    return true ;
            }
            if(top && right){
                if(     grid[i][j]     == grid[i-1][j+1] &&
                        grid[i-1][j+1] == grid[i-2][j+2] &&
                        grid[i-2][j+2] == grid[i-3][j+3] &&
                        grid[i][j] != raw.empty)
                    return true ;
            }
            if(top && left){
                if(     grid[i][j]     == grid[i-1][j-1] &&
                        grid[i-1][j-1] == grid[i-2][j-2] &&
                        grid[i-2][j-2] == grid[i-3][j-3] &&
                        grid[i][j] != raw.empty)
                    return true ;
            }
            return false ;
        }
    }
}