package com.mygdx.game.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Stack;

public class GameStateManager {
    private Stack<AbstractBaseState> states;

    public GameStateManager(){
        states = new Stack<AbstractBaseState>();
    }

    public void push(AbstractBaseState state){
        states.push(state);
    }

    public void pop(){
        this.states.pop();
    }

    public void set(AbstractBaseState state){
        states.pop();
        states.push(state);
    }

    public void update(float dt){

        this.states.peek().update(dt);
    }

    public void render(SpriteBatch batch){
        states.peek().render(batch);
    }
    public void func(){

    }

}
