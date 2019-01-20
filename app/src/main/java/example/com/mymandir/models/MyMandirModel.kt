package example.com.mymandir.models

import android.databinding.BaseObservable
import android.databinding.Bindable
import java.io.Serializable
import android.view.View
import com.android.databinding.library.baseAdapters.BR


class MyMandirModel(val xid: String,
                    val temple: Int, val user: Long, val text: String, val postType: String, val type: String,
                    val commentCount: Int, val reactionCount: Int, val likeCount: Int, val shareCount: Int, val isFeatured: Boolean,
                    val code: String, val precantCount: Int, val viewCount: Int, val isRepost: Boolean, val isHidden: Boolean,
                    val isValidPost: Boolean, val title: String, val isPNSent: Boolean, val parentId: String, val id: Long,
                    var createdAt: Long, var timeStamp: String, val tags: List<Tags>, val attachments: List<Attachments>, val webPath: String,
                    val sender: Sender, val recentReactions: List<RecentReactions>, val fromCache: Boolean, val liked: Boolean,
                    val saved: Boolean, val userFollowsPoster: Boolean) : Serializable

class Tags(val id: Long, val description: String, val text: String, val imageUrl: String)

class Attachments : BaseObservable() {
    val id: Long? = null
    val url: String? = null
    val post: Long? = null
    val size: Int? = null
    val type: String? = null
    val user: Long? = null
    val title: String? = null
    val temple: Int? = null
    @get:Bindable
    val userName: String? = null
    var userImage: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.userImage)
        }
    val thumbnail_url: String? = null
    val watermark_url: String? = null
    val medium_url: String? = null
    val downsampled_url: String? = null
    @get:Bindable
    var mobile_url: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.mobile_url)
        }
    val metadata: Metadataa? = null
}

class Metadataa(val width: Int, val height: Int)

class Sender : BaseObservable() {
    val id: Long? = null
    val name: String? = null
    @get:Bindable
    var imageUrl: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.imageUrl)
        }
    val language: String? = null
    val status: String? = null
    val friendlyId: String? = null
    val thumbnailUrl: String? = null
    val microThumbnailUrl: String? = null
    val isIdentityVerified: String? = null
    val webPath: String? = null
}

class RecentReactions(val id: Int, val staticUrl: String, val thumbnailUrl: String, val name: String, val thumbnailUrlWithBackground: String,
                      val selectedImageUrl: String, val nameKey: String)


class Signup : BaseObservable() {
    @get:Bindable
    var userImage: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.userImage)
        }
}

interface ClickHandler {
    fun onClick(view: View)
}



