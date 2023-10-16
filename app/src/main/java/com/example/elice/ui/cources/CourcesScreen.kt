package com.example.elice.ui.cources

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.elice.R
import com.example.elice.data.remote.model.Course
import com.example.elice.other.OnBottomReached
import com.example.elice.ui.compose.SimpleCircularProgressComponent
import com.example.elice.ui.compose.SpacerFun
import com.example.elice.ui.compose.theme.MatBlack
import com.example.elice.ui.compose.theme.SubTextColor
import com.example.elice.ui.courseDetails.CourseDetailsActivity

@Composable
fun CourcesScreen(viewModel: CoursesViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .background(color = Color.White)
                .padding(16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.header),
                contentDescription = "Header",
                modifier =
                Modifier
                    .width(147.dp)
                    .height(32.dp)
            )
            Icon(
                imageVector = Icons.Default.Search,
                tint = Color.Black,
                contentDescription = "Search",
                modifier = Modifier
                    .size(32.dp)
                    .clickable {
                        //
                    }
            )
        }

        val isLoading = viewModel.isLoading.observeAsState(false).value
        if (isLoading) {
            SimpleCircularProgressComponent()
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {

            val freeCourses = viewModel.freeCourse.observeAsState(emptyList()).value

            if (freeCourses.isNotEmpty()) {
                CoursesMainSelectionUI(cours = freeCourses, title = "무료 과목") {
                    if (freeCourses.size >= 10) {
                        viewModel.loadMoreFreeCourses()
                    }
                }
            }

            val recommendedCourses = viewModel.recommendedCourse.observeAsState(emptyList()).value
            if (recommendedCourses.isNotEmpty()) {
                CoursesMainSelectionUI(
                    cours = recommendedCourses,
                    title = "추천 과목"
                ) {
                    if (recommendedCourses.size >= 10) {
                        viewModel.loadMoreRecommendedCourses()
                    }

                }
            }

            val enrolledCourses = viewModel.enrolledCourse.observeAsState(emptyList()).value
            if (enrolledCourses.isNullOrEmpty().not()) {
                CoursesMainSelectionUI(cours = enrolledCourses!!, title = "내 학습") {

                    // If list size is less then 10 it means no more loading
                    // Because it is less than batch size hence have no more items
                    if (enrolledCourses.size >= 10) {
                        viewModel.loadMoreEnrolledCourses()
                    }

                }
            }
        }
    }
}

@Composable
private fun CoursesMainSelectionUI(cours: List<Course>, title: String, loadMore: () -> Unit) {

    Text(
        text = title,
        style = TextStyle(
            fontSize = 16.sp,
            lineHeight = 24.sp,
            color = Color.Black,
        ),
        fontWeight = FontWeight.Bold,
        maxLines = 2,
        modifier = Modifier.padding(start = 16.dp)
    )
    SpacerFun(size = 16)

    val listState = rememberLazyListState()

    LazyRow(content = {
        items(cours.size) {
            CourseItemCard(course = cours[it])
        }
    }, state = listState)

    listState.OnBottomReached(buffer = 0) {
        loadMore()
    }

}

@Composable
fun CourseItemCard(course: Course) {

    val context = LocalContext.current
    val haveImage = course.imageFileUrl != null

    Card(
        modifier = Modifier
            .width(250.dp)
            .wrapContentHeight()
            .padding(end = 16.dp, bottom = 30.dp, start = 16.dp)
            .clickable {
                Intent(context, CourseDetailsActivity::class.java)
                    .apply {
                        putExtra("id", course.id.toString())
                    }
                    .run {
                        context.startActivity(this)
                    }
            },
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(125.dp)
                .background(MatBlack, shape = RoundedCornerShape(10.dp))
                .clip(RoundedCornerShape(10.dp))

        ) {
            if (haveImage) {
                AsyncImage(
                    model = ImageRequest.Builder(context = context)
                        .data(course.imageFileUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.Center),
                    contentScale = ContentScale.Crop,
                )
            } else {
                AsyncImage(
                    model = ImageRequest.Builder(context = context)
                        .data(course.logoFileUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = "",
                    modifier = Modifier
                        .size(56.dp)
                        .align(Alignment.Center),
                    contentScale = ContentScale.Crop,
                    error = painterResource(id = R.drawable.logo_file_url)
                )
            }

        }

        SpacerFun(size = 8)

        Text(
            text = course.title.orEmpty(),
            style = TextStyle(
                fontSize = 14.sp,
                lineHeight = 24.sp,
                color = Color.Black,
            ),
            fontWeight = FontWeight.Bold,
            maxLines = 2,
        )
        SpacerFun(size = 2)
        Text(
            text = course.shortDescription.orEmpty(),
            style = TextStyle(
                fontSize = 10.sp,
                lineHeight = 14.sp,
                color = MatBlack,
            ),
            maxLines = 2
        )
        SpacerFun(size = 8)

        LazyRow(content = {
            items(course.taglist.size) {
                TagItem(tag = course.taglist[it])
            }
        })

        SpacerFun(size = 20)
    }
}

@Composable
private fun TagItem(tag: String) {
    Text(
        text = tag,
        style = TextStyle(
            fontSize = 10.sp,
            lineHeight = 14.sp,
            color = MatBlack,
        ),
        maxLines = 2,
        modifier = Modifier
            .padding(end = 4.dp)
            .background(color = SubTextColor, shape = RoundedCornerShape(size = 4.dp))
            .padding(start = 4.dp, top = 2.dp, end = 4.dp, bottom = 2.dp)
    )
}


