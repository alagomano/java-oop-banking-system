package model.entities;

import model.enums.TransactionType;
import model.exception.BankException;

public class BusinessAccount extends Account{
    private Double loanLimit;


    public BusinessAccount(Integer number, String holder, Double balance, Double withdrawLimit, Double loanLimit) {
        super(number, holder, balance, withdrawLimit);
        this.loanLimit = loanLimit;
    }

    public void loan(Double amount){
        if(amount > loanLimit){
            throw new BankException("O valor solicitado excede o limite de empréstimo solicitado.");
        }

        balance += amount;
        this.loanLimit -= amount;
        addTransaction(TransactionType.LOAN, amount);

    }

}
