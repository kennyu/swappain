package com.me.mygdxgame;

import com.badlogic.gdx.math.Vector2;

public class WorldController {
	
	enum Move {
		UP, DOWN, LEFT, RIGHT
	}
	
	private World world;
	private Board board;
	private int width, height, boardHeight;
	private int rowSize, colSize;
	private Vector2 startTouch, lastTouch;

	public WorldController(World world) {
		this.world = world;
		this.board = world.getBoard();
		
	}
	
	public void setSize(int w, int h){
		// Our game board takes up the whole width in portrait mode
		width = w;
		height = h;
		boardHeight = Math.min( ( width / board.cols ) * board.rows, height );
		
		colSize = width / board.cols;
		rowSize = boardHeight / board.rows;
	}
	
	
	public void update(float delta){
		processInput();
		board.update(delta);
	}
		
	/** Change board based on input controls **/
	private boolean processInput(){
		if (startTouch != null && lastTouch != null){
			if (isOnSameSquare(startTouch, lastTouch)) {
				return true;	
			}
			else {		
				Move direction = direction(startTouch, lastTouch);
				int boardPosX = (int) startTouch.x / colSize;
				int boardPosY =  -(((int) (startTouch.y / rowSize) )-board.rows+1); // hacky way to flip y
				
				switch(direction){
				case UP:
					board.swap(boardPosX, boardPosY, boardPosX, boardPosY + 1 );
					break;
				case DOWN:
					board.swap(boardPosX, boardPosY, boardPosX, boardPosY - 1 );
					break;
				case LEFT:
					board.swap(boardPosX, boardPosY, boardPosX - 1, boardPosY );
					break;
				case RIGHT:
					board.swap(boardPosX, boardPosY, boardPosX + 1, boardPosY );
				}
				touchUp(); // action done, the finger is ignored from then on
			}
		}
			
		return false;
	}
	
	/* Direction of finger based on movement from start to last point */
	private Move direction(Vector2 start, Vector2 last){
		if ( Math.abs(start.x - last.x) >= Math.abs(start.y - last.y)){
			if (start.x - last.x >= 0 )
				return Move.LEFT;
			else
				return Move.RIGHT;
		} else {
			if (start.y - last.y <= 0 )
				return Move.DOWN;
			else
				return Move.UP;			
		}
	}
	
	/* Is last touch coordinate on the same square as first touch */
	private boolean isOnSameSquare(Vector2 touch1 , Vector2 touch2){

		if ( (int) (touch1.x / colSize) == (int)(touch2.x / colSize) && 
				(int) (touch1.y / rowSize) == (int)(touch2.y / rowSize))
			return true;
		return false;
	}
	
	
	// ** Key presses and touches **************** //
	
	public void touchUp(){
		startTouch = null;
		lastTouch = null;
	}
	
	public void touchDragged(int x, int y){
		lastTouch = new Vector2(x,y);
	}
	
	public void touchDown(int x, int y){
		if (y < boardHeight)
			startTouch = new Vector2(x, y);
	}
}