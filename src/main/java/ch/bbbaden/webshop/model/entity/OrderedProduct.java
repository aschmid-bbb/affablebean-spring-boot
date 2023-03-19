package ch.bbbaden.webshop.model.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
public class OrderedProduct implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected OrderedProductPK orderedProductPK = new OrderedProductPK();

    @MapsId("productId")
    @JoinColumn(name="product_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Product product;

    @MapsId("customerOrderId")
    @JoinColumn(name="customer_order_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private CustomerOrder customerOrder;

    private short quantity;

    public OrderedProduct() {
    }

    public OrderedProductPK getOrderedProductPK() {
        return orderedProductPK;
    }

    public void setOrderedProductPK(OrderedProductPK orderedProductPK) {
        this.orderedProductPK = orderedProductPK;
    }

    public short getQuantity() {
        return quantity;
    }

    public void setQuantity(short quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public CustomerOrder getCustomerOrder() {
        return customerOrder;
    }

    public void setCustomerOrder(CustomerOrder customerOrder) {
        this.customerOrder = customerOrder;
    }

    public BigDecimal getPrice() {
        return getProduct().getPrice().multiply(BigDecimal.valueOf(quantity));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderedProduct that = (OrderedProduct) o;
        return quantity == that.quantity && Objects.equals(orderedProductPK, that.orderedProductPK);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderedProductPK, quantity);
    }

    @Override
    public String toString() {
        return "entity.OrderedProduct[orderedProductPK=" + orderedProductPK + "]";
    }

}