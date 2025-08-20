package jp.levtech.rookie.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private static final String UPLOAD_DIR = "uploads/";

    @Autowired
    private ProductService productService;

    // アプリケーション起動時にアップロードディレクトリが存在することを確認し、なければ作成する
    static {
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs(); // ディレクトリがなければ作成
        }
    }

    @GetMapping
    public ResponseEntity<Page<Product>> getAllAvailableProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size) {
        Page<Product> products = productService.getAllAvailableProducts(page, size);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Optional<Product> product = productService.getProductById(id);
        return product.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestParam("name") String name,
                                                 @RequestParam("price") Double price,
                                                 @RequestParam(value = "image", required = false) MultipartFile image) {
        System.out.println("ProductController: createProduct called.");
        System.out.println("Name: " + name + ", Price: " + price);
        Product newProduct = new Product();
        newProduct.setName(name);
        newProduct.setPrice(price);
        newProduct.setSold(false);

        if (image != null && !image.isEmpty()) {
            try {
                String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
                Path path = Paths.get(UPLOAD_DIR + fileName);
                System.out.println("Attempting to save image to: " + path.toAbsolutePath());
                Files.write(path, image.getBytes());
                newProduct.setImageUrl("/images/" + fileName);
                System.out.println("Image saved. Image URL set to: " + newProduct.getImageUrl());
            } catch (IOException e) {
                System.err.println("ProductController: Error saving image: " + e.getMessage());
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }

        Product savedProduct = productService.saveProduct(newProduct);
        System.out.println("ProductController: Product saved to DB. ID: " + savedProduct.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }

    @PutMapping("/{id}/purchase")
    public ResponseEntity<Product> purchaseProduct(@PathVariable Long id) {
        System.out.println("ProductController: purchaseProduct called for ID: " + id);
        try {
            Product purchasedProduct = productService.markProductAsSold(id);
            System.out.println("ProductController: Product " + id + " marked as sold.");
            return ResponseEntity.ok(purchasedProduct);
        } catch (RuntimeException e) {
            System.err.println("ProductController: Error purchasing product " + id + ": " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
