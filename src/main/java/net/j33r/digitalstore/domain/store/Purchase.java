package net.j33r.digitalstore.domain.store;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Getter;

@Getter
@Entity
@Table(name = "purchase")
class Purchase {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final Long id;

    private final String customerName;

    private final String customerEmail;

    @ManyToMany
    @JoinTable(name = "purchase_product", joinColumns = @JoinColumn(name = "purchase_id"), inverseJoinColumns = @JoinColumn(name = "product_id"))
    private final List<Product> products;

    Purchase(final String customerName, final String customerEmail, final List<Product> products) {
        this.id = null;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.products = products;
    }
}
