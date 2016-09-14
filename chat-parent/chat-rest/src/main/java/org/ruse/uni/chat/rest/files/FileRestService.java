package org.ruse.uni.chat.rest.files;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.JSONArray;
import org.json.JSONObject;
import org.ruse.uni.chat.core.services.FileService;
import org.ruse.uni.chat.core.services.RoomService;
import org.ruse.uni.chat.core.services.UserService;

/**
 * @author sinan
 */
@Path("/files")
public class FileRestService {

	@Inject
	private FileService fileService;

	@Inject
	private RoomService roomService;

	@Inject
	private UserService userService;

	@GET
	@Path("/{roomId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAll(@PathParam("roomId") Long roomId) {
		try {
			List<org.ruse.uni.chat.core.entity.File> files = fileService.getFilesForRoom(roomService.getById(roomId));
			JSONArray result = new JSONArray();
			files.forEach(file -> result.put(file.toJson()));
			return Response.ok(result.toString()).build();
		} catch (Exception e) {
			e.printStackTrace();
			JSONObject json = new JSONObject();
			json.put("message", e.getMessage());
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(json.toString()).build();
		}
	}

	@POST
	@Path("/upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public Response uploadFile(@Context HttpServletRequest request) {
		JSONArray result = new JSONArray();
		try {
			boolean multipartContent = ServletFileUpload.isMultipartContent(request);
			if (multipartContent) {
				DiskFileItemFactory factory = new DiskFileItemFactory();

				ServletContext servletContext = request.getServletContext();
				File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
				factory.setRepository(repository);

				ServletFileUpload upload = new ServletFileUpload(factory);
				upload.setFileSizeMax(fileService.getUploadMaxSize());
				List<FileItem> items = upload.parseRequest(request);
				Iterator<FileItem> iter = items.iterator();
				String roomId = "";
				String userId = "";
				while (iter.hasNext()) {
					FileItem item = iter.next();
					if (item.isFormField()) {
						if (item.getFieldName().equals("roomId")) {
							roomId = item.getString();
						}
						if (item.getFieldName().equals("userId")) {
							userId = item.getString();
						}
					} else {
						org.ruse.uni.chat.core.entity.File file = FileUtil.buildFile(item, fileService.getUploadBase(),
								roomService.getById(Long.valueOf(roomId)), userService.getById(Long.valueOf(userId)));
						fileService.saveFile(file);
						writeToFile(item.getInputStream(), FileUtil.buildPath(fileService.getUploadDir(),
								file.getFilename(), file.getExtension()));
						result.put(file.toJson());
					}
				}
			}
		} catch (IOException | FileUploadException e) {
			JSONObject json = new JSONObject();
			json.put("message",
					"Max allowed file size in MB is: " + FileUtil.convertBytesToMb(fileService.getUploadMaxSize()));
			json.put("error", e.getMessage());
			return Response.status(Status.BAD_REQUEST).entity(json.toString()).build();
		}

		return Response.ok(result.toString()).build();
	}

	private static void writeToFile(InputStream uploadedInputStream, String uploadedFileLocation) {
		try (OutputStream out = new FileOutputStream(new File(uploadedFileLocation))) {
			int read = 0;
			byte[] bytes = new byte[1024];

			while ((read = uploadedInputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
