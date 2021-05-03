package com.sparta.springcore.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration //SPRING이 기동될때 이 클래스를 바라보고 @Bean 정의 되어있는것을 빈으로 담는다.
//BeanConfiguration 삭제
public class BeanConfiguration {
/*    @Bean
    public ProductRepository productRepository() {
        String dbId = "sa";
        String dbPassword = "";
        String dbUrl = "jdbc:h2:mem:springcoredb";
        return new ProductRepository(dbId, dbPassword, dbUrl);
    }

    @Bean
    @Autowired
    public ProductService productService(ProductRepository productRepository) {
        return new ProductService(productRepository);
    }*/
}