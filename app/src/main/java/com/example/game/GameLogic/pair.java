package com.example.game.GameLogic;

public class pair<K,V> {
    K row ;
    V column ;

    public pair(K row, V column) {
        this.row = row;
        this.column = column;
    }

    public void setRow(K row) {
        this.row = row;
    }

    public void setColumn(V column) {
        this.column = column;
    }

    public K getRow() {
        return row;
    }

    public V getColumn() {
        return column;
    }
}
