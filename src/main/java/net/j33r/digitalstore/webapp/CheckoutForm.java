package net.j33r.digitalstore.webapp;

import java.time.YearMonth;

import lombok.Getter;
import lombok.Setter;

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

    YearMonth getExpirationDate() {
        return YearMonth.of(Integer.parseInt(cardExpirationYear), Integer.parseInt(cardExpirationMonth));
    }
}
