package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.custom_db.MyDatabase;
import com.mygdx.game.sprites.Bird;
import com.mygdx.game.sprites.SpriteGroup;

public class GameOverState extends AbstractBaseState{
    Bird bird;
    
    SpriteGroup pipe_group;

    Texture bg;
    Texture input_bg;
    BitmapFont NAME_PLATE;
    BitmapFont CONTINUE_MSG;
    String full_name;
    boolean is_pressed = false;
    long time0;
    MyDatabase database_connection;
    Sound theme_song;



    BitmapFont SCORE_BOARD;
    boolean named=false;



    protected GameOverState(GameStateManager gsm, GamePlayState game_play_state){
        super(gsm);
        this.bg=game_play_state.bg;
        this.bird=game_play_state.bird;
        this.pipe_group=game_play_state.pipe_group;
        this.SCORE_BOARD=game_play_state.SCORE_BOARD;
        this.theme_song=game_play_state.theme_song;
        this.input_bg = new Texture("gameover/input_bg.jpeg");
        this.full_name="";
        this.NAME_PLATE=new BitmapFont();
        this.NAME_PLATE.getData().setScale(1.5f);
        this.NAME_PLATE.setColor(1.0f,0.0f,1.0f,1.0f);

        this.CONTINUE_MSG=new BitmapFont();
        this.CONTINUE_MSG.getData().setScale(3f);
        this.CONTINUE_MSG.setColor(1.0f,1.0f,0.0f,1.0f);

        this.database_connection = new MyDatabase();



    }

    @Override
    public void handleInput() {
        char[] st = {'a', 'b', 'c'};
        for(char ch:"ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789 ".toCharArray()){
            if(Gdx.input.isKeyPressed(Input.Keys.valueOf(String.valueOf(ch))) && !this.is_pressed){
//                System.out.println(""+ch+" "+Input.Keys.valueOf(String.valueOf(ch))+" "+String.valueOf(ch));
                this.is_pressed=true;
                this.time0 = System.currentTimeMillis();
                if(ch==' '){
                    ch='_';
                }
                this.full_name=(this.full_name == null)?""+ch:this.full_name+ch;
            }
            else if(Gdx.input.isKeyPressed(Input.Keys.BACKSPACE) && !this.is_pressed){
                this.full_name="";
                this.is_pressed=true;

            }
            else if(Gdx.input.isKeyPressed(Input.Keys.ENTER) && !this.is_pressed && this.named){

                this.is_pressed=true;
                this.theme_song.stop();
                this.database_connection.add_record(this.full_name, this.bird.SCORE);
                this.gsm.pop();
            }
        }
    }

    @Override
    public void update(float dt) {
        this.handleInput();
        if(System.currentTimeMillis()-this.time0>140){
            this.is_pressed=false;
            this.time0=System.currentTimeMillis();
        }else{
            this.is_pressed=true;
        }
        if(this.full_name.length()>0){
            this.named=true;
        }
        else {
            this.named=false;
        }
    }

    @Override
    public void render(SpriteBatch sb) {

        sb.begin();
        sb.draw(
                this.bg,
                0,
                0,
                Gdx.graphics.getWidth(),
                Gdx.graphics.getHeight()
        );
        sb.end();
        this.pipe_group.render(sb);
        this.bird.render(sb);

        sb.begin();
        this.SCORE_BOARD.draw(sb,"Score: "+this.bird.SCORE,100,600);
        sb.draw(this.input_bg,
                Gdx.graphics.getWidth()/4,
                Gdx.graphics.getHeight()/2-100,
                Gdx.graphics.getWidth()/2+50,
                200
        );
        this.NAME_PLATE.draw(sb, "FULL NAME: "+this.full_name,
                Gdx.graphics.getWidth()/4+50,
                Gdx.graphics.getHeight()/2+50
                );
        if(this.named){
            this.CONTINUE_MSG.draw(sb, "Press ENTER to continue",
                    Gdx.graphics.getWidth()/4+20,
                    Gdx.graphics.getHeight()/2-50
            );
        }

        sb.end();



    }

    @Override
    public void dispose() {
        this.bird.getTexture().dispose();
    }
}
