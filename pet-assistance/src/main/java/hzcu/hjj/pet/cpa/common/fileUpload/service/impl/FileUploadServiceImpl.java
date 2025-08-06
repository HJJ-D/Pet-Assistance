package hzcu.hjj.pet.cpa.common.fileUpload.service.impl;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.CannedAccessControlList;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import hzcu.hjj.pet.cpa.common.fileUpload.util.FileUtil;
import hzcu.hjj.pet.cpa.common.fileUpload.service.FileUploadService;
import hzcu.hjj.pet.cpa.common.response.ResEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FileUploadServiceImpl implements FileUploadService {

    @Value("${file.upload.dir}")
    private String uploadDir;
    @Value("${cloud.SecretId}")
    private String secretId;

    @Value("${cloud.SecretKey}")
    private String secretKey;

    @Value("${cloud.region}")
    private String region;

    @Value("${cloud.bucket}")
    private String bucketName;

    @Value("${cloud.url}")
    private String path;

    public COSClient initCOSClient(){
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        Region region = new Region(this.region);
        ClientConfig clientConfig = new ClientConfig(region);
        // 生成 cos 客户端。
        COSClient cosClient = new COSClient(cred, clientConfig);
        return cosClient;
    }

    private String generateUniqueFileName(String originalFilename) {
        // 这里仅作示例，实际应用中应使用更可靠的方法生成唯一文件名
        return System.currentTimeMillis() + "_" + originalFilename;
    }
    @Override
    public ResEntity<?> handleFileUpload(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return ResEntity.badRequest("上传失败，请选择文件");
        }
        // 生成保存文件的路径（例如：/uploads/headphotos/unique_file_name.jpg）
        String fileName = generateUniqueFileName(file.getOriginalFilename());

        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, FileUtil.multipartFileToFile(file));
        COSClient cosClient1 = initCOSClient();
        PutObjectResult putObjectResult = cosClient1.putObject(putObjectRequest);
        // 设置权限(公开读)
        cosClient1.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);

        // 假设生成一个可访问的新图片URL（实际应用可能需要根据实际服务器配置和路由规则来生成）
        String imageUrl = path + "/" + fileName;
        // 返回包含新图片URL的对象，以便前端更新显示
        return ResEntity.ok(imageUrl);
    }
}
