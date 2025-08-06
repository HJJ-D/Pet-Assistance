package hzcu.hjj.pet.cpa.common.config;

import hzcu.hjj.pet.cpa.common.interceptors.HttpInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

@Configuration
public class WebConfig extends WebMvcConfigurationSupport {

    private final HttpInterceptor httpInterceptor;

    @Autowired
    public WebConfig(HttpInterceptor httpInterceptor) {
        this.httpInterceptor = httpInterceptor;
    }

    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();

        //将java对象序列化为json数据,为消息转换器设置序列转换器
        converter.setObjectMapper(new JacksonObjectMapper());

        //将自己的消息转换器加入容器中
        converters.add(0, converter);
    }


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 跨域相关配置, 并让 authorization 可在响应头中出现
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("POST", "GET", "PUT", "PATCH", "OPTIONS", "DELETE")
                .exposedHeaders("authorization")
                .allowedHeaders("*")
                .maxAge(3600);
    }

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        // 设置自定义的拦截器, 拦截所有界面
        // 排除 /login 请求, 未防止 /login 失效, 将 /error 也加入
        registry.addInterceptor(httpInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/user/login", "/error", "/user/register");
        super.addInterceptors(registry);
    }

}
