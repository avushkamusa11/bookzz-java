package online.library.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import online.library.entities.User;
import online.library.services.BookService;
import online.library.services.FileService;
import online.library.services.UserService;

@RestController
@RequestMapping("/file")
public class UploadFileController {
	@Autowired
	BookService bookService;

	@Autowired
	UserService userService;

	@PostMapping("/uploadBook/{bookId}")
	public String uploadBookImage(@RequestParam("image") MultipartFile imageFile,
			@PathVariable(name = "bookId") String bookId) {
		if (!bookId.equals("undefined")) {
			try {
				String uniqueFileName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
				String uploadPath = "C:\\Users\\a.musa\\Documents\\react\\library\\library\\public\\images";
				File file = new File(uploadPath, uniqueFileName);
				imageFile.transferTo(file);

				String fullFile = uniqueFileName;
				bookService.update(Long.parseLong(bookId), fullFile);

				return "Image uploaded successfully.";
			} catch (IOException e) {
				// Handle the case when there is an error saving the file
				return "Error uploading image: " + e.getMessage();
			}
		}
		return "Error uploading image";
	}

	@PostMapping("/uploadBookpdf/{bookId}")
	public String uploadBookPdf(@RequestParam("pdf") String pdfFile,
			@PathVariable(name = "bookId") String bookId) {
		if (!bookId.equals("undefined")) {
			String uniqueFileName = UUID.randomUUID().toString() + "_" + pdfFile;
			String uploadPath = "C:\\Users\\a.musa\\Documents\\react\\library\\library\\public\\pdf";
			File file = new File(uploadPath, uniqueFileName);
			

			String fullFile = uniqueFileName;
			bookService.updatePdf(Long.parseLong(bookId), pdfFile);

			return "Pdf uploaded successfully.";
		}
		return "Error uploading image";
	}

	@PostMapping("/uploadProfile/{userId}")
	public User uploadProfileImage(@RequestParam("image") MultipartFile imageFile,
			@PathVariable(name = "userId") String userId) {
		try {
			String uniqueFileName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
			String uploadPath = "C:\\Users\\a.musa\\Documents\\react\\library\\library\\public\\images";
			File file = new File(uploadPath, uniqueFileName);
			imageFile.transferTo(file);

			String fullFile = uniqueFileName;

			return userService.addProfilePicture(Long.parseLong(userId), fullFile);
		} catch (IOException e) {
			// Handle the case when there is an error saving the file
			return null;
		}
	}
}