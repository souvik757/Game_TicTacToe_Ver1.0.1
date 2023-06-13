package com.example.democodelogic.TicTacToe;

public class DemoRuns {
    public static void main(String[] args) {
        System.out.println("O -> "+2);
        System.out.println("X -> "+3);
        System.out.println("- -> "+-1);
        System.out.println("3 X 3 :::");
        TestRun3x3() ;
        System.out.println("N X N :::");
        TestRunNxN() ;
    }
    public static void TestRun3x3(){
        // -- 3 X 3 --
        three_Logics test_3x3 ;
        // case 1 :
        test_3x3 = new three_Logics(TestRuns.ThreeXThree.RowMatch) ;
        GridLayout.show(TestRuns.ThreeXThree.RowMatch);
        TestRuns.test_3X3(test_3x3);
        // case 2 :
        test_3x3 = new three_Logics(TestRuns.ThreeXThree.ColMatch) ;
        GridLayout.show(TestRuns.ThreeXThree.ColMatch);
        TestRuns.test_3X3(test_3x3);
        // case 3 :
        test_3x3 = new three_Logics(TestRuns.ThreeXThree.DiaMatch) ;
        GridLayout.show(TestRuns.ThreeXThree.DiaMatch);
        TestRuns.test_3X3(test_3x3);
    }
    public static void TestRunNxN(){
        // -- N X N --
        n_Logics     test_nxn ;
        // case 1 :
        test_nxn = new n_Logics(TestRuns.NXN.RowMatch) ;
        GridLayout.show(TestRuns.NXN.RowMatch);
        TestRuns.test_NxN(test_nxn);
        // case 2 :
        test_nxn = new n_Logics(TestRuns.NXN.ColMatch) ;
        GridLayout.show(TestRuns.NXN.ColMatch);
        TestRuns.test_NxN(test_nxn);
        // case 3 :
        test_nxn = new n_Logics(TestRuns.NXN.DiaMatch) ;
        GridLayout.show(TestRuns.NXN.DiaMatch);
        TestRuns.test_NxN(test_nxn);
    }
}