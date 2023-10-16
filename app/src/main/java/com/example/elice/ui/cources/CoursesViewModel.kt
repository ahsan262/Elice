package com.example.elice.ui.cources

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.elice.data.remote.model.Course
import com.example.elice.data.remote.model.FilterConditions
import com.example.elice.repository.MainRepository
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class CoursesViewModel @Inject constructor(private val mainRepository: MainRepository) :
    ViewModel() {

    private val _freeCourse = MutableLiveData<List<Course>>()
    val freeCourse: LiveData<List<Course>> = _freeCourse

    private val _recommendedCourse = MutableLiveData<List<Course>>()
    val recommendedCourse: LiveData<List<Course>> = _recommendedCourse

    private val _enrolledCourse = MutableLiveData<List<Course>?>()
    val enrolledCourse: LiveData<List<Course>?> = _enrolledCourse

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private var freeOffset = 0
    private var recommendedOffset = 0
    private var enrolledOffset = 0
    private val batchSize = 10
    private var allEnrolledCoursesIds = mutableListOf<Int>()

    init {
        getCourses()
    }

    private fun getCourses() {
        _isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            getFreeCourses()
            getRecommendedCourses()
            getAllEnrolledCourses()
        }

    }

    fun loadMoreFreeCourses() {
        viewModelScope.launch {
            getFreeCourses()
        }
    }

    fun loadMoreRecommendedCourses() {
        viewModelScope.launch {
            getRecommendedCourses()
        }
    }

    private suspend fun getFreeCourses() {
        mainRepository.getCourses(false, true, null, freeOffset, batchSize)
            .catch { it.printStackTrace() }
            .collectLatest {
                _isLoading.postValue(false)
                val newCourses = it.body()?.cours
                withContext(Dispatchers.Main) {
                    if (!newCourses.isNullOrEmpty()) {
                        _freeCourse.value = (_freeCourse.value ?: emptyList()) + newCourses
                        freeOffset += batchSize
                    }
                }
            }
    }

    private suspend fun getRecommendedCourses() {
        mainRepository.getCourses(true, false, null, recommendedOffset, batchSize)
            .catch { it.printStackTrace() }
            .collectLatest {
                _isLoading.postValue(false)
                val newCourses = it.body()?.cours
                withContext(Dispatchers.Main) {
                    if (!newCourses.isNullOrEmpty()) {
                        _recommendedCourse.value =
                            (_recommendedCourse.value ?: emptyList()) + newCourses
                        recommendedOffset += batchSize

                    }
                }
            }
    }

    private suspend fun getAllEnrolledCourses() {
        mainRepository.getAllEnrolled().collectLatest {
            val courseIds = it.mapNotNull { it.courseId }
            withContext(Dispatchers.IO) {
                allEnrolledCoursesIds.clear()
                allEnrolledCoursesIds.addAll(courseIds)
                enrolledOffset = 0
                getEnrolledCoursedDetails(true)
            }
        }

    }

    fun loadMoreEnrolledCourses() {
        viewModelScope.launch(Dispatchers.IO) {
            getEnrolledCoursedDetails(false)
        }
    }

    private suspend fun getEnrolledCoursedDetails(byUserChanged: Boolean) {

        val gson = Gson()
        val conditionString = gson.toJson(FilterConditions(allEnrolledCoursesIds))

        mainRepository.getCourses(null, null, conditionString, enrolledOffset, batchSize)
            .catch { it.printStackTrace() }
            .collectLatest {
                val newCourses = it.body()?.cours
                withContext(Dispatchers.Main) {
                    if (!newCourses.isNullOrEmpty()) {
                        if (byUserChanged) {
                            _enrolledCourse.value = newCourses
                        } else {
                            _enrolledCourse.value =
                                ((_enrolledCourse.value
                                    ?: emptyList()) + newCourses).distinctBy { it.id }
                            enrolledOffset += batchSize
                        }
                    } else if (byUserChanged) {
                        // User have removed all the items from the enrolled
                        _enrolledCourse.value = emptyList()
                    }
                }
            }

    }
}