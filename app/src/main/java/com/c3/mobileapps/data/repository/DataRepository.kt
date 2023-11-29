package com.c3.mobileapps.data.repository

class DataRepository(
    courseRepository: CourseRepository,
    roomRepository: RoomRepository
) {
    val remote = courseRepository
    val local = roomRepository

}