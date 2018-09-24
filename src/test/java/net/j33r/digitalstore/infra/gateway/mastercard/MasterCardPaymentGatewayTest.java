package net.j33r.digitalstore.infra.gateway.mastercard;

import java.math.BigDecimal;
import java.time.YearMonth;

import org.junit.Assert;
import org.junit.Test;

import net.j33r.digitalstore.domain.store.CreditCard;
import net.j33r.digitalstore.domain.store.PaymentGatewayException;

public class MasterCardPaymentGatewayTest {

    @Test
    public void checkout() throws Exception {
        final MasterCardPaymentGateway gateway = new MasterCardPaymentGateway();
        final CreditCard card = new CreditCard("1234", "MasterCard", "LUKE SKYWAKER", YearMonth.of(2020, 12), "123");
        gateway.checkout(card, new BigDecimal("123.51"));
    }

    @Test
    public void wrongCard() throws Exception {
        try {
            final MasterCardPaymentGateway gateway = new MasterCardPaymentGateway();
            final CreditCard card = new CreditCard("1234", "Visa", "LUKE SKYWAKER", YearMonth.of(2020, 12), "123");
            gateway.checkout(card, new BigDecimal("123.30"));
            Assert.fail("Should throw PaymentGatewayException");
        } catch (final PaymentGatewayException e) {
            // Expected exception
        }
    }

    @Test
    public void failedCheckout() throws Exception {
        try {
            final MasterCardPaymentGateway gateway = new MasterCardPaymentGateway();
            final CreditCard card = new CreditCard("1234", "MasterCard", "LUKE SKYWAKER", YearMonth.of(2020, 12),
                    "1232");
            gateway.checkout(card, new BigDecimal("123.51"));
            Assert.fail("Should throw PaymentGatewayException");
        } catch (final PaymentGatewayException e) {
            // Expected exception
        }
    }
}
