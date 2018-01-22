package cn.localhost01.service;

import cn.localhost01.domain.PositionDO;

/**
 * @Description:位置服务类
 * @Author Ran.chunlin
 * @Date: Created in 15:04 2017-12-20
 */
public interface PositionService {

    /**
     * Description:查询百度地图，根据positionDO的ip，完善其地理位置信息 eq:不传ip将根据当前请求的ip进行查询 <BR>
     *
     * @author ran.chunlin
     * @date 2017-12-20 15:05
     * @param positionDO
     * @return boolean
     * @throws Exception
     * @version 1.0
     */
    boolean selectAndFill(PositionDO positionDO) throws  Exception;
}
