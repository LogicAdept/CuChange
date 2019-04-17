package com.company.ConcreteCommand;

import com.company.Database;
import com.company.ICommand;

public class ChangeBaseCommand implements ICommand {
    Database  database;

    public ChangeBaseCommand(Database database) {
        this.database = database;
    }

    @Override
    public void execute() {
        database.changeBaseCurrency();
    }
}
