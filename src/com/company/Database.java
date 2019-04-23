package com.company;

import org.json.JSONException;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

//Класс Database, содержит всю ключевую логику приложения.
public class Database implements Serializable {
    private transient BigDecimal amount = new BigDecimal("100.0");//Сумма конвертации
    private transient String myBase = "USD";//Базовая валюта
    private transient String[] reqBase = {"EUR", "PLN", "RUB", "BYN", "UAH"};//Набор дополнительных валют
    private Date date;//Дата обновления курса
    private ArrayList<Currency> rates;//Список валют
    private transient boolean success = true;//Переменная, позволяющая выполнять методы.

    public Database() {
    }

    public Database(ArrayList<Currency> currency) {
        this.date = new Date();
        this.rates = currency;
    } //Конструктор для данных с сайта

    public Database(ArrayList<Currency> currency, Date date) {
        this.rates = currency;
        this.date = date;
    } // Конструктор для сохранения базы данных

    public void changeBaseCurrency() {
        if (success) {
            String temp = inputCurrency();
            if (getName(temp).equals("NOT FOUND")) {
                System.out.println("ВАЛЮТА НЕ НАЙДЕНА");
            } else {
                this.myBase = temp;
            }
        } else {
            System.out.println("ЭТА ОПЕРАЦИЯ НЕДОСТУПНА");
        }
    }//Метод для изменения базовой валюты

    public void changeOptionalCurrency() {
        if (success) {
            showReqBase();
            System.out.println("ВЫБЕРИТЕ НОМЕР ВАЛЮТЫ ДЛЯ ЗАМЕНЫ");
            try {
                Scanner scan = new Scanner(System.in);
                int choise = scan.nextInt();
                if (choise <= reqBase.length + 1 && choise > 0) {
                    System.out.println("ВВЕДИТЕ НАЗВАНИЕ НОВОЙ ВАЛЮТЫ");
                    showCurrency();
                    Scanner strscan = new Scanner(System.in);
                    String temp = strscan.nextLine();
                    temp = getName(temp);
                    if (temp.equals("NOT FOUND")) {
                        System.out.println("НЕВЕРНЫЙ ВВОД");
                    } else {
                        this.reqBase[choise - 1] = getName(temp);
                    }
                } else {
                    System.out.println("НЕВЕРНЫЙ ВВОД");
                }
            } catch (InputMismatchException ime) {
                System.out.println("НЕВЕРНЫЙ ВВОД");
            }
        } else {
            System.out.println("ЭТА ОПЕРАЦИЯ НЕДОСТУПНА");
        }
    }//Метод для изменения дополнительной валюты

    public void convert() {
        if (success) {
            System.out.println(date);
            System.out.println("BASE: " + myBase + ": " + amount.setScale(4, RoundingMode.HALF_UP));
            for (String s : reqBase) {
                BigDecimal result = getRate(s);
                if (result.equals(new BigDecimal(-1))) {
                    System.out.println(s + ": NOT FOUND");
                    continue;
                }
                result = result.divide(getRate(myBase), 10, RoundingMode.HALF_UP);
                result = result.multiply(amount);
                System.out.println(s + ": " + result.setScale(4, RoundingMode.HALF_UP));
            }
        } else {
            System.out.println("ЭТА ОПЕРАЦИЯ НЕДОСТУПНА");
        }
    }//Метод, выполняющий конвертацию валют

    public void inputAmount() {
        Scanner scan = new Scanner(System.in);
        System.out.println("ВВЕДИТЕ СУММУ");
        if (scan.hasNextBigDecimal()) {
            this.amount = scan.nextBigDecimal().abs();
        } else {
            System.out.println("НЕКОРРЕКТНЫЙ ВВОД");
        }
    }//Метод, изменяющий сумму для конвертации

    public void swap() {
        if (success) {
            System.out.println("ВЫБЕРИТЕ НОМЕР ВАЛЮТЫ ДЛЯ ЗАМЕНЫ БАЗОВОЙ");
            System.out.println("БАЗОВАЯ ВАЛЮТА: " + myBase);
            showReqBase();
            try {
                Scanner scan = new Scanner(System.in);
                int choice = scan.nextInt();
                if (choice <= reqBase.length + 1 && choice > 0) {
                    String temp = myBase;
                    myBase = reqBase[choice - 1];
                    reqBase[choice - 1] = temp;
                } else {
                    System.out.println("НЕВЕРНЫЙ ВВОД");
                }
            } catch (InputMismatchException ime) {
                System.out.println("НЕВЕРНЫЙ ВВОД");
            }
        } else {
            System.out.println("ЭТА ОПЕРАЦИЯ НЕДОСТУПНА");
        }
    }//Метод, меняющий одну из дополнительных и базовую валюты местами

    public void update() {
        try {
            ArrayList<Currency> patch = FixerData.getCurrency();
            if (patch.size() > 0) {
                this.date = new Date();
                for (int i = 0; i < patch.size(); i++) {
                    BigDecimal imitation = patch.get(i).rate;
                    imitation = imitation.add(new BigDecimal(imitation.doubleValue() / 400 * rnd(-10, 10)));
                    patch.set(i, new Currency(patch.get(i).name, imitation));
                }
                this.rates = patch;
                System.out.println("КУРСЫ ВАЛЮТ ОБНОВЛЕНЫ");
                this.success=true;
            } else {
                offMode();
            }
        } catch (IOException | JSONException e) {
            offMode();
        }
    }//Метод, обновляющий курсы валют, и, в случае неудачи, переключающий в оффлайн режим. Также иммитирует динамику валют

    public void presetMenu() {
        if (success) {
            int select;
            Scanner scan = new Scanner(System.in);
            System.out.println("[1] ВЫБРАТЬ ПРЕСЕТ");
            System.out.println("[2] СОЗДАТЬ ПРЕСЕТ");
            System.out.println("[3] УДАЛИТЬ ПРЕСЕТ");
            System.out.println("[4] СОХРАНИТЬ ТЕКУЩИЙ ПРЕСЕТ");
            System.out.println("ИЛИ ЛЮБУЮ КЛАВИШУ ЧТОБЫ ВЕРНУТЬСЯ НАЗАД");
            select = scan.nextInt();
            switch (select) {
                case 1:
                    try {
                        this.reqBase = Retention.choosePreset();
                    } catch (IOException | ClassNotFoundException e) {
                        System.out.println("ПРЕСЕТ С ТАКИМ ИМЕНЕМ НЕ НАЙДЕН");
                    }
                    break;
                case 2:
                    Retention.createPreset();
                    break;
                case 3:
                    Retention.deletePreset();
                    break;
                case 4:
                    Retention.savePreset(reqBase);
                    break;
            }
        } else {
            System.out.println("ЭТА ОПЕРАЦИЯ НЕДОСТУПНА");
        }
    } //Меню для работы с пресетами

    public void saveDtb() {
        try {
            Retention.saveDtb(new Database(this.rates, this.date));
        } catch (IOException e) {
            System.out.println("НЕ УДАЛОСЬ СОХРАНИТЬ БАЗУ ДАННЫХ");
        }
    }//Метод вызывающий метод для сохранения базы данных

    public void showAll() {
        if (success) {
            System.out.println(date.toString());
            System.out.println("BASE: " + myBase);
            for (int i = 0; i < rates.size(); i++) {
                System.out.println(i + 1 + " " + rates.get(i).toString());
            }
        } else {
            System.out.println("ЭТА ОПЕРАЦИЯ НЕДОСТУПНА");
        }
    }//Метод показывющий все валюты и их курс по отношению к базовой

    private BigDecimal getRate(String reqBase) {
        for (Currency iter : rates) {
            if (iter.name.equals(reqBase)) {
                return iter.rate.abs();
            }
        }
        return new BigDecimal(-1);
    }//Приватный метод возвращающий обменный курс по названию валюты

    private String getName(String name) {
        for (Currency iter : rates) {
            if (iter.name.equals(name)) {
                return iter.name;
            }
        }
        return "NOT FOUND";
    }//Приватный метод возвращающий название валюты, если она присутствует в списке валют

    private String inputCurrency() {
        System.out.println("ФОРМАТ СТРОКИ ДЛЯ ВВОДА: USD, BYR, BTC");
        System.out.println("ВВЕДИТЕ НОВУЮ ВАЛЮТУ");
        Scanner scan = new Scanner(System.in);
        return scan.nextLine().toUpperCase();
    }//Метод для ввода пользовательских названий валют

    private void showReqBase() {
        for (int i = 0; i < reqBase.length; i++) {
            System.out.println(i + 1 + ": " + reqBase[i]);
        }
    }

    private void showCurrency() {
        for (int i = 0; i < rates.size(); i++) {
            System.out.println(i + 1 + " " + rates.get(i).name);
        }
    }

    private int rnd(int min, int max) {
        max -= min;
        return (int) (Math.random() * ++max) + min;
    }//Рандом для имитации динамики валют

    private void offMode() {
        try {
            System.out.println("ВКЛЮЧЁН ОФФЛАЙН РЕЖИМ");
            Database temp = Retention.loadDtb();
            this.rates = temp.rates;
            this.date = temp.date;
                System.out.println("ЗАГРУЖЕНА БАЗА ДАННЫХ");
                this.success=true;
        } catch (IOException | ClassNotFoundException e) {
                System.out.println("НЕ УДАЛОСЬ ЗАГРУЗИТЬ БАЗУ ДАННЫХ");
            this.success = false;
        }
    }////Метод для загрузки базы данных при отсутствии доступа к сайту
}