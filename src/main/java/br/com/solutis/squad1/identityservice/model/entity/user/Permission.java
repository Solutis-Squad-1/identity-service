package br.com.solutis.squad1.identityservice.model.entity.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Permission {
    USER_READ("user:read"),
    USER_DELETE("user:delete"),
    USER_UPDATE("user:update"),

    PRODUCT_CREATE("product:create"),
    PRODUCT_CREATE_IMAGE("product:create:image"),
    PRODUCT_DELETE("product:delete"),
    PRODUCT_DELETE_IMAGE("product:delete:image"),
    PRODUCT_UPDATE("product:update"),

    CATEGORY_CREATE("category:create"),
    CATEGORY_DELETE("category:delete"),
    CATEGORY_UPDATE("category:update"),

    EMAIL_READ("email:read"),
    EMAIL_SEND("email:send"),
    EMAIL_DELETE("email:delete"),

    PAYMENT_UPDATE_STATUS("payment:update:status"),
    PAYMENT_CREATE("payment:create"),
    PAYMENT_CREATE_CREDIT_CARD("payment:create:credit-card"),
    PAYMENT_DELETE("payment:delete"),

    CART_READ("cart:read"),
    CART_CREATE("cart:create"),
    CART_UPDATE_STATUS("cart:update:status"),
    CART_DELETE("cart:delete"),

    ORDER_CREATE("order:create"),
    ORDER_GET("order:get"),
    ORDER_UPDATE("order:update"),
    ORDER_DELETE("order:delete");

    private final String permission;
}
