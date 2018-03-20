package Upload;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import com.sun.org.apache.xml.internal.utils.URI;

import stores.UploadStore;

public class textureDirectionHistogram {

	UploadStore us = new UploadStore();
	BufferedImage bImage = null;
	double imageSize;

	public void readImage(String image) throws IOException {

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
			for (int y = 0; y < bImage.getHeight() - 1; y++) {

				// Get RGB of top pixel
				int topPixel = bImage.getRGB(x, y);
				int redTop = (topPixel & 0x00ff0000) >> 16;
				int greenTop = (topPixel & 0x0000ff00) >> 8;
				int blueTop = topPixel & 0x000000ff;
				// Get RGB of lower pixel
				int lowerPixel = bImage.getRGB(x, y + 1);
				int redLower = (lowerPixel & 0x00ff0000) >> 16;
				int greenLower = (lowerPixel & 0x0000ff00) >> 8;
				int blueLower = lowerPixel & 0x000000ff;
				// Calculate intensity of both pixels
				double topIntensity = (redTop + greenTop + blueTop) / 3;
				double lowerIntensity = (redLower + greenLower + blueLower) / 3;
				// Set edges colour
				if (Math.abs(topIntensity - lowerIntensity) < 20) {
					bImage.setRGB(x, y, 0);
				} else {
					bImage.setRGB(x, y, 16777215);
				}

			}
		}

		try {
			File output = new File("C:\\Users\\Scott Howie\\eclipse-workspace\\edge.png");
			ImageIO.write(bImage, "png", output);
			System.out.println("Edge Pic Saved: " + output.getAbsolutePath());
		} catch (IOException e) {
			e.printStackTrace();
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
