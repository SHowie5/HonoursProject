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
	int numOfEdges = 0;

	//Texture direction buckets
	double bucket1 = 0;
	double bucket2 = 0;
	double bucket3 = 0;
	double bucket4 = 0;
	double bucket5 = 0;
	double bucket6 = 0;

	double norm1 = 0;
	double norm2 = 0;
	double norm3 = 0;
	double norm4 = 0;
	double norm5 = 0;
	double norm6 = 0;
	
	//Texture scale buckets
	double distanceBucket1 = 0;
	double distanceBucket2 = 0;
	double distanceBucket3 = 0;
	double distanceBucket4 = 0;
	double distanceBucket5 = 0;
	double distanceBucket6 = 0;
	
	double distanceNorm1 = 0;
	double distanceNorm2 = 0;
	double distanceNorm3 = 0;
	double distanceNorm4 = 0;
	double distanceNorm5 = 0;
	double distanceNorm6 = 0;

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

		numOfEdges = 0;

		bucket1 = 0;
		bucket2 = 0;
		bucket3 = 0;
		bucket4 = 0;
		bucket5 = 0;
		bucket6 = 0;

		norm1 = 0;
		norm2 = 0;
		norm3 = 0;
		norm4 = 0;
		norm5 = 0;
		norm6 = 0;

		edgeDetection();
		normaliseBuckets();
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
		for (int x = 1; x < bImage.getWidth() - 1; x++) {
			for (int y = 1; y < bImage.getHeight() - 1; y++) {

				// Get intensity of top left pixel
				int pixel = bImage.getRGB(x, y);
				double centrePixel = pixelIntensity(pixel);
				// Get intensity of lower pixel
				int bottomPixel = bImage.getRGB(x, y + 1);
				double lowerPixel = pixelIntensity(bottomPixel);
				// Set edges colour
				if (Math.abs(centrePixel - lowerPixel) < 20) {
					// Black pixel
					bImage.setRGB(x, y, 0);
				} else {
					// White pixel
					bImage.setRGB(x, y, 16777215);
					numOfEdges = numOfEdges + 1;
					textureHistograms(x, y);
				}
			}
		}

		// try {
		// File output = new File("C:\\Users\\Scott
		// Howie\\eclipse-workspace\\edge.png");
		// ImageIO.write(bImage, "png", output);
		// System.out.println("Edge Pic Saved: " + output.getAbsolutePath());
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
	}

	public void textureHistograms(int x, int y) {

		int topleft = bImage.getRGB(x - 1, y - 1);
		double top_left = pixelIntensity(topleft);

		int topmiddle = bImage.getRGB(x, y - 1);
		double top_middle = pixelIntensity(topmiddle);

		int topright = bImage.getRGB(x + 1, y - 1);
		double top_right = pixelIntensity(topright);

		int centreleft = bImage.getRGB(x - 1, y);
		double centre_left = pixelIntensity(centreleft);

		int centreright = bImage.getRGB(x + 1, y);
		double centre_right = pixelIntensity(centreright);

		int bottomleft = bImage.getRGB(x - 1, y + 1);
		double bottom_left = pixelIntensity(bottomleft);

		int bottommiddle = bImage.getRGB(x, y + 1);
		double bottom_middle = pixelIntensity(bottommiddle);

		int bottomright = bImage.getRGB(x + 1, y + 1);
		double bottom_right = pixelIntensity(bottomright);

		double y_direction = ((top_left - bottom_left) / 2 + (top_middle - bottom_middle) / 2
				+ (top_right - bottom_right) / 2) / 3;
		double x_direction = ((top_right - top_left) / 2 + (centre_right - centre_left) / 2
				+ (bottom_right - bottom_left) / 2) / 3;

		// Find angle of edge
		// double angle = Math.abs(Math.toDegrees(Math.atan(y_direction /
		// x_direction)));
		// System.out.println("Angle: " + angle);

		double normal = Math.pow((Math.pow(y_direction, 2) + Math.pow(x_direction, 2)), 0.5);
		// System.out.println("Normal: " + normal);
		directionBuckets(normal);
		textureScale(normal, x, y);
	}

	public void textureScale(double angle, int x, int y) {

		// Checks direction of angle for algorithm to walk in
		// Only need to check 0 - 180 degrees
		if (angle >= 0 && angle < 22.5) {
			walkEast(x, y);
			// System.out.println("Walk East");
		} else if (angle >= 22.5 && angle < 67.5) {
			walkSouthEast(x, y);
			// System.out.println("Walk South East");
		} else if (angle >= 67.6 && angle < 112.5) {
			walkSouth(x, y);
			// System.out.println("Walk South");
		} else if (angle >= 112.5 && angle < 157.5) {
			walkSouthWest(x, y);
			// System.out.println("Walk South West");
		}
	}

	public void walkEast(int x, int y) {
		// Calculate distance from
		int currX = 0;
		int currY = 0;

		int xDistance = x - currX;
		int yDistance = y - currY;

		double distance = Math.sqrt((Math.pow(xDistance, 2) + Math.pow(yDistance, 2)));
		System.out.println(distance);

		currX = x;
		currY = y;
	}

	public void walkSouthEast(int x, int y) {
		int currX = 0;
		int currY = 0;

		int xDistance = x - currX;
		int yDistance = y - currY;

		double distance = Math.sqrt((Math.pow(xDistance, 2) + Math.pow(yDistance, 2)));
		System.out.println(distance);

		currX = x;
		currY = y;
	}

	public void walkSouth(int x, int y) {
		int currX = 0;
		int currY = 0;

		int xDistance = x - currX;
		int yDistance = y - currY;

		double distance = Math.sqrt((Math.pow(xDistance, 2) + Math.pow(yDistance, 2)));
		System.out.println(distance);

		currX = x;
		currY = y;
	}

	public void walkSouthWest(int x, int y) {
		int currX = 0;
		int currY = 0;

		int xDistance = x - currX;
		int yDistance = y - currY;

		double distance = Math.sqrt((Math.pow(xDistance, 2) + Math.pow(yDistance, 2)));
		System.out.println(distance);

		currX = x;
		currY = y;
	}

	public void distanceBuckets(double distance) {
		/*
		 * Histogram buckets Bucket Ranges: 1 = 0-99, 2 = 100-199, 3 = 200-299, 4 = 300-399,
		 * 5 = 400-499, 6 = 500+
		 */
		if (distance < 100) {
			distanceBucket1 = distanceBucket1 + 1;
		} else if(distance >= 100 && distance < 200) {
			distanceBucket2 = distanceBucket2 + 1;
		} else if(distance >= 200 && distance < 300) {
			distanceBucket3 = distanceBucket3 + 1;
		} else if(distance >= 300 && distance < 400) {
			distanceBucket4 = distanceBucket4 + 1;
		} else if(distance >= 400 && distance < 500) {
			distanceBucket5 = distanceBucket5 + 1;
		} else {
			distanceBucket6 = distanceBucket6 + 1;
		}
	}

	public void directionBuckets(double normal) {

		/*
		 * Histogram buckets Bucket Ranges: 1 = 0-30, 2 = 31-60, 3 = 61-90, 4 = 91-120,
		 * 5 = 121-150, 6 = 151-180
		 */

		if (normal <= 30) {
			bucket1 = bucket1 + 1;
		} else if (normal > 30 && normal <= 60) {
			bucket2 = bucket2 + 1;
		} else if (normal > 60 && normal <= 90) {
			bucket3 = bucket3 + 1;
		} else if (normal > 90 && normal <= 120) {
			bucket4 = bucket4 + 1;
		} else if (normal > 120 && normal <= 150) {
			bucket5 = bucket5 + 1;
		} else {
			bucket6 = bucket6 + 1;
		}
	}

	public void normaliseBuckets() {

		norm1 = bucket1 / numOfEdges;
		norm2 = bucket2 / numOfEdges;
		norm3 = bucket3 / numOfEdges;
		norm4 = bucket4 / numOfEdges;
		norm5 = bucket5 / numOfEdges;
		norm6 = bucket6 / numOfEdges;
		
		distanceNorm1 = distanceBucket1 / numOfEdges;
		distanceNorm2 = distanceBucket2 / numOfEdges;
		distanceNorm3 = distanceBucket3 / numOfEdges;
		distanceNorm4 = distanceBucket4 / numOfEdges;
		distanceNorm5 = distanceBucket5 / numOfEdges;
		distanceNorm6 = distanceBucket6 / numOfEdges;
	}

	public double pixelIntensity(int pixel) {

		int red = (pixel & 0x00ff0000) >> 16;
		int green = (pixel & 0x0000ff00) >> 8;
		int blue = pixel & 0x000000ff;

		double intensity = (red + green + blue) / 3;

		return intensity;
	}
}
