package com.company.ConcreteCommand;

import com.company.Database;
import com.company.ICommand;

public class PresetCommand implements ICommand {
    Database database;

    public PresetCommand(Database database) {
        this.database = database;
    }

    @Override
    public void execute() throws Exception {
        database.presetMenu();
    }
}
