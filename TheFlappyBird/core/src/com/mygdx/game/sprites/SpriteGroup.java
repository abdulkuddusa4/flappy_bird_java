package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.FloatArray;

import java.util.ArrayList;

public class SpriteGroup {
    ArrayList<AbstractBaseSprite> sprites;
    public SpriteGroup(){
        this.sprites = new ArrayList<AbstractBaseSprite>();
    }
    public void add(AbstractBaseSprite sprite){
        this.sprites.add(sprite);
    }

    public void update(float dt){
        boolean flag = false;
        int index = 0;
        for(AbstractBaseSprite sp:this.sprites){
            if(sp.is_active){
                sp.update(dt);
            }
            else{
                flag = true;
                index = 0;
            }
        }
        if(flag){
            this.sprites.remove(index);
        }
    }
    public void render(SpriteBatch sb){

        sb.begin();
        for(AbstractBaseSprite sp:this.sprites){
            sp.draw(sb);
//
        }
        sb.end();
    }

    public boolean collide(AbstractBaseSprite sp){
        for(AbstractBaseSprite sprite:this.sprites){
            int X1=SpriteBatch.X1,X2=SpriteBatch.X2,X3=SpriteBatch.X3,
                    X4=SpriteBatch.X4;
            int Y1=SpriteBatch.Y1,Y2=SpriteBatch.Y2,Y3=SpriteBatch.Y3,
                    Y4=SpriteBatch.Y4;
            float[] vrtces1 = sp.getVertices();
            float[] vrtces2 = sprite.getVertices();

            // CALCULATING MINKOWSKI_DIFFERENCES OF THE TWO RECTANGLE
            float[][] minkowski = {
                    {
                        vrtces1[X1]-vrtces2[X3], vrtces1[Y1]-vrtces2[Y3]
                    },
                    {
                        vrtces1[X2]-vrtces2[X4], vrtces1[Y2]-vrtces2[Y4]
                    },
                    {
                        vrtces1[X3]-vrtces2[X1],vrtces1[Y3]-vrtces2[Y1]
                    },
                    {
                            vrtces1[X4]-vrtces2[X2], vrtces1[Y4]-vrtces2[Y2]
                    }
            };

            if(is_inside(minkowski,0,0)){
                return true;
            }
        }
        return false;
    }

    boolean is_inside(float[][] rectangle,float x,float y){
        int count=0;
        for(int i=0;i<4;i++){
            int point_2 = (i+1)%4;
            float x1=rectangle[i][0],y1=rectangle[i][1];
            float x2=rectangle[point_2][0],y2=rectangle[point_2][1];

            double x0 = x1+(y-y1)/(y2-y1)*(x2-x1);
//            if(i==2){
//                System.out.println("X0: "+x0);
//            }
            if((y1<y != y2<y) && x<x0){
                count+=1;
            }
        }
        return count%2==1;
    }
    public boolean collide(SpriteGroup grp){
        return false;
    }

}
