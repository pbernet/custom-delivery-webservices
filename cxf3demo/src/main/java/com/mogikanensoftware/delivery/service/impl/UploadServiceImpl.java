package com.mogikanensoftware.delivery.service.impl;

import com.mogikanensoftware.delivery.service.UploadService;
import com.mogikanensoftware.delivery.service.bean.upload.UploadMessageRequest;
import com.mogikanensoftware.delivery.service.bean.upload.UploadMessageResponse;

import javax.jws.WebService;
import javax.xml.ws.soap.MTOM;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@MTOM(enabled=true)
@WebService(endpointInterface = "com.mogikanensoftware.delivery.service.UploadService", portName="UploadServicePort", serviceName="UploadService", targetNamespace = "http://schemas.mogikanensoftware.com/upload")
		public class UploadServiceImpl implements UploadService {

	/** The path to upload received files. */
	private String uploadPath = "/tmp";

	public UploadMessageResponse uploadMessage(UploadMessageRequest request) {


		// randomly generating file name as a UUID
		String fileName = UUID.randomUUID().toString();
		File file = new File(uploadPath + File.separator + fileName);

		// writing attachment to file
		try(FileOutputStream fos = new FileOutputStream(file)) {
			request.getMessageContent().writeTo(fos);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// constructing the response
		UploadMessageResponse response = new UploadMessageResponse();
		response.setResponseTransactionUid(String.format("Hi, just received a %d byte file from ya, saved with id = %s",
				file.length(), fileName));

		return response;
	}

}
