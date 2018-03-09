package Upload;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import com.sun.org.apache.xml.internal.utils.URI;

public class textureDirectionHistogram {

	public void readImage(String image) {
		
		BufferedImage bImage = null;
		double imageSize;
		
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
