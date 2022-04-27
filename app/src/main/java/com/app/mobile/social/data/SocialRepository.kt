package com.app.mobile.social.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.zip
import javax.inject.Inject
import javax.inject.Singleton

interface SocialRepository {
    suspend fun sendReply(idx: Long, name: String, reply: String): Flow<Boolean>
    suspend fun like(idx: Long, likely: Boolean): Flow<Int>
    suspend fun getItems(page: Int, size: Int): Flow<List<SocialModel.Resource>>
    suspend fun getAD(page: Int, size: Int): Flow<List<SocialModel.AD>>
}

@Singleton
class SocialRepositoryImpl @Inject constructor() : SocialRepository {
    /***
     * @idx = content Idx
     * @name = 이름
     * @reply = 댓글
     * return 동기화된 댓글 리스트 예정
     */
    override suspend fun sendReply(idx: Long, name: String, reply: String): Flow<Boolean> {
        return flowOf(true)
    }

    /***
     * @idx = content Idx
     * return 서버 Like Count
     */
    override suspend fun like(idx: Long, likely: Boolean): Flow<Int> {
        return if(likely) flowOf(1) else flowOf(0)
    }

    /***
     * @page = 페이징처리 ( page or Content_Idx 마지막값을 기준으로 통신예정 )
     * return 서버 결과값
     */
    override suspend fun getItems(page: Int, size: Int): Flow<List<SocialModel.Resource>> {
        val temp = SocialModel.socialListData().subList(
            (page * size).coerceAtMost(SocialModel.socialListData().size),
            ((page * size) + size).coerceAtMost(SocialModel.socialListData().size)
        )
        return flowOf(temp).zip(getAD(page, size / 2)) { social, ad ->
            val resource = mutableListOf<SocialModel.Resource>()
            social.forEachIndexed { index, value ->
                if (index % 2 == 0 && index != 0) {
                    ad.getOrNull(index / 2 - 1)?.let {
                        resource.add(it)
                    }
                    resource.add(value)
                } else {
                    resource.add(value)
                }
            }
            resource
        }

    }
    /***
     * @page = 페이징처리 ( page or Content_Idx 마지막값을 기준으로 통신예정 )
     * return 서버 결과값
     */
    override suspend fun getAD(page: Int, size: Int): Flow<List<SocialModel.AD>> {
        return flowOf(
            SocialModel.adData().subList(
                (page * size).coerceAtMost(SocialModel.adData().size),
                ((page * size) + size).coerceAtMost(SocialModel.adData().size)
            )
        )
    }
}