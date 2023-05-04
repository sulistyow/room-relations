package com.sulistyo.roomdb.database

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RenameColumn
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.AutoMigrationSpec
import androidx.sqlite.db.SupportSQLiteDatabase
import com.sulistyo.roomdb.helper.InitialDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(
    entities = [Student::class, Course::class, University::class, CourseStudentCrossRef::class],
    version = 3,
    autoMigrations = [AutoMigration(from = 1, to = 2),
        AutoMigration(from = 2, to = 3, spec = StudentDatabase.MyAutoMigraion::class)],
    exportSchema = true
)
abstract class StudentDatabase : RoomDatabase() {

    @RenameColumn(tableName = "Student", fromColumnName = "graduate", toColumnName = "isGraduate")
    class MyAutoMigraion : AutoMigrationSpec

    abstract fun studentDao(): StudentDao

    companion object {
        @Volatile
        private var INSTANCE: StudentDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context, applicationScope: CoroutineScope): StudentDatabase {
            if (INSTANCE == null) {
                synchronized(StudentDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        StudentDatabase::class.java, "student_database"
                    ).fallbackToDestructiveMigration()
                        .addCallback(object : Callback() {
                            override fun onCreate(db: SupportSQLiteDatabase) {
                                super.onCreate(db)
                                INSTANCE?.let { database ->
                                    applicationScope.launch {
                                        val dao = database.studentDao()
                                        dao.insertStudent(InitialDataSource.getStudents())
                                        dao.insertCourse(InitialDataSource.getCourses())
                                        dao.insertUniversity(InitialDataSource.getUniversities())
                                        dao.insertCourseStudentCrossRef(InitialDataSource.getCourseStudentRelation())
                                    }
                                }
                            }
                        }).build()
                }
            }
            return INSTANCE as StudentDatabase
        }
    }
}