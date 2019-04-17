package com.company;

import com.company.ConcreteCommand.*;

import java.util.Scanner;

public class Starter {
    //

    public static void main(String[] args) throws Exception {
        MyThread example = new MyThread();
        Thread t1 = new Thread(example);
        t1.start();
        Database database = new Database(FixerData.getCurrency());
        Manager manager = new Manager();
        FixerData.getCurrency();
        ICommand CBCommand = new ChangeBaseCommand(database);
        ICommand COCommand = new ChangeOptionalCommand(database);
        ICommand CCommand = new ConvertCommand(database);
        ICommand IVCommand = new InputAmountCommand(database);
        ICommand SCommand = new SwapCommand(database);
        int selection ;
        Scanner scan = new Scanner(System.in);
        do {
            System.out.println("[1] ВВЕСТИ СУММУ");
            System.out.println("[2] КОНВЕРТИРОВАТЬ");
            System.out.println("[3] ИЗМЕНИТЬ БАЗОВУЮ ВАЛЮТУ");
            System.out.println("[4] ИЗМЕНИТЬ КОНВЕРТИРУЕМУЮ ВАЛЮТУ");
            System.out.println("[5] ПОМЕНЯТЬ МЕСТАМИ ВАЛЮТУ");
            System.out.println("[0] ВЫХОД");
            selection = scan.nextInt();
            switch (selection){
                case 1: manager.setCommand(IVCommand);
                break;
                case 2: manager.setCommand(CCommand);
                break;
                case 3: manager.setCommand(CBCommand);
                break;
                case 4: manager.setCommand(COCommand);
                break;
                case 5: manager.setCommand(SCommand);
                break;
            }
            manager.execute();
        }while (selection != 0);
    }
}