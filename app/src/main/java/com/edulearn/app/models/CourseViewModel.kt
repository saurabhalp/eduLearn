package com.edulearn.app.models

import androidx.lifecycle.ViewModel

class CourseViewModel: ViewModel() {
    fun getCourses():List<Courses>{
        val courseList = listOf<Courses>(
            Courses("python","url"),
            Courses("python","url"),
            Courses("python","url"),
            Courses("python","url"),
            Courses("python","url"),
            Courses("python","url")
        )
        return courseList
    }
}

data class Courses(val name: String, val imageUrl: String)