package com.navi.sampleapp.model

import com.navi.sampleapp.Constants
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface PullRequestApi {
      @Headers("Accept:application/vnd.github+json")
      @GET("repos/square/retrofit/pulls?state=closed")
    suspend fun getPullRequest(@Query(Constants.PAGE) pageNumber: Int, @Query(Constants.PAGE_SIZE) perPage: Int): List<PullRequest>
}
