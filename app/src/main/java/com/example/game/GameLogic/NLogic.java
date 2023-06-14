package com.example.game.GameLogic;
import android.widget.Button;
import java.util.Map;

public class NLogic {
    private int row ;
    private int col ;
    private Button[][] grid ;
    private Map<Button , Integer> val ;
    public NLogic(Button[][] grid , Map<Button,Integer> val){
        this.grid = grid ;
        this.val = val ;
        row = grid.length ;
        col = grid[0].length ;
    }
    public int CheckRow(){
        for (int i = 0 ; i < row ; i ++){
            for (int j = 0 ; j <= col - 4 ; j ++){
                if(val.get(grid[i][j]) == val.get(grid[i][j+1]) &&
                        val.get(grid[i][j+1]) == val.get(grid[i][j+2]) &&
                        val.get(grid[i][j+2]) == val.get(grid[i][j+3])
                        && val.get(grid[i][j]) != raw.empty)
                    return val.get(grid[i][j]) ;
            }
        }
        return -1 ;
    }
    public int CheckCol(){
        for (int j = 0 ; j < col ; j ++){
            for (int i = 0 ; i <= row - 4 ; i ++){
                if(        val.get(grid[i][j])   == val.get(grid[i+1][j]) &&
                           val.get(grid[i+1][j]) == val.get(grid[i+2][j]) &&
                           val.get(grid[i+2][j]) == val.get(grid[i+3][j])
                        && val.get(grid[i][j]) != -1)
                    return val.get(grid[i][j]) ;
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
                            return val.get(grid[i][j]) ;
                    }
                    if(valid(i , j , false , false , true , true , grid))// - if bottom-left valid
                    {
                        // - ? has same value
                        if(Value(i , j , false , false , true , true , grid))
                            return val.get(grid[i][j]) ;
                    }
                    if(valid(i , j , true , true , false , false , grid))// - if top-right valid
                    {
                        // - ? has same value
                        if(Value(i , j , true , true , false , false , grid))
                            return val.get(grid[i][j]) ;
                    }
                    if(valid(i , j , true , false , false , true , grid))// - if top-left valid
                    {
                        // - ? has same value
                        if(Value(i , j , true , false , false , true , grid))
                            return val.get(grid[i][j]) ;
                    }
                }
            }
            return -1;
        }
        private boolean valid(int i , int j , boolean top , boolean right , boolean bottom , boolean left , Button[][] grid){
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
        private boolean Value(int i , int j ,boolean top , boolean right , boolean bottom , boolean left , Button[][] grid){
            if(bottom && right){
                if(     val.get(grid[i][j])     == val.get(grid[i+1][j+1]) &&
                        val.get(grid[i+1][j+1]) == val.get(grid[i+2][j+2]) &&
                        val.get(grid[i+2][j+2]) == val.get(grid[i+3][j+3]) &&
                        val.get(grid[i][j]) != raw.empty)
                    return true ;
            }
            if(bottom && left){
                if(     val.get(grid[i][j])     == val.get(grid[i+1][j-1]) &&
                        val.get(grid[i+1][j-1]) == val.get(grid[i+2][j-2]) &&
                        val.get(grid[i+2][j-2]) == val.get(grid[i+3][j-3]) &&
                        val.get(grid[i][j]) != raw.empty)
                    return true ;
            }
            if(top && right){
                if(     val.get(grid[i][j])     == val.get(grid[i-1][j+1]) &&
                        val.get(grid[i-1][j+1]) == val.get(grid[i-2][j+2]) &&
                        val.get(grid[i-2][j+2]) == val.get(grid[i-3][j+3]) &&
                        val.get(grid[i][j]) != raw.empty)
                    return true ;
            }
            if(top && left){
                if(     val.get(grid[i][j])     == val.get(grid[i-1][j-1]) &&
                        val.get(grid[i-1][j-1]) == val.get(grid[i-2][j-2]) &&
                        val.get(grid[i-2][j-2]) == val.get(grid[i-3][j-3]) &&
                        val.get(grid[i][j]) != raw.empty)
                    return true ;
            }
            return false ;
        }
    }
}
