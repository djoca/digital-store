package net.j33r.digitalstore.webapp;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

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
import org.springframework.web.context.WebApplicationContext;

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
        performPost("/checkout", "/purchase-done");
    }

    @Test
    public void purchaseDone() throws Exception {
        performGet("/purchase-done", "purchase-done");
    }

    @Test
    public void shoppingCart() throws Exception {
        performGet("/shopping-cart", "shopping-cart");
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
}