package cn.localhost01.service.impl;

import cn.localhost01.constant.APICode;
import cn.localhost01.domain.SmsDO;
import cn.localhost01.service.SmsService;
import cn.localhost01.util.LogUtil;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description:短信发送实现类
 * @Author Ran.chunlin
 * @Date: Created in 14:58 2017/12/17
 */
@Service public class SmsServiceImpl implements SmsService {

    @Override public boolean send(SmsDO smsDO) throws Exception {

        //1. 初始化需要使用的对象
        HttpPost post = new HttpPost(smsDO.getUrl());

        //请求参数初始化
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("username", smsDO.getUserName()));
        params.add(new BasicNameValuePair("password",
                md5Encode(smsDO.getUserName() + md5Encode(smsDO.getPassword(), ""), "")));
        params.add(new BasicNameValuePair("mobile", smsDO.getPhone()));
        params.add(new BasicNameValuePair("content", URLEncoder.encode(smsDO.getContent(), "UTF-8")));
        params.add(new BasicNameValuePair("dstime", ""));
        post.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));// 设置编码

        // 2.发送Post,并返回HttpResponse对象
        HttpResponse response;
        try {
            response = HttpClients.createDefault().execute(post);
        } catch (IOException e) {
            LogUtil.getLogger(SmsServiceImpl.class).warn(smsDO.getPhone() + "的短信发送失败", e);
            return false;
        }

        // 3.解析结果
        if (response.getStatusLine().getStatusCode() == APICode.OK) {
            String result = EntityUtils.toString(response.getEntity());
            if (result.length() > 3)//返回的发送处理单号，大于三位即表示成功
                return true;
        }
        return false;
    }

    /**
     * Description:32位小写MD5加密 <BR>
     *
     * @param data 数据
     * @param salt 盐
     *
     * @return java.lang.String
     *
     * @throws Exception
     * @author ran.chunlin
     * @date 2017/12/17 16:10
     * @version 1.0
     */
    private static String md5Encode(String data, String salt) {
        try {

            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(data.getBytes("UTF-8"));

            StringBuilder result = new StringBuilder(32);
            byte[] temp = md5.digest(salt.getBytes("UTF-8"));
            for (byte t : temp)
                result.append(Integer.toHexString((0x000000ff & t) | 0xffffff00).substring(6));

            return result.toString();
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            LogUtil.getLogger(SmsServiceImpl.class).error("32位小写MD5加密异常", e);
            return "";
        }
    }
}
