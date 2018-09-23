package net.j33r.digitalstore.domain;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import net.j33r.digitalstore.domain.store.Product;
import net.j33r.digitalstore.domain.store.StoreService;

/**
 * Application service that acts like a domain façade.
 *
 * @author joses
 */
@Service
@AllArgsConstructor
public class DigitalStoreApplicationService {

    private final StoreService storeService;

    /**
     * Retrieve all products from the store
     *
     * @return a List of Product
     */
    public List<Product> retrieveProducts() {
        return storeService.retrieveProducts();
    }
}
