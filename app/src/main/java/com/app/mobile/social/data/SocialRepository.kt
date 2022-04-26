package com.app.mobile.social.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.zip
import javax.inject.Inject
import javax.inject.Singleton

interface SocialRepository {
    suspend fun sendReply(idx: Int, name: String, reply: String) : Flow<Boolean>
    suspend fun like(idx: Int, likely: Boolean) : Flow<Boolean>
    suspend fun getItems(page: Int, size: Int): Flow<List<SocialModel.Resource>>
    suspend fun getAD(page: Int, size: Int): Flow<List<SocialModel.AD>>
}

@Singleton
class SocialRepositoryImpl @Inject constructor() : SocialRepository {
    override suspend fun sendReply(idx: Int, name: String, reply: String) : Flow<Boolean>{
        return flowOf(true)
    }

    override suspend fun like(idx: Int, likely: Boolean) : Flow<Boolean>{
        return flowOf(true)
    }

    override suspend fun getItems(page: Int, size: Int): Flow<List<SocialModel.Resource>> {
        val temp = SocialModel.socialListData().subList(page * size, (page * size) + size)
        return flowOf(temp).zip(getAD(page, size / 2)) { social, ad ->
            val resource = mutableListOf<SocialModel.Resource>()
            social.forEachIndexed { index, value ->
                if (index % 3 == 0) {
                    resource.add(ad[index / 3 - 1])
                    resource.add(value)
                } else {
                    resource.add(value)
                }
            }
            resource
        }

    }

    override suspend fun getAD(page: Int, size: Int): Flow<List<SocialModel.AD>> {
        return flowOf(
            SocialModel.adData().subList(page * size, (page * size) + size)
        )
    }
}