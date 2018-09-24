package net.j33r.digitalstore.domain.store;

import java.math.BigDecimal;

public interface PaymentGateway {

    public void checkout(CreditCard creditCard, BigDecimal amount) throws PaymentGatewayException;
}
