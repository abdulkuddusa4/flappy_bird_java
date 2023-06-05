package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class AbstractBaseSprite extends Sprite {
    public boolean is_active=true;
    public boolean score_not_calculated = true;
    public AbstractBaseSprite(String img_path){
        super(new Texture(img_path));
    }
    abstract void update(float dt);
    abstract void render(SpriteBatch sb);
}
