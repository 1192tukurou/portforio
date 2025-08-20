package jp.levtech.rookie.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Page<Product> getAllAvailableProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findByIsSoldFalse(pageable);
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public Product saveProduct(Product product) {
        System.out.println("ProductService: Saving product to database. Name: " + product.getName());
        Product savedProduct = productRepository.save(product);
        System.out.println("ProductService: Product saved with ID: " + savedProduct.getId());
        return savedProduct;
    }

    public Product markProductAsSold(Long id) {
        System.out.println("ProductService: Attempting to mark product " + id + " as sold.");
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            product.setSold(true);
            Product savedProduct = productRepository.save(product);
            System.out.println("ProductService: Product " + id + " successfully marked as sold.");
            return savedProduct;
        } else {
            System.err.println("ProductService: Product " + id + " not found for marking as sold.");
            throw new RuntimeException("Product not found with id: " + id);
        }
    }
}
