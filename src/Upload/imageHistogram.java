package Upload;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import com.sun.org.apache.xml.internal.utils.URI;

import stores.UploadStore;

public class imageHistogram {

	UploadStore us = new UploadStore();
	BufferedImage bImage = null;
	double imageSize;

	/*
	 * Bucket Ranges: 1 = 0-63 2 = 64-127 3 = 128-191 4 = 192-255
	 */

	// Red buckets
	double rBucket1 = 0;
	double rBucket2 = 0;
	double rBucket3 = 0;
	double rBucket4 = 0;

	// Normalised buckets
	double normRB1 = 0;
	double normRB2 = 0;
	double normRB3 = 0;
	double normRB4 = 0;

	// Green buckets
	double gBucket1 = 0;
	double gBucket2 = 0;
	double gBucket3 = 0;
	double gBucket4 = 0;

	// Normalised buckets
	double normGB1 = 0;
	double normGB2 = 0;
	double normGB3 = 0;
	double normGB4 = 0;

	// Blue buckets
	double bBucket1 = 0;
	double bBucket2 = 0;
	double bBucket3 = 0;
	double bBucket4 = 0;

	// Normalised buckets
	double normBB1 = 0;
	double normBB2 = 0;
	double normBB3 = 0;
	double normBB4 = 0;

	public void readImage(String image) {

		// Checks if input is from local file or URL
		boolean check = checkInput(image);

		if (check) {
			try {
				URL is = new URL(image);
				bImage = ImageIO.read(is);
				imageSize = bImage.getHeight() * bImage.getWidth();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			try {
				File is = new File(image);
				bImage = ImageIO.read(is);
				imageSize = bImage.getHeight() * bImage.getWidth();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// Reset buckets for new images
		rBucket1 = 0;
		rBucket2 = 0;
		rBucket3 = 0;
		rBucket4 = 0;

		normRB1 = 0;
		normRB2 = 0;
		normRB3 = 0;
		normRB4 = 0;

		gBucket1 = 0;
		gBucket2 = 0;
		gBucket3 = 0;
		gBucket4 = 0;

		normGB1 = 0;
		normGB2 = 0;
		normGB3 = 0;
		normGB4 = 0;

		bBucket1 = 0;
		bBucket2 = 0;
		bBucket3 = 0;
		bBucket4 = 0;

		normBB1 = 0;
		normBB2 = 0;
		normBB3 = 0;
		normBB4 = 0;

		// Loops through each pixel to get RGB data
		for (int x = 0; x < bImage.getWidth(); x++) {
			for (int y = 0; y < bImage.getHeight(); y++) {
				int clr = bImage.getRGB(x, y);
				int red = (clr & 0x00ff0000) >> 16;
				int green = (clr & 0x0000ff00) >> 8;
				int blue = clr & 0x000000ff;
				redHistogram(red);
				greenHistogram(green);
				blueHistogram(blue);
			}
		}

		normRB1 = rBucket1 / imageSize;
		normRB2 = rBucket2 / imageSize;
		normRB3 = rBucket3 / imageSize;
		normRB4 = rBucket4 / imageSize;

		normGB1 = gBucket1 / imageSize;
		normGB2 = gBucket2 / imageSize;
		normGB3 = gBucket3 / imageSize;
		normGB4 = gBucket4 / imageSize;

		normBB1 = bBucket1 / imageSize;
		normBB2 = bBucket2 / imageSize;
		normBB3 = bBucket3 / imageSize;
		normBB4 = bBucket4 / imageSize;

	}

	public void redHistogram(int pixel) {

		if (pixel <= 63) {
			rBucket1 = rBucket1 + 1;
		} else if (pixel > 63 && pixel < 128) {
			rBucket2 = rBucket2 + 1;
		} else if (pixel > 127 && pixel < 192) {
			rBucket3 = rBucket3 + 1;
		} else if (pixel > 191 && pixel < 256) {
			rBucket4 = rBucket4 + 1;
		}
	}

	public void greenHistogram(int pixel) {

		if (pixel <= 63) {
			gBucket1 = gBucket1 + 1;
		} else if (pixel > 63 && pixel < 128) {
			gBucket2 = gBucket2 + 1;
		} else if (pixel > 127 && pixel < 192) {
			gBucket3 = gBucket3 + 1;
		} else if (pixel > 191 && pixel < 256) {
			gBucket4 = gBucket4 + 1;
		}
	}

	public void blueHistogram(int pixel) {

		if (pixel <= 63) {
			bBucket1 = bBucket1 + 1;
		} else if (pixel > 63 && pixel < 128) {
			bBucket2 = bBucket2 + 1;
		} else if (pixel > 127 && pixel < 192) {
			bBucket3 = bBucket3 + 1;
		} else if (pixel > 191 && pixel < 256) {
			bBucket4 = bBucket4 + 1;
		}
	}

	public boolean checkInput(String input) {
		try {
			URI uri = new URI(input);
			return uri.getScheme().equals("http") || uri.getScheme().equals("https") || uri.getScheme().equals("gs");

		} catch (Exception e) {
			return false;
		}

	}

}