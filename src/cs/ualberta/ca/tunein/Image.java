package cs.ualberta.ca.tunein;

import android.graphics.Bitmap;

/**
 * Model
 * Image Class:
 * This is a model for the image of a comment and uses a 
 * bitmap to store the image of a comment.
 * This is not yet complete.
 */
public class Image {

	private Bitmap bitMap;
	
	/**
	 * Constructor constructs a image that takes in a bitmap.
	 * @param bmp Bitmap of the image that a comment contains.
	 */
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
