package com.crave.crave_backend.validation;

import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;
import com.crave.crave_backend.constant.ErrorMessageConstants;
import com.crave.crave_backend.exception.InvalidImageException;

public class ImageValidator {
	
	public static byte[] validateImage(MultipartFile image) {
		if (image == null || image.isEmpty()) {
			throw new InvalidImageException(ErrorMessageConstants.IMAGE_REQUIRED);
		}
		String contentType = image.getContentType();
		if (contentType == null || !contentType.startsWith("image/")) {
			throw new InvalidImageException(ErrorMessageConstants.INVALID_IMAGE_TYPE);
		}
		if (image.getSize() > 2 * 1024 * 1024) {
			throw new InvalidImageException(ErrorMessageConstants.LARGE_IMAGE);
		}
		try {
			return image.getBytes();
		} catch (IOException ex) {
			throw new InvalidImageException(ErrorMessageConstants.IMAGE_READ_FAILED);
		}
	}
}
