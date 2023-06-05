package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class MenuState  extends AbstractBaseState {
    Texture bg;
    Sprite playbtn;
    Sprite high_score_button;
    Sprite high_score_button1;
    Sprite high_score_button2;
    int x=0,y=0;
    public MenuState(GameStateManager gsm) {
        super(gsm);

        this.playbtn = new Sprite(
                new Texture("menu/play_btn_1.png")
        );
        this.high_score_button1 = new Sprite(
                new Texture("menu/high_score_button.png")
        );
        this.high_score_button1.setSize(90,60);
        this.high_score_button1.setCenter(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2+80);
//        this.high_score_button1.setCenter(0,0);

        this.high_score_button2 = new Sprite(
                new Texture("menu/high_score_button_1.png")
        );
        this.high_score_button2.setSize(90,60);
        this.high_score_button2.setCenter(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2+80);
        this.high_score_button = this.high_score_button1;


        this.playbtn.setSize(90,60);
        this.playbtn.setCenter(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2);

        this.bg = new Texture(
                "menu/background.png"
        );
    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()){

            int x = Gdx.input.getX();
//            int x=Gdx.input.getAccelerometerX()
            int y = Gdx.graphics.getHeight()-Gdx.input.getY();

            if(x>this.playbtn.getX() &&
                    x<this.playbtn.getX()+this.playbtn.getWidth()&&
                    y>this.playbtn.getY() &&
                    y<this.playbtn.getY()+this.playbtn.getHeight()
            ){
                gsm.push(new GamePlayState(this.gsm));
            }

            else if(x>this.high_score_button.getX() &&
                    x<this.high_score_button.getX()+this.high_score_button.getWidth()&&
                    y>this.high_score_button.getY() &&
                    y<this.high_score_button.getY()+this.high_score_button.getHeight()
            ){
                gsm.push(new HighScoreState(this.gsm, this.bg));
            }

        }

    }

    @Override
    public void update(float dt) {
        this.handleInput();
        int x=Gdx.input.getX(),y=Gdx.graphics.getHeight()-Gdx.input.getY();
        if(x>this.high_score_button.getX() &&
                x<this.high_score_button.getX()+this.high_score_button.getWidth()&&
                y>this.high_score_button.getY() &&
                y<this.high_score_button.getY()+this.high_score_button.getHeight()
        ){
            this.high_score_button=this.high_score_button2;
        }else {
            this.high_score_button=this.high_score_button1;
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
        playbtn.draw(sb);
        this.high_score_button.draw(sb);
        sb.end();
    }

    @Override
    public void dispose() {
        this.bg.dispose();
        this.playbtn.getTexture().dispose();
    }
}
