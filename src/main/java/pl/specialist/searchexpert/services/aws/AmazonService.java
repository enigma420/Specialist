package pl.specialist.searchexpert.services.aws;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSSessionCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Objects;

@Service
public class AmazonService {

    private AmazonS3 s3client;

    private String endpointUrl = "${amazonProperties.s3.eu-central-1.amazonaws.com}";
    private String bucketName = "${amazonProperties.specjalisto}";
    private String accessKey = "${amazonProperties.AKIAR7SPURLZVS5T5YOP}";
    private String secretKey = "${amazonProperties.0ZempZ/tuKXdFanqLJ7LJ+TzfyOE/nq5cpj48g7H}";

    @PostConstruct
    private void initializeAmazon(){
        BasicAWSCredentials credentials = new BasicAWSCredentials(this.accessKey,this.secretKey);
        s3client = AmazonS3ClientBuilder.standard().withRegion("eu-central-1").withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException{
        File convFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    private String generateFileName(MultipartFile multipart){
        return new Date().getTime() + "-" + multipart.getOriginalFilename().replace(" ","_");
    }

    private void uploadFileTos3bucket(String fileName, File file){
        s3client.putObject(new PutObjectRequest(bucketName,fileName,file)
                .withCannedAcl(CannedAccessControlList.PublicRead));
    }

    public String uploadFile(MultipartFile multipartFile){
        String fileUrl = "";
        try{
            File file = convertMultiPartToFile(multipartFile);
            String fileName = generateFileName(multipartFile);
//            if(file ==null) throw new Exception("file is null");
//            if(fileName.equals("")) throw new Exception("fileName is null");
            fileUrl = endpointUrl + "/" + bucketName + "/" + fileName;
            uploadFileTos3bucket(fileName,file);
            file.delete();
        }catch (Exception e){
            e.printStackTrace();
        }
        return "FileUrl: " + fileUrl;
    }

    public String deleteFileFromS3Bucket(String fileUrl){
        String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
        s3client.deleteObject(new DeleteObjectRequest(bucketName + "/", fileName));
        return "Successfully deleted";
    }

}
