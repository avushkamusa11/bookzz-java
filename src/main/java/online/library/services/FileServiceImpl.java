package online.library.services;

import org.springframework.stereotype.Service;

@Service
public class FileServiceImpl implements FileService{
	String fileName;
	@Override
	public String saveImage(String imageName) {
		fileName = imageName;
		return fileName;
	}

	@Override
	public String getImageForStoreInDB() {
		return saveImage(fileName);
	}

}
