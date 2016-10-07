package servlet.bean;

import model.domain.Product;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Basket {
    private Map<Product, Integer> productMap = new HashMap<>();

    public void addProduct(Product product) {
        Integer counter = productMap.get(product);
        if (counter == null) {
            productMap.put(product, 1);
        } else {
            productMap.put(product, ++counter);
        }
    }

    public void addProduct(Product product, int productNum) {
        Integer counter = productMap.get(product);
        if (counter == null) {
            productMap.put(product, productNum);
        } else {
            productMap.put(product, counter + productNum);
        }
    }

    public void setProduct(Product product, int productNum) {
        productMap.put(product, productNum);
    }

    public void removeProduct(Product product) {
        productMap.remove(product);
    }

    public Map<Product, Integer> readItems() {
        return Collections.unmodifiableMap(productMap);
    }

    public int countProducts() {
        int result = 0;
        for (Map.Entry<Product, Integer> item : productMap.entrySet()) {
            result += item.getValue();
        }

        return result;
    }

    public BigDecimal countTotalPrice() {
        BigDecimal result = BigDecimal.ZERO;

        for (Map.Entry<Product, Integer> item : productMap.entrySet()) {
            result = result.add(BigDecimal.valueOf(item.getKey().getPrice() * item.getValue()));
        }

        return result;
    }

    public BigDecimal countTotalPrice(Product product) {
        Objects.requireNonNull(product);

        BigDecimal totalPrice = BigDecimal.ZERO;
        int counter = productMap.get(product);

        totalPrice = totalPrice.add(BigDecimal.valueOf(product.getPrice() * counter));

        return totalPrice;
    }
}
