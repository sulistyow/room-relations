package com.sulistyo.roomdb

import androidx.lifecycle.LiveData
import com.sulistyo.roomdb.database.Student
import com.sulistyo.roomdb.database.StudentAndUniversity
import com.sulistyo.roomdb.database.StudentDao
import com.sulistyo.roomdb.database.StudentWithCourse
import com.sulistyo.roomdb.database.UniversityAndStudent
import com.sulistyo.roomdb.helper.SortType
import com.sulistyo.roomdb.helper.SortUtils

class StudentRepository(private val dao: StudentDao) {
    fun getAllStudent(sortType: SortType): LiveData<List<Student>> {
        val query = SortUtils.getSortedQuery(sortType)
        return dao.getAllStudent(query)
    }

    fun getAllStudentAndUniversity(): LiveData<List<StudentAndUniversity>> =
        dao.getAllStudentAndUniversity()

    fun getAllUniversityAndStudent(): LiveData<List<UniversityAndStudent>> =
        dao.getAllUniversityAndStudent()

    fun getAllStudentWithCourse(): LiveData<List<StudentWithCourse>> = dao.getAllStudentWithCourse()

    /*suspend fun insertAllData() {
        dao.insertStudent(InitialDataSource.getStudents())
        dao.insertUniversity(InitialDataSource.getUniversities())
        dao.insertCourse(InitialDataSource.getCourses())
        dao.insertCourseStudentCrossRef(InitialDataSource.getCourseStudentRelation())
    }*/

}