package model.services;

public class BoletoPayment implements PaymentService{
    @Override
    public Double applyFee(Double amount) {
        return amount * 1.02;
    }
}
