package net.j33r.digitalstore.webapp;

import java.time.YearMonth;

import lombok.Getter;
import lombok.Setter;

/**
 * The {@link CheckoutForm} is used to hold the values of the checkout form.
 *
 * @author joses
 *
 */
@Setter
@Getter
class CheckoutForm {

    private String customerName;

    private String customerEmail;

    private String cardHolder;

    private String cardNumber;

    private String cardExpirationYear;

    private String cardExpirationMonth;

    private String cardCvv;

    private String cardBrand;

    /**
     * Returns the credit card expiration date as a YearDate.
     * 
     * @return a YearDate representation of the credit card expiration date.
     */
    YearMonth getExpirationDate() {
        return YearMonth.of(Integer.parseInt(cardExpirationYear), Integer.parseInt(cardExpirationMonth));
    }
}
