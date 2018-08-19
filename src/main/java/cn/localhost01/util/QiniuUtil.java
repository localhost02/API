package cn.localhost01.util;

import cn.localhost01.configuration.QiniuProperties;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;

import java.io.File;

/**
 * @Description: 七牛工具类
 * @Author Ran.chunlin
 * @Date: Created in 0:44 2018-08-20
 */
public class QiniuUtil {

    /**
     * 认证对象
     */
    private Auth auth;
    /**
     * 上传对象
     */
    private UploadManager uploadManager = new UploadManager(new Configuration());
    /**
     * 空间名
     */
    private String bucketName;
    private String outerLink;

    public QiniuUtil(QiniuProperties qiniuProperties) {

        //配置
        this.auth = Auth.create(qiniuProperties.getAk(), qiniuProperties.getSk());
        this.outerLink = qiniuProperties.getOuterLink();
        this.bucketName = qiniuProperties.getBucketName();
    }
    /**指定保存到七牛的文件名--同名上传会报错  {"error":"file exists"}*/
    /**
     * {"hash":"FrQF5eX_kNsNKwgGNeJ4TbBA0Xzr","key":"aa1.jpg"} 正常返回 key为七牛空间地址 http:/xxxx.com/aa1.jpg
     */

    private String getUpToken() {
        return auth.uploadToken(bucketName);
    }

    /**
     * 上传七牛云
     *
     * @param file 本地文件
     * @param newFileName 七牛云新名字
     *
     * @return
     */
    public String upload(File file, String newFileName) {
        try {
            // 1.调用put方法上传
            Response res = uploadManager.put(file, newFileName, getUpToken());
            // 2.解析返回
            if (res.statusCode == 200)
                // 在源地址后面加上“?attname=”，可实现点击链接即下载
                return outerLink + "/" + newFileName + "?attname=";
            else
                LogUtil.getLogger(QiniuUtil.class).error("上传七牛云失败", res.bodyString());

            return res.bodyString();
        } catch (QiniuException e) {
            LogUtil.getLogger(QiniuUtil.class).error("上传七牛云失败", e);
        }
        return null;
    }

    /**
     * 上传七牛云
     *
     * @param file 本地文件
     *
     * @return
     */
    public String upload(File file) {
        return upload(file, file.getName());
    }

    /**
     * 上传七牛云
     *
     * @param localPath 本地文件路径
     *
     * @return
     */
    public String upload(String localPath) {
        return upload(new File(localPath));
    }

}
