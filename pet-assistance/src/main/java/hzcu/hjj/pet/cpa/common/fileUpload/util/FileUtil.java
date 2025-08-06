package hzcu.hjj.pet.cpa.common.fileUpload.util;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
@Component
public class FileUtil {
    public static File multipartFileToFile (MultipartFile multipartFile) throws IOException{

        String targetFolder = "C:\\Users\\hp\\Desktop\\graduation_design\\pet-assistance\\img";
        // 获取MultipartFile的原文件名
        String originalFilename = multipartFile.getOriginalFilename();

        // 构建目标文件路径（包括文件名）
        File targetFile = new File(targetFolder, originalFilename);

        // 使用MultipartFile的transferTo方法将上传的文件内容保存到本地文件
        multipartFile.transferTo(targetFile);

        return targetFile;

    }

}
