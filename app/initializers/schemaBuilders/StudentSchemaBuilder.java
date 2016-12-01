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
        ColumnDef           student_student_id         = this.createNewColumnDef("student_id", "INT");
        ColumnDef           student_student_firstname  = this.createNewColumnDef("student_firstname", "VARCHAR(255)");
        ColumnDef           student_student_lastname   = this.createNewColumnDef("student_lastname", "VARCHAR(255)");
        ColumnDef           exam_exam_id               = this.createNewColumnDef("exam_id", "INT");
        ColumnDef           exam_exam_name             = this.createNewColumnDef("exam_name", "VARCHAR(255)");
        ColumnDef           studentExam_student_id     = this.createNewColumnDef("student_id", "INT");
        ColumnDef           studentExam_exam_id        = this.createNewColumnDef("exam_id", "INT");
        ColumnDef           studentExam_grade          = this.createNewColumnDef("exam_grade", "INT");
        ForeignKey          studentExam_student        = this.createForeignKey("FK_StudentExam_Student");
        ForeignKey          studentExam_exam           = this.createForeignKey("FK_StudentExam_Exam");
        ForeignKeyRelation  studentExam_student_rel    = this.createForeignKeyRelation(studentExam_student_id, student_student_id);
        ForeignKeyRelation  studentExam_exam_rel       = this.createForeignKeyRelation(studentExam_exam_id, exam_exam_id);

        student_student_id.setPrimary(true);
        student_student_id.setNullable(false);

        exam_exam_id.setPrimary(true);
        exam_exam_id.setNullable(false);

        studentExam_student_id.setPrimary(true);
        studentExam_exam_id.setPrimary(true);
        studentExam_grade.setNullable(false);

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
        johnTask.setReferenceStatement("SELECT * FROM student WHERE student_firstname = \"John\";");

        taskList.add(johnTask);

        return taskList;
    }
}
