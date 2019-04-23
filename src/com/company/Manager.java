package com.company;

//Класс Manager, получает объект команды и отдаёт запрос на её выполнение
public class Manager {
    private ICommand Slot;

    public Manager() {
    }

    public void setCommand(ICommand object) {
        Slot = object;
    }

    public void execute() throws Exception {
        Slot.execute();
    }
}
