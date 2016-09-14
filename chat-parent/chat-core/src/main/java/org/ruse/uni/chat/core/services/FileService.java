package org.ruse.uni.chat.core.services;

import java.util.List;

import org.ruse.uni.chat.core.entity.File;
import org.ruse.uni.chat.core.entity.Room;

/**
 * @author sinan
 */
public interface FileService {

	File getById(Long id);

	void saveFile(File file);

	String getUploadDir();

	String getUploadBase();

	long getUploadMaxSize();

	List<File> getFilesForRoom(Room room);

}
