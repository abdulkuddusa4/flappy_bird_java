package com.mygdx.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.security.PublicKey;

public class Bird extends AbstractBaseSprite {

    double g=-.5;
    double v=0;
    boolean UP=false;
    public boolean not_dead = true;
    public int SCORE;
    BitmapFont SCORE_BOARD;

    Sound flap_music = Gdx.audio.newSound(Gdx.files.internal("gameplay/musics/wing.mp3"));
    public Bird(String img_path,int x, int y){
        super(img_path);
        this.setSize(30,30);
        this.setCenter(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2);
        this.SCORE_BOARD=new BitmapFont();
        this.SCORE_BOARD.getData().setScale(2f);
        this.SCORE_BOARD.setColor(0.0f,0.0f,0.0f,1.0f);

    }

    public void handleInput(){
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            if(Gdx.graphics.getHeight()>this.getVertices()[SpriteBatch.Y1]){
                this.jump();
            }
        }
    }
    public void update(float dt){
        this.handleInput();
        this.v+=g;

        //CALCULATING SCORE




        //END
        if(this.v<0 && this.UP){
            this.setTexture(new Texture("gameplay/flappy1.png"));
            this.UP=false;
        }
        else if(this.v>0 && ! this.UP){
            this.setTexture(new Texture("gameplay/flappy2.png"));
            this.UP=true;
        }

        // HANDLING GROUND COLISION
        float[] coords = this.getVertices();
        if(coords[SpriteBatch.Y1]<0){
            this.v=0;
            this.die();
        }
        // END HANDLING GROUND COLLISION




        this.setY(this.getY()+(float) this.v);
    }
    public void render(SpriteBatch sb){
        sb.begin();
        this.draw(sb);
        sb.end();
    }

    public void jump(){
        if(not_dead && this.getVertices()[SpriteBatch.Y2]<Gdx.graphics.getHeight()){
            this.flap_music.play(1f);

            this.v=7;
        }
    }

    public void die(){
        if (! not_dead)
            return;
        this.not_dead = false;
        this.v=0;
        Gdx.audio.newSound(
                Gdx.files.internal("gameplay/musics/hit.mp3")
        ).play();
    }

}
