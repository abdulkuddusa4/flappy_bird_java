package com.mygdx.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Pipe extends AbstractBaseSprite {
    public Pipe(String img_path, boolean flip, int gap, int shiftY){
        super(img_path);
        this.setSize(50,500);
        if (flip){
            this.flip(false,true);
            this.setPosition(1200, Gdx.graphics.getHeight()/2+gap+shiftY);
        }
        else{
            this.setPosition(1200, Gdx.graphics.getHeight()/2-this.getHeight()-gap+shiftY);
        }
    }

    public void update(float dt){

        this.setX(this.getX()-400*dt);
        if(this.getX()<=-100){
            this.is_active=false;
        }
//        this.setX(this.getX()-1);
    }
    public void render(SpriteBatch sb){

    }
}
