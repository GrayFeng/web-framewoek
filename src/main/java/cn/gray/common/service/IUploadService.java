package cn.gray.common.service;

import org.springframework.web.multipart.MultipartFile;

import cn.gray.common.enums.EUploadType;
import cn.gray.common.pojo.ImageUploadResult;

public interface IUploadService {

	public ImageUploadResult processupload(Integer memberId,MultipartFile file,EUploadType uploadType);

}
