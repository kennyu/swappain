package com.me.mygdxgame;

public class World {

	Board board;
	int score;
	
	public Board getBoard() {
		return board;
	}
	public World() {
		board = new Board(8,8);
		score = 0;
	}

}
