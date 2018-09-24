package net.j33r.digitalstore.domain.store;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

/**
 * The {@link PaymentGatewayFactory} is a factory of PaymentGateway objects.
 *
 * @author joses
 *
 */
@Component
public class PaymentGatewayFactory {

    private final Map<String, PaymentGateway> gateways;

    PaymentGatewayFactory(final PaymentGateway visaPaymentGateway, final PaymentGateway masterCardPaymentGateway) {
        gateways = new HashMap<>();
        gateways.put("Visa", visaPaymentGateway);
        gateways.put("MasterCard", masterCardPaymentGateway);
    }

    /**
     * Returns a {@link PaymentGateway} for the specified credit card provider
     *
     * @param provider
     * @return a {@link PaymentGateway}
     */
    public PaymentGateway getPaymentGateway(final String provider) {
        return gateways.get(provider);
    }
}
