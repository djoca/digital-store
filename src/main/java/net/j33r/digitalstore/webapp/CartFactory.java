package net.j33r.digitalstore.webapp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;

/**
 * The CartFactory is a factory of Cart objects. Besides the Cart creation, it is also responsible to put the Cart
 * object into session scope.
 * 
 * @author joses
 *
 */
@Configuration
class CartFactory {

    @Bean
    @Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
    Cart createCart() {
        return new Cart();
    }

}
