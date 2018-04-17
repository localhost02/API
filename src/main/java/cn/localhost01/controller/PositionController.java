package cn.localhost01.controller;

import cn.localhost01.service.PositionService;
import cn.localhost01.util.HttpUtil;
import cn.localhost01.annotation.RequestFormBody;
import cn.localhost01.configuration.PositionProperties;
import cn.localhost01.constant.APICode;
import cn.localhost01.constant.APIMsg;
import cn.localhost01.domain.JsonVO;
import cn.localhost01.domain.PositionDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description:位置控制层
 * @Author Ran.chunlin
 * @Date: Created in 19:29 2017/12/16
 */
@RestController public class PositionController {

    @Autowired private PositionProperties positionProperties;
    @Autowired private PositionService positionService;

    @GetMapping(value = "/position") public JsonVO getPosition(HttpServletRequest request,
            @RequestFormBody PositionDO positionDO) throws Exception {
        JsonVO jsonVO = new JsonVO();

        //1.进行校验
        if (positionDO == null)
            return jsonVO.withCodeAndMsg(APICode.BAD_REQUEST, APIMsg.BAD_REQUEST);

        if (positionDO.getAk() == null)
            positionDO.setAk(positionProperties.getAk());

        if (positionDO.getIp() == null)
            positionDO.setIp(HttpUtil.getIpAddress(request));

        //2.业务处理
        boolean isSuccess = positionService.selectAndFill(positionDO);
        if (isSuccess) {
            jsonVO.put("positionDO", positionDO);
            return jsonVO.withCodeAndMsg(APICode.OK, APIMsg.OK);
        } else
            return jsonVO.withCodeAndMsg(APICode.NOT_FOUND, APIMsg.NOT_FOUND);
    }
}
