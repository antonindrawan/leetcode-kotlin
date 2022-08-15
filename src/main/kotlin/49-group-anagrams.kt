class GroupAnagrams {
    fun groupAnagrams(strs: Array<String>): List<List<String>> {

        // sorted each value (a)
        // put (a) and (unsorted value) in a hash map if (a) does not exist
        // append (unsorted value) if (a) exists in the hash map

        // print all values of hash map

        var anagramGroup : HashMap<String, MutableList<String>> = hashMapOf()
        strs.forEach {
            var sortedVal = it.toCharArray().sorted().joinToString("")
            var anagramList: MutableList<String>? = anagramGroup[sortedVal]
            if (anagramList != null) {
                // append (it) to the list
                anagramList.add(it)
            } else {
                // put it on the hash map
                var anagram = mutableListOf(it)
                anagramGroup.put(sortedVal, anagram)
            }
        }

        return anagramGroup.values.toList()
    }
}

fun main() {
    val sol = GroupAnagrams()
    println(sol.groupAnagrams(arrayOf("eat","tea","tan","ate","nat","bat")))
}