package jp.levtech.rookie.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findByIsSoldFalse(Pageable pageable); // 販売中の商品のみ取得（ページング対応）
}
