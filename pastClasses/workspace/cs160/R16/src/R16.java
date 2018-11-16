// R16 Assignment
// Author: Sean Russell
// Date:   Nov 20, 2014
// Class:  CS160
// Email:  slrussel@rams.colostate.edu

public class R16 implements RecitationInterface{
	
	private PictureLibrary pics = null;
	private int imageWidth = 0;
	private int imageHeight = 0;
	private int[][] imageData;
	
	public R16(){
		pics = new PictureLibrary();
	}

	@Override
	public void readImage(String inFile) {
		try {
			pics.readPGM(inFile);
			imageHeight = pics.getHeight();
			imageWidth = pics.getWidth();
			imageData = pics.getData();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void writeImage(String outFile) {
		try {
			pics.setData(imageData);
			pics.writePGM(outFile);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public int[][] imageData() {
		return imageData;
	}

	@Override
	public void negateImage() {
		for(int i=0;i<imageHeight;i++){
			for(int j=0;j<imageWidth;j++){
				imageData[i][j] = PictureLibrary.MAXVAL - imageData[i][j];
			}
		}
	}

	@Override
	public void increaseContrast() {
		for(int i=0;i<imageHeight;i++){
			for(int j=0;j<imageWidth;j++){
				if(0 <= imageData[i][j] && imageData[i][j] <= 127){
					if(imageData[i][j]-16<0)
						imageData[i][j] = 0;
					else
						imageData[i][j] -= 16;
				}
				if(128 <= imageData[i][j] && imageData[i][j] <= PictureLibrary.MAXVAL){
					if(imageData[i][j]+16>PictureLibrary.MAXVAL)
						imageData[i][j] = PictureLibrary.MAXVAL;
					else
						imageData[i][j] += 16;
				}
				
			}
		}
	}

	@Override
	public void decreaseContrast() {
		for(int i=0;i<imageHeight;i++){
			for(int j=0;j<imageWidth;j++){
				if(0 <= imageData[i][j] && imageData[i][j] <= 127){
					imageData[i][j] += 16;
				}
				if(128 <= imageData[i][j] && imageData[i][j] <= PictureLibrary.MAXVAL){
					imageData[i][j] -= 16;
				}
				
			}
		}
	}

	
	
	
	
}