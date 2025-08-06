package hzcu.hjj.pet.cpa.common.fileUpload.service;

import hzcu.hjj.pet.cpa.common.response.ResEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileUploadService {

    public ResEntity<?> handleFileUpload(MultipartFile file) throws IOException;
}
