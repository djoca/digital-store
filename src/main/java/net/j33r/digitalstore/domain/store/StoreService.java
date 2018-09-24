package net.j33r.digitalstore.domain.store;

import java.math.BigDecimal;
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

    private final PurchaseRepository purchaseRepository;

    private final PaymentGatewayFactory paymentGatewayFactory;

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

    /**
     * Perform the purchase checkout.
     *
     * @param ids
     *            the product ids
     * @param customer
     *            the customer information
     * @param creditCard
     *            the credit card information
     */
    public Long checkout(final Customer customer, final CreditCard creditCard, final Set<Long> ids)
            throws PaymentGatewayException {
        final List<Product> products = retrieveProductsFromList(ids);
        final Purchase purchase = new Purchase(customer.getName(), customer.getEmail(), products);
        // FIXME DRY
        final BigDecimal amount = products.stream().map(p -> p.getPrice()).reduce(BigDecimal.ZERO, (a, b) -> a.add(b));

        final PaymentGateway gateway = paymentGatewayFactory.getPaymentGateway(creditCard.getBrand());
        gateway.checkout(creditCard, amount);
        purchaseRepository.save(purchase);
        return purchase.getId();
    }

    /**
     * Retrieve a purchase.
     *
     * @param id
     *            the purchase id
     * @return a Purchase
     */
    public Purchase retrievePurchase(final Long id) {
        return purchaseRepository.findOne(id);
    }

}
