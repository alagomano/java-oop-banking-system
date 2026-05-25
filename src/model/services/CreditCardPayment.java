package model.services;

public class CreditCardPayment implements PaymentService{
    @Override
    public Double applyFee(Double amount) {
        return amount * 1.05;
    }
}
