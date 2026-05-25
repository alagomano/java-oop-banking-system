package model.services;

public class PixPayment implements PaymentService{
    @Override
    public Double applyFee(Double amount) {
        return amount;
    }
}
