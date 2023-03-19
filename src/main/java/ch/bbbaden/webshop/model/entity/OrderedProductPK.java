package ch.bbbaden.webshop.model.entity;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class OrderedProductPK implements Serializable {
    private Long customerOrderId;
    private Long productId;

    public OrderedProductPK() {
    }

    public OrderedProductPK(Long customerOrderId, Long productId) {
        this.customerOrderId = customerOrderId;
        this.productId = productId;
    }

    public Long getCustomerOrderId() {
        return customerOrderId;
    }

    public void setCustomerOrderId(Long customerOrderId) {
        this.customerOrderId = customerOrderId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }


    @Override
    public int hashCode() {
        return Objects.hash(customerOrderId, productId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderedProductPK that = (OrderedProductPK) o;
        return Objects.equals(customerOrderId, that.customerOrderId) && Objects.equals(productId, that.productId);
    }

    @Override
    public String toString() {
        return "entity.OrderedProductPK[customerOrderId=" + customerOrderId + ", productId=" + productId + "]";
    }
}