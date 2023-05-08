package cn.sc.love.product.controller;


import cn.sc.love.common.result.Result;
import io.minio.PutObjectArgs;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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

//              这个不行，无法明确知道文件位置
//            // Create a minioClient with the MinIO server playground, its access key and secret key.
//            MinioClient minioClient = MinioClient.builder().endpoint(endpointUrl).credentials(accessKey, secreKey).build();
//
//            // Make 'asiatrip' bucket if not exist.
//            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
//            if (!found) {
//                // Make a new bucket called 'asiatrip'.
//                minioClient.makeBucket(MakeBucketArgs.builder().bucket("asiatrip").build());
//            } else {
//                System.out.println("Bucket 'asiatrip' already exists.");
//            }
//
//            // Upload '/home/user/Photos/asiaphotos.zip' as object name 'asiaphotos-2015.zip' to bucket
//            // 'asiatrip'.
//            minioClient.uploadObject(UploadObjectArgs.builder().bucket("asiatrip").object("asiaphotos-2015.zip").filename("/home/user/Photos/asiaphotos.zip").build());
//            System.out.println("'/home/user/Photos/asiaphotos.zip' is successfully uploaded as " + "object 'asiaphotos-2015.zip' to bucket 'asiatrip'.");
//

        // Upload known sized input stream.
        minioClient.putObject(
                PutObjectArgs.builder().bucket("my-bucketname").object("my-objectname").stream(
                                inputStream, size, -1)
                        .contentType("video/mp4")
                        .build());

        return Result.ok();

    }


}
