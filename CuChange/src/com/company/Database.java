package com.company;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

//Receiver
public class Database {
    private BigDecimal amount = new BigDecimal("100.0");//to client
    private boolean success;
    private String myBase = "USD";
    private String[] reqBase = {"BYN","EUR","AMD","BTC","BYR"};
    private String date;//to client
    private List<Currency> rates;

    public Database(boolean success, String date, ArrayList<Currency> currency) {
        this.success = success;
        this.date = date; //текущая дата на компьютере до секунд
        this.rates = currency;
    }

//    public Database(Database currency) {
//        this.reqBase = currency.reqBase;
//        this.success = currency.success;
//        this.date = currency.date;
//        this.rates = currency.rates;
//    }

    public void changeBaseCurrency(){
        String temp = inputCurrency();
        if(getName(temp).equals("NOT FOUND")){
            System.out.println("ВАЛЮТА НЕ НАЙДЕНА");
        }
        else {
            this.myBase = temp;
        }
    }//+

    public void changeOptionalCurrency(){
        System.out.println("ВЫБЕРИТЕ НОМЕР ВАЛЮТЫ ДЛЯ ЗАМЕНЫ");
        showReqBase();
        try {
            Scanner scan = new Scanner(System.in);
            int choise = scan.nextInt();
            if(choise <= reqBase.length+1 && choise>0) {
                System.out.println("ВВЕДИТЕ НАЗВАНИЕ НОВОЙ ВАЛЮТЫ");
                showCurrency();
                 Scanner strscan = new Scanner(System.in);
                 String temp = strscan.nextLine();
                 temp = getName(temp);
                 if(temp.equals("NOT FOUND")){
                     System.out.println("НЕВЕРНЫЙ ВВОД");
                 }
                 else {
                     this.reqBase[choise - 1] = getName(temp);
                 }
            }
            else {
                System.out.println("НЕВЕРНЫЙ ВВОД");
            }
        } catch (InputMismatchException ime) {
            System.out.println("НЕВЕРНЫЙ ВВОД");
        }
    }//-

    public void convert(){
        System.out.println("BASE: " + myBase + ": " + amount.setScale(4,RoundingMode.HALF_UP));
        for (String temp:reqBase) {
            BigDecimal result = getRate(temp).divide(getRate(myBase),10,RoundingMode.HALF_UP);
            result = result.multiply(amount);
            System.out.println(temp + ": " + result.setScale(4,RoundingMode.HALF_UP));
        }
    }//-

    public void inputAmount(){
        Scanner scan = new Scanner(System.in);
        System.out.println("ВВЕДИТЕ СУММУ");
        if(scan.hasNextBigDecimal()){
            this.amount = scan.nextBigDecimal().abs();
        }
        else {
            System.out.println("НЕКОРРЕКТНЫЙ ВВОД");
        }
   }//+

    public void swap(){
        System.out.println("ВЫБЕРИТЕ НОМЕР ВАЛЮТЫ ДЛЯ ЗАМЕНЫ БАЗОВОЙ");
        System.out.println("БАЗОВАЯ ВАЛЮТА: " + myBase);
        showReqBase();
        try {
            Scanner scan = new Scanner(System.in);
            int choise = scan.nextInt();
            if(choise <= reqBase.length+1 && choise>0) {
                String temp = myBase;
                myBase = reqBase[choise-1];
                reqBase[choise-1] = temp;
            }
            else {
                System.out.println("НЕВЕРНЫЙ ВВОД");
            }
        } catch (InputMismatchException ime) {
            System.out.println("НЕВЕРНЫЙ ВВОД");
        }
    }//+

    public void toUpdate(){

        if(!myList.isEmpty()) {
            Collections.sort(myList);
            this.rates = myList;
        }
    }

    private BigDecimal getRate(String reqBase){
        for(Currency iter:rates){
            if (iter.name.equals(reqBase)){
                BigDecimal temp = new BigDecimal(iter.rate);
                return temp;
            }
        }
        return new BigDecimal(-1);
    }//+

    private String getName(String name){
        for(Currency iter:rates){
            if (iter.name.equals(name)){
                return iter.name;
            }
        }
        return "NOT FOUND";
    }//+

    private String inputCurrency(){
        System.out.println("ФОРМАТ СТРОКИ ДЛЯ ВВОДА: USD, BYR, BTC");
        System.out.println("ВВЕДИТЕ НОВУЮ ВАЛЮТУ");
        Scanner scan = new Scanner(System.in);
        String newCurrency = scan.nextLine().toUpperCase();
        return newCurrency;
    }//+

    private void showReqBase(){
        for(int i = 0; i<reqBase.length;i++ ){
            System.out.println(i+1 + ": " + reqBase[i]);
        }
    }

    private void showCurrency(){
        for (int i = 0; i < rates.size();i++){
            System.out.println(i+1 + " " + rates.get(i).toString());
        }
    }
}