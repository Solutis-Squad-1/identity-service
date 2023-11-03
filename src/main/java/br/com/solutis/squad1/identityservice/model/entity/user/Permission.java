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
    EMAIL_DELETE("email:delete");

    private final String permission;
}
