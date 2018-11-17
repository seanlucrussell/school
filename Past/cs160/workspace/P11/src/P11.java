// P11 Assignment
// Author: Sean Russell
// Date:   Nov 22, 2014
// Class:  CS160
// Email:  srussel@rams.colostate.edu

public class P11 implements ImageInterface{
	
	private PictureLibrary pic = null;
	private int width = 0;
	private int height = 0;
	private int[][] data;
	
	public P11(){
		pic = new PictureLibrary();
	}
	
	@Override
	public void readImage(String inFile) {
		try {
			pic.readPGM(inFile);
			width = pic.getWidth();
			height = pic.getHeight();
			data = pic.getData();
		} catch (Exception e){
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void writeImage(String outFile) {
		try {
			pic.setData(data);
			pic.writePGM(outFile);
		} catch (Exception e){
			System.out.println(e.getMessage());
		}
	}

	@Override
	public int[][] imageData() {
		return data;
	}

	@Override
	public void decode() {
		for(int i=0;i<height;i++){
			for(int j=0;j<width;j++){
				if(0<=data[i][j] && data[i][j]<=127){
					data[i][j]+=128;
				} else{
					data[i][j]-=128;
				}
			}
		}
	}

	@Override
	public void swap() {
		for(int i=0;i<height;i++){
			for(int j=0;j<width;j++){
				int u = data[i][j] / 16;
				int l = (data[i][j] % 16) * 16;
				data[i][j] = u + l;
			}
		}
	}

	@Override
	public void mirror() {
		int tempData[][] = new int[height][width];
		for(int i=0;i<height;i++){
			for(int j=0;j<width;j++){
				tempData[i][j] = data[i][j];
			}
		}
		for(int i=0;i<height;i++){
			for(int j=0;j<width;j++){
				data[i][j] = tempData[height-i-1][width-j-1];
			}
		}
	}

	@Override
	public void exchange() {
		for(int i=60;i<120;i++){
			for(int j=100;j<400;j++){
				int temp = data[i][j];
				data[i][j] = data[i+120][j];
				data[i+120][j] = temp;
			}
		}
	}

}
