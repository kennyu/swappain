package com.me.mygdxgame;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/* Board is contains a grid of squares 
 * Squares hold the color, values, x, y values
 * The board handles creating, swapping, match finding, and clearing
 */
public class Board {
	private Square[][] squares;
	public int rows, cols;
	
	// Matches on the board
	private Array<Square> matches;
	
	// Swaps currently on the board
	private Array<SwapPair> swaps;
	
	public Board(int rows, int cols) {
		squares = new Square[rows][cols];
		matches = new Array<Square>();
		swaps = new Array<SwapPair>();
		this.rows = rows;
		this.cols = cols;
		
		generate();
	}
	
	public Square getSquare(int i, int j){
		return squares[i][j];
	}
	
	public void generate() {
		boolean repeat = false;
		
		do {
			repeat = false;
			System.out.println("### Generating...");
			
			// sets squares to piece type
			for (int i = 0; i < cols; i++) {
				for (int j = 0; j < rows; j++) {
					squares[i][j] = new Square(i,j);
				}
			}
			
			if (check().size != 0) {
				System.out.println("Generated board has matches, repeating...");
				repeat = true;
			}
//			
//			else if (solutions().size == 0) {
//				System.out.println("Generated board doesn't have solutions, repeating...");
//				repeat = true;
//			}
		} while(repeat);
		
		System.out.println("The generated board has no matches but some possible solutions.");
	}
	

	public void emptySquares(Array<Square> matches){
		for (int i=0; i < matches.size  ; i++){
			Square s = matches.get(i);
			if (!s.isEmpty()){
				System.out.println(s.getType()+" "+s.destX+" "+ s.destY); // Squares being emptied
				s.setEmpty();
			}
		}
		matches.clear();	// Clearing the list
	}
	
	
	public Array<Square> check() {
		
		// Empty this array, turn into pool later
	    matches.clear();
	    
	    // First, we check each row (horizontal)
	    for (int y = 0; y < rows; y++) {
	    	for (int x = 0; x < cols-2; x++){
	    		// Equality checks type equality only if state is idle and not empty
	    		if (squares[x][y].equals(squares[x+1][y]) && squares[x][y].equals(squares[x+2][y])){
	    			matches.add(squares[x][y]);
	    			matches.add(squares[x+1][y]);
	    			matches.add(squares[x+2][y]);
	    		}
	        }   
	    }
	    // check column
	    for (int x = 0; x < cols; x++) {
	    	for (int y = 0; y < rows-2; y++){
	    		if (squares[x][y].equals(squares[x][y+1]) && squares[x][y].equals(squares[x][y+2])){
	    			matches.add(squares[x][y]);
	    			matches.add(squares[x][y+1]);
	    			matches.add(squares[x][y+2]);
	    		}
	        }   
	    }
	    
	    return matches;
	}
	
	/* Drops the pieces downwards */
	public void applyFall() {
		for (int x = 0; x < cols; x++) {
			float maxCurrY = rows;	// If squares in same col need filling, we use this as currY positioning
			for (int y = 0; y < rows ; y++ ) {
				if (squares[x][y].isEmpty()){
					for (int k = y+1; k < rows; k++){
													
						if (squares[x][k].isEmpty())
							continue;
						if (squares[x][y].isSwapping())
							break;
						else {
							squares[x][y].dropWith(squares[x][k]);
							break;
						}
					}
					if (squares[x][y].isEmpty()){	// Empty all the way to the top
						squares[x][y].newSquare(maxCurrY);
						maxCurrY += 1.0;
					}
				}   

			}
		}
	}
	
	
	public void swap(int x1, int y1, int x2, int y2){
		Square s1 = squares[x1][y1];
		Square s2 = squares[x2][y2];
		if ( s1.isIdle() && !s1.isEmpty() && s2.isIdle() && !s2.isEmpty()){
			System.out.println("From "+x1+" "+y1+" To "+x2+" "+y2);
			s1.swapWith(s2);
			swaps.add(new SwapPair(s1,s2));  // Used in update() to swap squares back
			
		}
	}
	
	public void update(float delta){
		emptySquares(check());
		
		for (int i = 0; i < swaps.size ; i++){
			SwapPair p = swaps.get(i);
			if (p.isChecked())
				swaps.removeIndex(i);
			else
				p.updatePair();
		}
		
		applyFall();
		
		for (int x = 0 ; x < cols ; x++){
			for (int y = 0; y < rows; y++){
				squares[x][y].update(delta);
			}
		}
	}
	
}
