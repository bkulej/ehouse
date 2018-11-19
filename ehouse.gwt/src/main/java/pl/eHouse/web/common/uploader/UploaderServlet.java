package pl.eHouse.web.common.uploader;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import pl.eHouse.api.bootloader.Software;
import pl.eHouse.api.config.Config;
import pl.eHouse.api.config.Settings;
import pl.eHouse.web.common.client.utils.ClientConst;

public class UploaderServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ServletFileUpload upload = new ServletFileUpload(
				new DiskFileItemFactory());
		String fileType = null;
		String fileDesc = null;
		FileItem fileItem = null;
		try {
			// Wyszukaj pola
			for (FileItem item : upload.parseRequest(request)) {
				if (item.getFieldName().equals(ClientConst.UPLOAD_FIELD_TYPE)
						&& item.isFormField()) {
					fileType = item.getString();
				} else if (item.getFieldName().equals(
						ClientConst.UPLOAD_FIELD_DESC)
						&& item.isFormField()) {
					fileDesc = item.getString();
				} else if (item.getFieldName().equals(
						ClientConst.UPLOAD_FIELD_FILE)
						&& !item.isFormField()) {
					fileItem = item;
				}
			}
			// Czy pola kompletne
			if ((fileType != null) && (fileDesc != null) && (fileItem != null)) {
				Software software = new Software(fileType, fileItem.getName(),
						fileDesc);
				fileItem.write(new File(Settings.getUploadDirectory()
						+ software.getFileName()));
				Config.getInstance().save(software);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
