import java.util.LinkedList
import java.util.Queue

class Twitter() {

    // user id -> followees
    var userToFollowees: HashMap<Int, MutableSet<Int>> = hashMapOf()


    var tweetCounter: Int = 0
    data class Tweet(val userId: Int, val id: Int, val timestamp: Int)

    // user id -> tweets
    var userToTweets: HashMap<Int, Queue<Tweet>> = hashMapOf()

    // post
    // [1] user: 1, Tweet(id: 5)
    // [2] user: 2, Tweet(id: 6)
    // Max 10 tweets per user
    fun postTweet(userId: Int, tweetId: Int) {
        tweetCounter += 1
        var tweet = Tweet(userId, tweetId, tweetCounter)

        userToTweets[userId] = userToTweets.getOrDefault(userId, LinkedList<Tweet>())
            .apply {
                if (size >= 10) {
                    poll()
                }
                add(tweet)
            }
    }

    fun getNewsFeed(userId: Int): List<Int> {
        // combine userId's tweet + followees' tweet

        // Approach:
        // 1. add all my posts
        // 2. get my followees
        //      add all posts

        // 3. sort the tweets in descending order
        // 4. take the first 10 posts

        var allTweets: MutableList<Tweet> = mutableListOf()

        // user + followees
        val followeeList = userToFollowees.get(userId)
        var users = mutableListOf<Int>(userId)
        if (followeeList != null) {
            users.addAll(followeeList.toList())
        }

        // append all tweets
        users.forEachIndexed { index, value ->
            val tweets = userToTweets[value]
            if (tweets != null) {
                allTweets.addAll(tweets.toList())
            }
        }

        allTweets.sortByDescending { it.timestamp }

        // Take the first 10
        var result = mutableListOf<Int>()
        val lastIndex = if (allTweets.lastIndex >= 10) 9 else allTweets.lastIndex
        for (i in 0..lastIndex) {
            result.add(allTweets[i].id)
        }
        return result
    }

    fun follow(followerId: Int, followeeId: Int) {
        userToFollowees[followerId] = userToFollowees.getOrDefault(followerId, mutableSetOf())
            .apply { add(followeeId) }
    }

    fun unfollow(followerId: Int, followeeId: Int) {
        var followees = userToFollowees.get(followerId)
        followees?.apply { remove(followeeId) }
    }
}

fun main() {
    val cmd = listOf("Twitter","postTweet","postTweet","postTweet","postTweet","postTweet","postTweet","postTweet","postTweet","postTweet","postTweet","postTweet","getNewsFeed")
    val cmdParams = listOf(null,Pair(1,5), Pair(1,3), Pair(1,101), Pair(1,13), Pair(1,10), Pair(1,2), Pair(1,94), Pair(1,505), Pair(1,333), Pair(1,22), Pair(1,11), 1)

    //val cmd = listOf("Twitter","postTweet","postTweet","postTweet","postTweet","postTweet","postTweet","postTweet","postTweet","postTweet","getNewsFeed")
    //val cmdParams = listOf(null, Pair(1,101), Pair(1,13), Pair(1,10), Pair(1,2), Pair(1,94), Pair(1,505), Pair(1,333), Pair(1,22), Pair(1,11), 1)

    //val cmd = listOf("Twitter", "postTweet", "getNewsFeed", "follow", "postTweet", "getNewsFeed", "unfollow", "getNewsFeed")
    //val cmdParams = listOf(null, Pair(1, 5), 1, Pair(1, 2), Pair(2, 6), 1, Pair(1, 2), 1)

    val twitter = Twitter()
    cmd.forEachIndexed { index, it ->
        when (it) {
            "postTweet" -> {
                val prm = cmdParams[index] as Pair<Int, Int>
                //println("postTweet ${prm.first} - ${prm.second}")
                twitter.postTweet(prm.first, prm.second)
            }
            "getNewsFeed" -> {
                println("getNewsFeed ${cmdParams[index] as Int}")
                println(twitter.getNewsFeed(cmdParams[index] as Int))

            }
            "follow" -> {
                val prm = cmdParams[index] as Pair<Int, Int>
                //println("follow ${prm.first} - ${prm.second}")
                twitter.follow(prm.first, prm.second)

            }
            "unfollow" -> {
                val prm = cmdParams[index] as Pair<Int, Int>
                //println("unfollow ${prm.first} - ${prm.second}")
                twitter.unfollow(prm.first, prm.second)
            }
        }
    }


}

