// P6 Assignment
// Author: Sean Russell
// Date:   Oct 7, 2014
// Class:  CS160
// Email:  slrussel@rams.colostate.edu
public class P6 {

	public static void main(String[] args) {
		// Create maze
		String fileName = args[0];
		Maze maze = new Maze(fileName);
		System.out.println("Maze name: " + fileName);

		// Get dimensions
		int mazeWidth = maze.getWidth();
		int mazeHeight = maze.getHeight();
		System.out.println("Maze width: " + mazeWidth);
		System.out.println("Maze height: " + mazeHeight);
		
		for(int i=1;i<mazeWidth;i++){
			maze.moveRight();
			if(maze.isDone()){
				break;
			}
		}
		
		
	}

}
