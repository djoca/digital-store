package net.j33r.digitalstore.infra.gateway.visa;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import net.j33r.digitalstore.domain.store.CreditCard;
import net.j33r.digitalstore.domain.store.PaymentGateway;
import net.j33r.digitalstore.domain.store.PaymentGatewayException;

/**
 * The {@link PaymentGateway} is the class used for Visa credit card processing.
 *
 * @author joses
 *
 */
@Component
public class VisaPaymentGateway implements PaymentGateway {

    @Override
    public void checkout(final CreditCard creditCard, final BigDecimal amount) throws PaymentGatewayException {
        if (amount.doubleValue() > 200) {
            throw new PaymentGatewayException();
        }
    }

}
