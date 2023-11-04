package br.com.solutis.squad1.identityservice.model.entity.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static br.com.solutis.squad1.identityservice.model.entity.user.Permission.*;

@Getter
@RequiredArgsConstructor
public enum Role {
    // Usuario que esta logado, mas não se autenticou novamente pelo o email, então não pode comprar
    USER(
            USER_READ,
            USER_DELETE,
            USER_UPDATE
    ),
    // Usuario que se autenticou pelo email, então pode comprar
    CLIENT(
            USER_READ,
            USER_DELETE,
            USER_UPDATE,
            PAYMENT_CREATE,
            PAYMENT_DELETE,
            PAYMENT_CREATE_CREDIT_CARD,
            CART_CREATE,
            CART_UPDATE_STATUS,
            CART_DELETE
    ),
    // Vendedor que esta logado, mas não se autenticou novamente pelo o email, então não pode comprar
    SELLER(
            USER_READ,
            USER_DELETE,
            USER_UPDATE,
            PRODUCT_CREATE,
            PRODUCT_DELETE,
            PRODUCT_UPDATE,
            PRODUCT_CREATE_IMAGE,
            PRODUCT_DELETE_IMAGE,
            CATEGORY_CREATE,
            CATEGORY_DELETE,
            CATEGORY_UPDATE
    ),
    // Vendedor que se autenticou pelo email, então pode comprar
    SELLER_CLIENT(
            USER_READ,
            USER_DELETE,
            USER_UPDATE,
            PRODUCT_CREATE,
            PRODUCT_DELETE,
            PRODUCT_UPDATE,
            PRODUCT_CREATE_IMAGE,
            PRODUCT_DELETE_IMAGE,
            CATEGORY_CREATE,
            CATEGORY_DELETE,
            CATEGORY_UPDATE,
            PAYMENT_CREATE,
            PAYMENT_DELETE,
            PAYMENT_CREATE_CREDIT_CARD,
            CART_CREATE,
            CART_UPDATE_STATUS,
            CART_DELETE
    ),
    ADMIN(
            USER_READ,
            USER_DELETE,
            USER_UPDATE,
            PRODUCT_CREATE,
            PRODUCT_DELETE,
            PRODUCT_UPDATE,
            PRODUCT_CREATE_IMAGE,
            PRODUCT_DELETE_IMAGE,
            CATEGORY_CREATE,
            CATEGORY_DELETE,
            CATEGORY_UPDATE,
            EMAIL_READ,
            EMAIL_SEND,
            EMAIL_DELETE,
            PAYMENT_CREATE,
            PAYMENT_DELETE,
            PAYMENT_CREATE_CREDIT_CARD,
            PAYMENT_UPDATE_STATUS,
            CART_CREATE,
            CART_UPDATE_STATUS,
            CART_DELETE

    );

    private final Set<Permission> permissions;

    Role(Permission... permissions) {
        this.permissions = Set.of(permissions);
    }

    public List<SimpleGrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());

        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
