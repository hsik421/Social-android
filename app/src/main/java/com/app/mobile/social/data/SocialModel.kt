package com.app.mobile.social.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

class SocialModel {
    interface Resource {
        val idx: Int
    }

    @Parcelize
    data class Social(
        override val idx: Int,
        val title: String,
        val like: Int,
        val imgUrl: String,
        var replies: MutableList<Reply>
    ) : Parcelable, Resource

    @Parcelize
    data class Reply(
        val idx: Int,
        val name: String,
        val comment: String
    ): Parcelable

    data class AD(
        override val idx: Int,
        val imgUrl: String,
        val url: String
    ) : Resource

    companion object {
        fun socialListData(): List<Social> {
            val temp = mutableListOf<Social>()
            (0 until 18).forEach {
                temp.add(
                    Social(
                        idx = it,
                        title = "title $it",
                        like = 0,
                        imgUrl = if (it % 2 == 0) "" else "img",
                        replies = mutableListOf()
                    )
                )
            }
            return temp
        }

        fun adData(): List<AD> {
            val temp = mutableListOf<AD>()
            (19 until 27).forEach {
                temp.add(
                    AD(
                        idx = it,
                        imgUrl = "img",
                        url = if (it % 2 == 0) "https://www.google.com" else "https://www.naver.com"
                    )
                )
            }
            return temp
        }
    }

}