package com.me.mygdxgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.me.mygdxgame.Square.Type;

public class WorldRenderer {
	
	/** Temporary sizes **/
	private static final float CAMERA_WIDTH = 8;
	private static final float CAMERA_HEIGHT = 8;

	private World world;
	private OrthographicCamera cam;

	/** for debug rendering **/
	ShapeRenderer debugRenderer = new ShapeRenderer();

	/** Textures **/ 
	private TextureRegion texWhite;
	private TextureRegion texRed;
	private TextureRegion texPurple;
	private TextureRegion texOrange;
	private TextureRegion texGreen;
	private TextureRegion texYellow;
	private TextureRegion texBlue;
	
	private SpriteBatch spriteBatch;

	private int width;
	private int height;
	private float ppuX; //pixels per unit on the X axis
	private float ppuY; //pixels per unit on the Y axis
	public void setSize( int w, int h){
		this.width = w;
		this.height = h;
		ppuX = (float) width / CAMERA_WIDTH;
		ppuY = (float) height / CAMERA_HEIGHT;
	}

	public WorldRenderer(World world, boolean debug) {
		this.world = world;
		this.cam = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT);
		this.cam.position.set(CAMERA_WIDTH / 2f, CAMERA_HEIGHT / 2f, 0);
		this.cam.update();

		spriteBatch = new SpriteBatch();
		loadTextures();
	}
	
	private void loadTextures(){
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("swapple/texture/swapple.pack"));
    	texWhite = atlas.findRegion("gemWhite");
    	texRed = atlas.findRegion("gemRed");
    	texPurple = atlas.findRegion("gemPurple");
    	texOrange = atlas.findRegion("gemOrange");
    	texGreen = atlas.findRegion("gemGreen");
    	texYellow = atlas.findRegion("gemYellow");
    	texBlue = atlas.findRegion("gemBlue");
    	
    	
    
	}

	public void render() {
		spriteBatch.begin();
			drawSquares();
		spriteBatch.end();
	}

	private void drawSquares(){
		Board board = world.getBoard();
		for (int i = 0 ; i < board.cols ; i++)
			for (int j = 0; j < board.rows ; j++){
				Square square = board.getSquare(i,j);
				if ( getTexture(square.getType()) != null ){
					spriteBatch.draw( getTexture(square.getType()), square.currX * ppuX, square.currY *ppuY,
							square.SIZE*ppuX, square.SIZE*ppuY);
				}
			}
		// TODO: draw swapping pieces again
	}

	private TextureRegion getTexture(Type type){
		switch(type){
		case sqWhite:
			return texWhite;
		case sqRed:
			return texRed;
		case sqPurple:
			return texPurple;
		case sqOrange:
			return texOrange;
		case sqGreen:
			return texGreen;
		case sqYellow:
			return texYellow;
		case sqBlue:
			return texBlue;
		default:
			return null;
		}
	}
	
}

