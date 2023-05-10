package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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

    public GamePlayState(GameStateManager gsm) {
        super(gsm);
        this.bg = new Texture("gameplay/background.png");
        this.bird = new Bird("gameplay/flappy1.png",200,Gdx.graphics.getHeight()-200);
        this.pipe_group = new SpriteGroup();
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
        int n = ThreadLocalRandom.current().nextInt(40 , 150);
        int shiftY = ThreadLocalRandom.current().nextInt(-90 , 90);
        int pipe_gap = 65;
        if(++var>n){
            var=0;
            this.pipe_group.add(new Pipe("gameplay/pipe.png", false, 70,shiftY));
            this.pipe_group.add(new Pipe("gameplay/pipe.png", true, 70, shiftY));
        }

        // COLLISION DETECTION
        if (pipe_group.collide(this.bird)){
            this.bird.die();
        }
        if(! this.bird.not_dead){
            this.gsm.set(new GameOverState(
                    gsm,
                    bird,
                    pipe_group
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

        sb.end();


        this.pipe_group.render(sb);
        this.bird.render(sb);
    }

    @Override
    public void dispose() {

    }
}
