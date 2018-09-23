package net.j33r.digitalstore.domain.store;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * The Product repository
 *
 * @author joses
 */
public interface ProductRepository extends CrudRepository<Product, Long> {

    @Query("select p from #{#entityName} p order by p.name")
    public List<Product> findProducts();

}
