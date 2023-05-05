package com.sulistyo.roomdb

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.switchMap
import com.sulistyo.roomdb.database.Student
import com.sulistyo.roomdb.database.StudentAndUniversity
import com.sulistyo.roomdb.database.StudentWithCourse
import com.sulistyo.roomdb.database.UniversityAndStudent
import com.sulistyo.roomdb.helper.SortType

class MainViewModel(private val repo: StudentRepository) : ViewModel() {
    /*
        init {
            insertAllData()
        }
    */

    private val _sort = MutableLiveData<SortType>()

    init {
        _sort.value = SortType.ASCENDING
    }

    fun changeSortType(sortType: SortType) {
        _sort.value = sortType
    }

    fun getAllStudent(): LiveData<List<Student>> = _sort.switchMap {
        repo.getAllStudent(it)
    }

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