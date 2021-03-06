package net.j33r.digitalstore.webapp;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.AllArgsConstructor;
import net.j33r.digitalstore.domain.DigitalStoreApplicationService;
import net.j33r.digitalstore.domain.store.CreditCard;
import net.j33r.digitalstore.domain.store.Customer;
import net.j33r.digitalstore.domain.store.PaymentGatewayException;
import net.j33r.digitalstore.domain.store.Product;
import net.j33r.digitalstore.domain.store.Purchase;

/**
 * Controller class for the Digital Store web application
 *
 * @author joses
 */
@Controller
@AllArgsConstructor
public class DigitalStoreController {

    private final DigitalStoreApplicationService applicationService;

    private final CheckoutFormValidator checkoutFormValidator;

    private final Cart cart;

    @InitBinder
    protected void initBinder(final WebDataBinder binder) {
        binder.addValidators(checkoutFormValidator);
    }

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
    public String checkout(final Model model) {
        model.addAttribute("checkoutForm", new CheckoutForm());
        return "checkout";
    }

    /**
     * Process the checkout form
     *
     * @return the result page after processing
     */
    @PostMapping("/checkout")
    public String performCheckout(@ModelAttribute @Valid final CheckoutForm checkoutForm, final BindingResult result,
            final Model model) {
        if (result.hasErrors()) {
            for (final FieldError error : result.getFieldErrors()) {
                model.addAttribute(error.getField() + "Error", error.getDefaultMessage());
            }

            return "checkout";
        }

        final Customer customer = new Customer(checkoutForm.getCustomerName(), checkoutForm.getCustomerEmail());
        final CreditCard creditCard = new CreditCard(checkoutForm.getCardNumber(), checkoutForm.getCardBrand(),
                checkoutForm.getCardHolder(), checkoutForm.getExpirationDate(), checkoutForm.getCardCvv());

        try {
            final Long purchaseId = applicationService.checkout(customer, creditCard, cart.getItems());
            cart.clear();

            return "redirect:/purchase-done/" + purchaseId;
        } catch (final PaymentGatewayException e) {
            model.addAttribute("error", e.getMessage());
            return "checkout";
        }
    }

    /**
     * Render the purchase done page
     *
     * @return the purchase done page
     */
    @GetMapping("/purchase-done/{purchaseId}")
    public String purchaseDone(final Model model, @PathVariable final Long purchaseId) {
        final Purchase purchase = applicationService.retrievePurchase(purchaseId);
        model.addAttribute("products", purchase.getProducts());
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
