package com.company;

//Класс Starter, создаёт объект класса Database, инициализирует
public class Starter {
    public static void main(String[] args) {
        Database database = new Database();
        Manager manager = new Manager();
            Thread updater = new Thread(new UpdateThread(database), "rates_updater");
            updater.start();
            boolean flag = true;
            while (flag) {
                try {
                    flag = ConsoleMenu.start(database, manager);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            updater.interrupt();
    }
}