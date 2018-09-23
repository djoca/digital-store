package net.j33r.digitalstore.webapp;

import org.junit.Assert;
import org.junit.Test;

public class CartTest {

    @Test
    public void create() {
        final Cart cart = new Cart();
        Assert.assertNotNull(cart.getItems());
        Assert.assertEquals(0, cart.getItems().size());
        Assert.assertEquals(0, cart.getSize().intValue());
    }

    @Test
    public void addToCart() {
        final Cart cart = new Cart();

        cart.add(1L);
        Assert.assertEquals(1, cart.getSize().intValue());

        cart.add(2L);
        Assert.assertEquals(2, cart.getSize().intValue());

        cart.add(1L);
        Assert.assertEquals(2, cart.getSize().intValue());
    }

    @Test
    public void removeFromCart() {
        final Cart cart = new Cart();
        cart.remove(1L);
        Assert.assertEquals(0, cart.getSize().intValue());

        cart.add(1L);
        Assert.assertEquals(1, cart.getSize().intValue());

        cart.remove(2L);
        Assert.assertEquals(1, cart.getSize().intValue());

        cart.remove(1L);
        Assert.assertEquals(0, cart.getSize().intValue());
    }
}
