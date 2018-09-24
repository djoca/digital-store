package net.j33r.digitalstore.webapp;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;

/**
 * This class holds the items for the store shopping cart. It must be used in a session scope.
 *
 * @author joses
 *
 */
@Getter
class Cart {

    private final Set<Long> items;

    Cart() {
        items = new HashSet<>();
    }

    void add(final Long id) {
        items.add(id);
    }

    Integer getSize() {
        return items.size();
    }

    void remove(final Long id) {
        items.remove(id);
    }

    void clear() {
        items.clear();
    }

}
