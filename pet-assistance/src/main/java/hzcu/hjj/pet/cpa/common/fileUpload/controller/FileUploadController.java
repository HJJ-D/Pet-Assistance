package hzcu.hjj.pet.cpa.common.fileUpload.controller;

import hzcu.hjj.pet.cpa.common.fileUpload.service.FileUploadService;
import hzcu.hjj.pet.cpa.common.response.ResEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class FileUploadController {

    @Autowired
    private FileUploadService fileUploadService;

    @PostMapping("/fileUpload")
    public ResEntity<?> handleFileUpload(@RequestParam("file") MultipartFile file) throws IOException {
        return fileUploadService.handleFileUpload(file);
    }

}