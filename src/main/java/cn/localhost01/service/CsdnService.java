package cn.localhost01.service;

import cn.localhost01.domain.CsdnDO;

/**
 * @Description:Csdn下载接口
 * @Author Ran.chunlin
 * @Date: Created in 14:55 2017/12/17
 */
public interface CsdnService {

    /**
     * Description:Csdn下载 <BR>
     *
     * @param csdnDO
     *
     * @return boolean 是否下载成功
     *
     * @throws Exception
     * @author ran.chunlin
     * @date 2017/12/16 18:16
     * @version 1.0
     */
    boolean download(CsdnDO csdnDO) throws Exception;
}
