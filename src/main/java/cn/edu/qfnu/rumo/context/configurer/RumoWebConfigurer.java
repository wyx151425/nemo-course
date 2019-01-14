package cn.edu.qfnu.rumo.context.configurer;

import cn.edu.qfnu.rumo.context.interceptor.RumoInterceptor;
import org.springframework.format.FormatterRegistry;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Nemo Web配置器
 *
 * @author 王振琦
 * createAt: 2018/08/02
 * updateAt: 2018/08/03
 */
@Component
public class RumoWebConfigurer implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {

    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RumoInterceptor())
                .addPathPatterns("/api/**")
                .addPathPatterns("")
                .addPathPatterns("/")
                .addPathPatterns("/index")
                .addPathPatterns("/user/**")
                .addPathPatterns("/author/**")
                .addPathPatterns("/book/**")
                .addPathPatterns("/admin/**");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("").setViewName("index");
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/index").setViewName("index");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/register").setViewName("register");
        registry.addViewController("/user/blog").setViewName("user-blog");
        registry.addViewController("/user/book").setViewName("user-book");
        registry.addViewController("/user/page").setViewName("user-page");
        registry.addViewController("/user/settings").setViewName("user-settings");
        registry.addViewController("/author/list").setViewName("author-list");
        registry.addViewController("/author/blog").setViewName("author-blog");
        registry.addViewController("/author/book").setViewName("author-book");
        registry.addViewController("/book/list").setViewName("book-list");
        registry.addViewController("/admin/index").setViewName("admin-index");
    }
}
