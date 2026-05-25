package model.entities;
import model.enums.TransactionType;
import model.exception.BankException;
import model.services.PaymentService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Account {
    private Integer number;
    private String holder;
    protected Double balance;
    private Double withdrawLimit;

    private List<Transaction> transactions = new ArrayList<>();

    public Account(Integer number, String holder, Double balance, Double withdrawLimit) {
        this.number = number;
        this.holder = holder;
        this.balance = balance;
        this.withdrawLimit = withdrawLimit;
    }

    public Integer getNumber() {
        return number;
    }
    public String getHolder(){ return holder;}
    public Double getBalance(){ return balance;}

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void deposit(Double amount){
        if(amount > 0) {
            balance += amount;
            addTransaction(TransactionType.DEPOSIT, amount);

        }
        else {
            throw new BankException("Quantia inávlida.");
        }
    }

    public void withdraw(Double amount){
        if(amount > withdrawLimit){
            throw new BankException("Valor ultrapassa o limite de saque");
        }

        if(amount > balance){
            throw new BankException("Saldo isuficiente");
        }

        balance -= amount;
        addTransaction(TransactionType.WITHDRAW, amount);

    }

    public void transfer(Account target, Double amount){
        if(amount > balance){
            throw new BankException("Saldo insuficiente.");
        }
        this.balance -= amount;
        this.addTransaction(TransactionType.TRANSFER, amount);

        target.deposit(amount);
    }
    public void payment(Double amount, PaymentService service){
        Double newAmount = service.applyFee(amount);
        if(balance < newAmount){
            throw new BankException("Saldo insuficiente");
        }

        balance -= newAmount;
        addTransaction(TransactionType.PAYMENT, newAmount);
    }

    public void addTransaction(TransactionType type, Double amount){
        Transaction transaction = new Transaction(LocalDateTime.now(), type, amount);
        transactions.add(transaction);
    }


    @Override
    public String toString(){

        StringBuilder sb = new StringBuilder();
        sb.append("Conta: ").append(number).append(" | ").append(holder).append(" | ");
        sb.append("Saldo atual: ").append(String.format("%.2f", balance)).append("\n");

        return sb.toString();
    }

}
