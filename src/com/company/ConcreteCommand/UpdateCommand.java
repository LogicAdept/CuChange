package com.company.ConcreteCommand;

import com.company.Database;
import com.company.ICommand;

public class UpdateCommand implements ICommand {
    Database database;

    public UpdateCommand(Database database) {
        this.database = database;
    }

    @Override
    public void execute(){
        database.update();
    }

}
