package com.company;

public class UpdateThread implements Runnable { //Поток, который каждые 60 секунд выполняет метод запроса курсов валют
    private Database database;

    public UpdateThread(Database database) {
        this.database = database;
    }

    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                database.update();
                Thread.sleep(60000);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}