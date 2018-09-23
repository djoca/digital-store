package net.j33r.digitalstore.domain.store;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

/**
 * The Store service class
 *
 * @author joses
 */
@Service
@AllArgsConstructor
public class StoreService {

    private final ProductRepository productRepository;

    /**
     * Return all the products from the store.
     *
     * @return A List of Product
     */
    public List<Product> retrieveProducts() {
        return productRepository.findProducts();
    }
}
