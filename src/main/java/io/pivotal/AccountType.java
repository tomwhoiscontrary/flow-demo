package io.pivotal;

public enum AccountType {
    STANDARD(112233), PREMIER(113344), BASIC(114455);

    private int sortCode;

    private AccountType(int sortCode) {
        this.sortCode = sortCode;
    }

    public int getSortCode() {
        return sortCode;
    }

}
