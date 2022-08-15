import java.util.*
import kotlin.Comparator

class TopK {
    fun topKFrequent(nums: IntArray, k: Int): IntArray {
        var count = hashMapOf<Int, Int>()

        // count the unique number
        nums.forEach {
            count[it] = if (count.containsKey(it)) count[it]!!.plus(1) else 1
        }

        // use a priority queue to sort
        val compareByFirst: Comparator<Pair<Int, Int>> = compareBy { it.first }
        var pq = PriorityQueue<Pair<Int, Int>>(compareByFirst)
        count.forEach {
            pq.add(Pair<Int, Int>(it.value, it.key))
        }

        // remove elements until k elements left
        while (pq.count() != k) {
            pq.poll()
        }

        // top k elements
        var result = pq.map {
            it.second
        }.toIntArray()

        return result
    }
}

fun main() {
    val res = TopK().topKFrequent(intArrayOf(1,1,1,2,2,2,3), 2)
    println(Arrays.toString(res))
}