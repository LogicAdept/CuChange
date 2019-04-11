package com.company;

public class Manager {
    Command changeBaseCurrency;
    Command changeOptionalCurrency;
    Command convert;
    Command inputValue;
    Command swap;
    Command exit;

    public Manager(Command changeBaseCurrency, Command changeOptionalCurrency, Command convert, Command inputValue, Command swap, Command exit){
        this.changeBaseCurrency = changeBaseCurrency;
        this.changeOptionalCurrency = changeOptionalCurrency;
        this.convert = convert;
        this.inputValue = inputValue;
        this.swap = swap;
        this.exit = exit;
    }

    public void changeBaseCurrency(){
        changeBaseCurrency.execute();
    }

    public void changeOptionalCurrency(){
        changeOptionalCurrency.execute();
    }

    public void convert(){
        convert.execute();
    }

    public void inputValue(){
        inputValue.execute();
    }

    public void swap(){
        swap.execute();
    }

    public void exit(){
        exit.execute();
    }
}
