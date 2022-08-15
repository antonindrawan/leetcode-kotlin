import java.util.PriorityQueue

class Sol332 {
    data class Destination(val city: String, var visited: Boolean)
    private var _itineraryDictionary = HashMap<String, MutableList<Destination>>()
    private var _expectedItinerarySize: Int = 0
    fun findItinerary(tickets: List<List<String>>): List<String> {
        /*
        JFK -> SFO
            -> ATL
        SFO -> ATL
        ATL -> JFK
            -> SFO

        JFK -> SFO -> ATL -> JFK -> ATL -> SFO
        JFK -> ATL -> JFK -> SFO -> ATL -> SFO
         */
        _expectedItinerarySize = tickets.size + 1
        _itineraryDictionary = buildTicketDictionary(tickets)
        _itineraryDictionary.forEach {

            it.value.sortBy { it.city }
            println(it)
        }


        var itineraryList: MutableList<String> = mutableListOf()
        buildItinerary("JFK", _itineraryDictionary, itineraryList)

        return itineraryList
    }

    fun buildItinerary(from: String,
                       itineraryDictionary: HashMap<String, MutableList<Destination>>,
                       itineraryList: MutableList<String>) : Boolean {
        // println("Visiting $from")
        itineraryList.add(from)

        var result = false
        var destinationList = itineraryDictionary[from]
        var hasVisitedOnce = false

        destinationList?.forEach {
            // mark visited
            if (!it.visited) {
                it.visited = true
                hasVisitedOnce = true
                if (buildItinerary(it.city, itineraryDictionary, itineraryList)) {
                    result = true
                    return@forEach
                }
                it.visited = false
                itineraryList.removeAt(itineraryList.size - 1) // leetcode doesn't support removeLast() yet
            }
        }

        if (!hasVisitedOnce) {
            if (itineraryList.size == _expectedItinerarySize) {
                result = true
            }
        }

        return result
    }

    fun buildTicketDictionary(tickets: List<List<String>>) : HashMap<String, MutableList<Destination>> {

        val compareByName: Comparator<Destination> = compareBy { it.city }
        var itineraryDictionary = hashMapOf<String, MutableList<Destination>>()

        tickets.forEach {
            val from = it[0]
            val to = it[1]
            // rintln("from: $from, to: $to")

            var destinationList = itineraryDictionary.get(from)
            if (destinationList == null) {
                var newDestinationList = mutableListOf(Destination(to ,false))
                itineraryDictionary.put(from, newDestinationList)
            } else {
                destinationList.add(Destination(to, false))
            }
        }

        return itineraryDictionary
    }
}

fun main() {
    val input2 = listOf(
        listOf("JFK","SFO"),
        listOf("JFK","ATL"),
        listOf("SFO","ATL"),
        listOf("ATL","JFK"),
        listOf("ATL","SFO")
    )
    val input = listOf(
        listOf("JFK","KUL"),
        listOf("JFK","NRT"),
        listOf("NRT","JFK")
    )
    val input3 = listOf(
        listOf("JFK","AAA"),listOf("AAA","JFK"), listOf("JFK","BBB"), listOf("JFK","CCC"), listOf("CCC","JFK")
    )
    val input4 = listOf(
        listOf("EZE","TIA"),listOf("EZE","AXA"),listOf("AUA","EZE"),listOf("EZE","JFK"),listOf("JFK","ANU"),listOf("JFK","ANU"),listOf("AXA","TIA"),listOf("JFK","AUA"),listOf("TIA","JFK"),listOf("ANU","EZE"),listOf("ANU","EZE"),listOf("TIA","AUA")
    )
    //
    // Expected:  listOf("JFK","ANU","EZE","AXA","TIA","AUA","EZE","JFK","ANU","EZE","TIA","JFK","AUA"]
    println(Sol332().findItinerary(input4))
}