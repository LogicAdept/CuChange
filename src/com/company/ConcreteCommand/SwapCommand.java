package com.company.ConcreteCommand;

import com.company.Database;
import com.company.ICommand;

public class SwapCommand implements ICommand {
    Database database;

    public SwapCommand(Database database) {
        this.database = database;
    }

    @Override
    public void execute() {
        database.swap();
    }
}
