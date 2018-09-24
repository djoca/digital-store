package net.j33r.digitalstore.webapp;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
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
        final MvcResult result = performCheckoutPost("Luke Skywalker", "luke@starwars.com", "Visa", "LUKE SKYWALKER",
                "1425262396826452", "02", "2020", "124");
        final String redirectedUrl = result.getResponse().getRedirectedUrl();
        Assert.assertTrue(redirectedUrl.startsWith("/purchase-done"));

        performGet(redirectedUrl, "purchase-done");
    }

    @Test
    public void checkoutValidator() throws Exception {
        performInvalidCheckoutPost(null, "luke@starwars.com", "Visa", "LUKE SKYWALKER", "1425262396826452", "02",
                "2020", "124");
        performInvalidCheckoutPost(StringUtils.repeat("A", 121), "luke@starwars.com", "Visa", "LUKE SKYWALKER",
                "1425262396826452", "02", "2020", "124");
        performInvalidCheckoutPost("Luke Skywalker", null, "Visa", "LUKE SKYWALKER", "1425262396826452", "02", "2020",
                "124");
        performInvalidCheckoutPost("Luke Skywalker", StringUtils.repeat("A", 151), "Visa", "LUKE SKYWALKER",
                "1425262396826452", "02", "2020", "124");
        performInvalidCheckoutPost("Luke Skywalker", "abc", "Visa", "LUKE SKYWALKER", "1425262396826452", "02", "2020",
                "124");
        performInvalidCheckoutPost("Luke Skywalker", "luke@starwars.com", null, "LUKE SKYWALKER", "1425262396826452",
                "02", "2020", "124");
        performInvalidCheckoutPost("Luke Skywalker", "luke@starwars.com", "Visa", null, "1425262396826452", "02",
                "2020", "124");
        performInvalidCheckoutPost("Luke Skywalker", "luke@starwars.com", "Visa", StringUtils.repeat("A", 26),
                "1425262396826452", "02", "2020", "124");
        performInvalidCheckoutPost("Luke Skywalker", "luke@starwars.com", "Visa", "LUKE SKYWALKER", null, "02", "2020",
                "124");
        performInvalidCheckoutPost("Luke Skywalker", "luke@starwars.com", "Visa", "LUKE SKYWALKER", "012", "02", "2020",
                "124");
        performInvalidCheckoutPost("Luke Skywalker", "luke@starwars.com", "Visa", "LUKE SKYWALKER", "-1425262396826452",
                "02", "2020", "124");
        performInvalidCheckoutPost("Luke Skywalker", "luke@starwars.com", "Visa", "LUKE SKYWALKER", "ABC", "02", "2020",
                "124");
        performInvalidCheckoutPost("Luke Skywalker", "luke@starwars.com", "Visa", "LUKE SKYWALKER",
                StringUtils.repeat("1", 11), "02", "2020", "124");
        performInvalidCheckoutPost("Luke Skywalker", "luke@starwars.com", "Visa", "LUKE SKYWALKER",
                StringUtils.repeat("1", 20), "02", "2020", "124");
        performInvalidCheckoutPost("Luke Skywalker", "luke@starwars.com", "Visa", "LUKE SKYWALKER", "1425262396826452",
                null, "2020", "124");
        performInvalidCheckoutPost("Luke Skywalker", "luke@starwars.com", "Visa", "LUKE SKYWALKER", "1425262396826452",
                "02", null, "124");
        performInvalidCheckoutPost("Luke Skywalker", "luke@starwars.com", "Visa", "LUKE SKYWALKER", "1425262396826452",
                "-02", "2020", "124");
        performInvalidCheckoutPost("Luke Skywalker", "luke@starwars.com", "Visa", "LUKE SKYWALKER", "1425262396826452",
                "13", "2020", "124");
        performInvalidCheckoutPost("Luke Skywalker", "luke@starwars.com", "Visa", "LUKE SKYWALKER", "1425262396826452",
                "02", "1999", "124");
        performInvalidCheckoutPost("Luke Skywalker", "luke@starwars.com", "Visa", "LUKE SKYWALKER", "1425262396826452",
                "A", "1999", "124");
        performInvalidCheckoutPost("Luke Skywalker", "luke@starwars.com", "Visa", "LUKE SKYWALKER", "1425262396826452",
                "02", "A", "124");
        performInvalidCheckoutPost("Luke Skywalker", "luke@starwars.com", "Visa", "LUKE SKYWALKER", "1425262396826452",
                "02", "2020", null);
        performInvalidCheckoutPost("Luke Skywalker", "luke@starwars.com", "Visa", "LUKE SKYWALKER", "1425262396826452",
                "02", "2020", "1");
        performInvalidCheckoutPost("Luke Skywalker", "luke@starwars.com", "Visa", "LUKE SKYWALKER", "1425262396826452",
                "02", "2020", "12345");
        performInvalidCheckoutPost("Luke Skywalker", "luke@starwars.com", "Visa", "LUKE SKYWALKER", "1425262396826452",
                "02", "2020", "ABC");
    }

    @Test
    public void postUnauthorizedCheckout() throws Exception {
        final MvcResult result = performCheckoutPost("Luke Skywalker", "luke@starwars.com", "MasterCard",
                "LUKE SKYWALKER", "1425262396826452", "02", "2020", "1234");
        Assert.assertEquals("checkout", result.getModelAndView().getViewName());
        Assert.assertNotNull(result.getModelAndView().getModel().get("error"));
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
    private MvcResult performCheckoutPost(final String name, final String email, final String brand,
            final String holder, final String number, final String month, final String year, final String cvv)
            throws Exception {
        final ResultActions action = mockMvc
                .perform(post("/checkout").param("customerName", name).param("customerEmail", email)
                        .param("cardBrand", brand).param("cardHolder", holder).param("cardNumber", number)
                        .param("cardExpirationMonth", month).param("cardExpirationYear", year).param("cardCvv", cvv));

        return action.andReturn();
    }

    /**
     * Perform an invalid post with the specified parameters. This is the same as
     * {@link DigitalStoreControllerTest#performCheckoutPost(String, String, String, String, String, String, String, String)}
     * but verifies that sever returns to the original page.
     *
     * @return
     * @throws Exception
     */
    private MvcResult performInvalidCheckoutPost(final String name, final String email, final String brand,
            final String holder, final String number, final String month, final String year, final String cvv)
            throws Exception {
        final MvcResult result = performCheckoutPost(name, email, brand, holder, number, month, year, cvv);
        Assert.assertEquals("checkout", result.getModelAndView().getViewName());
        return result;
    }
}
