package cs.ualberta.ca.tunein;

import android.graphics.Bitmap;

public class Image {

	private Bitmap bitMap;
	
	public Image(Bitmap bmp) 
	{
		this.bitMap = bmp;
	}

	public Bitmap getBitMap() {
		return bitMap;
	}

	public void setBitMap(Bitmap bitMap) {
		this.bitMap = bitMap;
	}
	

}
