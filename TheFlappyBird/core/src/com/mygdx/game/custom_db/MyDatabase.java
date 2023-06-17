package com.mygdx.game.custom_db;

import com.mygdx.game.my_exception.InvalidCSVRecord;
import com.mygdx.game.states.HighScoreState;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class MyDatabase {
    int length;
    ArrayList<MyRecord> records;
    File db;

    boolean is_empty=false;

    public MyDatabase(){
        this.db = new File("core/src/com/mygdx/game/custom_db/db/scores");
        records = new ArrayList<>();
        try{
            Scanner sc = new Scanner(this.db);
            while (sc.hasNextLine()){
                try{
                    this.records.add(new MyRecord(sc.nextLine()));
                }
                catch (InvalidCSVRecord e){
                    System.out.println(e);
                    System.out.println("Reseting the db");
                    this.reset_db();
                }
            }
        }
        catch (FileNotFoundException e){
            try{
                if(this.db.createNewFile()){
                }
            }
            catch (IOException er1){
                System.out.println(er1);
            }

        }

    }

    public void add_record(String name, int value){
        for(MyRecord record:this.records){
            if (record.isEqueal(name)){
                record.setScore(Math.max(value, record.getScore()));
                this.save();
                return;
            }
        }
        this.records.add(new MyRecord(name.trim().toUpperCase(),value));
        this.save();
    }
    public void reset_db(){
        FileWriter writer;
        try{
            writer = new FileWriter(this.db);
            writer.write("");
            writer.close();
        }
        catch (IOException e){
            for(MyRecord record : this.records){
                System.out.println(record.toCSVString());
            }
        }
    }
    public void save(){
        FileWriter writer;
        try{
            writer = new FileWriter(this.db);
            for(MyRecord record : this.records){
                writer.write(record.toCSVString()+"\n");
                System.out.println(record.toCSVString());
            }
            writer.close();
        }
        catch (IOException e){
            for(MyRecord record : this.records){
                System.out.println(record.toCSVString());
            }
        }

    }
    public String GET_PERFECT_RECORDS(){
        int count=0;
        String my_st="";
        ArrayList<MyRecord> counted_records= new ArrayList<>();
        if (this.records.isEmpty())
            return my_st;
        counted_records.addAll(this.records);
        MyRecord commulative_best;
        while (count!=this.records.size()){
            commulative_best = counted_records.get(0);
            for(MyRecord record:counted_records){
                if(commulative_best.getScore()<record.getScore()){
                    commulative_best=record;
                }
            }
            counted_records.remove(commulative_best);
            my_st+=HighScoreState.getPerfectString(
                    commulative_best.name,
                    "--",
                    ">"
            )+commulative_best.getScore()+"\n";

            count++;
        }

        return my_st;
    }

    public double get_high_score(){
        double highscore = 0;
        for(MyRecord record:this.records){
            highscore = Math.max(highscore,record.getScore());
        }
        return highscore;
    }

}
