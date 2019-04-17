package com.company.ConcreteCommand;

import com.company.Database;
import com.company.ICommand;

public class toUpdateCommand implements ICommand {
    Database database;

    public toUpdateCommand(Database database) {
        this.database = database;
    }

    @Override
    public void execute() {
        database.toUpdate();
    }

}
