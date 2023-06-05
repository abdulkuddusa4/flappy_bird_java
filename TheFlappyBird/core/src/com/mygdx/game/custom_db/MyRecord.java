package com.mygdx.game.custom_db;

import com.mygdx.game.my_exception.InvalidCSVRecord;

public class MyRecord {
    String name;
    double score;
    long hash_id;
    MyRecord(String name, double score){
        this.name=name;
        this.score=score;
        this.hash_id=hash(name);

    }
    MyRecord(String record_string) {
        String[] name_score = record_string.split(",");
        if(name_score.length!=2){
            throw new InvalidCSVRecord("");
        }
        this.name=name_score[0];
        this.score=Double.parseDouble(name_score[1]);
        this.hash_id=hash(name);
    }

    void setScore(double score){
        this.score=score;
    }
    double getScore(){
        return this.score;
    }

    public String toCSVString(){
        return this.name+", "+this.score;
    }
    public static long hash(String st){
        long my_hash = 0;
        int i=1;
        for(char ch:st.toCharArray()){
            my_hash+=i++*(long)ch;
        }
//        System.out.println("TT> "+my_hash);
        return my_hash;
    }

    public boolean isEqueal(MyRecord obj){
        return this.hash_id==obj.hash_id;
    }
    public boolean isEqueal(String name){
        return this.hash_id==hash(name);
    }

}
