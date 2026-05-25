package model.entities;

public class SavingsAccount extends Account{
    private Double interestRate;

    public SavingsAccount(Integer number, String holder, Double balance, Double withdrawLimit, Double interestRate) {
        super(number, holder, balance, withdrawLimit);
        this.interestRate = interestRate;
    }

    public void updateBalance(){
        Double earning = getBalance() * interestRate;
        deposit(earning);

    }

    public void withdraw(Double amount){
        super.withdraw(amount);
    }


}
