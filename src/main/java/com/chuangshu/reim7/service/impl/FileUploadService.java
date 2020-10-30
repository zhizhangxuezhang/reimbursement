package com.chuangshu.reim7.service.impl;



import com.aliyun.oss.OSS;
import com.aliyun.oss.model.ListObjectsRequest;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.chuangshu.reim7.config.AliyunConfig;
import com.chuangshu.reim7.result.FileUploadResult;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;


/**
 * @author Guo Jiaqi
 * @version 1.0
 * @date 2020/10/2 23:20
 */
@Service
public class FileUploadService {
    // 允许上传的格式
    private static final String[] IMAGE_TYPE = new String[]{".bmp", ".jpg",
            ".jpeg", ".gif", ".png",};
    @Autowired
    private OSS ossClient;
    @Autowired
    private AliyunConfig aliyunConfig;

    /**
     * @author 团子
     * @desc 文件上传
     * @date 2019-07-31 11:31
     */
    public FileUploadResult upload(MultipartFile uploadFile) {
        // 校验图片格式
        boolean isLegal = false;
        //封装Result对象，并且将文件的byte数组放置到result对象中
        FileUploadResult fileUploadResult = new FileUploadResult();

        //文件新路径
        String fileName = uploadFile.getOriginalFilename();
        String filePath = getFilePath(fileName);
        // 上传到阿里云
        try {
            ossClient.putObject(aliyunConfig.getBucketName(), filePath, new
                    ByteArrayInputStream(uploadFile.getBytes()));
        } catch (Exception e) {
            e.printStackTrace();
            //上传失败
            fileUploadResult.setStatus("error");
            return fileUploadResult;
        }
        fileUploadResult.setStatus("done");
        fileUploadResult.setResponse("success");
        fileUploadResult.setName(this.aliyunConfig.getUrlPrefix() + filePath);
        fileUploadResult.setUid(String.valueOf(System.currentTimeMillis()));
        return fileUploadResult;
    }
    /**
     * @author 团子
     * @desc 生成路径以及文件名 例如：//images/2019/08/10/15564277465972939.jpg
     * @date 2019-07-31 11:31
     */
    private String getFilePath(String sourceFileName) {
        DateTime dateTime = new DateTime();
        return "images/" + dateTime.toString("yyyy")
                + "/" + dateTime.toString("MM") + "/"
                + dateTime.toString("dd") + "/" + System.currentTimeMillis() +
                RandomUtils.nextInt(100, 9999) + "." +
                StringUtils.substringAfterLast(sourceFileName, ".");
    }



    /**
     * @author 团子
     * @desc 查看文件列表
     * @date 2019-07-31 11:31
     */
    public List<OSSObjectSummary> list() {
        // 设置最大个数。
        final int maxKeys = 200;
        // 列举文件。
        ObjectListing objectListing = ossClient.listObjects(new ListObjectsRequest(aliyunConfig.getBucketName()).withMaxKeys(maxKeys));
        List<OSSObjectSummary> sums = objectListing.getObjectSummaries();
        return sums;
    }

    /**
     * @author 团子
     * @desc 删除文件
     * @date 2019-07-31 11:31
     */
    public FileUploadResult delete(String objectName) {
        // 根据BucketName,objectName删除文件
        ossClient.deleteObject(aliyunConfig.getBucketName(), objectName);
        FileUploadResult fileUploadResult = new FileUploadResult();
        fileUploadResult.setName(objectName);
        fileUploadResult.setStatus("removed");
        fileUploadResult.setResponse("success");
        return fileUploadResult;
    }

    /**
     * @author 团子
     * @desc 下载文件
     * @date 2019-07-31 11:31
     */
    public void exportOssFile(OutputStream os, String objectName) throws IOException {
        // ossObject包含文件所在的存储空间名称、文件名称、文件元信息以及一个输入流。
        OSSObject ossObject = ossClient.getObject(aliyunConfig.getBucketName(), objectName);
        // 读取文件内容。
        BufferedInputStream in = new BufferedInputStream(ossObject.getObjectContent());
        BufferedOutputStream out = new BufferedOutputStream(os);
        byte[] buffer = new byte[1024];
        int lenght = 0;
        while ((lenght = in.read(buffer)) != -1) {
            out.write(buffer, 0, lenght);
        }
        if (out != null) {
            out.flush();
            out.close();
        }
        if (in != null) {
            in.close();
        }
    }
}
