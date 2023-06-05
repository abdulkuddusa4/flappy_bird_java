package com.mygdx.game.my_exception;

public class InvalidCSVRecord extends RuntimeException{
    public InvalidCSVRecord(String msg){
        super(msg);
    }
}
