// P6 Assignment
// Author: Sean Russell
// Date:   Oct 9, 2014
// Class:  CS160
// Email:  srussel@rams.colostate.edu

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

		for (int i = 0; i < mazeHeight; i++) {
			if (i % 2 == 0) {

				for (int j = 0; j < mazeWidth - 1; j++) {
					if (maze.checkRight()) {
						maze.moveRight();
					} else {
						maze.moveDown();
						maze.moveRight();
						maze.moveRight();
						maze.moveUp();
						j ++;
					}
					if (maze.isDone()) {
						return;
					}
				}
			} else {
				for (int j = 0; j < mazeWidth - 1; j++) {
					if (maze.checkLeft()) {
						maze.moveLeft();
					} else {
						maze.moveDown();
						maze.moveLeft();
						maze.moveLeft();
						maze.moveUp();
						j ++;
					}
					if (maze.isDone()) {
						return;
					}
				}
			}
			maze.moveDown();
		}

	}

}
