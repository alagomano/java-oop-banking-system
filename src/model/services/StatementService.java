package model.services;

import model.entities.Account;
import model.entities.Transaction;

public class StatementService {
    public void generate(Account account){
        System.out.println("========= EXTRATO =========");
        System.out.println("Conta: " + account.getNumber());
        System.out.println("Titular: " + account.getHolder());
        System.out.println("Saldo: R$" + account.getBalance());
        System.out.println("---------------------------");
        writeTransactions(account);
        System.out.println("===========================");
        System.out.println();
    }

    private void writeTransactions(Account account){
        for(Transaction t : account.getTransactions()){
            System.out.println(t);
        }
    }
}
