package cn.sc.love.product.controller;


import cn.sc.love.common.result.Result;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * @author YPT
 * @create 2023-05-07-6:22
 */
@RestController
@RequestMapping("/admin/product")
public class FileUploadController {

    @Value("${minio.endpointUrl}")
    private String endpointUrl;
    @Value("${minio.accessKey}")
    private String accessKey;
    @Value("${minio.secreKey}")
    private String secreKey;
    @Value("${minio.bucketName}")
    private String bucketName;

    @PostMapping("/fileUpload")
    public Result fileUpload(MultipartFile file) {

        String url = null;
        try {
            // Create a minioClient with the MinIO server playground, its access key and secret key.
            MinioClient minioClient = MinioClient.builder().endpoint(endpointUrl).credentials(accessKey, secreKey).build();

            // Make 'asiatrip' bucket if not exist.
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!found) {
                // Make a new bucket called 'asiatrip'.
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            } else {
                System.out.println("Bucket 'asiatrip' already exists.");
            }

//            // Upload '/home/user/Photos/asiaphotos.zip' as object name 'asiaphotos-2015.zip' to bucket
//            // 'asiatrip'.
//            minioClient.uploadObject(UploadObjectArgs.builder().bucket("asiatrip").object("asiaphotos-2015.zip").filename("/home/user/Photos/asiaphotos.zip").build());
//            System.out.println("'/home/user/Photos/asiaphotos.zip' is successfully uploaded as " + "object 'asiaphotos-2015.zip' to bucket 'asiatrip'.");
//              这个不行，无法明确知道文件位置

            // Upload known sized input stream.
            //文件名称
            String fileName = System.currentTimeMillis() + UUID.randomUUID().toString();
            minioClient.putObject(PutObjectArgs.builder().bucket(bucketName).object(fileName).stream(file.getInputStream(), file.getSize(), -1).contentType(file.getContentType()).build());

            url = endpointUrl + "/" + bucketName + "/" + fileName;
            System.out.println("图片的路径：" + url);


        } catch (Exception e) {
            e.printStackTrace();
        }

        return Result.ok(url);

    }


}
