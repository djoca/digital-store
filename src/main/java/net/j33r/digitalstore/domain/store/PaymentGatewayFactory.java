package net.j33r.digitalstore.domain.store;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class PaymentGatewayFactory {

    private final Map<String, PaymentGateway> gateways;

    PaymentGatewayFactory(final PaymentGateway visaPaymentGateway, final PaymentGateway masterCardPaymentGateway) {
        gateways = new HashMap<>();
        gateways.put("Visa", visaPaymentGateway);
        gateways.put("MasterCard", masterCardPaymentGateway);
    }

    public PaymentGateway getPaymentGateway(final String brand) {
        return gateways.get(brand);
    }
}
