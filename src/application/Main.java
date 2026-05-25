package application;

import model.entities.Account;
import model.entities.BusinessAccount;
import model.entities.SavingsAccount;
import model.exception.BankException;
import model.services.*;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final BankService bank = new BankService();
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        seedData(); // dados iniciais para demonstração
        run();
        sc.close();
    }

    // ── Loop principal ────────────────────────────────────────────
    private static void run() {
        int option = -1;
        while (option != 0) {
            printMenu();
            option = readInt("Opção");
            System.out.println();
            try {
                handleOption(option);
            } catch (BankException e) {
                System.out.println("⚠  Erro: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("⚠  Entrada inválida.");
            }
            System.out.println();
        }
        System.out.println("Encerrando sistema. Até logo!");
    }

    // ── Roteamento de opções ──────────────────────────────────────
    private static void handleOption(int option) {
        switch (option) {
            case 1  -> createCurrentAccount();
            case 2  -> createSavingsAccount();
            case 3  -> createBusinessAccount();
            case 4  -> deposit();
            case 5  -> withdraw();
            case 6  -> transfer();
            case 7  -> payment();
            case 8  -> applyLoan();
            case 9  -> updateSavings();
            case 10 -> showStatement();
            case 11 -> listAccounts();
            case 0  -> {}
            default -> System.out.println("Opção inválida.");
        }
    }

    // ── Criação de contas ─────────────────────────────────────────
    private static void createCurrentAccount() {
        System.out.println("─── Nova Conta Corrente ───");
        int number       = readInt("Número da conta");
        String holder    = readString("Titular");
        double balance   = readDouble("Saldo inicial (R$)");
        double limit     = readDouble("Limite de saque (R$)");

        bank.createAccount(new Account(number, holder, balance, limit));
        System.out.println("✓ Conta corrente " + number + " criada com sucesso.");
    }

    private static void createSavingsAccount() {
        System.out.println("─── Nova Conta Poupança ───");
        int number       = readInt("Número da conta");
        String holder    = readString("Titular");
        double balance   = readDouble("Saldo inicial (R$)");
        double limit     = readDouble("Limite de saque (R$)");
        double rate      = readDouble("Taxa de rendimento (ex: 0.05 para 5%)");

        bank.createAccount(new SavingsAccount(number, holder, balance, limit, rate));
        System.out.println("✓ Conta poupança " + number + " criada com sucesso.");
    }

    private static void createBusinessAccount() {
        System.out.println("─── Nova Conta Empresarial ───");
        int number       = readInt("Número da conta");
        String holder    = readString("Titular");
        double balance   = readDouble("Saldo inicial (R$)");
        double limit     = readDouble("Limite de saque (R$)");
        double loanLimit = readDouble("Limite de empréstimo (R$)");

        bank.createAccount(new BusinessAccount(number, holder, balance, limit, loanLimit));
        System.out.println("✓ Conta empresarial " + number + " criada com sucesso.");
    }

    // ── Operações ─────────────────────────────────────────────────
    private static void deposit() {
        System.out.println("─── Depósito ───");
        int number    = readInt("Número da conta");
        double amount = readDouble("Valor (R$)");
        bank.deposit(number, amount);
        System.out.printf("✓ Depósito de R$ %.2f realizado na conta %d.%n", amount, number);
    }

    private static void withdraw() {
        System.out.println("─── Saque ───");
        int number    = readInt("Número da conta");
        double amount = readDouble("Valor (R$)");
        bank.withdraw(number, amount);
        System.out.printf("✓ Saque de R$ %.2f realizado na conta %d.%n", amount, number);
    }

    private static void transfer() {
        System.out.println("─── Transferência ───");
        int from      = readInt("Conta de origem");
        int to        = readInt("Conta de destino");
        double amount = readDouble("Valor (R$)");
        bank.transfer(from, to, amount);
        System.out.printf("✓ Transferência de R$ %.2f da conta %d para %d realizada.%n", amount, from, to);
    }

    private static void payment() {
        System.out.println("─── Pagamento ───");
        int number    = readInt("Número da conta");
        double amount = readDouble("Valor (R$)");

        System.out.println("Forma de pagamento:");
        System.out.println("  1. Pix       (sem taxa)");
        System.out.println("  2. Boleto    (+2%)");
        System.out.println("  3. Cartão    (+5%)");
        int choice = readInt("Opção");

        PaymentService service = switch (choice) {
            case 1 -> new PixPayment();
            case 2 -> new BoletoPayment();
            case 3 -> new CreditCardPayment();
            default -> throw new BankException("Forma de pagamento inválida.");
        };

        bank.payment(number, amount, service);
        System.out.printf("✓ Pagamento de R$ %.2f realizado via %s.%n",
                amount, service.getClass().getSimpleName());
    }

    private static void applyLoan() {
        System.out.println("─── Empréstimo Empresarial ───");
        int number    = readInt("Número da conta empresarial");
        double amount = readDouble("Valor do empréstimo (R$)");
        bank.applyLoan(number, amount);
        System.out.printf("✓ Empréstimo de R$ %.2f concedido para conta %d.%n", amount, number);
    }

    private static void updateSavings() {
        System.out.println("─── Aplicar Rendimento ───");
        int number = readInt("Número da conta poupança");
        Account account = bank.findAccount(number);
        if (account instanceof SavingsAccount) {
            SavingsAccount sa = (SavingsAccount) account;
            sa.updateBalance();
            System.out.println("✓ Rendimento aplicado com sucesso.");
        } else {
            System.out.println("⚠  A conta informada não é uma conta poupança.");
        }
    }

    private static void showStatement() {
        System.out.println("─── Extrato ───");
        int number = readInt("Número da conta");
        bank.showStatement(number);
    }

    private static void listAccounts() {
        List<Account> accounts = bank.getAccounts();
        if (accounts.isEmpty()) {
            System.out.println("Nenhuma conta cadastrada.");
            return;
        }
        System.out.println("─── Contas cadastradas ───");
        accounts.forEach(a -> {
            String type;
            if (a instanceof SavingsAccount) {
                type = "[POUPANÇA]    ";
            } else if (a instanceof BusinessAccount) {
                type = "[EMPRESARIAL] ";
            } else {
                type = "[CORRENTE]    ";
            }
            System.out.println(type + a);
        });
    }

    // ── Dados de demonstração ─────────────────────────────────────
    private static void seedData() {
        bank.createAccount(new Account(1001, "Alice Silva", 2500.00, 1000.00));
        bank.createAccount(new SavingsAccount(2001, "Bruno Lima", 5000.00, 500.00, 0.05));
        bank.createAccount(new BusinessAccount(3001, "Tech Corp Ltda", 15000.00, 5000.00, 20000.00));
        System.out.println("✓ Sistema iniciado com 3 contas de demonstração.\n");
    }

    // ── Utilitários de leitura ────────────────────────────────────
    private static int readInt(String label) {
        System.out.print(label + ": ");
        try {
            return sc.nextInt();
        } catch (InputMismatchException e) {
            sc.nextLine();
            throw new BankException("Valor inválido para " + label);
        } finally {
            sc.nextLine();
        }
    }

    private static double readDouble(String label) {
        System.out.print(label + ": ");
        try {
            return sc.nextDouble();
        } catch (InputMismatchException e) {
            sc.nextLine();
            throw new BankException("Valor inválido para " + label);
        } finally {
            sc.nextLine();
        }
    }

    private static String readString(String label) {
        System.out.print(label + ": ");
        return sc.nextLine().trim();
    }

    // ── Menu visual ───────────────────────────────────────────────
    private static void printMenu() {
        System.out.println("╔══════════════════════════════════╗");
        System.out.println("║     BANKING SYSTEM — MENU        ║");
        System.out.println("╠══════════════════════════════════╣");
        System.out.println("║  1. Criar conta corrente         ║");
        System.out.println("║  2. Criar conta poupança         ║");
        System.out.println("║  3. Criar conta empresarial      ║");
        System.out.println("║  4. Depositar                    ║");
        System.out.println("║  5. Sacar                        ║");
        System.out.println("║  6. Transferir                   ║");
        System.out.println("║  7. Pagamento                    ║");
        System.out.println("║  8. Empréstimo empresarial       ║");
        System.out.println("║  9. Rendimento (poupança)        ║");
        System.out.println("║ 10. Extrato                      ║");
        System.out.println("║ 11. Listar contas                ║");
        System.out.println("║  0. Sair                         ║");
        System.out.println("╚══════════════════════════════════╝");
    }
}