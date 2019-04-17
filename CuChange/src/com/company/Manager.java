package com.company;
import java.util.Queue;

public class Manager {
    private Queue<ICommand> Slot;

    //Invoker

    public Manager() {}
    public void setCommand(ICommand object){
        Slot.add(object);
    }

    public void execute(){
        Slot.element().execute();
    }
}
