package com.company.ConcreteCommand;

import com.company.Database;
import com.company.ICommand;

public class ShowAllCommand implements ICommand {
    Database database;

    public ShowAllCommand(Database database) {
        this.database = database;
    }

    @Override
    public void execute() {
        database.showAll();
    }
}
