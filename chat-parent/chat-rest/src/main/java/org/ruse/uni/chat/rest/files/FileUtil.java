package org.ruse.uni.chat.rest.files;

import java.util.UUID;

import org.apache.commons.fileupload.FileItem;
import org.ruse.uni.chat.core.entity.File;
import org.ruse.uni.chat.core.entity.Room;
import org.ruse.uni.chat.core.entity.User;

/**
 * @author sinan
 */
public class FileUtil {

	public static long convertBytesToMb(long bytes) {
		return bytes / 1024 / 1024;
	}

	public static String getExtension(String fileName) {
		return fileName.substring(fileName.lastIndexOf('.') + 1);
	}

	public static File buildFile(FileItem item, String location, Room room, User user) {
		String name = item.getName();
		File file = new File();
		file.setExtension(getExtension(name));
		file.setFilename(UUID.randomUUID().toString());
		file.setName(name);
		file.setMimetype(item.getContentType());
		file.setSize(item.getSize());
		file.setRoom(room);
		file.setUser(user);
		file.setLocation(buildPath(location, file.getFilename(), file.getExtension()));
		return file;
	}

	public static String buildPath(String locaiton, String fileName, String extension) {
		return locaiton + fileName + "." + extension;
	}

}
