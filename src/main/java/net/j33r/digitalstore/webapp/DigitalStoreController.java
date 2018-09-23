package net.j33r.digitalstore.webapp;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.AllArgsConstructor;
import net.j33r.digitalstore.domain.DigitalStoreApplicationService;
import net.j33r.digitalstore.domain.store.Product;

/**
 * Controller class for the Digital Store web application
 *
 * @author joses
 */
@Controller
@AllArgsConstructor
public class DigitalStoreController {

    private final DigitalStoreApplicationService applicationService;

    private final Cart cart;

    /**
     * Renders the index page
     *
     * @param model
     * @return the index page
     */
    @GetMapping("/")
    public String index(final Model model) {
        final List<Product> products = applicationService.retrieveProducts();
        model.addAttribute("products", products);
        return "index";
    }

    /**
     * Renders the shopping cart page
     *
     * @return the shopping cart page
     */
    @GetMapping("/shopping-cart")
    public String shoppingCart(final Model model) {
        final List<Product> products = applicationService.retrieveProductsFromList(cart.getItems());
        model.addAttribute("products", products);

        final BigDecimal total = products.stream().map(p -> p.getPrice()).reduce(BigDecimal.ZERO, (a, b) -> a.add(b));
        model.addAttribute("cartTotal", total);

        return "shopping-cart";
    }

    /**
     * Adds a product to the cart.
     *
     * @param productId
     *            the product id
     * @return the shopping cart page
     */
    @PostMapping("/shopping-cart/add/{productId}")
    public String addToCart(@PathVariable final Long productId) {
        cart.add(productId);
        return "redirect:/shopping-cart";
    }

    /**
     * Removes a product from the cart.
     *
     * @param productId
     *            the productId
     * @return the shopping cart page
     */
    @PostMapping("/shopping-cart/remove/{productId}")
    public String deleteFromCart(@PathVariable final Long productId) {
        cart.remove(productId);
        return "redirect:/shopping-cart";
    }

    /**
     * Render the checkout page
     *
     * @return the checkout page
     */
    @GetMapping("/checkout")
    public String checkout() {
        return "checkout";
    }

    /**
     * Process the checkout form
     *
     * @return the result page after processing
     */
    @PostMapping("/checkout")
    public String performCheckout() {
        return "redirect:/purchase-done";
    }

    /**
     * Render the purchase done page
     *
     * @return the purchase done page
     */
    @GetMapping("/purchase-done")
    public String purchaseDone() {
        return "purchase-done";
    }

    /**
     * Adds the cart size to the model.
     *
     * @param model
     *            the Model object
     */
    @ModelAttribute
    private void cartSize(final Model model) {
        model.addAttribute("cartSize", cart.getSize());
    }

}
