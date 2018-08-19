package cn.localhost01.filter;

import cn.localhost01.configuration.CommonProperties;
import cn.localhost01.constant.APISubCode;
import cn.localhost01.domain.JsonVO;
import cn.localhost01.util.ExpiryMap;
import cn.localhost01.util.HttpUtil;
import cn.localhost01.util.qqwry.IPSeeker;
import cn.localhost01.constant.APISubMsg;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;

import static cn.localhost01.constant.APIConstant.AREA_MOBILE;
import static cn.localhost01.constant.APIConstant.AREA_Telecom;
import static cn.localhost01.constant.APIConstant.AREA_Unicom;

/**
 * @Description:ip访问限制过滤器
 * @Author Ran.chunlin
 * @Date: Created in 1:33 2018-08-08
 */
@Order(1) @WebFilter(filterName = "ipLimitFilter", urlPatterns = "/*") public class IPFilter implements Filter {
    private static ExpiryMap<String, Integer> ipMap = new ExpiryMap();

    private CommonProperties commonProperties;

    private IPSeeker ipSeeker;

    @Override public void init(FilterConfig filterConfig) throws ServletException {
        ServletContext context = filterConfig.getServletContext();
        ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(context);
        commonProperties = ctx.getBean(CommonProperties.class);
        ipSeeker = new IPSeeker(commonProperties.getQqwryPath());
    }

    @Override public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        //1.初始返回对象
        JsonVO jsonVO = new JsonVO();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");

        //2.解析请求来源
        String ip = HttpUtil.getIpAddress((HttpServletRequest) request);
        //本地网络可直接访问
        if (HttpUtil.isLocalNetwork(ip)) {
            filterChain.doFilter(request, response);
            return;
        }
        String area = ipSeeker.getArea(ip);
        boolean isHomeUser = AREA_MOBILE.equals(area) || AREA_Unicom.equals(area) || AREA_Telecom.equals(area);
        if (!isHomeUser) {
            //如果不是家庭用户（非中国三大运营商）
            try (PrintWriter writer = response.getWriter()) {
                writer.append(jsonVO.withSubStr(APISubCode.NOT_HOME_USER, APISubMsg.NOT_HOME_USER).toString());
            }
            return;
        }

        //3.判断是否超出请求限制
        if (ipMap.containsKey(ip) && ipMap.get(ip) >= commonProperties.getRequestLimit()) {
            //请求超出限制
            try (PrintWriter writer = response.getWriter()) {
                writer.append(jsonVO.withSubStr(APISubCode.OVER_LIMIT, APISubMsg.OVER_LIMIT).toString());
            }
            return;
        } else {
            //未访问过或未超过限制
            Integer v = ipMap.get(ip);
            ipMap.put(ip, (v == null ? 0 : v) + 1);
            filterChain.doFilter(request, response);
        }
    }

    @Override public void destroy() {

    }
}
