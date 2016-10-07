package model.domain;

import java.math.BigDecimal;

final public class OrderItem {
    private final Product product;
    private final int productCount;
    private final BigDecimal productPrice;

    private OrderItem() {
        throw new UnsupportedOperationException();
    }

    public OrderItem( Product product, int productCount, BigDecimal productPrice) {
        this.product = new Product(product);
        this.productCount = productCount;
        this.productPrice = productPrice;
    }

    public Product getProduct() {
        return product;
    }

    public int getProductCount() {
        return productCount;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                ", product=" + product +
                ", productCount=" + productCount +
                ", productPrice=" + productPrice +
                '}';
    }
}
