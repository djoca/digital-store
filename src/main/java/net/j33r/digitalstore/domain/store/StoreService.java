package net.j33r.digitalstore.domain.store;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
     * @return a List of Product
     */
    public List<Product> retrieveProducts() {
        return productRepository.findProducts();
    }

    /**
     * Return a List of Product with the specified ids
     *
     * @param ids
     *            the list of ids to retrieve
     * @return a List of Product
     */
    public List<Product> retrieveProductsFromList(final Set<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return new ArrayList<>();
        }

        return productRepository.findProductsFromList(ids);
    }

}
