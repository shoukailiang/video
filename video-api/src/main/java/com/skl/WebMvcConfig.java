package com.skl;

import org.springframework.context.annotation.Configuration;
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
        .addResourceLocations("file:C:/video-user-face-upload/");
  }
}
