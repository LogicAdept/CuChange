package com.company;

import java.io.*;
import java.util.Scanner;

public final class Retention { //Класс, для сериализации базы данных и пресетов
    private static String dtb = "LAST.DTB"; //Название файла базы данных
    private static String dir = "C://Exchanger_Presets"; //Путь к папке с файлами
    private static String ext = "prs";//Расширение для пресетов

    public static void createPreset() {
        String[] preset = new String[5];
        System.out.println("ВВЕДИТЕ ЖЕЛАЕМЫЕ ВАЛЮТЫ");
        System.out.println("ФОРМАТ СТРОКИ ДЛЯ ВВОДА: USD");
        Scanner scan = new Scanner(System.in);
        for (int i = 0; i < 5; i++) {
            System.out.println(i + ": ");
            preset[i] = scan.nextLine().toUpperCase();
        }
        savePreset(preset);
    }//Метод для создания пресета

    public static String[] choosePreset() throws IOException, ClassNotFoundException {
        String[] preset = new String[5];
        String name = selectPreset();
        if (!name.equals("NOT FOUND")) {
            FileInputStream fis = new FileInputStream(dir + File.separator + name);
            ObjectInputStream oin = new ObjectInputStream(fis);
            preset = (String[]) oin.readObject();
            return preset;
        } else {
            return preset;
        }
    }//Метод для выбора пресета из заранее сохранённых

    public static void deletePreset() {
        String name = selectPreset();
        if (!name.equals("NOT FOUND")) {
            File[] listFiles = findFiles();
            for (File f : listFiles) {
                if (f.getName().equals(name)) {
                    boolean del = f.delete();
                    if (del) {
                        System.out.println("ПРЕСЕТ УДАЛЁН");
                    } else {
                        System.out.println("ОШИБКА УДАЛЕНИЯ");
                    }
                }
            }
        } else {
            System.out.println("ПРЕСЕТ С ТАКИМ ИМЕНЕМ НЕ НАЙДЕН");
        }
    }//Метод для удаления ранее сохранённого пресета

    private static File[] findFiles() {
        File file = new File(dir);
        if (!file.exists()) System.out.println("ПАПКА НЕ СУЩЕСТВУЕТ");//?
        return file.listFiles(new myFileNameFilter(ext));
    }//Приватный метод для получения списка пресетов

    private static String selectPreset() {
        File[] listFiles = findFiles();
        if (listFiles.length == 0) {
            System.out.println(dir + " НЕ СОДЕРЖИТ ПРЕСЕТОВ");
        } else {
            for (File f : listFiles) {
                System.out.println(dir + File.separator + f.getName());
            }
            System.out.println("ВВЕДИТЕ ИМЯ ПРЕСЕТА");
            Scanner scan = new Scanner(System.in);
            String name = scan.nextLine();
            return name+"."+ext;
        }
        return "NOT FOUND";
    }//Приватный метод для выбора пресета из пресетов, расположенных в dir

    public static void savePreset(String[] preset) {
        System.out.println("ВВЕДИТЕ ИМЯ ПРЕСЕТА");
        Scanner scan = new Scanner(System.in);
        String name = scan.nextLine();
        File directory = new File(dir);
        try {
            if (!directory.exists()) {
                directory.mkdir();
            }
            if (directory.exists()) {
                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(dir + File.separator + name + "." + ext, false));
                oos.writeObject(preset);
                oos.flush();
                oos.close();
            } else {
                System.out.println("НЕ УДАЛОСЬ СОЗДАТЬ КАТАЛОГ");
            }
        } catch (FileNotFoundException e) {
            System.out.println("НЕ УДАЛОСЬ СОЗДАТЬ ПРЕСЕТ");
        } catch (IOException e) {
//            e.printStackTrace();
        }
    }//Метод для сохранения текущего пресета

    public static void saveDtb(Database database) throws IOException {
        File directory = new File(dir);
        if (!directory.exists()) {
            directory.mkdir();
        }
        if (directory.exists()) {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(dir + File.separator + dtb, false));
            oos.writeObject(database);
            oos.flush();
            oos.close();
        } else {
            System.out.println("НЕ УДАЛОСЬ СОЗДАТЬ КАТАЛОГ");
        }
    }//Метод сохраняющий базу данных

    public static Database loadDtb() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(dir + File.separator + dtb);
        ObjectInputStream oin = new ObjectInputStream(fis);
        Database database = (Database) oin.readObject();
        oin.close();
        fis.close();
        return database;
    }//Метод загружающий сохранённую базу данных

    private static class myFileNameFilter implements FilenameFilter {
        private String ext;

        myFileNameFilter(String ext) {
            this.ext = ext.toLowerCase();
        }

        @Override
        public boolean accept(File dir, String name) {
            return name.toLowerCase().endsWith(ext);
        }
    }//Реализация интеррфейса FilenameFilter для для фильтрации имён в папке
}