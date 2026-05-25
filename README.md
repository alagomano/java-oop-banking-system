# 🏦 Banking System — Java OOP

Sistema bancário desenvolvido em Java com foco na aplicação prática dos pilares da Orientação a Objetos: **herança**, **polimorfismo**, **encapsulamento** e **abstração**. O projeto simula operações reais de um banco, incluindo tipos diferentes de conta, serviços de pagamento e geração de extrato.

---

## 📋 Funcionalidades

- Cadastro e busca de contas correntes, poupança e empresariais
- Depósito, saque e transferência com validação de saldo e limite
- Pagamento via **Pix**, **Boleto** e **Cartão de Crédito** com taxas distintas
- Empréstimo exclusivo para contas empresariais com controle de limite
- Rendimento automático para contas poupança
- Extrato completo com histórico de transações formatado
- Tratamento de erros com exceção customizada `BankException`

---

## 🏗️ Arquitetura

```
src/
├── application/
│   └── Main.java                   # Ponto de entrada — menu interativo via console
├── docs/
│   └── DiagramClass.puml           # Diagrama de classes UML (PlantUML)
└── model/
    ├── entities/
    │   ├── Account.java            # Entidade base — regras comuns de conta
    │   ├── SavingsAccount.java     # Conta poupança com rendimento por taxa
    │   ├── BusinessAccount.java    # Conta empresarial com limite de empréstimo
    │   └── Transaction.java        # Registro de cada operação com data e tipo
    ├── enums/
    │   └── TransactionType.java    # Tipos: DEPOSIT, WITHDRAW, TRANSFER, PAYMENT, LOAN
    ├── exception/
    │   └── BankException.java      # Exceção customizada de domínio
    └── services/
        ├── BankService.java        # Coordenador central das operações bancárias
        ├── StatementService.java   # Geração de extrato formatado
        ├── PaymentService.java     # Interface de pagamento (Strategy Pattern)
        ├── PixPayment.java         # Implementação Pix — sem taxa
        ├── BoletoPayment.java      # Implementação Boleto — taxa de 2%
        └── CreditCardPayment.java  # Implementação Cartão — taxa de 5%
```

---

## 🧩 Diagrama de Classes

```
┌─────────────────────────────────────────────┐
│                  Account                    │
│─────────────────────────────────────────────│
│ # balance : Double                          │
│ - number : Integer                          │
│ - holder : String                           │
│ - withdrawLimit : Double                    │
│ - transactions : List<Transaction>          │
│─────────────────────────────────────────────│
│ + deposit(amount) : void                    │
│ + withdraw(amount) : void                   │
│ + transfer(target, amount) : void           │
│ + payment(amount, service) : void           │
│ + addTransaction(type, amount) : void       │
└──────────────┬──────────────────────────────┘
               │
       ┌───────┴────────┐
       ▼                ▼
SavingsAccount    BusinessAccount
- interestRate    - loanLimit
+ updateBalance() + loan(amount)
+ withdraw()      + withdraw()
```

> O diagrama completo em PlantUML está disponível no arquivo `DiagramClass.puml`.

---

## 🔑 Conceitos de POO Aplicados

| Conceito | Onde é aplicado |
|---|---|
| **Herança** | `SavingsAccount` e `BusinessAccount` estendem `Account` |
| **Polimorfismo** | `BankService` opera sobre `Account` aceitando qualquer subclasse |
| **Encapsulamento** | `balance` só é alterado via métodos — nunca diretamente de fora |
| **Abstração** | `PaymentService` define contrato sem expor implementação |
| **Strategy Pattern** | `Pix`, `Boleto` e `CreditCard` são estratégias intercambiáveis de pagamento |
| **SRP** | Cada classe tem uma única responsabilidade bem definida |

---

## 💻 Como executar

### Pré-requisitos
- Java 17+
- IDE: IntelliJ IDEA, Eclipse ou VS Code com Extension Pack for Java

### Rodando o projeto

```bash
# Clone o repositório
git clone https://github.com/seu-usuario/java-oop-banking-system.git

# Entre na pasta
cd banking-system-java

# Compile
javac -d out src/application/Main.java src/model/**/*.java

# Execute
java -cp out application.Main
```

---

## 🖥️ Menu interativo

```
╔══════════════════════════════════╗
║       BANKING SYSTEM — MENU      ║
╠══════════════════════════════════╣
║  1. Criar conta corrente         ║
║  2. Criar conta poupança         ║
║  3. Criar conta empresarial      ║
║  4. Depositar                    ║
║  5. Sacar                        ║
║  6. Transferir                   ║
║  7. Pagamento                    ║
║  8. Empréstimo empresarial       ║
║  9. Rendimento (poupança)        ║
║ 10. Extrato                      ║
║ 11. Listar contas                ║
║  0. Sair                         ║
╚══════════════════════════════════╝
```

---

## 📚 Aprendizados

Este projeto foi desenvolvido como exercício prático de POO em Java. As principais decisões técnicas foram:

- Uso de `HashMap<Integer, Account>` em vez de `List` para busca em O(1) por número de conta
- Atributo `balance` como `protected` para permitir acesso direto em subclasses sem expor ao mundo externo
- `PaymentService` como interface para desacoplar as implementações de pagamento — extensível sem modificar código existente (Open/Closed Principle)
- `addTransaction()` centralizado em `Account` para garantir que toda operação que altera saldo seja registrada no mesmo lugar

---

## 🛠️ Tecnologias

![Java](https://img.shields.io/badge/Java-17-orange?style=flat&logo=java)
![OOP](https://img.shields.io/badge/Paradigma-OOP-blue?style=flat)
![Status](https://img.shields.io/badge/Status-Em%20Desenvolvimento-yellow?style=flat)
---

## 👨‍💻 Autor

Desenvolvido como projeto de estudo de **Orientação a Objetos em Java**.  
Fique à vontade para explorar, sugerir melhorias ou usar como referência.