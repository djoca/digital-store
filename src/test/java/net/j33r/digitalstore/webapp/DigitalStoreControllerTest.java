package net.j33r.digitalstore.webapp;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.context.WebApplicationContext;

import net.j33r.digitalstore.domain.store.Product;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class DigitalStoreControllerTest {
    protected MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Before
    public void setup() throws Exception {
        mockMvc = webAppContextSetup(context).build();
    }

    @Test
    public void index() throws Exception {
        final MvcResult result = performGet("/", "index");
        final Map<String, Object> model = result.getModelAndView().getModel();
        Assert.assertNotNull(model.get("products"));
        Assert.assertTrue(model.get("products") instanceof java.util.List);
    }

    @Test
    public void checkout() throws Exception {
        performGet("/checkout", "checkout");
    }

    @Test
    public void postCheckout() throws Exception {
        final MvcResult result = peformCheckoutPost("Luke Skywalker", "luke@starwars.com", "Visa", "LUKE SKYWALKER",
                "1425262396826452", "02", "2020", "124");
        Assert.assertEquals("/purchase-done", result.getResponse().getRedirectedUrl());
    }

    @Test
    public void postInvalidCheckout() throws Exception {
        final MvcResult result = peformCheckoutPost(null, "luke@starwars.com", "Visa", "LUKE SKYWALKER",
                "1425262396826452", "02", "2020", "124");
        Assert.assertEquals("checkout", result.getModelAndView().getViewName());
    }

    @Test
    public void purchaseDone() throws Exception {
        performGet("/purchase-done", "purchase-done");
    }

    @Test
    public void shoppingCart() throws Exception {
        final MvcResult result = performGet("/shopping-cart", "shopping-cart");
        final Map<String, Object> model = result.getModelAndView().getModel();

        @SuppressWarnings("unchecked")
        final List<Product> products = (List<Product>) model.get("products");
        final BigDecimal total = (BigDecimal) model.get("cartTotal");
        Assert.assertNotNull(products);
        Assert.assertNotNull(total);
        Assert.assertEquals(total,
                products.stream().map(p -> p.getPrice()).reduce(BigDecimal.ZERO, (a, b) -> a.add(b)));
    }

    @Test
    public void addToCart() throws Exception {
        performPost("/shopping-cart/add/2", "/shopping-cart");
    }

    @Test
    public void removeFromCart() throws Exception {
        performPost("/shopping-cart/remove/2", "/shopping-cart");
    }

    /**
     * Perform a get request and assert the HttpStatus is 200 and the view is the expected one
     *
     * @param uri
     *            the uri this method will request
     * @param expectedView
     *            the expected view name
     * @return the MvcResult from the get request
     * @throws Exception
     */
    private MvcResult performGet(final String uri, final String expectedView) throws Exception {
        final MvcResult result = mockMvc.perform(get(uri)).andReturn();
        Assert.assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        Assert.assertEquals(expectedView, result.getModelAndView().getViewName());
        return result;
    }

    /**
     * Perform a post request and assert the HttpStatus is 301 and the view is correctly redirected
     *
     * @param uri
     *            the uri this method will request
     * @param expectedRedirect
     *            the expected redirected view
     * @return the MvcResult from the post request
     * @throws Exception
     */
    private MvcResult performPost(final String uri, final String expectedRedirect) throws Exception {
        final MvcResult result = mockMvc.perform(post(uri)).andReturn();
        Assert.assertEquals(HttpStatus.FOUND.value(), result.getResponse().getStatus());
        Assert.assertEquals(expectedRedirect, result.getResponse().getRedirectedUrl());
        return result;
    }

    /**
     * Perform a post with the specified parameters
     *
     * @return
     * @throws Exception
     */
    private MvcResult peformCheckoutPost(final String name, final String email, final String brand, final String holder,
            final String number, final String month, final String year, final String cvv) throws Exception {
        final ResultActions action = mockMvc
                .perform(post("/checkout").param("customerName", name).param("customerEmail", email)
                        .param("cardBrand", brand).param("cardHolder", holder).param("cardNumber", number)
                        .param("cardExpirationMonth", month).param("cardExpirationYear", year).param("cardCvv", cvv));

        return action.andReturn();
    }

}
