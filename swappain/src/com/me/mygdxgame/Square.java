package com.me.mygdxgame;

import com.badlogic.gdx.math.MathUtils;

public class Square  {
	public enum Type {sqEmpty,
					  sqWhite,
					  sqRed,
					  sqPurple,
					  sqOrange,
					  sqGreen,
					  sqYellow,
					  sqBlue};
					  
	public enum State { idle,
						falling,
						swapping
	}
	
	/* Constants */
	public static float dropVel = 4.0f ; // Should be in board class
	public static float swapVel = 3.5f ;
	public static float SIZE = 1.0f; 
	
	/* Positions */
	public float currY, currX;
	public int destY, destX;
	
	/* Attributes */
	public State state;
	private Type type;
	
	public Square( int destX, int destY) {
		setRandomType();
		this.destX = destX;
		this.destY = destY;		
		
		currX = destX;
		currY = destY;
	//	state = State.falling;
		state = State.idle;
	}
	
	public void newType(float currY){
		setRandomType();
		this.currY = currY;
		
		state = State.falling;
	}

	private void setRandomType(){
		this.type =  numToType(MathUtils.random(1,7)); 
	}
	
	
	public Type getType() {
		return type;
	}
	
	public boolean equals(Square other) {
		if ( !this.isIdle() || !other.isIdle() || this.isEmpty() || other.isEmpty())
			return false;
		else
			return other.type == type;
	}
	
	public boolean isMoving(){
		return ! (state == State.idle);
	}
	
	public void setEmpty(){
		type = Type.sqEmpty;
		state = State.idle;
	}
	
	public boolean isEmpty(){
		return type == Type.sqEmpty;
	}
	
	public boolean isSwapping(){
		return state == State.swapping;
	}
	
	public void newSquare(float currY){
		setRandomType();
		this.currY = currY;
		this.state = State.falling;
	}
	
	public boolean isFalling(){
		return state == State.falling;
	}

	public void swapWith(Square sq){
//		System.out.println("Original - X From "+currX+" to "+destX);
//		System.out.println("Original - Y From "+currY+" to "+destY);
		
		Type tempType = this.type;
		
		this.currX = sq.destX;
		this.currY = sq.destY;
		this.type  = sq.type;
		this.state = State.swapping;
		
		sq.currX = this.destX;
		sq.currY = this.destY;
		sq.type  = tempType;
		sq.state = State.swapping;
		
	}
	
	public void dropWith(Square sq){
		this.type = sq.type;
		this.currY = sq.currY;
		this.state = State.falling;
		
		sq.setEmpty();
	}

	public boolean isIdle(){
		return state == State.idle;
	}
	
	public boolean update(float delta){
		switch(state){
		case idle:
			break;
		case falling:
			currY -= dropVel*delta;
			if (destY >= currY ){
				currY = destY;				
				state = State.idle;
			}
			break;
		case swapping:
//			System.out.println("Before: currX "+currX+" currY "+currY);
			if (currY != destY){
				if (currY > destY){
					currY -= swapVel*delta;
					if (currY <= destY){
						currY = destY;
					}
				} else {
					currY += swapVel*delta;
					if (currY >= destY){
						currY = destY;
					}
				}
			}
			if (currX != destX){
				if (currX > destX){
					currX -= swapVel*delta;
					if (currX <= destX){
						currX = destX;
					}
				} else {
					currX += swapVel*delta;
					if (currX >= destX){
						currX = destX;
					}
				}				
			}
//			System.out.println("After: currX "+currX+" currY "+currY);
			if (currX == destX && currY == destY)
				state = State.idle;
			break;
		default:
			break;
		}
		return true;
	}
	
	public static Type numToType(int num) {
		switch (num) {
		case 1:
			return Type.sqWhite;
		case 2:
			return Type.sqRed;
		case 3:
			return Type.sqPurple;
		case 4:
			return Type.sqOrange;
		case 5:
			return Type.sqGreen;
		case 6:
			return Type.sqYellow;
		case 7:
			return Type.sqBlue;
		default:
			return Type.sqEmpty;
		}
	}
}
