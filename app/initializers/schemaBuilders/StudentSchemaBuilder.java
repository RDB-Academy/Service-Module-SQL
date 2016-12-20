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
        TableDef            exam                       = this.createNewTableDef("exam");
        TableDef            studentExam                = this.createNewTableDef("student_exam");
        ColumnDef           student_student_id         = this.createNewColumnDef("id", "INT");
        ColumnDef           student_student_firstname  = this.createNewColumnDef("firstname", "VARCHAR(255)");
        ColumnDef           student_student_lastname   = this.createNewColumnDef("lastname", "VARCHAR(255)");
        ColumnDef           exam_exam_id               = this.createNewColumnDef("id", "INT");
        ColumnDef           exam_exam_name             = this.createNewColumnDef("name", "VARCHAR(255)");
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

        exam_exam_id.setPrimary(true);
        exam_exam_id.setNotNull(true);
        exam_exam_id.setMetaValueSet(ColumnDef.META_VALUE_SET_ID);
        exam_exam_name.setMetaValueSet(ColumnDef.META_VALUE_SET_NAME);

        studentExam_student_id.setPrimary(true);
        studentExam_student_id.setMetaValueSet(ColumnDef.META_VALUE_SET_FOREIGN_KEY);
        studentExam_exam_id.setPrimary(true);
        studentExam_exam_id.setMetaValueSet(ColumnDef.META_VALUE_SET_FOREIGN_KEY);
        studentExam_grade.setNotNull(true);
        studentExam_grade.setMetaValueSet(ColumnDef.META_VALUE_SET_GRADE);

        student.addColumnDef(student_student_id);
        student.addColumnDef(student_student_firstname);
        student.addColumnDef(student_student_lastname);

        exam.addColumnDef(exam_exam_id);
        exam.addColumnDef(exam_exam_name);

        studentExam.addColumnDef(studentExam_student_id);
        studentExam.addColumnDef(studentExam_exam_id);
        studentExam.addColumnDef(studentExam_grade);

        studentExam_student.addForeignKeyRelation(studentExam_student_rel);
        studentExam_exam.addForeignKeyRelation(studentExam_exam_rel);

        studentExamSchema.addTableDef(student);
        studentExamSchema.addTableDef(exam);
        studentExamSchema.addTableDef(studentExam);

        studentExamSchema.addForeignKey(studentExam_student);
        studentExamSchema.addForeignKey(studentExam_exam);

        return studentExamSchema;
    }

    @Override
    protected List<Task> buildTasks() {
        List<Task> taskList = new ArrayList<>();

        Task johnTask = new Task();

        johnTask.setName("Find John");
        johnTask.setText("Find John");
        johnTask.setReferenceStatement("SELECT * FROM student WHERE firstname = 'John';");

        taskList.add(johnTask);

        return taskList;
    }
}
