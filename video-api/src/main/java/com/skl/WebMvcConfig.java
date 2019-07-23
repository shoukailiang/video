package com.skl;

import com.skl.controller.interceptor.MiniInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 图片上传至本地或服务器，配置虚拟路径
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/**")// 所有资源
        .addResourceLocations("classpath:/META-INF/resources/")   // 使 swagger网页正常
        .addResourceLocations("file:C:/video/");
  }

  /**
   * 注册拦截器
   * @return
   */
  @Bean
  public MiniInterceptor miniInterceptor(){
    return new MiniInterceptor();
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {

    String[] add = new String[]{"/user/**", "/video/upload", "/video/uploadCover",
        "/video/userLike", "/video/userUnLike", "/video/saveComment",
        "/bgm/**"};
    String[] exclude = new String[]{"/user/queryPublisher"};

    // 添加拦截器
    registry.addInterceptor(miniInterceptor()).addPathPatterns(add)
        .excludePathPatterns(exclude);

    super.addInterceptors(registry);
  }
}
