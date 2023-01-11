package snake_ultimate;

public class PlayerInGame {

	public static void main(String[] args) {
		boolean gameInProgress = true;
		//thread method for sending movement
		//thread method for updating graphic
		while(gameInProgress) {
			
			
			
		}

	}
	
	class sendInput implements Runnable {
		char lastInput;
		char newInput;
		public sendInput() {
			lastInput = ' ';
		}
		
		public void run() {
			//send nothing
			
			while(true) {
				//newInput = det du holder inde nu, hvis ikke a eller d: så ' '
				if(lastInput == ' ') {
					if(newInput == 'a') {
						//put.channel('a')
					}
					else if(newInput == 'd') {
						//put.channel('d')
					}
					else {
						//nothing
					}
				}
				else if(lastInput == 'a') {
					if(newInput == 'a') {
						//nothing
					}
					else if(newInput == 'd') {
						//put.channel('d')
						//get.channel('a')
					}
					else {
						//get.channel('a')
					}
				}
				else { //lastInput == 'd'
					if(newInput == 'a') {
						//put.channel('a')
						//get.channel('d')
					}
					else if(newInput == 'd') {
						//nothing
					}
					else {
						//get.channel('d')
					}
				}
			}
			
			
		}
	}

	class DrawUpdate implements Runnable {
		int formerPosX[];
		int formerPosY[];
		int numPlayers;

	    public DrawUpdate(int numPlayers) {
	    	numPlayers = numPlayers;
	    	
	    	formerPosX = new int[numPlayers];
	    	formerPosY = new int[numPlayers];
	    	
	    	for(int i = 0; i < numPlayers; i++) {
	  //  		formerPosX[i] = startPosX[i];
	  //   		formerPosY[i] = startPosY[i];
	    	}
	    	
	    	//draw Start Pos
	    	
	    }

	    public void run() {
	    	int playerInfo[] = new int[numPlayers];
	    	while(true) {
	    		for(int i = 0; i < numPlayers; i++){
	    			//get from sequential Space of player i which is pushed from host in that order
	    			//playerInfo[i] = get.nextTuple(int);
	    		}
	    		//draw lines from formerPos to newPos for all players
	    		//delete and update circles to newPos
	    		//formerPos = newPos
	    	}

	       }

	   }



}
