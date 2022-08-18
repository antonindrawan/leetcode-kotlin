class MedianOfTwoSortedArrays {
    fun findMedianSortedArrays(nums1: IntArray, nums2: IntArray): Double {

        // Case 1
        // iteration 1
        // 1 [1, 3]
        //    ^
        // 2 [1, 3]
        //    ^
        // merged array: [1]

        // iteration 2
        // 1 [1, 3]
        //       ^
        // 2 [1, 3]
        //    ^
        // merged array: [1, 1]

        // iteration 3
        // 1 [1, 3]
        //       ^
        // 2 [1, 3]
        //       ^
        // merged array: [1, 1, 3]

        // Case 2
        // iteration 1
        // 1 [1, 2]
        //    ^
        // 2 [3, 3]
        //    ^
        // merged array [1]

        // iteration 2
        // 1 [1, 2]
        //       ^
        // 2 [3, 3]
        //    ^
        // merged array [1, 2]

        // iteration 3
        // 1 [1, 2]
        //          ^
        // 2 [3, 3]
        //    ^
        // merged array [1, 2, 3] DONE. Median is (2+3)/2 = 2.50

        val len = nums1.size + nums2.size
        var mergedArray = mutableListOf<Int>()
        var nums1Idx = 0
        var nums2Idx = 0

        val targetIdx = len / 2
        var i = 0
        while (i <= targetIdx) {
            if (nums1Idx == nums1.size) {
                // no more item in nums1, so add nums2
                mergedArray.add(nums2[nums2Idx])
                nums2Idx += 1
            } else if (nums2Idx == nums2.size) {
                // no more item in nums1, so add nums2
                mergedArray.add(nums1[nums1Idx])
                nums1Idx += 1
            } else if (nums1[nums1Idx] <= nums2[nums2Idx]) {
                // put it on the merged array
                // increment nums1Idx
                mergedArray.add(nums1[nums1Idx])
                nums1Idx += 1
            } else {
                mergedArray.add(nums2[nums2Idx])
                nums2Idx += 1
            }
            i += 1
        }
        println(mergedArray)

        // special case is mergedArray.size == nums1.size + nums2.size. That size must be 2

        return if (len % 2 == 0) {
            (mergedArray[targetIdx] + mergedArray[targetIdx-1]) / 2.0
        } else {
            mergedArray[targetIdx] / 1.0
        }
    }
}

fun main() {
    val nums1 = intArrayOf()
    val nums2 = intArrayOf(1, 2, 3, 4,5)
    val sol = MedianOfTwoSortedArrays()

    val med = sol.findMedianSortedArrays(nums1, nums2)
    println(med)
}