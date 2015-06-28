package com.slabs.collaborate.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.slabs.collaborate.listener.FileUploadListener;
import com.slabs.collaborate.utilities.FileUtil;
import com.slabs.collaborate.utilities.JSONUtil;
import com.slabs.collaborate.utilities.PropertiesUtil;
import com.slabs.collaborate.core.FileExplorer;
import com.slabs.collaborate.core.entity.User;
import com.slabs.collaborate.core.pojo.Directory;

@Controller
@RequestMapping("/request")
public class FileController {

	private static final Logger L = LoggerFactory.getLogger(FileController.class);

	@RequestMapping(value = "/process/fileUpload", method = { RequestMethod.POST })
	public ModelAndView fileUpload(HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> outputMap = new HashMap<String, Object>();
		Map<String, String> paramsMap = new HashMap<String, String>();
		String filePath = null;
		HttpSession session = request.getSession(false);
		if (session != null) {
			User user = (User) session.getAttribute("user_info");
			if (user != null) {
				try {
					L.info("Inititating file upload...");

					Properties p = PropertiesUtil.getPropertiesMap().get("collaborate.properties");
					String uploadPath = p.getProperty("collaborate.userrepository") + File.separator + user.getUserName();

					if (ServletFileUpload.isMultipartContent(request)) {
						FileUploadListener listener = new FileUploadListener();
						FileItemFactory factory = new DiskFileItemFactory();
						ServletFileUpload uploader = new ServletFileUpload(factory);
						uploader.setProgressListener(listener);
						session.setAttribute("uploadListener", listener);

						FileItem item = null;
						List<FileItem> fileItems = uploader.parseRequest(request);

						for (FileItem fileItem : fileItems) {
							if (fileItem.isFormField()) {
								paramsMap.put(fileItem.getFieldName(), fileItem.getString());
							} else {
								item = fileItem;
							}
						}

						if (Boolean.valueOf(paramsMap.get("root"))) {
							filePath = uploadPath + File.separator + getFileName(item.getName());
						} else {
							uploadPath = paramsMap.get("uploadPath");
							filePath = uploadPath + File.separator + getFileName(item.getName());
						}

						L.info("Uploading file {} to {}", getFileName(item.getName()), uploadPath);
						item.write(new File(filePath));

						outputMap.put("statusCode", 00);
						outputMap.put("success", true);
						outputMap.put("msg", "File Uploaded successfully...");
					}
				} catch (FileUploadException ex) {
					L.error("Exception.... {}", ex.getMessage());
					outputMap.put("statusCode", 99);
					outputMap.put("success", false);
					outputMap.put("msg", ex.getMessage());
				} catch (IOException ex) {
					L.error("Exception.... {}", ex.getMessage());
					outputMap.put("statusCode", 99);
					outputMap.put("success", false);
					outputMap.put("msg", ex.getMessage());
				} catch (Exception ex) {
					L.error("Exception.... {}", ex.getMessage());
					outputMap.put("statusCode", 99);
					outputMap.put("success", false);
					outputMap.put("msg", ex.getMessage());
				}
			} else {
				outputMap.put("statusCode", 98);
				request.getSession(false).invalidate();
				outputMap.put("success", false);
				outputMap.put("redirectURL", "/Collaborate");
				outputMap.put("msg", "Invalid session");
			}
		} else {
			outputMap.put("statusCode", 98);
			outputMap.put("success", false);
			outputMap.put("redirectURL", "/Collaborate");
			outputMap.put("msg", "Invalid session");
		}
		return new ModelAndView("jsonView", outputMap);
	}

	@RequestMapping(value = "/process/fileDownload", method = { RequestMethod.GET })
	public void fileDownload(HttpServletRequest request, HttpServletResponse response) {

		String filePath = request.getParameter("filePath");
		String fileName = request.getParameter("fileName");
		ServletContext ctx = request.getServletContext();

		try {

			File file = new File(filePath);

			InputStream is = new FileInputStream(file);
			String mimeType = ctx.getMimeType(file.getAbsolutePath());

			response.setContentType(mimeType != null ? mimeType : "application/octet-stream");
			response.setContentLength((int) file.length());
			response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

			ServletOutputStream os = response.getOutputStream();
			byte[] bufferData = new byte[1024];
			int read = 0;
			while ((read = is.read(bufferData)) != -1) {
				os.write(bufferData, 0, read);
			}
			os.flush();
			os.close();
			is.close();

		} catch (FileNotFoundException ex) {
			L.error("Error Downloading file {} - {}", fileName, ex);
		} catch (IOException ex) {
			L.error("Error Downloading file {} - {}", fileName, ex);
		}

	}

	@RequestMapping(value = "/process/getProgress", method = { RequestMethod.GET })
	public ModelAndView getUploadProgress(HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> outputMap = new HashMap<String, Object>();
		response.setContentType("application/json");
		FileUploadListener listener = (FileUploadListener) request.getSession().getAttribute("uploadListener");

		if (listener != null) {
			outputMap.put("progress", listener.getPercentDone());
			outputMap.put("actual", listener.getActualDone());
			outputMap.put("isUploadComplete", false);
			if (100L == listener.getPercentDone()) {
				outputMap.put("isUploadComplete", true);
			}
		} else {
			outputMap.put("progress", 0);
			outputMap.put("isUploadComplete", true);
		}

		return new ModelAndView("jsonView", outputMap);

	}

	@RequestMapping(value = "/process/getFiles", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView getFiles(HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> outputMap = new HashMap<String, Object>();
		FileExplorer explorer = FileExplorer.getInstance();
		Properties p = PropertiesUtil.getPropertiesMap().get("collaborate.properties");
		HttpSession session = request.getSession(false);
		if (session != null) {
			User user = (User) session.getAttribute("user_info");
			if (user != null) {
				try {
					String filePath = p.getProperty("collaborate.userrepository") + File.separator + user.getUserName();
					File[] files = explorer.getFilesArray(new File(filePath), true);
					List<Directory> directory = explorer.prepareDirectoryTree(files);
					outputMap.put("success", true);
					outputMap.put("statusCode", 00);
					outputMap.put("children", directory);

				} catch (FileNotFoundException ex) {
					L.error("Exception occured while retrieving the software list - {}", ex);
					outputMap.put("success", false);
					outputMap.put("statusCode", 99);
					outputMap.put("msg", "Error retrieving files. Please contact customer support");
				}
			} else {
				outputMap.put("success", false);
				outputMap.put("statusCode", 98);
				outputMap.put("msg", "Invalid session");
			}
		} else {
			outputMap.put("success", false);
			outputMap.put("statusCode", 98);
			outputMap.put("redirectURL", "/Collaborate");
			outputMap.put("msg", "Invalid session");
		}
		return new ModelAndView("jsonView", outputMap);
	}

	@RequestMapping(value = "/process/createFolder", method = { RequestMethod.POST })
	public ModelAndView createFolder(HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> outputMap = new HashMap<String, Object>();
		Properties p = PropertiesUtil.getPropertiesMap().get("collaborate.properties");
		boolean isDirectoryCreated = false;

		HttpSession session = request.getSession(false);
		if (session != null) {
			User user = (User) session.getAttribute("user_info");
			if (user != null) {
				try {

					Map<String, String> inputParams = JSONUtil.getObjectFromInputStream(request.getInputStream());
					String path = inputParams.get("folderPath");
					String folderName = inputParams.get("folderName");
					boolean root = Boolean.valueOf(inputParams.get("root"));

					if (!root) {
						isDirectoryCreated = FileUtil.createDirectory(new File(path), folderName);
					} else {
						String rootPath = p.getProperty("collaborate.userrepository") + File.separator + user.getUserName();
						isDirectoryCreated = FileUtil.createDirectory(new File(rootPath), folderName);
					}

					if (isDirectoryCreated) {
						outputMap.put("success", true);
						outputMap.put("statusCode", 00);
						outputMap.put("msg", "Folder created successfully");
					} else {
						outputMap.put("success", false);
						outputMap.put("statusCode", 00);
						outputMap.put("msg", "Folder already exists");
					}

				} catch (IOException ex) {
					L.error("Exception - {}", ex);
					outputMap.put("success", false);
					outputMap.put("statusCode", 99);
					outputMap.put("msg", "Exception occured while creating folder. Please contact customer support.");
				}
			} else {
				outputMap.put("success", false);
				outputMap.put("statusCode", 98);
				outputMap.put("redirectURL", "/Collaborate");
				outputMap.put("msg", "Invalid session");
			}
		} else {
			outputMap.put("success", false);
			outputMap.put("statusCode", 98);
			outputMap.put("redirectURL", "/Collaborate");
			outputMap.put("msg", "Invalid session");
		}

		return new ModelAndView("jsonView", outputMap);
	}

	@RequestMapping(value = "/process/delete", method = { RequestMethod.POST })
	public ModelAndView delete(HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> outputMap = new HashMap<String, Object>();
		HttpSession session = request.getSession(false);

		if (session != null) {
			User user = (User) session.getAttribute("user_info");
			if (user != null) {
				try {
					Map<String, String> inputParams = JSONUtil.getObjectFromInputStream(request.getInputStream());
					String path = inputParams.get("path");

					boolean isFileDeleted = FileUtil.deleteFile(new File(path));

					if (isFileDeleted) {
						outputMap.put("success", true);
						outputMap.put("statusCode", 00);
						outputMap.put("msg", "File deleted successfully");
					} else {
						outputMap.put("success", false);
						outputMap.put("statusCode", 00);
						outputMap.put("msg", "Error occurred while deleting file " + getFileName(path));
					}

				} catch (IOException ex) {
					L.error("Exception - {}", ex);
					outputMap.put("success", false);
					outputMap.put("statusCode", 99);
					outputMap.put("msg", "Exception occured while deleting. Please contact customer support.");
				} catch (Exception ex) {
					L.error("Exception - {}", ex);
					outputMap.put("statusCode", 99);
					outputMap.put("success", false);
					outputMap.put("msg", "Exception occured while deleting. Please contact customer support.");
				}
			} else {
				outputMap.put("success", false);
				outputMap.put("statusCode", 98);
				outputMap.put("redirectURL", "/Collaborate");
				outputMap.put("msg", "Invalid session");
			}
		} else {
			outputMap.put("success", false);
			outputMap.put("statusCode", 98);
			outputMap.put("redirectURL", "/Collaborate");
			outputMap.put("msg", "Invalid session");
		}
		return new ModelAndView("jsonView", outputMap);
	}

	@RequestMapping(value = "/process/moveFile", method = { RequestMethod.POST })
	public ModelAndView moveFile(HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> outputMap = new HashMap<String, Object>();
		HttpSession session = request.getSession(false);
		if (session != null) {
			User user = (User) session.getAttribute("user_info");
			if (user != null) {
				try {
					Map<String, String> inputParams = JSONUtil.getObjectFromInputStream(request.getInputStream());
					String destPath = inputParams.get("destPath");
					String srcPath = inputParams.get("srcPath");
					boolean root = Boolean.valueOf(inputParams.get("root"));
					boolean isFileMoved = false;

					if (!root) {
						isFileMoved = FileUtil.moveFile(new File(srcPath), new File(destPath));
					} else if (root) {
						Properties p = PropertiesUtil.getPropertiesMap().get("collaborate.properties");
						String rootPath = p.getProperty("collaborate.userrepository") + File.separator + user.getUserName();
						isFileMoved = FileUtil.moveFile(new File(srcPath), new File(rootPath));
					}

					if (isFileMoved) {
						outputMap.put("success", true);
						outputMap.put("statusCode", 00);
						outputMap.put("msg", "File moved successfully");
					} else {
						outputMap.put("success", false);
						outputMap.put("statusCode", 00);
						outputMap.put("msg", "Error occurred while moving file " + getFileName(srcPath));
					}

				} catch (IOException ex) {
					L.error("Exception - {}", ex);
					outputMap.put("statusCode", 99);
					outputMap.put("success", false);
					outputMap.put("msg", "Exception occured while moving file. Please contact customer support.");
				}
			} else {
				outputMap.put("success", false);
				outputMap.put("statusCode", 98);
				outputMap.put("redirectURL", "/Collaborate");
				outputMap.put("msg", "Invalid session");
			}
		} else {
			outputMap.put("success", false);
			outputMap.put("statusCode", 98);
			outputMap.put("redirectURL", "/Collaborate");
			outputMap.put("msg", "Invalid session");
		}

		return new ModelAndView("jsonView", outputMap);
	}

	private String getFileName(String name) {

		if (name != null) {
			String fileName = name.substring(name.lastIndexOf("\\") + 1);
			return fileName;
		}
		return name;
	}

}
