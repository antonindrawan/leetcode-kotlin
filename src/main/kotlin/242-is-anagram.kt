class Anagram {
    fun isAnagram(s: String, t: String): Boolean {
        println(s.toCharArray().sorted())
        println(t.toCharArray().sorted())
        val sortedS = s.toCharArray().sorted()
        val sortedT = t.toCharArray().sorted()
        return sortedS.equals(sortedT)
    }
}

fun main() {
    val sol = Anagram()
    println(sol.isAnagram("aaa", "aaa"))
}