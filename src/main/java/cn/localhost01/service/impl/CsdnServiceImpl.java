package cn.localhost01.service.impl;

import cn.localhost01.configuration.CsdnProperties;
import cn.localhost01.configuration.MailProperties;
import cn.localhost01.configuration.QiniuProperties;
import cn.localhost01.domain.CsdnDO;
import cn.localhost01.domain.MailDO;
import cn.localhost01.service.CsdnService;
import cn.localhost01.service.MailService;
import cn.localhost01.util.ExpiryMap;
import cn.localhost01.util.HttpUtil;
import cn.localhost01.util.LogUtil;
import cn.localhost01.util.QiniuUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description:Csdn下载实现类
 * @Author Ran.chunlin
 * @Date: Created in 14:58 2017/12/17
 */
@Service public class CsdnServiceImpl implements CsdnService {

    @Autowired private CsdnProperties csdnProperties;
    @Autowired private QiniuProperties qiniuProperties;
    @Autowired private MailProperties mailProperties;
    @Autowired private MailService mailService;

    private ExpiryMap<String, String> csdnMap = new ExpiryMap<>(6 * 24 * 60 * 60);
    private static final String LOGIN_COOKIE = "login-cookie";

    @Override public boolean download(CsdnDO csdnDO) throws Exception {

        //1.是否已登录CSDN
        int count = 0;
        while (count < 3 && !csdnMap.containsKey(LOGIN_COOKIE)) {
            String html = HttpUtil.getResultByURL(csdnProperties.getLoginUrl());
            Document doc = Jsoup.parse(html);
            Element form = doc.getElementById("fm1");
            String location = form.attr("action");
            String lt = form.select("input[name=lt]").get(0).val();
            String execution = form.select("input[name=execution]").get(0).val();
            String _eventId = form.select("input[name=_eventId]").get(0).val();

            Map<String, String> params = new HashMap<>();
            params.put("username", csdnProperties.getUserName());
            params.put("password", csdnProperties.getPassWord());
            params.put("lt", lt);
            params.put("execution", execution);
            params.put("_eventId", _eventId);
            //CSDN貌似判断机器人，睡眠一下，增加成功率
            Thread.sleep(1000);
            String loginCookie = HttpUtil.login(location, params);

            if (!loginCookie.matches(".*UserToken=\"\".*"))
                csdnMap.put(LOGIN_COOKIE, loginCookie);
            count++;
        }

        if (csdnMap.isEmpty()) {
            //登录失败
            LogUtil.getLogger(CsdnServiceImpl.class).warn("CSDN登录失败，请注意！");
            return false;
        }

        //2.下载文件
        String html = HttpUtil.getResultByURL(csdnDO.getLink(), csdnMap.get(LOGIN_COOKIE));
        Document doc = Jsoup.parse(html);
        String realUrl = doc.getElementById("vip_btn").attr("href");
        HttpURLConnection connection = HttpUtil.getConnectionByURL(realUrl, csdnMap.get(LOGIN_COOKIE));
        connection.connect();

        //解析文件名
        String Disposition = connection.getHeaderField("Content-Disposition");
        Matcher matcher = Pattern.compile(".*?;filename=\"(.*)\"").matcher(Disposition);
        String filename;
        if (matcher.find()) {
            filename = URLDecoder.decode(matcher.group(1), "UTF-8").replace(" ", "_");
        } else {
            LogUtil.getLogger(CsdnServiceImpl.class).warn("CSDN下载失败，请注意！");
            return false;
        }

        //保存到本地
        File dir = new File(csdnProperties.getLocalDir());
        if (!dir.exists())
            dir.mkdir();
        File file = new File(csdnProperties.getLocalDir() + "/" + filename);
        if (!file.exists()) {
            file.createNewFile();
            InputStream is = connection.getInputStream();
            FileOutputStream fos = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int len;
            while ((len = is.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }
        }

        //3.上传文件到七牛云
        QiniuUtil qiniuUtil = new QiniuUtil(qiniuProperties);
        String fileUrl = qiniuUtil.upload(file);

        //4.发送邮件
        MailDO mailDO = new MailDO();
        mailDO.setSubject(filename);
        mailDO.setContent(fileUrl + "<br/><br/><br/><br/><br/>Trip：如果有任何问题，请GitHub上联系作者[localhost02]，谢谢支持！");
        mailDO.setReceiver(csdnDO.getEmail());

        return mailService.send(mailDO);
    }
}
