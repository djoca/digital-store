package net.j33r.digitalstore.domain.store;

import java.math.BigDecimal;
import java.util.List;

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

    private StoreService storeService;

    @Before
    public void setup() throws Exception {
        productRepository.deleteAll();

        final Product p1 = new Product(1L, "A song", "The song description", new BigDecimal("8.34"), "/tmp/song.mp3");
        productRepository.save(p1);

        final Product p2 = new Product(2L, "A program", "The description", new BigDecimal("65.99"), "/tmp/program.zip");
        productRepository.save(p2);

        storeService = new StoreService(productRepository);
    }

    @Test
    public void retrieveProducts() throws Exception {
        final List<Product> products = storeService.retrieveProducts();
        Assert.assertNotNull(products);
        Assert.assertEquals(2, products.size());
    }

}
