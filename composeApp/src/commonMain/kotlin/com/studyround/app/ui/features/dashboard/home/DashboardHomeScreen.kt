package com.studyround.app.ui.features.dashboard.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.studyround.app.data.model.remote.dto.Category
import com.studyround.app.data.model.remote.dto.Course
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import studyround.composeapp.generated.resources.Res
import studyround.composeapp.generated.resources.home
import studyround.composeapp.generated.resources.ic_home

class DashboardHomeScreen: Tab {

    @Composable
    override fun Content() {
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            CategorisedCoursesContent(categories = listOf(
                Category(
                    1, "One",
                    listOf(
                        Course(1, title = "A course", rating = 3.77f, formattedPrice = "Free", imageUrl = "https://backend.studyround.com/rails/active_storage/blobs/eyJfcmFpbHMiOnsibWVzc2FnZSI6IkJBaHBBcWdCIiwiZXhwIjpudWxsLCJwdXIiOiJibG9iX2lkIn19--02144408fb9e7ff4ca98a8af7ef457d649664e60/image.jpeg"),
                        Course(1, title = "A course", rating = 3.77f, formattedPrice = "Free", imageUrl = "https://backend.studyround.com/rails/active_storage/blobs/eyJfcmFpbHMiOnsibWVzc2FnZSI6IkJBaHBBcWdCIiwiZXhwIjpudWxsLCJwdXIiOiJibG9iX2lkIn19--02144408fb9e7ff4ca98a8af7ef457d649664e60/image.jpeg"),
                        Course(1, title = "A course", rating = 3.77f, formattedPrice = "Free", imageUrl = "https://backend.studyround.com/rails/active_storage/blobs/eyJfcmFpbHMiOnsibWVzc2FnZSI6IkJBaHBBcWdCIiwiZXhwIjpudWxsLCJwdXIiOiJibG9iX2lkIn19--02144408fb9e7ff4ca98a8af7ef457d649664e60/image.jpeg"),
                        Course(1, title = "A course", rating = 3.77f, formattedPrice = "Free", imageUrl = "https://backend.studyround.com/rails/active_storage/blobs/eyJfcmFpbHMiOnsibWVzc2FnZSI6IkJBaHBBcWdCIiwiZXhwIjpudWxsLCJwdXIiOiJibG9iX2lkIn19--02144408fb9e7ff4ca98a8af7ef457d649664e60/image.jpeg"),
                        Course(1, title = "A course", rating = 3.77f, formattedPrice = "Free", imageUrl = "https://backend.studyround.com/rails/active_storage/blobs/eyJfcmFpbHMiOnsibWVzc2FnZSI6IkJBaHBBcWdCIiwiZXhwIjpudWxsLCJwdXIiOiJibG9iX2lkIn19--02144408fb9e7ff4ca98a8af7ef457d649664e60/image.jpeg"),
                    )
                ),

                Category(
                    1, "Two",
                    listOf(
                        Course(1, title = "A course", rating = 3.77f, formattedPrice = "Free", imageUrl = "https://backend.studyround.com/rails/active_storage/blobs/eyJfcmFpbHMiOnsibWVzc2FnZSI6IkJBaHBBcWdCIiwiZXhwIjpudWxsLCJwdXIiOiJibG9iX2lkIn19--02144408fb9e7ff4ca98a8af7ef457d649664e60/image.jpeg"),
                        Course(1, title = "A course", rating = 3.77f, formattedPrice = "Free", imageUrl = "https://backend.studyround.com/rails/active_storage/blobs/eyJfcmFpbHMiOnsibWVzc2FnZSI6IkJBaHBBcWdCIiwiZXhwIjpudWxsLCJwdXIiOiJibG9iX2lkIn19--02144408fb9e7ff4ca98a8af7ef457d649664e60/image.jpeg"),
                        Course(1, title = "A course", rating = 3.77f, formattedPrice = "Free", imageUrl = "https://backend.studyround.com/rails/active_storage/blobs/eyJfcmFpbHMiOnsibWVzc2FnZSI6IkJBaHBBcWdCIiwiZXhwIjpudWxsLCJwdXIiOiJibG9iX2lkIn19--02144408fb9e7ff4ca98a8af7ef457d649664e60/image.jpeg"),
                        Course(1, title = "A course", rating = 3.77f, formattedPrice = "Free", imageUrl = "https://backend.studyround.com/rails/active_storage/blobs/eyJfcmFpbHMiOnsibWVzc2FnZSI6IkJBaHBBcWdCIiwiZXhwIjpudWxsLCJwdXIiOiJibG9iX2lkIn19--02144408fb9e7ff4ca98a8af7ef457d649664e60/image.jpeg"),
                        Course(1, title = "A course", rating = 3.77f, formattedPrice = "Free", imageUrl = "https://backend.studyround.com/rails/active_storage/blobs/eyJfcmFpbHMiOnsibWVzc2FnZSI6IkJBaHBBcWdCIiwiZXhwIjpudWxsLCJwdXIiOiJibG9iX2lkIn19--02144408fb9e7ff4ca98a8af7ef457d649664e60/image.jpeg"),
                    )
                ),

                Category(
                    1, "Three",
                    listOf(
                        Course(1, title = "A course", rating = 3.77f, formattedPrice = "Free", imageUrl = "https://backend.studyround.com/rails/active_storage/blobs/eyJfcmFpbHMiOnsibWVzc2FnZSI6IkJBaHBBcWdCIiwiZXhwIjpudWxsLCJwdXIiOiJibG9iX2lkIn19--02144408fb9e7ff4ca98a8af7ef457d649664e60/image.jpeg"),
                        Course(1, title = "A course", rating = 3.77f, formattedPrice = "Free", imageUrl = "https://backend.studyround.com/rails/active_storage/blobs/eyJfcmFpbHMiOnsibWVzc2FnZSI6IkJBaHBBcWdCIiwiZXhwIjpudWxsLCJwdXIiOiJibG9iX2lkIn19--02144408fb9e7ff4ca98a8af7ef457d649664e60/image.jpeg"),
                        Course(1, title = "A course", rating = 3.77f, formattedPrice = "Free", imageUrl = "https://backend.studyround.com/rails/active_storage/blobs/eyJfcmFpbHMiOnsibWVzc2FnZSI6IkJBaHBBcWdCIiwiZXhwIjpudWxsLCJwdXIiOiJibG9iX2lkIn19--02144408fb9e7ff4ca98a8af7ef457d649664e60/image.jpeg"),
                        Course(1, title = "A course", rating = 3.77f, formattedPrice = "Free", imageUrl = "https://backend.studyround.com/rails/active_storage/blobs/eyJfcmFpbHMiOnsibWVzc2FnZSI6IkJBaHBBcWdCIiwiZXhwIjpudWxsLCJwdXIiOiJibG9iX2lkIn19--02144408fb9e7ff4ca98a8af7ef457d649664e60/image.jpeg"),
                        Course(1, title = "A course", rating = 3.77f, formattedPrice = "Free", imageUrl = "https://backend.studyround.com/rails/active_storage/blobs/eyJfcmFpbHMiOnsibWVzc2FnZSI6IkJBaHBBcWdCIiwiZXhwIjpudWxsLCJwdXIiOiJibG9iX2lkIn19--02144408fb9e7ff4ca98a8af7ef457d649664e60/image.jpeg"),
                    )
                ),

                Category(
                    1, "Four",
                    listOf(
                        Course(1, title = "A course", rating = 3.77f, formattedPrice = "Free", imageUrl = "https://backend.studyround.com/rails/active_storage/blobs/eyJfcmFpbHMiOnsibWVzc2FnZSI6IkJBaHBBcWdCIiwiZXhwIjpudWxsLCJwdXIiOiJibG9iX2lkIn19--02144408fb9e7ff4ca98a8af7ef457d649664e60/image.jpeg"),
                        Course(1, title = "A course", rating = 3.77f, formattedPrice = "Free", imageUrl = "https://backend.studyround.com/rails/active_storage/blobs/eyJfcmFpbHMiOnsibWVzc2FnZSI6IkJBaHBBcWdCIiwiZXhwIjpudWxsLCJwdXIiOiJibG9iX2lkIn19--02144408fb9e7ff4ca98a8af7ef457d649664e60/image.jpeg"),
                        Course(1, title = "A course", rating = 3.77f, formattedPrice = "Free", imageUrl = "https://backend.studyround.com/rails/active_storage/blobs/eyJfcmFpbHMiOnsibWVzc2FnZSI6IkJBaHBBcWdCIiwiZXhwIjpudWxsLCJwdXIiOiJibG9iX2lkIn19--02144408fb9e7ff4ca98a8af7ef457d649664e60/image.jpeg"),
                        Course(1, title = "A course", rating = 3.77f, formattedPrice = "Free", imageUrl = "https://backend.studyround.com/rails/active_storage/blobs/eyJfcmFpbHMiOnsibWVzc2FnZSI6IkJBaHBBcWdCIiwiZXhwIjpudWxsLCJwdXIiOiJibG9iX2lkIn19--02144408fb9e7ff4ca98a8af7ef457d649664e60/image.jpeg"),
                        Course(1, title = "A course", rating = 3.77f, formattedPrice = "Free", imageUrl = "https://backend.studyround.com/rails/active_storage/blobs/eyJfcmFpbHMiOnsibWVzc2FnZSI6IkJBaHBBcWdCIiwiZXhwIjpudWxsLCJwdXIiOiJibG9iX2lkIn19--02144408fb9e7ff4ca98a8af7ef457d649664e60/image.jpeg"),
                    )
                )
            ))
        }
    }

    override val options: TabOptions
        @Composable
        get() {
            val title = stringResource(Res.string.home)
            val icon = painterResource(Res.drawable.ic_home)

            return remember {
                TabOptions(
                    index = 0u,
                    title = title,
                    icon = icon,
                )
            }
        }
}
