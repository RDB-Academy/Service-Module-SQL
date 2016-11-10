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

    }
}
