package com.sparta.springcore;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;

//@RequiredArgsConstructor // final로 선언된 멤버 변수를 자동으로 생성합니다.
@RestController // JSON으로 데이터를 주고받음을 선언합니다.
public class ProductController {
    // 멤버 변수 선언
    private final ProductService productService;

    // 생성자: ProductController() 가 생성될 때 호출됨
    @Autowired
    public ProductController(ProductService productService) {
        // 멤버 변수 생성
        this.productService = productService;
    }

    // 등록된 전체 상품 목록 조회
    @GetMapping("/api/products")
    public List<Product> getProducts() throws SQLException {
        List<Product> products = productService.getProducts();
        // 응답 보내기
        return products;
    }

    // 신규 상품 등록
    @PostMapping("/api/products")
    public Product createProduct(@RequestBody ProductRequestDto requestDto) throws SQLException {
        Product product = productService.createProduct(requestDto);
        // 응답 보내기
        return product;
    }

    // 설정 가격 변경
    @PutMapping("/api/products/{id}")
    public Long updateProduct(@PathVariable Long id, @RequestBody ProductMypriceRequestDto requestDto) throws SQLException {
        Product product = productService.updateProduct(id, requestDto);
        return product.getId();
    }

    //실습
    // 1. 최저가 업데이트 조건 변경 : 사용자가 상품에 대한 최저가 업데이트 시 0월 이하의 값을 입력할 경우, 에러를 발생시키자
    // 2. db 테이블 이름 변경 : Product 테이블의 lprice를 lowprice로 변경
    // 3. db의 id, pw를 다음과 같이 변경
    // id : sa -> sb
    // pw : (없음) -> bs
}
