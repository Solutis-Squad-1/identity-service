package br.com.solutis.squad1.identityservice.model.entity.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Permission {
    USER_READ("user:read"),
    USER_WRITE("user:write"),
    USER_DELETE("user:delete"),
    USER_UPDATE("user:update"),

    PRODUCT_WRITE("product:write"),
    PRODUCT_DELETE("product:delete"),
    PRODUCT_UPDATE("product:update"),

    CATEGORY_READ("category:read"),
    CATEGORY_WRITE("category:write"),
    CATEGORY_DELETE("category:delete"),
    CATEGORY_UPDATE("category:update");


    private final String permission;
}
