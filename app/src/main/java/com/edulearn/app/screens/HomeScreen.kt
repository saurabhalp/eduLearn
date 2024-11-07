package com.edulearn.app.screens

import android.annotation.SuppressLint
import android.media.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.edulearn.app.models.CourseViewModel
import com.edulearn.app.models.Courses
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.edulearn.app.R


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(courseViewModel: CourseViewModel){
    var courseList = courseViewModel.getCourses()
    Scaffold{
        Column (Modifier.padding(it)){
            Text(
                text ="Programming Courses",
                maxLines = 1,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(8.dp)
            )
            Box() {
                LazyHorizontalGrid(
                    rows = GridCells.Fixed(1),

                    ) {
                    items(courseList.size) {
                        CourseCard(courseList[it])
                    }
                }
            }
        }
    }
}

@Composable
fun CourseCard(courses: Courses){
    Box(Modifier.padding(16.dp).fillMaxSize()){

        Card() {
            Column(Modifier.align(Alignment.CenterHorizontally)) {
               Image(painter = painterResource(R.drawable.edulearn_logo), contentDescription = null,
                   Modifier.height(170.dp)
               )
                Spacer(Modifier.height(8.dp))

                Text(text = courses.name, fontSize = 24.sp, fontWeight = FontWeight.Bold, modifier = Modifier.align(
                    Alignment.CenterHorizontally))
            }
        }
    }

}



@Preview
@Composable
fun home(){
    val courseViewModel = CourseViewModel()
    HomeScreen(courseViewModel)
}