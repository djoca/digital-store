package net.j33r.digitalstore.webapp;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CheckoutForm {

    private String customerName;

    private String customerEmail;

    private String cardHolder;

    private String cardNumber;

    private String cardExpirationYear;

    private String cardExpirationMonth;

    private String cardCvv;

    private String cardBrand;
}
