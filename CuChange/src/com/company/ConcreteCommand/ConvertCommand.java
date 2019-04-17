package com.company.ConcreteCommand;

import com.company.Database;
import com.company.ICommand;

public class ConvertCommand implements ICommand {
    Database database;

    public ConvertCommand(Database database) {
        this.database = database;
    }

    @Override
    public void execute() {
        database.convert();
    }
}
