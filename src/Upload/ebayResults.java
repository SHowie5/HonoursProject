package Upload;

public class ebayResults {

	public double score;
	String title;
	String itemID;
	String galleryUrl;
	String currentPrice;
	String itemUrl;

	public ebayResults(double score, String title, String itemID, String galleryUrl, String currentPrice, String itemUrl) {

		this.score = score;
		this.title = title;
		this.itemID = itemID;
		this.galleryUrl = galleryUrl;
		this.currentPrice = currentPrice;
	}
	
	public void setScore(double score) {
		this.score = score;
	}

	public double getScore() {
		return score;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}
	
	public void setItemID(String itemID) {
		this.itemID = itemID;
	}

	public String getItemId() {
		return itemID;
	}
	
	public void setGalleryUrl(String galleryUrl) {
		this.galleryUrl = galleryUrl;
	}

	public String getGalleryUrl() {
		return galleryUrl;
	}
	
	public void setCurrentPrice(String currentPrice) {
		this.currentPrice = currentPrice;
	}

	public String getCurrentPrice() {
		return currentPrice;
	}
	
	public void setItemUrl(String itemUrl) {
		this.itemUrl = itemUrl;
	}
	
	public String getItemUrl() {
		return itemUrl;
	}

	@Override
	public String toString() {
		return ("Score:" + score + "Title:" + title + "ItemID:" + itemID + "GalleryURL:" + galleryUrl + "Price:"
				+ currentPrice);
	}

}
