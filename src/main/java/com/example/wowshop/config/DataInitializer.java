package com.example.wowshop.config;

import java.math.BigDecimal;
import java.util.List;

import com.example.wowshop.product.Product;
import com.example.wowshop.product.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner seedProducts(ProductRepository productRepository) {
        return args -> {
            if (productRepository.count() > 0) {
                return;
            }
            productRepository.saveAll(List.of(
                    new Product("Aero Knit Hoodie", "FASHION", BigDecimal.valueOf(89000), 30, "가볍고 탄탄한 조직감의 프리미엄 니트 후디.", "BEST", "https://images.unsplash.com/photo-1556821840-3a63f95609a7?auto=format&fit=crop&w=900&q=80"),
                    new Product("Orbit Desk Lamp", "LIVING", BigDecimal.valueOf(129000), 18, "집중 모드와 무드 모드를 오가는 알루미늄 데스크 램프.", "NEW", "https://images.unsplash.com/photo-1507473885765-e6ed057f782c?auto=format&fit=crop&w=900&q=80"),
                    new Product("Pulse Wireless Buds", "TECH", BigDecimal.valueOf(159000), 22, "깊은 저음과 선명한 통화 품질을 갖춘 무선 이어버드.", "HOT", "https://images.unsplash.com/photo-1606220588913-b3aacb4d2f46?auto=format&fit=crop&w=900&q=80"),
                    new Product("Cloud Runner 2", "FASHION", BigDecimal.valueOf(139000), 15, "하루 종일 가볍게 걷는 쿠셔닝 러닝화.", "LIMITED", "https://images.unsplash.com/photo-1542291026-7eec264c27ff?auto=format&fit=crop&w=900&q=80"),
                    new Product("Ceramic Brew Set", "LIVING", BigDecimal.valueOf(72000), 40, "느린 아침을 위한 핸드드립 세라믹 브루 세트.", "GIFT", "https://images.unsplash.com/photo-1495474472287-4d71bcdd2085?auto=format&fit=crop&w=900&q=80"),
                    new Product("Nova Smart Watch", "TECH", BigDecimal.valueOf(249000), 12, "운동, 수면, 알림을 한 화면에서 관리하는 스마트 워치.", "PRO", "https://images.unsplash.com/photo-1523275335684-37898b6baf30?auto=format&fit=crop&w=900&q=80")
            ));
        };
    }
}
