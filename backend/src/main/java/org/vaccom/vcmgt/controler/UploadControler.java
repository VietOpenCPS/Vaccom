package org.vaccom.vcmgt.controler;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.vaccom.vcmgt.action.FileStorageAction;
import org.vaccom.vcmgt.response.UploadFileResponse;

@RestController
@RequestMapping("/rest/v1/upload")
public class UploadControler {
	@Autowired
	private FileStorageAction fileStorageAction;

	@RequestMapping(value = "/one", method = RequestMethod.POST)
	public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file) {

		// TODO validate
		String fileName = fileStorageAction.storeFile(file);

		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/")
				.path(fileName).toUriString();

		return new UploadFileResponse(fileName, fileDownloadUri, file.getContentType(), file.getSize());
	}

	@PostMapping("/multi")
	public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
		return Arrays.asList(files).stream().map(file -> uploadFile(file)).collect(Collectors.toList());
	}
}
