package com.sulistyo.roomdb

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.sulistyo.roomdb.database.Student
import com.sulistyo.roomdb.database.StudentAndUniversity
import com.sulistyo.roomdb.database.StudentWithCourse
import com.sulistyo.roomdb.database.UniversityAndStudent
import kotlinx.coroutines.launch

class MainViewModel(private val repo: StudentRepository) : ViewModel() {
/*
    init {
        insertAllData()
    }
*/

    fun getAllStudent(): LiveData<List<Student>> = repo.getAllStudent()

    fun getAllStudentAndUniversity(): LiveData<List<StudentAndUniversity>> =
        repo.getAllStudentAndUniversity()

    fun getAllUniversityAndStudent(): LiveData<List<UniversityAndStudent>> =
        repo.getAllUniversityAndStudent()

    fun getAllStudentWithCourse(): LiveData<List<StudentWithCourse>> =
        repo.getAllStudentWithCourse()

/*
    private fun insertAllData() = viewModelScope.launch {
        repo.insertAllData()
    }
*/
}

class ViewModelFactory(private val repository: StudentRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}