package com.sulistyo.roomdb

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.sulistyo.roomdb.adapter.StudentAndUniversityAdapter
import com.sulistyo.roomdb.adapter.StudentListAdapter
import com.sulistyo.roomdb.adapter.StudentWithCourseAdapter
import com.sulistyo.roomdb.adapter.UniversityAndStudentAdapter
import com.sulistyo.roomdb.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels {
        ViewModelFactory((application as MyApp).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvMain.layoutManager = LinearLayoutManager(this)

        getStudent()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_single_table -> {
                getStudent()
                return true
            }

            R.id.action_many_to_one -> {
                getStudentAndUniversity()
                true
            }

            R.id.action_one_to_many -> {
                getUniversityAndStudent()
                true
            }

            R.id.action_many_to_many -> {
                getStudentWithCourse()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun getStudent() {
        val adapter = StudentListAdapter()
        binding.rvMain.adapter = adapter
        mainViewModel.getAllStudent().observe(this) {
            it.forEach(::println)
            adapter.submitList(it)
        }
    }

    private fun getStudentAndUniversity() {
        val adapter = StudentAndUniversityAdapter()
        binding.rvMain.adapter = adapter
        mainViewModel.getAllStudentAndUniversity().observe(this) {
            adapter.submitList(it)
            Log.d(TAG, "getStudentAndUniversity: $it")

        }
    }

    private fun getUniversityAndStudent() {
        val adapter = UniversityAndStudentAdapter()
        binding.rvMain.adapter = adapter
        mainViewModel.getAllUniversityAndStudent().observe(this) {
            adapter.submitList(it)
            Log.d(TAG, "getStudentAndUniversity: $it")

        }
    }


    private fun getStudentWithCourse() {
        val adapter = StudentWithCourseAdapter()
        binding.rvMain.adapter = adapter
        mainViewModel.getAllStudentWithCourse().observe(this) {
            adapter.submitList(it)
            Log.d(TAG, "getStudentAndUniversity: $it")

        }
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}