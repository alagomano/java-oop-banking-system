package model.services;

import model.entities.Account;
import model.entities.BusinessAccount;
import model.exception.BankException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BankService {
    private Map<Integer, Account> accounts = new HashMap();


    public List< Account> getAccounts(){
        return new ArrayList<>(accounts.values());
    }

    public Account findAccount(Integer number){
        return accounts.get(number);
    }

    public void createAccount(Account account){

        if(findAccount(account.getNumber()) != null){
            throw new BankException("Conta existente");
        }
        accounts.put(account.getNumber(), account);
    }


    public void deposit(Integer number, Double amount){
        Account account = findAccount(number);
        if (account == null){
            throw new BankException("Conta não encontrada");
        }
        account.deposit(amount);
    }

    public void withdraw(Integer number, Double amount){
        Account account = findAccount(number);
        if(account == null){
            throw new BankException("Conta não encontrada");
        }
        account.withdraw(amount);
    }

    public void transfer(Integer from, Integer to, Double amount){
        Account fromAccount = findAccount(from);
        if(fromAccount == null){
            throw new BankException("Conta do remetente não encontrada");
        }

        Account toAccount = findAccount(to);
        if(toAccount == null){
            throw new BankException("Conta do destinatário não encontrada");
        }

        fromAccount.transfer(toAccount, amount);

        }

        public void payment(Integer number, Double amount, PaymentService service){
            Account account = findAccount(number);

            if (account == null){
                throw new BankException("Conta não encontrada");
            }
            account.payment(amount, service);
        }


        public void applyLoan(Integer number, Double amount ){
            Account account = findAccount(number);

            if(account == null){
                throw new BankException("Conta não existente.");
            }

            if(account instanceof BusinessAccount){
                BusinessAccount businessAccount = (BusinessAccount) account;

                businessAccount.loan(amount);
            }else {
                throw new BankException("Esta operação é exclusiva para Contas Empresariais.");
            }


        }


        public void showStatement(Integer number){
            Account account = findAccount(number);
            if(account == null){
                throw new BankException("Conta não encontrada");
            }

            StatementService statementService = new StatementService();
            statementService.generate(account);

        }

    }

