package org.ruse.uni.chat.core.services;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.ruse.uni.chat.core.configuration.ConfigurationProperty;
import org.ruse.uni.chat.core.dao.FileDao;
import org.ruse.uni.chat.core.entity.File;
import org.ruse.uni.chat.core.entity.Room;

/**
 * @author sinan
 */
@ApplicationScoped
public class FileServiceImpl implements FileService {

	@Inject
	@ConfigurationProperty(name = "file.upload.dir")
	private String fileUploadDir;

	@Inject
	@ConfigurationProperty(name = "file.upload.max.size")
	private String fileUploadMaxSize;

	@Inject
	@ConfigurationProperty(name = "file.upload.base")
	private String fileUploadBase;

	@Inject
	private FileDao fileDao;

	@Override
	public File getById(Long id) {
		return fileDao.findById(File.class, id);
	}

	@Override
	public void saveFile(File file) {
		fileDao.save(file);
	}

	@Override
	public String getUploadDir() {
		return fileUploadDir;
	}

	@Override
	public long getUploadMaxSize() {
		return Long.parseLong(fileUploadMaxSize);
	}

	@Override
	public String getUploadBase() {
		return fileUploadBase;
	}

	@Override
	public List<File> getFilesForRoom(Room room) {
		return fileDao.getFilesForRoom(room);
	}

}
