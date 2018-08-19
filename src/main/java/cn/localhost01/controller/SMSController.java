package cn.localhost01.controller;

import cn.localhost01.annotation.RequestFormBody;
import cn.localhost01.configuration.SmsProperties;
import cn.localhost01.constant.APICode;
import cn.localhost01.constant.APIMsg;
import cn.localhost01.constant.APISubCode;
import cn.localhost01.domain.JsonVO;
import cn.localhost01.domain.SmsDO;
import cn.localhost01.service.SmsService;
import cn.localhost01.constant.APISubMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:短信控制层
 * @Author Ran.chunlin
 * @Date: Created in 19:29 2017/12/16
 */
@RestController public class SmsController {

    @Autowired private SmsProperties smsProperties;
    @Autowired private SmsService smsService;

    @PostMapping(value = "/sms") public JsonVO sendMail(@RequestFormBody SmsDO smsdoDO) throws Exception {
        JsonVO jsonVO = new JsonVO();

        //1.进行校验
        if (smsdoDO == null || smsdoDO.getPhone()==null)
            return jsonVO.withBase(APICode.BAD_REQUEST, APIMsg.BAD_REQUEST);

        if (smsdoDO.getContent()==null)
            smsdoDO.setContent(smsProperties.getContent());

        if (smsdoDO.getUrl()==null||smsdoDO.getUserName()==null||smsdoDO.getPassword()==null){
            smsdoDO.setUrl(smsProperties.getUrl());
            smsdoDO.setUserName(smsProperties.getUserName());
            smsdoDO.setPassword(smsProperties.getPassword());
        }

        //2.业务处理
        boolean isSendOk = smsService.send(smsdoDO);
        if (isSendOk)
            return jsonVO.withSub(APISubCode.SUCCESS, APISubMsg.SUCCESS);
        else
            return jsonVO.withSub(APISubCode.FAILED, APISubMsg.FAILED);
    }
}
