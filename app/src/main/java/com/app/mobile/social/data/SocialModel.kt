package com.app.mobile.social.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

class SocialModel {

    interface Resource {
        val idx: Long
    }

    @Parcelize
    data class Social(
        override val idx: Long,
        val title: String,
        var like: Int,
        var isLike : Boolean,
        val imgUrl: String,
        var replies: MutableList<Reply>
    ) : Parcelable, Resource

    @Parcelize
    data class Reply(
        val idx: Long,
        val name: String,
        val comment: String
    ): Parcelable

    data class AD(
        override val idx: Long,
        val imgUrl: String,
        val url: String
    ) : Resource

    companion object {
        fun socialListData(): List<Social> {
            val temp = mutableListOf<Social>()
            (0 until 20).forEach {
                temp.add(
                    Social(
                        idx = it.toLong(),
                        title = "title $it",
                        like = 0,
                        isLike = false,
                        imgUrl = if (it % 2 == 0) "" else "img",
                        replies = mutableListOf()
                    )
                )
            }
            return temp
        }

        fun adData(): List<AD> {
            val temp = mutableListOf<AD>()
            (0 until 10).forEach {
                temp.add(
                    AD(
                        idx = Int.MAX_VALUE - it.toLong(),
                        imgUrl = "img",
                        url = if (it % 2 == 0) "https://www.google.com" else "https://www.naver.com"
                    )
                )
            }
            return temp
        }
    }

}