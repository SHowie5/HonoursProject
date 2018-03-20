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
		
		edgeDetection();

	}

	public boolean checkInput(String input) {
		try {
			URI uri = new URI(input);
			return uri.getScheme().equals("http") || uri.getScheme().equals("https");

		} catch (Exception e) {
			return false;
		}

	}

	public void edgeDetection() {
		// Edge detection code
		for (int x = 0; x < bImage.getWidth(); x++) {
			for (int y = 0; y < bImage.getHeight() - 1; y++) {

				// Get intensity of top left pixel
				int pixel = bImage.getRGB(x, y);
				double topPixel = pixelIntensity(pixel);
				// Get intensity of lower pixel
				int bottomPixel = bImage.getRGB(x, y + 1);
				double lowerPixel = pixelIntensity(bottomPixel);
				// Set edges colour
				if (Math.abs(topPixel - lowerPixel) < 20) {
					// Black pixel
					bImage.setRGB(x, y, 0);
				} else {
					// White pixel
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

	public double pixelIntensity(int pixel) {

		int red = (pixel & 0x00ff0000) >> 16;
		int green = (pixel & 0x0000ff00) >> 8;
		int blue = pixel & 0x000000ff;

		double intensity = (red + green + blue) / 3;

		return intensity;
	}
}
