package com.nderitu.ecommerce.services.admin.faq;

import com.nderitu.ecommerce.dto.FAQDto;

public interface FAQService {

    FAQDto postFAQ(Long productId, FAQDto faqDto);
}
