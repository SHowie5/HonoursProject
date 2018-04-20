package Upload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import stores.UploadStore;

/**
 * Servlet implementation class Upload
 */
public class Upload extends HttpServlet {
	UploadStore us = new UploadStore();
	private static final long serialVersionUID = 1L;
	private static final String DATA_DIRECTORY = "data";
	private static final int MAX_MEMORY_SIZE = 1024 * 1024 * 2;
	private static final int MAX_REQUEST_SIZE = 1024 * 1024;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Check that we have a file upload request
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);

		if (!isMultipart) {
			String imageUrl = request.getParameter("image");
			try {
				urlUpload(request, response, imageUrl);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			localFile(request, response);
		}

	}

	public void localFile(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Create a factory for disk-based file items
		DiskFileItemFactory factory = new DiskFileItemFactory();

		// Set the size threshold beyond which files are written directly to disk
		factory.setSizeThreshold(MAX_MEMORY_SIZE);

		// Set the directory used to temporarily store files that are larger
		// than the configured size threshold
		factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

		// Constructs the folder where uploaded file will be stored
		String uploadFolder = getServletContext().getRealPath("") + File.separator + DATA_DIRECTORY;

		// Create new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);

		// Set overall request size constraint
		upload.setSizeMax(MAX_REQUEST_SIZE);

		try {
			// Parse request
			List<?> items = upload.parseRequest(request);
			Iterator<?> iter = items.iterator();
			HttpSession session = request.getSession();

			while (iter.hasNext()) {
				FileItem item = (FileItem) iter.next();

				if (!item.isFormField()) {
					String fileName = new File(item.getName()).getName();
					us.setFilename(fileName);
					String filePath = uploadFolder + File.separator + fileName;
					File uploadedFile = new File(filePath);
					UploadStore us = new UploadStore();
					us.setFilePath(filePath);
					session.setAttribute("UploadStore", us);
					// Saves file to upload directory
					item.write(uploadedFile);
					// Sends image to Google Vision API
					googleVision gv = new googleVision();
					gv.main(filePath);
					// Call eBay API
					EbayDriver.main(null);
				}
			}

			// Displays eBay page after upload finished
			getServletContext().getRequestDispatcher("/results.jsp").forward(request, response);

		} catch (FileUploadException ex) {
			throw new ServletException(ex);
		} catch (Exception ex) {
			throw new ServletException(ex);
		}
	}

	public void urlUpload(HttpServletRequest request, HttpServletResponse response, String imageUrl) throws Exception {
		HttpSession session = request.getSession();
		String destinationFile = "image.jpg";
		us.setFilename(destinationFile);
		String uploadFolder = getServletContext().getRealPath("") + File.separator + DATA_DIRECTORY;
		String filePath = uploadFolder + File.separator + destinationFile;

		URLConnection openConnection = new URL(imageUrl).openConnection();
		openConnection.addRequestProperty("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");

		InputStream is = openConnection.getInputStream();
		OutputStream os = new FileOutputStream(filePath);

		byte[] b = new byte[2048];
		int length;

		while ((length = is.read(b)) != -1) {
			os.write(b, 0, length);
		}

		is.close();
		os.close();

		UploadStore us = new UploadStore();
		us.setFilePath(filePath);
		session.setAttribute("UploadStore", us);
		// Google vision API
		googleVision gv = new googleVision();
		gv.main(filePath);
		// eBay API
		EbayDriver.main(null);

		getServletContext().getRequestDispatcher("/results.jsp").forward(request, response);

	}

}
