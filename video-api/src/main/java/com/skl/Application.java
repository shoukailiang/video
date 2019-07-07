package com.skl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages="com.skl.mapper")
//定义扫描的路径从中找出标识了需要装配的类自动装配到spring的bean容器中
// org.n3r.idworker 生成独一无二的id
@ComponentScan(basePackages= {"com.skl","org.n3r.idworker"})
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

}
