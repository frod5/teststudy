package sample.cafekiosk.spring.api.service.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import sample.cafekiosk.spring.domain.product.ProductRepository;

@Component
@RequiredArgsConstructor
public class ProductNumberFactory {

    private final ProductRepository productRepository;
    public String createNextProduct() {
        String latestProductNumber = productRepository.findLatestProductNumber();

        if(!StringUtils.hasText(latestProductNumber)) {
            return "001";
        }

        int latestProductNumberInt = Integer.valueOf(latestProductNumber);
        int nextProductNumber = latestProductNumberInt + 1;

        return String.format("%03d",nextProductNumber);
    }
}
