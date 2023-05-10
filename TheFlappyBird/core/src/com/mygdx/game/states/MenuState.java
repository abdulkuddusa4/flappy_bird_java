package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class MenuState  extends AbstractBaseState {
    Texture bg;
    Sprite playbtn;
    int playbtn_size[] = {150,70}; // width, height
    int x=0,y=0;
    public MenuState(GameStateManager gsm) {
        super(gsm);

        this.playbtn = new Sprite(
                new Texture("menu/play_btn_1.png")
        );
        this.playbtn.setSize(80,60);
        this.playbtn.setCenter(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2);

        this.bg = new Texture(
                "menu/background.png"
        );

    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()){

            int x = Gdx.input.getX();
            int y = Gdx.input.getY();

            if(x>this.playbtn.getX() &&
                    x<this.playbtn.getX()+this.playbtn.getWidth()&&
                    y>this.playbtn.getY() &&
                    y<this.playbtn.getY()+this.playbtn.getHeight()
            ){
                gsm.push(new GamePlayState(this.gsm));
            }
        }
    }

    @Override
    public void update(float dt) {
        this.handleInput();

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
//        playbtn.setSize(playbtn_size[0],playbtn_size[1]);
        playbtn.draw(sb);
//        playbtn.get
//        playbtn.setCenter(100,0);
//        sb.draw(
//                playbtn,0,0,
////                Gdx.graphics.getWidth()/2-this.playbtn_size[0]/2,
////                Gdx.graphics.getHeight()/2,
//                this.playbtn_size[0],
//                this.playbtn_size[1]
//        );
        sb.end();
    }

    @Override
    public void dispose() {
        this.bg.dispose();
        this.playbtn.getTexture().dispose();
    }
}
