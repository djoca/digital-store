package net.j33r.digitalstore.domain.store;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class StoreServiceTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private PaymentGatewayFactory paymentGatewayFactory;

    @Autowired
    private FileRepository fileRepository;

    private StoreService storeService;

    @Before
    public void setup() throws Exception {
        storeService = new StoreService(productRepository, purchaseRepository, paymentGatewayFactory, fileRepository);
    }

    @Test
    public void retrieveProducts() throws Exception {
        final List<Product> products = storeService.retrieveProducts();
        Assert.assertNotNull(products);
        Assert.assertEquals(4, products.size());
    }

    @Test
    public void retrieveProductsFromList() throws Exception {
        final Set<Long> ids = new HashSet<>();
        final Product theProduct = storeService.retrieveProducts().get(0);
        ids.add(theProduct.getId());
        final List<Product> products = storeService.retrieveProductsFromList(ids);
        Assert.assertNotNull(products);
        Assert.assertEquals(1, products.size());
        Assert.assertEquals(theProduct.getName(), products.get(0).getName());
    }

    @Test
    public void emptyProductsFromList() throws Exception {
        final Set<Long> ids = new HashSet<>();
        final List<Product> products = storeService.retrieveProductsFromList(ids);
        Assert.assertNotNull(products);
        Assert.assertEquals(0, products.size());
    }

}
