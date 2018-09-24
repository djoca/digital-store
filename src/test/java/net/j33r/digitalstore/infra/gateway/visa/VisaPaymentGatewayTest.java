package net.j33r.digitalstore.infra.gateway.visa;

import java.math.BigDecimal;
import java.time.YearMonth;

import org.junit.Assert;
import org.junit.Test;

import net.j33r.digitalstore.domain.store.CreditCard;
import net.j33r.digitalstore.domain.store.PaymentGatewayException;

public class VisaPaymentGatewayTest {

    @Test
    public void checkout() throws Exception {
        final VisaPaymentGateway gateway = new VisaPaymentGateway();
        final CreditCard card = new CreditCard("1234", "Visa", "LUKE SKYWAKER", YearMonth.of(2020, 12), "123");
        gateway.checkout(card, new BigDecimal("123.30"));
    }

    @Test
    public void wrongCard() throws Exception {
        try {
            final VisaPaymentGateway gateway = new VisaPaymentGateway();
            final CreditCard card = new CreditCard("1234", "MasterCard", "LUKE SKYWAKER", YearMonth.of(2020, 12),
                    "123");
            gateway.checkout(card, new BigDecimal("123.30"));
            Assert.fail("Should throw PaymentGatewayException");
        } catch (final PaymentGatewayException e) {
            // Expected exception
        }
    }

    @Test
    public void failedCheckout() throws Exception {
        try {
            final VisaPaymentGateway gateway = new VisaPaymentGateway();
            final CreditCard card = new CreditCard("1234", "Visa", "LUKE SKYWAKER", YearMonth.of(2020, 12), "123");
            gateway.checkout(card, new BigDecimal("999.00"));
            Assert.fail("Should throw PaymentGatewayException");
        } catch (final PaymentGatewayException e) {
            // Expected exception
        }
    }
}
