package cn.localhost01.util.qqwry;

/**
 * @Description:一条IP范围记录，不仅包括国家和区域，也包括起始IP和结束IP
 * @Author Ran.chunlin
 * @Date: Created in 1:43 2018-08-07
 */
class IPEntry {
    public String beginIp;
    public String endIp;
    public String country;
    public String area;

    /**
     * 构造函数
     */
    public IPEntry() {
        beginIp = endIp = country = area = "";
    }
}
