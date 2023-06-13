package com.example.democodelogic.TicTacToe;

public class TestRuns {
    public static class ThreeXThree{
        // 3 X 3 ::
        // case 1 : row match
        public static int[][] RowMatch = new int[][]{
                {raw.empty, raw.empty , raw.empty} ,
                {raw.cross, raw.cross , raw.cross} ,
                {raw.empty, raw.empty , raw.empty}
        };
        // case 2 : col match
        public static int[][] ColMatch = new int[][]{
                {raw.empty, raw.circle , raw.empty} ,
                {raw.cross, raw.circle , raw.cross} ,
                {raw.empty, raw.circle , raw.empty}
        };
        // case 3 : dia match
        public static int[][] DiaMatch = new int[][]{
                {raw.cross , raw.empty , raw.circle} ,
                {raw.circle, raw.circle , raw.empty} ,
                {raw.circle , raw.empty , raw.cross}
        };
    }
    public static class NXN{
        // N X N :::
        // case 1 : row match for 4 X 4
        public static int[][] RowMatch = new int[][]{
                {raw.empty , raw.empty , raw.empty , raw.cross} ,
                {raw.empty , raw.empty , raw.cross , raw.empty} ,
                {raw.empty , raw.cross , raw.empty , raw.empty} ,
                {raw.cross , raw.empty , raw.empty , raw.empty}
        } ;
        // case 2 : col match for 6 X 6
        public static int[][] ColMatch = new int[][]{
                {raw.empty , raw.empty , raw.empty , raw.empty , raw.empty , raw.empty} ,
                {raw.empty , raw.empty , raw.empty , raw.cross , raw.empty , raw.empty} ,
                {raw.empty , raw.empty , raw.cross , raw.empty , raw.empty , raw.empty} ,
                {raw.empty , raw.cross , raw.empty , raw.empty , raw.empty , raw.empty} ,
                {raw.cross , raw.empty , raw.empty , raw.empty , raw.empty , raw.empty} ,
                {raw.empty , raw.empty , raw.empty , raw.empty , raw.empty , raw.empty}
        } ;
        // case 3 : dia match for 7 X 7
        public static int[][] DiaMatch = new int[][]{
                {raw.empty , raw.empty , raw.empty , raw.empty , raw.empty , raw.empty , raw.empty} ,
                {raw.empty , raw.empty , raw.empty , raw.empty , raw.empty , raw.empty , raw.empty} ,
                {raw.empty , raw.empty , raw.empty , raw.cross , raw.empty , raw.empty , raw.empty} ,
                {raw.empty , raw.empty , raw.empty , raw.circle , raw.cross , raw.empty , raw.empty} ,
                {raw.empty , raw.empty , raw.circle , raw.empty , raw.empty , raw.cross , raw.empty} ,
                {raw.empty , raw.circle , raw.empty , raw.empty , raw.empty , raw.empty , raw.empty} ,
                {raw.circle , raw.empty , raw.empty , raw.empty , raw.empty , raw.empty , raw.empty}
        } ;
    }
    public static void test_3X3(three_Logics test_3x3){
        if(test_3x3.CheckRow() != -1 || test_3x3.CheckCol() != -1 || test_3x3.CheckDiagonal() != -1)
        {
            if(test_3x3.CheckRow() != -1)
                System.out.println(test_3x3.CheckRow());
            if(test_3x3.CheckCol() != -1)
                System.out.println(test_3x3.CheckCol());
            if(test_3x3.CheckDiagonal() != -1)
                System.out.println(test_3x3.CheckDiagonal());
        }
        else
            System.out.println("draw");
    }
    public static void test_NxN(n_Logics test_nxn){
        if(test_nxn.CheckRow() != -1 || test_nxn.CheckCol() != -1 || test_nxn.CheckDiagonal() != -1)
        {
            if(test_nxn.CheckRow() != -1)
                System.out.println(test_nxn.CheckRow());
            if(test_nxn.CheckCol() != -1)
                System.out.println(test_nxn.CheckCol());
            if(test_nxn.CheckDiagonal() != -1)
                System.out.println(test_nxn.CheckDiagonal());
        }
        else
            System.out.println("draw");
    }
}
