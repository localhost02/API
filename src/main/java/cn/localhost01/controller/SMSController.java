package cn.localhost01.controller;

import cn.localhost01.constant.APICode;
import cn.localhost01.constant.APIMsg;
import cn.localhost01.constant.APISubCode;
import cn.localhost01.constant.APISubMsg;
import cn.localhost01.domain.JsonVO;
import cn.localhost01.domain.SMSDO;
import cn.localhost01.service.SMSService;
import cn.localhost01.annotation.RequestFormBody;
import cn.localhost01.configuration.SMSProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:短信控制层
 * @Author Ran.chunlin
 * @Date: Created in 19:29 2017/12/16
 */
@RestController public class SMSController {

    @Autowired private SMSProperties smsProperties;
    @Autowired private SMSService smsService;

    @PostMapping(value = "/sms") public JsonVO sendMail(@RequestFormBody SMSDO smsdoDO) throws Exception {
        JsonVO jsonVO = new JsonVO();

        //1.进行校验
        if (smsdoDO == null || smsdoDO.getPhone()==null)
            return jsonVO.withCodeAndMsg(APICode.BAD_REQUEST, APIMsg.BAD_REQUEST);

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
            return jsonVO.withSubCodeAndSubMsg(APISubCode.SUCCESS, APISubMsg.SUCCESS);
        else
            return jsonVO.withSubCodeAndSubMsg(APISubCode.FAILED, APISubMsg.FAILED);
    }
}
