package hzcu.hjj.pet.cpa.common.interceptors;

import com.google.gson.Gson;
import hzcu.hjj.pet.cpa.common.response.ResEntity;
import hzcu.hjj.pet.cpa.common.util.UserSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration
public class HttpInterceptor implements HandlerInterceptor {

    private final Gson gson;
    private final UserSecurityUtil userSecurityUtil;

    @Autowired
    public HttpInterceptor(Gson gson, UserSecurityUtil userSecurityUtil) {
        this.gson = gson;
        this.userSecurityUtil = userSecurityUtil;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 为了处理跨域请求, 如果发送的是 OPTIONS 直接正常返回
        if (HttpMethod.OPTIONS.matches(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }
        // 设置请求头和响应头的编码格式
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        // 调用校验方法, 校验请求头的 Token 是否合法
        boolean isOk = userSecurityUtil.verifyWebToken(request, response);
        // 如果不合法, 返回 false, 否则为 true
        if (!isOk) {
            ResEntity<String> resultEntity = new ResEntity<>();
            resultEntity.setMessage("请重新登录");
            resultEntity.setStatusCode(404);
            response.getWriter().write(gson.toJson(resultEntity));
            return false;
        }
        response.setHeader("authorization", request.getHeader("authorization"));
        return true;
    }

}
