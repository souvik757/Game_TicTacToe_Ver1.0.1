package com.example.game.GameLogic;
import android.widget.Button;
import java.util.Map;

public class ThreeLogic {
    private Button[][] grid ;
    private Map<Button , Integer> val ;
    public ThreeLogic(Button[][] buttons, Map<Button, Integer> val) {
        this.grid = buttons;
        this.val = val;
    }
    public int CheckRow(){
        for (int i = 0 ; i < 3 ; i ++){
            for (int j = 0 ; j <= 0 ; j ++){
                if(val.get(grid[i][j]) == val.get(grid[i][j+1]) &&
                        val.get(grid[i][j+1]) == val.get(grid[i][j+2]) &&
                        val.get(grid[i][j]) != raw.empty)
                    return val.get(grid[i][j]) ;
            }
        }
        return -1 ;
    }
    public int CheckCol(){
        for (int j = 0 ; j < 3 ; j ++){
            for (int i = 0 ; i <= 0 ; i ++){
                if(val.get(grid[i][j]) == val.get(grid[i+1][j]) &&
                        val.get(grid[i+1][j]) == val.get(grid[i+2][j]) &&
                        val.get(grid[i][j]) != raw.empty)
                    return val.get(grid[i][j]) ;
            }
        }
        return -1 ;
    }
    public int CheckDiagonal(){
        for (int i = 0 ; i < 3 ; i ++){
            if(i == 0){
                if(val.get(grid[i][i]) == val.get(grid[i+1][i+1]) &&
                        val.get(grid[i+1][i+1]) == val.get(grid[i+2][i+2]) &&
                        val.get(grid[i][i]) != raw.empty)
                    return val.get(grid[i][i]) ;
            }
            if(i == 2){
                if(val.get(grid[i][0]) == val.get(grid[i-1][1]) &&
                        val.get(grid[i-1][1]) == val.get(grid[i-2][2]) &&
                        val.get(grid[i][0]) != raw.empty)
                    return val.get(grid[i][0]) ;
            }
        }
        return -1 ;
    }
}