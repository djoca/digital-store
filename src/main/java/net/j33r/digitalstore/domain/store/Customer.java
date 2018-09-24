package net.j33r.digitalstore.domain.store;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The {@link Customer} class holds the customer information during the checkout
 * process.
 *
 * @author joses
 *
 */
@AllArgsConstructor
@Getter
public class Customer {

    private final String name;

    private final String email;
}
