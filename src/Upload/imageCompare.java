package Upload;

import Upload.imageHistogram;

public class imageCompare {
	
	public static double colourScore;

	public void compareImages(String imageA, String imageB) {
		
		// Get colour histogram for image A
		imageHistogram ih = new imageHistogram();
		ih.readImage(imageA);
		
		double A_rb1 = ih.normRB1;
		double A_rb2 = ih.normRB2;
		double A_rb3 = ih.normRB3;
		double A_rb4 = ih.normRB4;

		double A_gb1 = ih.normGB1;
		double A_gb2 = ih.normGB2;
		double A_gb3 = ih.normGB3;
		double A_gb4 = ih.normGB4;
		
		double A_bb1 = ih.normBB1;
		double A_bb2 = ih.normBB2;
		double A_bb3 = ih.normBB3;
		double A_bb4 = ih.normBB4;
		
		// Get colour histogram for image B
		ih.readImage(imageB);
		
		double B_rb1 = ih.normRB1;
		double B_rb2 = ih.normRB2;
		double B_rb3 = ih.normRB3;
		double B_rb4 = ih.normRB4;
		
		double B_gb1 = ih.normGB1;
		double B_gb2 = ih.normGB2;
		double B_gb3 = ih.normGB3;
		double B_gb4 = ih.normGB4;
		
		double B_bb1 = ih.normBB1;
		double B_bb2 = ih.normBB2;
		double B_bb3 = ih.normBB3;
		double B_bb4 = ih.normBB4;
		
		double diffRB1 = A_rb1 - B_rb1;		
		double diffRB2 = A_rb2 - B_rb2;
		double diffRB3 = A_rb3 - B_rb3;
		double diffRB4 = A_rb4 - B_rb4;
		double totalRed = diffRB1 + diffRB2 + diffRB3 + diffRB4;

		double diffGB1 = A_gb1 - B_gb1;		
		double diffGB2 = A_gb2 - B_gb2;
		double diffGB3 = A_gb3 - B_gb3;
		double diffGB4 = A_gb4 - B_gb4;
		double totalGreen = diffGB1 + diffGB2 + diffGB3 + diffGB4;
		
		double diffBB1 = A_bb1 - B_bb1;		
		double diffBB2 = A_bb2 - B_bb2;
		double diffBB3 = A_bb3 - B_bb3;
		double diffBB4 = A_bb4 - B_bb4;
		double totalBlue = diffBB1 + diffBB2 + diffBB3 + diffBB4;
		
		
		imageCompare.colourScore = Math.abs(totalRed + totalGreen + totalBlue);

		//System.out.println("Total Difference: " + (colourScore));
	}
	
	public double getScore() {		
		return colourScore;
	}

}
