package io.pivotal;

import java.io.Serializable;

public class Account implements Serializable {

    private int number;
    private int sortCode;
    private String owner;
    private AccountType type;
    private int overdraft;

    public Account(int number, int sortCode, String owner, AccountType type, int overdraft) {
        this.number = number;
        this.sortCode = sortCode;
        this.owner = owner;
        this.type = type;
        this.overdraft = overdraft;
    }

    /**
     * Necessary to be used as a variable in Web Flow.
     */
    public Account() {
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getSortCode() {
        return sortCode;
    }

    public void setSortCode(int sortCode) {
        this.sortCode = sortCode;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    public int getOverdraft() {
        return overdraft;
    }

    public void setOverdraft(int overdraft) {
        this.overdraft = overdraft;
    }

    @Override
    public String toString() {
        return "Account{" +
                "number=" + number +
                ", sortCode=" + sortCode +
                ", owner='" + owner + '\'' +
                ", type='" + type + '\'' +
                ", overdraft=" + overdraft +
                '}';
    }

}
