package com.company.ConcreteCommand;

import com.company.Database;
import com.company.ICommand;

public class ExitCommand implements ICommand {
    Database database;

    public ExitCommand(Database database) {
        this.database = database;
    }

    @Override
    public void execute() {
        database.saveDtb();
    }
}