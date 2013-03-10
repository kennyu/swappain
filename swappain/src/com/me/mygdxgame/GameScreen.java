package com.me.mygdxgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class GameScreen implements Screen, InputProcessor {


	private World 			world;		// Contains the board and score panels
	private WorldRenderer 	renderer;
	private WorldController controller;
	
	private static int none = -1;
	private int currTouchPtr = none; // The touch ptr we are tracking

	private int width, height;

	@Override
	public void show() {
		world = new World();
		renderer = new WorldRenderer(world, false);
		controller = new WorldController(world);
        Gdx.input.setInputProcessor(this);

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		controller.update(delta);
		renderer.render();
	}



	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
        Gdx.input.setInputProcessor(null);
	}


	@Override
	public void resize(int width, int height) {
		this.width = width;
		this.height = height;
		controller.setSize(width, height);
		renderer.setSize(width, height);
	}
	

	@Override
	public void hide() {
        Gdx.input.setInputProcessor(null);
	}
	// * InputProcessor methods ********************************//
	@Override
	public boolean keyDown(int keycode){
		return false;
	}
	
	@Override
	public boolean keyUp(int keycode){
		return false;
	}
	
	@Override
	public boolean keyTyped(char character){
		return false;
	}
	
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button){
		if (currTouchPtr == none){
			currTouchPtr = pointer;
			controller.touchDown(screenX, screenY);
		}
		return true;
	}
	
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button){
		if (currTouchPtr == pointer){
			currTouchPtr = none;
			controller.touchUp();
		}
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		if (currTouchPtr == pointer){
			controller.touchDragged(screenX, screenY);
		}
		return true;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
}
