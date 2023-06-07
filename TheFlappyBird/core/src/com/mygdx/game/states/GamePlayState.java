package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.sprites.AbstractBaseSprite;
import com.mygdx.game.sprites.Bird;
import com.mygdx.game.sprites.Pipe;
import com.mygdx.game.sprites.SpriteGroup;

import java.util.concurrent.ThreadLocalRandom;

public class GamePlayState extends AbstractBaseState {
    Bird bird;
    Texture bg;

    SpriteGroup pipe_group;
    int var=0;
    int ctn=0;

    int time;

    BitmapFont SCORE_BOARD;
    Sound theme_song = Gdx.audio.newSound(Gdx.files.internal("gameplay/musics/theme_song.mp3"));

    public GamePlayState(GameStateManager gsm) {
        super(gsm);
        this.bg = new Texture("gameplay/background.png");
        this.bird = new Bird("gameplay/flappy1.png",200,Gdx.graphics.getHeight()-200);
        this.pipe_group = new SpriteGroup();
        this.SCORE_BOARD=new BitmapFont();
        this.SCORE_BOARD.getData().setScale(2f);
        this.SCORE_BOARD.setColor(0.0f,0.0f,0.0f,1.0f);
        this.theme_song.loop(1f);
//        this.pipe_group.add(new Pipe("gameplay/pipe.png", false, 70,80));

    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()){
//            this.gsm.pop();
            System.out.println(Gdx.input.getX());
            System.out.println(Gdx.input.getY());
        }
    }

    @Override
    public void update(float dt) {

        // ADDING NEW PIPE TO THE GAME
//        int n = ThreadLocalRandom.current().nextInt(100 , 150);
        int n = ThreadLocalRandom.current().nextInt(50 , 140);
        int shiftY = ThreadLocalRandom.current().nextInt(-115 , 115);
        int pipe_gap = 65;
        if(++var>n){
            var=0;
            this.pipe_group.add(new Pipe("gameplay/pipe.png", false, 70,shiftY));
            this.pipe_group.add(new Pipe("gameplay/pipe.png", true, 70, shiftY));
        }

        // CALCULATE SCORE FOR BIRD

        for(AbstractBaseSprite pipe:this.pipe_group.sprites){
            if(
                    this.bird.not_dead
                            && pipe.isFlipY()
                            && pipe.score_not_calculated
                            && this.bird.getVertices()[SpriteBatch.X1]>pipe.getVertices()[SpriteBatch.X1]
                            && this.bird.getVertices()[SpriteBatch.X4]<pipe.getVertices()[SpriteBatch.X4]
            ){
                this.bird.SCORE+=1;
                pipe.score_not_calculated=false;
            }

        }

        //END

        // COLLISION DETECTION
        if (pipe_group.collide(this.bird)){
            this.bird.die();
        }
        if(! this.bird.not_dead){

            this.gsm.set(new GameOverState(
                    gsm,
                    this
            ));
        }else{
            this.handleInput();
            this.bird.update(dt);
            this.pipe_group.update(dt);
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        this.ctn-=5;
        sb.begin();
        if (this.ctn==-Gdx.graphics.getWidth()){
            this.ctn=0;
        }

        sb.draw(
                this.bg,
                this.ctn,
                0,
                Gdx.graphics.getWidth(),
                Gdx.graphics.getHeight()
        );

        sb.draw(
                this.bg,
                Gdx.graphics.getWidth()+ctn+1,//+(this.ctn--)-100,
                5,
                Gdx.graphics.getWidth(),
                Gdx.graphics.getHeight()
        );
        this.SCORE_BOARD.draw(sb,"Score: "+this.bird.SCORE,100,600);
        sb.end();


        this.pipe_group.render(sb);
        this.bird.render(sb);
    }

    @Override
    public void dispose() {
        this.theme_song.dispose();
    }
}
