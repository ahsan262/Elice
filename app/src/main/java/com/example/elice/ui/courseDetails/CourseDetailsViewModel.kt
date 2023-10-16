package com.example.elice.ui.courseDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.elice.data.local.model.CourseLocal
import com.example.elice.data.remote.model.Course
import com.example.elice.data.remote.model.Lecture
import com.example.elice.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CourseDetailsViewModel @Inject constructor(private val repository: MainRepository) :
    ViewModel() {

    private val _isEnrolled = MutableLiveData(false)
    val isEnrolled: LiveData<Boolean> = _isEnrolled

    private val _courseDetails = MutableLiveData<Course>()
    val courseDetails: LiveData<Course> = _courseDetails

    private val _lectures = MutableLiveData<List<Lecture>>(emptyList())
    val lectures: LiveData<List<Lecture>> = _lectures

    private lateinit var courseId: String

    @OptIn(DelicateCoroutinesApi::class)
    fun changeEnrollmentStatus() {
        _isEnrolled.value = _isEnrolled.value?.not()
        // This needs to be on Global scope .
        // We are closing the screen as soon as we press enroll.
        // To make sure the data is saved it should be on GlobalScope
        GlobalScope.launch(Dispatchers.IO) {
            if (isEnrolled.value == true) {
                repository.insertCourse(CourseLocal(0, courseId = courseId.toInt()))
            } else {
                repository.delete(courseId.toInt())
            }
        }
    }

    private fun setEnrollmentStatus() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.isEnrolled(courseId.trim().toInt()).let {
                _isEnrolled.postValue(it)
            }
        }
    }

    fun setCourseDetails(id: String) {
        courseId = id
        getLectures()
        getCourseDetails()
        setEnrollmentStatus()
    }

    private fun getLectures() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getLecturesById(courseId)
                .catch { it.printStackTrace() }
                .collectLatest {
                    if (it.isSuccessful && it.body()?.lectures != null) {
                        _lectures.postValue(it.body()?.lectures!!)
                    }
                }
        }
    }

    private fun getCourseDetails() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getCourseById(courseId)
                .catch {
                    it.printStackTrace()
                }
                .collectLatest {
                    if (it.isSuccessful && it.body()?.course != null) {
                        _courseDetails.postValue(it.body()?.course!!)
                    }
                }
        }
    }
}