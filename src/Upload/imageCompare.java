package Upload;

import java.io.IOException;

import Upload.imageHistogram;

public class imageCompare {
	
	public static double colourScore;

	public void compareImages(String imageA, String imageB) throws IOException {
		
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
		
		//Get texture direction histogram for image A
		textureDirectionHistogram tdh = new textureDirectionHistogram();
		tdh.readImage(imageB);

		
		double A_td1 = tdh.norm1;
		double A_td2 = tdh.norm2;
		double A_td3 = tdh.norm3;
		double A_td4 = tdh.norm4;
		double A_td5 = tdh.norm5;
		double A_td6 = tdh.norm6;
		 
		// Get texture scale histogram from image A
		double A_ts1 = tdh.distanceNorm1;
		double A_ts2 = tdh.distanceNorm2;
		double A_ts3 = tdh.distanceNorm3;
		double A_ts4 = tdh.distanceNorm4;
		double A_ts5 = tdh.distanceNorm5;
		double A_ts6 = tdh.distanceNorm6;
		
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
		
		// Get texture direction histogram for image B
		tdh.readImage(imageA);
		
		double B_td1 = tdh.norm1;
		double B_td2 = tdh.norm2;
		double B_td3 = tdh.norm3;
		double B_td4 = tdh.norm4;
		double B_td5 = tdh.norm5;
		double B_td6 = tdh.norm6;
		
		// Get texture scale histogram from image B;
		double B_ts1 = tdh.distanceNorm1;
		double B_ts2 = tdh.distanceNorm2;
		double B_ts3 = tdh.distanceNorm3;
		double B_ts4 = tdh.distanceNorm4;
		double B_ts5 = tdh.distanceNorm5;
		double B_ts6 = tdh.distanceNorm6;
		
		// Calculate colour difference between images
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
		
		// Calculate texture direction difference between images
		double diffTDB1 = A_td1 - B_td1;
		double diffTDB2 = A_td2 - B_td2;
		double diffTDB3 = A_td3 - B_td3;
		double diffTDB4 = A_td4 - B_td4;
		double diffTDB5 = A_td5 - B_td5;
		double diffTDB6 = A_td6 - B_td6;
		double totalTextDir = diffTDB1 + diffTDB2 + diffTDB3 + diffTDB4 + diffTDB5 + diffTDB6;	
		
		// Calculate texture scale difference between images
		double diffTSB1 = A_ts1 - B_ts1;
		double diffTSB2 = A_ts2 - B_ts2;
		double diffTSB3 = A_ts3 - B_ts3;
		double diffTSB4 = A_ts4 - B_ts4;
		double diffTSB5 = A_ts5 - B_ts5;
		double diffTSB6 = A_ts6 - B_ts6;
		double totalTextScale = diffTSB1 + diffTSB2 + diffTSB3 + diffTSB4 + diffTSB5 + diffTSB6;
			
		imageCompare.colourScore = Math.abs(totalRed + totalGreen + totalBlue + totalTextDir + totalTextScale);
	}
	
	public double getScore() {		
		return colourScore;
	}

}
