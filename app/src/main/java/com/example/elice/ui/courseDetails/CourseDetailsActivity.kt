package com.example.elice.ui.courseDetails

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.elice.ui.compose.theme.EliceTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CourseDetailsActivity : ComponentActivity() {

    private val viewModel : CourseDetailsViewModel by viewModels()

    private val course by lazy {
        intent.getStringExtra("id") ?: ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EliceTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CourseDetailsScreen(viewModel){
                        finish()
                    }
                }
            }
        }

        viewModel.setCourseDetails(course)
    }
}