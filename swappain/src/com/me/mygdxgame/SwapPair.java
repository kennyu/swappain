package com.me.mygdxgame;

public class SwapPair{
	
	Square first, second;
	boolean checked;
	
	public SwapPair(Square first, Square second){
		checked = false;
		this.first = first;
		this.second = second;

	}
	
	/** Swaps the squares back if they are not used for a match **/
	public void updatePair(){
			if (first.isIdle() && second.isIdle()) {
				checked = true;	// Used by board if this swap pair was already checked
				if (! (first.isEmpty() || second.isEmpty()) ){
					first.swapWith(second);
				}

			}
	}
	
	public boolean isChecked(){
		return checked;
	}
	
}