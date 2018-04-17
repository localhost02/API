package cn.localhost01.service.impl;

import cn.localhost01.domain.PositionDO;
import cn.localhost01.service.PositionService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import cn.localhost01.util.HttpUtil;
import cn.localhost01.configuration.PositionProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @Description:短信发送实现类
 * @Author Ran.chunlin
 * @Date: Created in 14:58 2017/12/17
 */
@Service public class PositionServiceImpl implements PositionService {

    @Autowired private PositionProperties positionProperties;

    @Override public boolean selectAndFill(PositionDO positionDO) throws Exception {
        //1.构造请求地址
        StringBuilder sb = new StringBuilder();
        String url=positionProperties.getUrl();
        url+="?ak=" + positionDO.getAk();
        url+="&coor=" + positionProperties.getCoor();
        url+="&ip=" + positionDO.getIp();

        //2.开始请求
        String result = HttpUtil.getFromURL(url);

        //3.解析结果
        JSONObject j_all = JSON.parseObject(result);
        if (Objects.equals(j_all.getString("status"), "0")) {//请求解析成功
            JSONObject j_content = j_all.getJSONObject("content");

            JSONObject j_point = j_content.getJSONObject("point");
            String longitude=j_point.getString("x");
            String latitude=j_point.getString("y");

            JSONObject j_address_detail = j_content.getJSONObject("address_detail");
            String city = j_address_detail.getString("city");
            String district = j_address_detail.getString("district");
            String province = j_address_detail.getString("province");
            String street = j_address_detail.getString("street");
            String street_number = j_address_detail.getString("street_number");

            //设置对象值
            positionDO.setLongitude(longitude);
            positionDO.setLatitude(latitude);
            positionDO.setCity(city);
            positionDO.setDistrict(district);
            positionDO.setProvince(province);
            positionDO.setStreet(street);
            positionDO.setStreet_number(street_number);

            return true;
        }else
            return false;
    }
}
