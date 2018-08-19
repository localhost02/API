package cn.localhost01.controller;

import cn.localhost01.annotation.RequestFormBody;
import cn.localhost01.configuration.SmsProperties;
import cn.localhost01.constant.APICode;
import cn.localhost01.constant.APIMsg;
import cn.localhost01.constant.APISubCode;
import cn.localhost01.domain.CsdnDO;
import cn.localhost01.domain.JsonVO;
import cn.localhost01.service.CsdnService;
import cn.localhost01.constant.APISubMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:短信控制层
 * @Author Ran.chunlin
 * @Date: Created in 19:29 2017/12/16
 */
@RestController public class CsdnController {

    @Autowired private SmsProperties smsProperties;
    @Autowired private CsdnService csdnService;

    @PostMapping(value = "/csdn") public JsonVO sendMail(@RequestFormBody CsdnDO csdnDO) throws Exception {
        JsonVO jsonVO = new JsonVO();

        //1.进行校验
        if (csdnDO == null || csdnDO.getEmail() == null || csdnDO.getLink() == null)
            return jsonVO.withBase(APICode.BAD_REQUEST, APIMsg.BAD_REQUEST);

        //2.业务处理
        boolean isDownloadOk = csdnService.download(csdnDO);
        if (isDownloadOk)
            return jsonVO.withSub(APISubCode.SUCCESS, APISubMsg.SUCCESS);
        else
            return jsonVO.withSub(APISubCode.FAILED, APISubMsg.FAILED);
    }
}
