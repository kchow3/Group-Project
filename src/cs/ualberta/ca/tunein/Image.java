package cs.ualberta.ca.tunein;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Model
 * Image Class:
 * This is a model for the image of a comment and uses a 
 * bitmap to store the image of a comment.
 * This is not yet complete.
 */
public class Image implements Serializable{

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
	
    /**
     * Converts the Bitmap into a byte array for serialization
     * Code from:
     * http://stackoverflow.com/questions/6002800/android-serializable-problem
     * @param out
     * @throws IOException
     */
    private void writeObject(java.io.ObjectOutputStream out) throws IOException {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        bitMap.compress(Bitmap.CompressFormat.PNG, 0, byteStream);
        byte bitmapBytes[] = byteStream.toByteArray();
        out.write(bitmapBytes, 0, bitmapBytes.length);
    }

    /**
     * Deserializes a byte array representing the Bitmap and decodes it
     * Code from:
     * http://stackoverflow.com/questions/6002800/android-serializable-problem
     * @param in
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        int b;
        while((b = in.read()) != -1)
            byteStream.write(b);
        byte bitmapBytes[] = byteStream.toByteArray();
        bitMap = BitmapFactory.decodeByteArray(bitmapBytes, 0, bitmapBytes.length);
    }
	

}
