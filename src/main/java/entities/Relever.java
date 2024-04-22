package entities;

import java.time.LocalDate;

public class Relever {

    int id;
    Double debit,credit;
    String iban, operation;
    LocalDate date;


    public Relever() {
    }

    public Relever(Double debit, Double credit, String iban, String operation, LocalDate date) {
        this.debit = debit;
        this.credit = credit;
        this.iban = iban;
        this.operation = operation;
        this.date = date;
    }

    public Relever(int id, Double debit, Double credit, String iban, String operation, LocalDate date) {
        this.id = id;
        this.debit = debit;
        this.credit = credit;
        this.iban = iban;
        this.operation = operation;
        this.date = date;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getDebit() {
        return debit;
    }

    public void setDebit(Double debit) {
        this.debit = debit;
    }

    public Double getCredit() {
        return credit;
    }

    public void setCredit(Double credit) {
        this.credit = credit;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }


    @Override
    public String toString() {
        return "Relever{" +
                "id=" + id +
                ", debit=" + debit +
                ", credit=" + credit +
                ", iban='" + iban + '\'' +
                ", operation='" + operation + '\'' +
                ", date=" + date +
                '}';
    }

    public java.sql.Date getSqlDate() {
        return java.sql.Date.valueOf(date);
    }

}
