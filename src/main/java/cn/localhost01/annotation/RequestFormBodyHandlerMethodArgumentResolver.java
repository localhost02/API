package cn.localhost01.annotation;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.MethodParameter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

/**
 * @Description:
 * @Author Ran.chunlin
 * @Date: Created in 4:48 2017/12/17
 */
public class RequestFormBodyHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Override public boolean supportsParameter(MethodParameter methodParameter) {
        // 过滤出符合条件的参数，这里指的是加了RequestFormBody注解的参数
        return methodParameter.hasParameterAnnotation(RequestFormBody.class);
    }

    @Override public Object resolveArgument(MethodParameter methodParameter,
            ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest,
            WebDataBinderFactory webDataBinderFactory) throws HttpMessageNotReadableException {
        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);

        Map requestMap = request.getParameterMap();
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            String jsonString = objectMapper.writeValueAsString(requestMap);
            return objectMapper.readValue(jsonString, methodParameter.getParameterType());
        } catch (IOException e) {
            throw new HttpMessageNotReadableException(
                    "Could not convert this data of x-www-form-urlencoded to JavaBean!");
        }
        // return JSONObject.parseObject(JSONObject.toJSONString(requestMap),methodParameter.getParameterType());
    }

}
