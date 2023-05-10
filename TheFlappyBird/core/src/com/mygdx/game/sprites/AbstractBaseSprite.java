package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class AbstractBaseSprite extends Sprite {
    boolean is_active=true;
    public AbstractBaseSprite(String img_path){
        super(new Texture(img_path));
    }
    abstract void update(float dt);
    abstract void render(SpriteBatch sb);
    public void func(){
        System.out.println("thsi is abstrafunc fck");
    }
}
