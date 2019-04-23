package com.company.ConcreteCommand;

import com.company.Database;
import com.company.ICommand;

public class InputAmountCommand implements ICommand {
    Database database;

    public InputAmountCommand(Database database) {
        this.database = database;
    }

    @Override
    public void execute() {
        database.inputAmount();
    }
}
