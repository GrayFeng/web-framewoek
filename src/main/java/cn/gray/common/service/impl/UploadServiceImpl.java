package cn.gray.common.service.impl;

import java.io.File;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import cn.gray.common.Constant;
import cn.gray.common.enums.EUploadType;
import cn.gray.common.pojo.ImageUploadResult;
import cn.gray.common.service.IUploadService;
import cn.gray.common.utils.SimpleFileUtil;

import com.google.common.io.Files;

@Service
public class UploadServiceImpl implements IUploadService{
	
	private final static Logger logger = Logger.getLogger(UploadServiceImpl.class);

	public ImageUploadResult processupload(Integer memberId,MultipartFile uploadFile,EUploadType uploadType) {
		ImageUploadResult result = null;
		if (uploadFile == null) {
			return new ImageUploadResult(false,"请选择文件上传");
		}
//		long fileSize = uploadFile.getSize();
//		if (fileSize > 300 * 1024L) {
//			return new ImageUploadResult(false,"图片大小不能超过300Kkb");
//		}
		try {
			//奖品图片格式检测
//			if(EUploadType.PRIZE_PHOTO.equals(uploadType)){
//				 BufferedImage sourceImg = ImageIO.read(uploadFile.getInputStream());
//				 if(sourceImg != null 
//						 && (sourceImg.getHeight() > 220 || sourceImg.getWidth() > 305)){
//					 return new ImageUploadResult(false,"图片尺寸大于305x220");
//				 }
//			}
			byte[] imgbytes = SimpleFileUtil.boBin(uploadFile.getInputStream());
			String fileExtension = SimpleFileUtil.detectFileStype(imgbytes);
			if (!cheackFileExtention(fileExtension)) {
				return new ImageUploadResult(false,"不支持的文件格式");
			}
			String filePath = getNewFilePath(uploadType,fileExtension);
			if(StringUtils.isNotEmpty(filePath)){
				File imageFile = new File(Constant.PHOTO_BASE_PATH + filePath);
				Files.createParentDirs(imageFile);
				Files.write(imgbytes, imageFile);
				result = new ImageUploadResult(true,"图片上传成功");
				result.setUrl(filePath);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}finally{
			if(result == null){
				result = new ImageUploadResult(false,"图片上传失败,服务器故障");
			}
		}
		return result;
	}
	
	private boolean cheackFileExtention(String fileStyle) {
		boolean result = false;
		if (StringUtils.isNotBlank(fileStyle)) {
			fileStyle = fileStyle.toLowerCase();
			if (fileStyle.equals("gif") || fileStyle.equals("jpeg") || fileStyle.equals("jpg")
					|| fileStyle.equals("bmp") || fileStyle.equals("png")) {
				result = true;
			}
		}
		return result;
	}
	
	private String getNewFilePath(EUploadType uploadType,String fileExtension){
		StringBuffer pathBuffer = new StringBuffer();
		switch (uploadType) {
		case MEMBER_PHOTO:
			pathBuffer.append("memberPhoto");
			pathBuffer.append("/");
			pathBuffer.append(DateFormatUtils.format(new Date(),"yyyy-MM-dd"));
			pathBuffer.append("/");
			pathBuffer.append("mp_");
			pathBuffer.append(System.currentTimeMillis());
			pathBuffer.append(".");
			pathBuffer.append(fileExtension.equals("jpeg") ? "jpg" : fileExtension);
			return pathBuffer.toString();
		case FORUM_PHOTO:
			pathBuffer.append("forumPhoto");
			pathBuffer.append("/");
			pathBuffer.append(DateFormatUtils.format(new Date(),"yyyy-MM-dd"));
			pathBuffer.append("/");
			pathBuffer.append(DateFormatUtils.format(new Date(),"HH"));
			pathBuffer.append("/");
			pathBuffer.append("forum_");
			pathBuffer.append(System.currentTimeMillis());
			pathBuffer.append(".");
			pathBuffer.append(fileExtension.equals("jpeg") ? "jpg" : fileExtension);
			return pathBuffer.toString();
		case COF_PHOTO:
			pathBuffer.append("cofPhoto");
			pathBuffer.append("/");
			pathBuffer.append(DateFormatUtils.format(new Date(),"yyyy-MM-dd"));
			pathBuffer.append("/");
			pathBuffer.append(DateFormatUtils.format(new Date(),"HH"));
			pathBuffer.append("/");
			pathBuffer.append("cof_");
			pathBuffer.append(System.currentTimeMillis());
			pathBuffer.append(".");
			pathBuffer.append(fileExtension.equals("jpeg") ? "jpg" : fileExtension);
			return pathBuffer.toString();
		default:
			break;
		}
		return null;
	}
}
