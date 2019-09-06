package com.stylefeng.guns.alipay.modular.auth.util;


import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.ObjectMetadata;

import com.stylefeng.guns.alipay.config.OSSConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;

/**
 * @author 申涛涛
 * @date 2019/9/5 22:59
 */
@Slf4j
@Component
public class MyOssClientUtil {
//    @Value("${myoss.endpoint}")
    private String endpoint = "http://oss-cn-beijing.aliyuncs.com";
//    @Value("${myoss.bucket}")
    private String bucket = "meetingfilm";
//    @Value("${myoss.accessKey}")
    private String accessKey ="LTAI4Fc7HsAaCZKXdWWks3X4";
//    @Value("${myoss.secret}")
    private String secret ="ieQvUHoBxB90gedJdcEaMWV5lfcp3k";


    //Constructs a client instance with your account for accessing OSS
    private static OSSClient client = null;
    public OSSClient getInstance()
    {
        if(null == client)
        {
            client = new OSSClient(endpoint, accessKey, secret);
        }
        return client;
    }
    /**
     * 上传单个文件到OSS
     * @param uploadFile 要上传的文件File对象
     * @return
     */
    public boolean upload(File uploadFile) {
        try {
            if(uploadFile == null){
                return false;
            }

            getInstance().putObject(bucket, uploadFile.getName(), uploadFile);
            log.info("FileUploadUtils#upload success: bucket="+bucket+", filename="+uploadFile.getName());
            return true;
        } catch (OSSException oe) {
            log.error("OSSException", oe);
        } catch (ClientException ce) {
            log.error("ClientException", ce);
        }
        return false;
    }

    /**
     * 获取图片的url
     * @creator shentaotao
     * @creat date 2019/9/5 23:52
     * @param key
     * @return java.lang.String
     * @throws
     * @since
     */
    public String getUrl(String key) {
        // 设置URL过期时间为10年 3600l* 1000*24*365*10
        OSSClient ossClient = new OSSClient(endpoint, accessKey, secret);
        Date expiration = new Date(System.currentTimeMillis() + 3600L * 1000 * 24 * 365 * 10);
        // 生成URL
        URL url = ossClient.generatePresignedUrl(bucket, key, expiration);
        if (url != null) {
            return url.toString();
        }
        return null;
    }

}
