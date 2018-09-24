package net.j33r.digitalstore.domain.store;

import java.time.YearMonth;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The {@link CreditCard} class holds the credit card information during the
 * checkout process.
 *
 * @author joses
 *
 */
@AllArgsConstructor
@Getter
public class CreditCard {

    private final String number;

    private final String brand;

    private final String holder;

    private final YearMonth expiration;

    private final String cvv;
}
