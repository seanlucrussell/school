// P12 Assignment
// Author: Sean Russell
// Date:   Dec 1, 2014
// Class:  CS160
// Email:  srussel@rams.colostate.edu

import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

public class P12 {

	private static int notes = 13;
	private static GuitarString[] gString = new GuitarString[notes];
	private static String[] nString = { "C", "C#", "D", "D#", "E", "F", "F#",
			"G", "G#", "A", "A#", "B", "H" };

	public static void main(String[] args) {

		// Instantiate object
		P12 p12 = new P12();

		for (int i = 0; i < notes; i++) {
			double frequency = 440.0 * Math.pow(1.05956, i - 9);
			P12.gString[i] = new GuitarString(frequency);
		}

		// Play keyboard
		p12.playKeyboard(args[1]);

		// Play file
		if (args.length >= 1)
			p12.playFile(args[0]);
	}

	// Play from keyboard
	public void playKeyboard(String output) {

		System.out.println("Press keys to play music, 'X' exits.");

		String keyboard = "Q2W3ER5T6Y7UI";

		try {
			PrintWriter songWriter = new PrintWriter(new File(output));

			// Processing loop
			while (true) {

				// Has user has typed a key?
				if (StdDraw.hasNextKeyTyped()) {

					// Which key has been typed
					char key = Character.toUpperCase(StdDraw.nextKeyTyped());
					System.out.println("Key pressed: " + key);
					if (key == 'X')
						break;

					int index = -1;
					if (keyboard.indexOf(key) != -1) {
						index = keyboard.indexOf(key);
					}
					// Pluck string
					if (index != -1) {
						songWriter.println(nString[index]);
						pluckGuitar(nString[index]);
					}
				}

				// Play guitar
				playGuitar();
			}

			songWriter.close();
		} catch (Exception e) {
			System.out.println("Holla atcha");
		}

	}

	// Play from file
	public void playFile(String filename) {
		try {
			Scanner music = new Scanner(new File(filename));
			while (music.hasNext()) {
				pluckGuitar(music.next());
				for (int i = 0; i < 11025; i++) {
					playGuitar();
				}
			}
			music.close();
		} catch (Exception e) {
			System.out.println("Ya done goof'd");
		}
	}

	// Pluck guitar strings
	public void pluckGuitar(String note) {
		if (note.equals("C"))
			gString[0].pluck();
		else if (note.equals("C#"))
			gString[1].pluck();
		else if (note.equals("D"))
			gString[2].pluck();
		else if (note.equals("D#"))
			gString[3].pluck();
		else if (note.equals("E"))
			gString[4].pluck();
		else if (note.equals("F"))
			gString[5].pluck();
		else if (note.equals("F#"))
			gString[6].pluck();
		else if (note.equals("G"))
			gString[7].pluck();
		else if (note.equals("G#"))
			gString[8].pluck();
		else if (note.equals("A"))
			gString[9].pluck();
		else if (note.equals("A#"))
			gString[10].pluck();
		else if (note.equals("B"))
			gString[11].pluck();
		else if (note.equals("H"))
			gString[12].pluck();
	}

	// Play guitar strings
	public void playGuitar() {

		double sample = 0.0;

		// Combine the samples
		for (int i = 0; i < notes; i++) {
			sample += gString[i].sample();
		}

		// Send sample to standard audio
		StdAudio.play(sample);

		// Advance the guitar simulation
		for (int i = 0; i < notes; i++) {
			gString[i].tic();
		}
	}
}