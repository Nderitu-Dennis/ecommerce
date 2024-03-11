package com.nderitu.ecommerce.services.admin.faq;

import com.nderitu.ecommerce.dto.FAQDto;
import com.nderitu.ecommerce.entity.FAQ;
import com.nderitu.ecommerce.entity.Product;
import com.nderitu.ecommerce.repository.FAQRepository;
import com.nderitu.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FAQServiceImpl implements FAQService {

    private final FAQRepository faqRepository;

    private final ProductRepository productRepository;

    public FAQDto postFAQ(Long productId, FAQDto faqDto) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isPresent()) {
            //        if product is present create FAQ
            FAQ faq=new FAQ();

            faq.setQuestion(faqDto.getQuestion());
            faq.setAnswer(faqDto.getAnswer());
            faq.setProduct(optionalProduct.get());

            return faqRepository.save(faq).getFAQDto();
        }

//        if product is not present
        return null;
    }
}
