package com.leetcode.longestconsecutive

import kotlin.math.max

class Solution {
    fun longestConsecutive(nums: IntArray): Int {
        var numberSet =  hashSetOf<Int>()

        nums.forEach {
             numberSet.add(it)
        }

        var longestSequence = 0
        var sequenceCount = 0
        nums.forEach {
            if (!numberSet.contains(it - 1)) {
                sequenceCount = 0
                while (numberSet.contains(it + sequenceCount)) {
                    sequenceCount += 1
                }
                longestSequence = max(sequenceCount, longestSequence)
            }
        }
        return longestSequence
    }
}
fun main() {
    val sol = Solution()
    println(sol.longestConsecutive(intArrayOf(100,4,200,1,3,2)))
}