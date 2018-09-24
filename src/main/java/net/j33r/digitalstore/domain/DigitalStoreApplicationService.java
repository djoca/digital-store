package net.j33r.digitalstore.domain;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import net.j33r.digitalstore.domain.store.CreditCard;
import net.j33r.digitalstore.domain.store.Customer;
import net.j33r.digitalstore.domain.store.PaymentGatewayException;
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

    public List<Product> retrieveProductsFromList(final Set<Long> ids) {
        return storeService.retrieveProductsFromList(ids);
    }

    public void checkout(final Customer customer, final CreditCard creditCard, final Set<Long> ids)
            throws PaymentGatewayException {
        storeService.checkout(customer, creditCard, ids);
    }
}
