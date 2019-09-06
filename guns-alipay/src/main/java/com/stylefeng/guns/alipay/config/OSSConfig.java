package com.stylefeng.guns.alipay.config;

import com.aliyun.oss.OSSClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author 申涛涛
 * @date 2019/9/6 8:54
 */
@ConfigurationProperties(prefix = "myoss")
@Component
public class OSSConfig {
    @Value("${myoss.buket}")
    static String bucket;
    @Value("${myoss.endpoint}")
    static String endpoint ;
    @Value("${myoss.accessKey}")
    static String accessKey ;
    @Value("${myoss.secret}")
    static String secret ;

    public static String getBucket() {
        return bucket;
    }

    public static void setBucket(String bucket) {
        OSSConfig.bucket = bucket;
    }

    public static String getEndpoint() {
        return endpoint;
    }

    public static void setEndpoint(String endpoint) {
        OSSConfig.endpoint = endpoint;
    }

    public static String getAccessKey() {
        return accessKey;
    }

    public static void setAccessKey(String accessKey) {
        OSSConfig.accessKey = accessKey;
    }

    public static String getSecret() {
        return secret;
    }

    public static void setSecret(String secret) {
        OSSConfig.secret = secret;
    }

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
}
