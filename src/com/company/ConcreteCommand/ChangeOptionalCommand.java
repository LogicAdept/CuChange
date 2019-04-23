package com.company.ConcreteCommand;

import com.company.Database;
import com.company.ICommand;

public class ChangeOptionalCommand implements ICommand {
    Database database;

    public ChangeOptionalCommand(Database database) {
        this.database = database;
    }

    @Override
    public void execute() {
        database.changeOptionalCurrency();
    }
}
