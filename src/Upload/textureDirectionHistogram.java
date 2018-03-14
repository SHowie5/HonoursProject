package Upload;

import java.awt.image.BufferedImage;
import java.awt.image.ImagingOpException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;

import javax.imageio.ImageIO;

import com.sun.org.apache.xml.internal.utils.URI;

import stores.UploadStore;

public class textureDirectionHistogram {
	
	UploadStore us = new UploadStore();
	BufferedImage bImage = null;
	double imageSize;
	
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
		
		// Edge detection code

		for (int x = 0; x < bImage.getWidth(); x++) {
			for (int y = 0; y < bImage.getHeight(); y++) {
				int clr = bImage.getRGB(x, y);
				int red = (clr & 0x00ff0000) >> 16;
				int green = (clr & 0x0000ff00) >> 8;
				int blue = clr & 0x000000ff;

				float luminance = (red * 0.2126f + green * 0.7152f + blue * 0.0722f) / 255;
				
				// choose brightness threshold as appropriate:
				if (luminance >= 0.5f) {
				    // bright color
					bImage.setRGB(x, y, 255);
				} else {
				    // dark color
					bImage.setRGB(x, y, 0);
				}
			}
		}
		
	}
	
	
	public boolean checkInput(String input) {
		try {
			URI uri = new URI(input);
			return uri.getScheme().equals("http") || uri.getScheme().equals("https");

		} catch (Exception e) {
			return false;
		}

	}
}
