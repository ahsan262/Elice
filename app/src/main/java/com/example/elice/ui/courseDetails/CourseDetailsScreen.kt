package com.example.elice.ui.courseDetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.elice.R
import com.example.elice.data.remote.model.Course
import com.example.elice.data.remote.model.Lecture
import com.example.elice.ui.compose.Circle
import com.example.elice.ui.compose.SpacerFun
import dev.jeziellago.compose.markdowntext.MarkdownText

@Composable
fun CourseDetailsScreen(
    viewModel: CourseDetailsViewModel,
    onBackPressed: () -> Unit
) {


    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        Icon(
            imageVector = Icons.Default.KeyboardArrowLeft,
            contentDescription = "",
            modifier = Modifier
                .size(56.dp)
                .padding(10.dp)
                .clickable {
                    onBackPressed()
                },
            tint = Color.Black
        )
    }, bottomBar = {

        val isEnrolled = viewModel.isEnrolled.observeAsState(false).value
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(Color.Transparent)
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)

        ) {
            Button(
                onClick = {
                    viewModel.changeEnrollmentStatus()
                    onBackPressed()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .background(
                        Color.Transparent
                    ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isEnrolled) colorResource(
                        id = R.color.orange
                    ) else colorResource(id = R.color.blue_text)
                )
            ) {
                Text(text = if (isEnrolled) "수강 신청" else "수강 취소")
            }
        }
    }, containerColor = Color.White) {
        Column(
            modifier = Modifier
                .padding(
                    top = it.calculateTopPadding(),
                    bottom = it.calculateBottomPadding()
                )
                .verticalScroll(rememberScrollState())
        ) {

            val courses = viewModel.courseDetails.observeAsState().value
            courses?.let { it1 -> TopImageHeading(course = it1) }

            Column(modifier = Modifier.padding(16.dp)) {
                if (courses?.description.isNullOrEmpty().not()) {
                    Text(text = "과목 소개", color = colorResource(id = R.color.blue_text))
                    SpacerFun(size = 8)
                    Divider()
                    SpacerFun(size = 8)

                    MarkdownText(
                        markdown = courses?.description.orEmpty(),
                        modifier = Modifier.padding(8.dp)
                    )
                    SpacerFun(size = 16)
                    Image(
                        painter = painterResource(id = R.drawable.description),
                        contentDescription = "",
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .align(Alignment.CenterHorizontally)

                    )

                    SpacerFun(size = 16)

                    val lectures = viewModel.lectures.observeAsState(initial = emptyList()).value
                    if (lectures.isNotEmpty()) {
                        Text(text = "커리큘럼", color = colorResource(id = R.color.blue_text))
                        SpacerFun(size = 8)
                        Divider()
                        SpacerFun(size = 8)

                        lectures.forEach {
                            LectureItem(it)
                        }
                    }

                }

            }

        }
    }
}

@Composable
private fun LectureItem(lecture: Lecture) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = 16.dp, bottom = 16.dp, end = 16.dp)

    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            // verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(top = 6.dp)
                .fillMaxHeight()
                .width(20.dp)
        ) {
            Circle(color = colorResource(id = R.color.blue_text))

        }

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.padding(start = 16.dp)
        ) {
            Text(
                text = lecture.title.orEmpty(),
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = TextUnit(18f, TextUnitType.Sp)
            )

            Text(
                text = lecture.description.orEmpty(),
                color = Color.Black,
                fontSize = TextUnit(14f, TextUnitType.Sp)
            )
        }

    }
}

@Composable
private fun TopImageHeading(course: Course) {
    val haveImage = course.imageFileUrl != null
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, bottom = 8.dp)
            .wrapContentHeight()
    ) {
        if (haveImage) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 8.dp, bottom = 8.dp, start = 16.dp, end = 16.dp)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(context = context)
                        .data(course.logoFileUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = "",
                    modifier = Modifier
                        .size(50.dp)
                        .background(colorResource(id = R.color.light_gray))
                        .clip(RoundedCornerShape(10.dp)),
                    contentScale = ContentScale.Crop,
                )

                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    text = course.title.orEmpty(),
                    style = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 24.sp,
                        color = Color.Black,
                    ),
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                )
            }

            SpacerFun(size = 16)

            AsyncImage(
                model = ImageRequest.Builder(context = context)
                    .data(course.imageFileUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(2f),
                contentScale = ContentScale.Crop,
            )

        } else {

            Column {

                Box(
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .background(colorResource(id = R.color.light_gray))
                        .clip(RoundedCornerShape(10.dp))
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(context = context)
                            .data(course.logoFileUrl)
                            .crossfade(true)
                            .build(),
                        contentDescription = "",
                        modifier = Modifier
                            .size(80.dp)
                            .background(colorResource(id = R.color.light_gray))
                            .clip(RoundedCornerShape(10.dp)),
                        contentScale = ContentScale.Crop
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = course.title.orEmpty(),
                    style = TextStyle(
                        fontSize = 18.sp,
                        lineHeight = 24.sp,
                        color = Color.Black,
                    ),
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                )

                if (!course.shortDescription.isNullOrEmpty()) {
                    Text(
                        text = course.shortDescription.orEmpty(),
                        style = TextStyle(
                            fontSize = 14.sp,
                            lineHeight = 24.sp,
                            color = Color.DarkGray,
                        ),
                        modifier = Modifier.padding(
                            top = 8.dp,
                            bottom = 8.dp,
                            start = 16.dp,
                            end = 16.dp
                        )
                    )
                }

            }

        }
    }

}

