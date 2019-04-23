package com.company;

import com.company.ConcreteCommand.*;

import java.util.Scanner;
//Класс Console управляет набором объектов командю При выборе пункта меню активизируется метод execute объекта команд.
public final class ConsoleMenu {

    public static boolean start(Database database, Manager manager) throws Exception {
        ICommand CBCommand = new ChangeBaseCommand(database);
        ICommand COCommand = new ChangeOptionalCommand(database);
        ICommand CCommand = new ConvertCommand(database);
        ICommand IVCommand = new InputAmountCommand(database);
        ICommand SCommand = new SwapCommand(database);
        ICommand UCommand = new UpdateCommand(database);
        ICommand SACommand = new ShowAllCommand(database);
        ICommand PCommand = new PresetCommand(database);
        ICommand ECommand = new ExitCommand(database);
        int selection;
        Scanner scan = new Scanner(System.in);
        boolean notexit = true;
        while (notexit) {
            System.out.println("[1] ВВЕСТИ СУММУ");
            System.out.println("[2] КОНВЕРТИРОВАТЬ");
            System.out.println("[3] ИЗМЕНИТЬ БАЗОВУЮ ВАЛЮТУ");
            System.out.println("[4] ИЗМЕНИТЬ КОНВЕРТИРУЕМУЮ ВАЛЮТУ");
            System.out.println("[5] ПОМЕНЯТЬ МЕСТАМИ ВАЛЮТУ");
            System.out.println("[6] ОБНОВИТЬ КУРСЫ ВАЛЮТ");
            System.out.println("[7] ПОКАЗАТЬ ИНФОРМАЦИЮ О ТЕКУЩЕМ КУРСЕ ВАЛЮТ");
            System.out.println("[8] МЕНЮ ПРЕСЕТОВ");
            System.out.println("[0] ВЫХОД");
            selection = scan.nextInt();
            switch (selection) {
                case 1:
                    manager.setCommand(IVCommand);
                    break;
                case 2:
                    manager.setCommand(CCommand);
                    break;
                case 3:
                    manager.setCommand(CBCommand);
                    break;
                case 4:
                    manager.setCommand(COCommand);
                    break;
                case 5:
                    manager.setCommand(SCommand);
                    break;
                case 6:
                    manager.setCommand(UCommand);
                    break;
                case 7:
                    manager.setCommand(SACommand);
                    break;
                case 8:
                    manager.setCommand(PCommand);
                    break;
                case 0:
                    manager.setCommand(ECommand);
                    notexit = false;
                    break;
                default:
                    System.out.println("НЕВЕРНЫЙ ВВОД");
                    break;
            }
            manager.execute();
        }
        return false;
    }
}