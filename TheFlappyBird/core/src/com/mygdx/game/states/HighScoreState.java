package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.custom_db.MyStorage;

public class HighScoreState extends AbstractBaseState{
    Texture bg,dark_bg;
    BitmapFont high_score_title;
    BitmapFont high_score_index;
    BitmapFont HIGH_SCORE_LIST;
    BitmapFont ESCAPE_MSG;
    MyStorage database_connection;

//    static int PERFECT_STRING_WIDTH=10;
    static int PERFECT_STRING_WIDTH=50;

    float ANIMATION_VAR = 0.0f;
    protected HighScoreState(GameStateManager gsm, Texture bg) {
        super(gsm);
        this.bg=bg;
        this.dark_bg = new Texture("highscore/darkbg.png");
        this.high_score_title = new BitmapFont();
        this.high_score_title.getData().setScale(3.0f);
        this.high_score_title.setColor(1,0,1,1.0f);

        this.high_score_index = new BitmapFont();
        this.high_score_index.getData().setScale(1.0f);
        this.high_score_index.setColor(1,0,1,1.0f);

        this.HIGH_SCORE_LIST = new BitmapFont();
        this.HIGH_SCORE_LIST.getData().setScale(1.0f);
        this.HIGH_SCORE_LIST.setColor(1,1,1,1.0f);

        this.ESCAPE_MSG = new BitmapFont();
        this.ESCAPE_MSG.getData().setScale(1.0f);
        this.ESCAPE_MSG.setColor(1,1,1,1.0f);



        this.database_connection = new MyStorage();


    }

    @Override
    public void handleInput() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            System.out.println("Pressed");
            this.gsm.pop();
        }
    }

    @Override
    public void update(float dt) {
        this.handleInput();
        if(this.ANIMATION_VAR<1){
            ANIMATION_VAR+=.05;
        }
        else ANIMATION_VAR=0;
        this.ESCAPE_MSG.setColor(ANIMATION_VAR,1-ANIMATION_VAR,1-ANIMATION_VAR,1.0f);
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
        sb.draw(
                this.dark_bg,
                100,
                0,
                Gdx.graphics.getWidth()-100*2,
                Gdx.graphics.getHeight()
        );
        this.high_score_title.draw(sb,"High Scores",100+250,Gdx.graphics.getHeight()-20);
        this.ESCAPE_MSG.draw(sb,"Press ESC to Menu",100+10,Gdx.graphics.getHeight()-20);
        this.high_score_index.draw(
                sb,
                HighScoreState.getPerfectString("Player's Name")+"High Score",
                100+150,
                Gdx.graphics.getHeight()-80

        );
        this.HIGH_SCORE_LIST.draw(
                sb,
                this.database_connection.GET_PERFECT_RECORDS(),
                100+150,
                Gdx.graphics.getHeight()-180
        );
        sb.end();
    }

    @Override
    public void dispose() {
        this.bg.dispose();
    }

    public static String getPerfectString(String st, String fill_st, String last_st) {
        if(st.length()>=HighScoreState.PERFECT_STRING_WIDTH)
            return st;
        for(int i=st.length();i<HighScoreState.PERFECT_STRING_WIDTH;i++){
            st+=fill_st;
        }
        return st+last_st;
    }
    public static String getPerfectString(String st){
        return HighScoreState.getPerfectString(st, "  ", "  ");
    }
}
