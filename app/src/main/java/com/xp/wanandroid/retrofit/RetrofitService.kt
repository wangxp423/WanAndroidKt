import com.xp.wanandroid.blog.entity.BlogEntity
import com.xp.wanandroid.blog.entity.BlogTypeEntity
import com.xp.wanandroid.login.entity.UserEntity
import com.xp.wanandroid.main.entity.BannerEntity
import com.xp.wanandroid.main.entity.HotLableEntity
import kotlinx.coroutines.experimental.Deferred
import retrofit2.http.*

/**
 * Retrofit请求api
 */
interface RetrofitService {

    /**
     * 首页Banner
     * @return BannerResponse
     */
    @GET("/banner/json")
    fun getBanner(): Deferred<BannerEntity>

    /**
     * 首页数据
     * http://www.wanandroid.com/article/list/0/json
     * @param page page
     */

    @GET("/article/list/{page}/json")
    fun getHomeList(
            @Path("page") page: Int
    ): Deferred<BlogEntity>

    /**
     * 知识体系
     * http://www.wanandroid.com/tree/json
     */
    @GET("/tree/json")
    fun getTypeTreeList(): Deferred<BlogTypeEntity>

    /**
     * 知识体系下的文章
     * http://www.wanandroid.com/article/list/0/json?cid=168
     * @param page page
     * @param cid cid
     */
    @GET("/article/list/{page}/json")
    fun getArticleList(
            @Path("page") page: Int,
            @Query("cid") cid: Int
    ): Deferred<BlogEntity>

    /**
     * 我的常用网址
     */
    @GET("/lg/collect/usertools/json")
    fun getMyBookmarkList(): Deferred<HotLableEntity>
    /**
     * 常用网站
     * http://www.wanandroid.com/friend/json
     */
    @GET("/friend/json")
    fun getHotUseList(): Deferred<HotLableEntity>

    /**
     * 大家都在搜
     * http://www.wanandroid.com/hotkey/json
     */
    @GET("/hotkey/json")
    fun getHotSearchList(): Deferred<HotLableEntity>

    /**
     * 搜索
     * http://www.wanandroid.com/article/query/0/json
     * @param page page
     * @param k POST search key
     */
    @POST("/article/query/{page}/json")
    @FormUrlEncoded
    fun getSearchList(
            @Path("page") page: Int,
            @Field("k") k: String
    ): Deferred<BlogEntity>

    /**
     * 登录
     * @param username username
     * @param password password
     */
    @FormUrlEncoded
    @POST("/user/login")
    fun loginWanAndroid(
            @Field("username") username: String,
            @Field("password") password: String
    ): Deferred<UserEntity>

    /**
     * 注册
     * @param username username
     * @param password password
     * @param repassword repassword
     */
    @POST("/user/register")
    @FormUrlEncoded
    fun registerWanAndroid(
            @Field("username") username: String,
            @Field("password") password: String,
            @Field("repassword") repassowrd: String
    ): Deferred<UserEntity>

    /**
     * 获取自己收藏的文章列表
     * @param page page
     */
    @GET("/lg/collect/list/{page}/json")
    fun getLikeList(
            @Path("page") page: Int
    ): Deferred<BlogEntity>

    /**
     * 收藏文章
     * @param id id
     */
    @POST("/lg/collect/{id}/json")
    fun addCollectArticle(
            @Path("id") id: Int
    ): Deferred<BlogEntity>

    /**
     * 收藏站外文章
     * @param title title
     * @param author author
     * @param link link
     */
    @POST("/lg/collect/add/json")
    @FormUrlEncoded
    fun addCollectOutsideArticle(
            @Field("title") title: String,
            @Field("author") author: String,
            @Field("link") link: String
    ): Deferred<BlogEntity>

    /**
     * 删除收藏文章
     * @param id id
     * @param originId -1
     */
    @POST("/lg/uncollect/{id}/json")
    @FormUrlEncoded
    fun removeCollectArticle(
            @Path("id") id: Int,
            @Field("originId") originId: Int = -1
    ): Deferred<BlogEntity>

}