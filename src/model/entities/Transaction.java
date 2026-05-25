package model.entities;

import model.enums.TransactionType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {
    private LocalDateTime moment;
    private TransactionType type;
    private Double amount;

    private DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    public Transaction(LocalDateTime moment, TransactionType type, Double amount) {
        this.moment = moment;
        this.type = type;
        this.amount = amount;
    }

    @Override
    public String toString(){
        return moment.format(fmt) + " | " + type +
                " | R$ " + String.format("%.2f", amount);
     }
}
