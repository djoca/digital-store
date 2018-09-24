package net.j33r.digitalstore.domain.store;

import java.math.BigDecimal;

/**
 * The {@link PaymentGateway} interface is used on the integration with the
 * credit card gateway.
 *
 * @author joses
 *
 */
public interface PaymentGateway {

    /**
     * Calls the credit card provider and performs the payment.
     *
     * @param creditCard
     *            the credit card information
     * @param amount
     *            the value to charge on the credit card
     * @throws PaymentGatewayException
     *             if the gateway refuses processing the information
     */
    public void checkout(CreditCard creditCard, BigDecimal amount) throws PaymentGatewayException;
}
