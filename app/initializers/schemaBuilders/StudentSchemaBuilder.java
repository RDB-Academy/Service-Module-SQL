package initializers.schemaBuilders;

import initializers.SchemaBuilder;
import models.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ronja on 24.11.16.
 */
public class StudentSchemaBuilder extends SchemaBuilder {
    @Override
    protected String getSchemaName() {
        return "StudentSchema";
    }

    @Override
    protected SchemaDef buildSchema() {
        SchemaDef           studentExamSchema          = this.createNewSchemaDef();

        TableDef            student                    = this.createNewTableDef("student");
        TableDef            professor                  = this.createNewTableDef("professor");
        TableDef            exam                       = this.createNewTableDef("exam");
        TableDef            studentExam                = this.createNewTableDef("student_exam");

        ColumnDef           student_student_id         = this.createNewColumnDef("id", "INT");
        ColumnDef           student_student_firstname  = this.createNewColumnDef("firstname", "VARCHAR(255)");
        ColumnDef           student_student_lastname   = this.createNewColumnDef("lastname", "VARCHAR(255)");
        ColumnDef           student_student_birthday   = this.createNewColumnDef("birthday", "Date");

        ColumnDef           professor_professor_id         = this.createNewColumnDef("id", "INT");
        ColumnDef           professor_professor_firstname  = this.createNewColumnDef("firstname", "VARCHAR(255)");
        ColumnDef           professor_professor_lastname   = this.createNewColumnDef("lastname", "VARCHAR(255)");
        ColumnDef           professor_professor_field      = this.createNewColumnDef("field", "VARCHAR(255)");
        ColumnDef           professor_professor_birthday   = this.createNewColumnDef("birthday", "Date");

        ColumnDef           exam_exam_id               = this.createNewColumnDef("id", "INT");
        ColumnDef           exam_exam_professor_id        = this.createNewColumnDef("professor_id", "INT");
        ColumnDef           exam_exam_name             = this.createNewColumnDef("name", "VARCHAR(255)");
        ColumnDef           exam_exam_day              = this.createNewColumnDef("day", "INT");
        ColumnDef           exam_exam_month            = this.createNewColumnDef("month", "INT");
        ColumnDef           exam_exam_year             = this.createNewColumnDef("year", "INT");
        ForeignKey          exam_professor             = this.createForeignKey("FK_Exam_Professor");
        ForeignKeyRelation  exam_professor_rel         = this.createForeignKeyRelation(exam_exam_professor_id, professor_professor_id);

        ColumnDef           studentExam_student_id     = this.createNewColumnDef("student_id", "INT");
        ColumnDef           studentExam_exam_id        = this.createNewColumnDef("exam_id", "INT");
        ColumnDef           studentExam_grade          = this.createNewColumnDef("grade", "INT");
        ForeignKey          studentExam_student        = this.createForeignKey("FK_StudentExam_Student");
        ForeignKey          studentExam_exam           = this.createForeignKey("FK_StudentExam_Exam");
        ForeignKeyRelation  studentExam_student_rel    = this.createForeignKeyRelation(studentExam_student_id, student_student_id);
        ForeignKeyRelation  studentExam_exam_rel       = this.createForeignKeyRelation(studentExam_exam_id, exam_exam_id);

        student_student_id.setPrimary(true);
        student_student_id.setNotNull(true);
        student_student_id.setMetaValueSet(ColumnDef.META_VALUE_SET_ID);
        student_student_firstname.setMetaValueSet(ColumnDef.META_VALUE_SET_FIRSTNAME);
        student_student_lastname.setMetaValueSet(ColumnDef.META_VALUE_SET_LASTNAME);
        student_student_birthday.setMetaValueSet(ColumnDef.META_VALUE_SET_DATE);
        student_student_birthday.setMinValueSet(1980);
        student_student_birthday.setMaxValueSet(1996);

        professor_professor_id.setPrimary(true);
        professor_professor_id.setNotNull(true);
        professor_professor_id.setMetaValueSet(ColumnDef.META_VALUE_SET_ID);
        professor_professor_firstname.setMetaValueSet(ColumnDef.META_VALUE_SET_FIRSTNAME);
        professor_professor_lastname.setMetaValueSet(ColumnDef.META_VALUE_SET_LASTNAME);
        professor_professor_birthday.setMetaValueSet(ColumnDef.META_VALUE_SET_DATE);
        professor_professor_field.setMetaValueSet(ColumnDef.META_VALUE_SET_STUDY);
        professor_professor_birthday.setMinValueSet(1970);
        professor_professor_birthday.setMaxValueSet(1988);

        exam_exam_id.setPrimary(true);
        exam_exam_id.setNotNull(true);
        exam_exam_id.setMetaValueSet(ColumnDef.META_VALUE_SET_ID);
        exam_exam_professor_id.setNotNull(true);
        exam_exam_professor_id.setMetaValueSet(ColumnDef.META_VALUE_SET_FOREIGN_KEY);
        exam_exam_name.setMetaValueSet(ColumnDef.META_VALUE_SET_NAME);
        exam_exam_day.setMetaValueSet(ColumnDef.META_VALUE_SET_DAY);
        exam_exam_month.setMetaValueSet(ColumnDef.META_VALUE_SET_MONTH);
        exam_exam_year.setMinValueSet(2005);
        exam_exam_year.setMaxValueSet(2017);

        studentExam_student_id.setPrimary(true);
        studentExam_student_id.setMetaValueSet(ColumnDef.META_VALUE_SET_FOREIGN_KEY);
        studentExam_exam_id.setPrimary(true);
        studentExam_exam_id.setMetaValueSet(ColumnDef.META_VALUE_SET_FOREIGN_KEY);
        studentExam_grade.setNotNull(true);
        studentExam_grade.setMetaValueSet(ColumnDef.META_VALUE_SET_GRADE);

        student.addColumnDef(student_student_id);
        student.addColumnDef(student_student_firstname);
        student.addColumnDef(student_student_lastname);
        student.addColumnDef(student_student_birthday);

        professor.addColumnDef(professor_professor_id);
        professor.addColumnDef(professor_professor_firstname);
        professor.addColumnDef(professor_professor_lastname);
        professor.addColumnDef(professor_professor_birthday);
        professor.addColumnDef(professor_professor_field);

        exam.addColumnDef(exam_exam_id);
        exam.addColumnDef(exam_exam_name);
        exam.addColumnDef(exam_exam_professor_id);
        exam.addColumnDef(exam_exam_day);
        exam.addColumnDef(exam_exam_month);
        exam.addColumnDef(exam_exam_year);

        exam_professor.addForeignKeyRelation(exam_professor_rel);

        studentExam.addColumnDef(studentExam_student_id);
        studentExam.addColumnDef(studentExam_exam_id);
        studentExam.addColumnDef(studentExam_grade);

        studentExam_student.addForeignKeyRelation(studentExam_student_rel);
        studentExam_exam.addForeignKeyRelation(studentExam_exam_rel);

        studentExamSchema.addTableDef(student);
        studentExamSchema.addTableDef(professor);
        studentExamSchema.addTableDef(exam);
        studentExamSchema.addTableDef(studentExam);

        studentExamSchema.addForeignKey(studentExam_student);
        studentExamSchema.addForeignKey(studentExam_exam);

        return studentExamSchema;
    }

    @Override
    protected List<Task> buildTasks() {
        List<Task> taskList = new ArrayList<>();

        Task task = new Task();
        task.setDifficulty(0);
        task.setText("Find the Student with firstname John");
        task.setReferenceStatement("SELECT firstname FROM student WHERE firstname = 'John';");
        taskList.add(task);

        task = new Task();
        task.setDifficulty(0);
        task.setText("the firstname of all students with an id lower than 20");
        task.setReferenceStatement("SELECT firstname FROM student WHERE ID < 20;");
        taskList.add(task);

        task = new Task();
        task.setDifficulty(1);
        task.setText("Find all students by ids, who are born in november");
        task.setReferenceStatement("SELECT id FROM student WHERE birthday like '%-11-%';");
        taskList.add(task);

        task = new Task();
        task.setDifficulty(3);
        task.setText("Give me the firstname and lastname of the professor, who is overseeing the most exams.");
        task.setReferenceStatement("select p.firstname, p.lastname\n" +
                "from professor as p\n" +
                "where p.id = (\n" +
                "select p.id from exam as e join professor as p ON e.professor_id = p.id\n" +
                "group by p.id\n" +
                "order by count(e.id) desc\n" +
                "fetch first 1 row only)");
        taskList.add(task);



        return taskList;
    }
}
