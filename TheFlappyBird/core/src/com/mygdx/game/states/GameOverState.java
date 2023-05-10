package com.mygdx.game.states;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.sprites.Bird;
import com.mygdx.game.sprites.SpriteGroup;

public class GameOverState extends AbstractBaseState{
    Bird bird;
    
    SpriteGroup pipe_group;

    Texture bg=new Texture("gameplay/background.png");
    protected GameOverState(GameStateManager gsm, Bird brd, SpriteGroup pipe_group) {
        super(gsm);
        this.bird=brd;
        this.pipe_group=pipe_group;

    }

    @Override
    public void handleInput() {

    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void render(SpriteBatch sb) {
        this.pipe_group.render(sb);
        this.bird.render(sb);
    }

    @Override
    public void dispose() {
        this.bird.getTexture().dispose();
    }
}
