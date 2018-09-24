package net.j33r.digitalstore.domain.store;

import java.io.IOException;
import java.io.OutputStream;
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

    private final FileRepository fileRepository;

    /**
     * Returns a product.
     *
     * @param productId
     *            the product id
     * @return a Product
     */
    public Product retrieveProduct(final Long id) {
        return productRepository.findOne(id);
    }

    /**
     * Returns all the products from the store.
     *
     * @return a List of Product
     */
    public List<Product> retrieveProducts() {
        return productRepository.findProducts();
    }

    /**
     * Returns a List of Product with the specified ids
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
     * Performs the purchase checkout.
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
     * Retrieves a purchase.
     *
     * @param id
     *            the purchase id
     * @return a Purchase
     */
    public Purchase retrievePurchase(final Long id) {
        return purchaseRepository.findOne(id);
    }

    /**
     * Download the product file.
     * 
     * @param out
     *            the OutputStream where the file will be sent.
     * @param productId
     *            the product id
     * @throws IOException
     */
    public void download(final OutputStream out, final Long productId) throws IOException {
        final Product product = retrieveProduct(productId);
        fileRepository.read(out, product.getFileContent());
    }

}
