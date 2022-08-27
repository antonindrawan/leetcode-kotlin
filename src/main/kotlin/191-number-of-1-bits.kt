class Solution191 {
    // you need treat n as an unsigned value
    fun hammingWeight(n:Int):Int {
        var count = 0
        var tmp = n

        for (i in 0..31) {
            if ((tmp and 1) == 1) {
                count += 1
            }
            tmp = (tmp shr 1)
        }
        return count
    }
}

fun main() {
    val sol = Solution191()
    println(sol.hammingWeight(-3))
}