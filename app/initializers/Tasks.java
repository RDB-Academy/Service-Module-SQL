package initializers;

import models.Task;

/**
 * @author fabiomazzone
 */
class Tasks {
    static void initDev() {

        Task task = new Task();
        task.setText("Find Fabio");
        task.setReferenceStatement("SELECT * FROM User");
        //task.setSchema(new SchemaDef());
        task.save();

        Task task1 = new Task();
        task1.setText("Find Sören");
        task1.setReferenceStatement("SELECT * FROM User WHERE firstname = 'Soeren'");
        //task1.setSchema(new SchemaDef());
        task1.save();

        Task task2 = new Task();
        task2.setText("Find Caaarl");
        task2.setReferenceStatement("SELECT * FROM User WHERE firstname = 'Caaarl'");
        //task2.setSchema(new SchemaDef());
        task2.save();

    }
}
