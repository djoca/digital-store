package net.j33r.digitalstore.domain.store;

public class PaymentGatewayException extends Exception {

    private static final long serialVersionUID = 2223321891618106386L;

    public PaymentGatewayException() {
        super("A instituição financeira não autorizou a compra.");
    }
}
