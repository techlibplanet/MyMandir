package example.com.mymandir.models

class MyMandirModel(val xid : String, val temple : Int, val user : Long, val text : String, val postType : String, val type : String,
                    val commentCount : Int, val reactionCount : Int, val likeCount : Int, val shareCount : Int, val isFeatured : Boolean,
                    val code : String, val precantCount : Int, val viewCount: Int, val isRepost : Boolean, val isHidden : Boolean,
                    val isValidPost : Boolean, val title : String, val isPNSent : Boolean, val parentId : String, val id : Long,
                    val createdAt : Long,val tags : List<Tags>, val attachments : List<Attachments>,val webPath : String,val sender : Sender, val recentReactions : List<RecentReactions>, val fromCache : Boolean, val liked : Boolean,
                    val saved : Boolean, val userFollowsPoster : Boolean)

class Tags(val id : Long, val description : String, val text : String, val imageUrl : String)

class Attachments(val id : Long, val url : String, val post : Long, val size : Int, val type : String, val user : Long, val title : String,
                  val temple : Int, val userName : String,val userImage : String, val thumbnail_url : String, val watermark_url : String,
                  val medium_url : String, val downsampled_url : String, val mobile_url : String, val metadata : Metadataa)

class Metadataa(val width : Int, val height : Int)

class Sender(val id : Long, val name : String, val imageUrl : String,val language : String, val status : String, val friendlyId : String,
             val thumbnailUrl : String, val microThumbnailUrl : String, val isIdentityVerified : String, val webPath : String)

class RecentReactions(val id : Int, val staticUrl : String, val thumbnailUrl : String, val name : String, val thumbnailUrlWithBackground : String,
                      val selectedImageUrl :String, val nameKey : String)



