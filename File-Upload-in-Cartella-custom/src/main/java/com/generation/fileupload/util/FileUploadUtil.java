package com.generation.fileupload.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import com.generation.fileupload.config.CustomProperties;
import com.generation.fileupload.entity.Veicolo;

public class FileUploadUtil {

	public static void saveFile(String uploadDir, String fileName, MultipartFile multipartFile) throws IOException {

		// converte percorso stringa in un path
		Path uploadPath = Paths.get(uploadDir);

		if (!Files.exists(uploadPath)) {
			// crea cartella se non esiste dove salvare l'immagine
			Files.createDirectories(uploadPath); // throws IOException
		}
		try (InputStream inputStream = multipartFile.getInputStream()) {
			Path filePath = uploadPath.resolve(fileName); // percorso file completo
			// sovrascrive file se già presente con stesso nome
			Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);


		} catch (IOException ioe) {
			throw new IOException("Could not save image file: " + fileName, ioe);
		}
	}

	public static void deleteDir(Veicolo trovato) {
		try {

			String dir = CustomProperties.IMG_URL_PATH + "/" + trovato.getId();
			
			if (Files.exists(Paths.get(dir))) {
				// N.B. aggiungere nel pom.xml la dipendenza "commons-io"
				// <!-- https://mvnrepository.com/artifact/commons-io/commons-io -->


				FileUtils.deleteDirectory(new File(dir));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
