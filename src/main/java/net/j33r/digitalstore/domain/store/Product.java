package net.j33r.digitalstore.domain.store;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The Product entity
 *
 * @author joses
 */
@AllArgsConstructor
@Getter
@Entity
@Table(name = "product")
public class Product {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final Long id;

    @Column(name = "name")
    private final String name;

    @Column(name = "description")
    private final String description;

    @Column(name = "price")
    private final BigDecimal price;

    @Column(name = "file_content")
    private final String fileContent;

    @Column(name = "file_size")
    private final Integer fileSize;

    @SuppressWarnings("unused")
    private Product() {
        this(null, null, null, null, null, null);
    }
}
