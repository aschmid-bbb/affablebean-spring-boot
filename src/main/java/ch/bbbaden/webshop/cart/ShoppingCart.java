package ch.bbbaden.webshop.cart;

import ch.bbbaden.webshop.model.entity.Product;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.math.BigDecimal;
import java.util.*;

@SessionScope
@Lazy(false)
@Component
public class ShoppingCart {
    private Map<Long, ShoppingCartItem> items = new HashMap<>();


    public void setItems(Map<Long, ShoppingCartItem> items) {
        this.items = items;
    }

    public void add(Product product) {
        ShoppingCartItem item = getItem(product);
        short q = item.getQuantity();
        item.setQuantity(++q);
    }

    public void updateQuantity(Product product, short quantity) {
        getItem(product).setQuantity(quantity);
    }

    private ShoppingCartItem getItem(Product product) {
        ShoppingCartItem item = items.get(product.getId());
        if (item == null) {
            item = new ShoppingCartItem(product);
            items.put(product.getId(), item);
        }
        return item;
    }

    public List<ShoppingCartItem> getItemList() {
        return items.values().stream()
                .sorted(Comparator.comparing(i -> i.getProduct().getName()))
                .toList();
    }

    public void clear() {
        items = new HashMap<>();
    }

    public int getNumberOfItems() {
        return items.values().stream()
                .mapToInt(ShoppingCartItem::getQuantity)
                .sum();
    }

    public BigDecimal getTotal() {
        return items.values().stream()
                .map(ShoppingCartItem::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
