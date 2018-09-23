package net.j33r.digitalstore.webapp;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
    public String shoppingCart() {
        return "shopping-cart";
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
}
