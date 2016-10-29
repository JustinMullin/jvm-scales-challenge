package com.nerdery.jvm

/**
 * The various scale/run types this submission supports.
 */
enum class ScaleType(val offsets: List<Int>) {
    MAJOR(listOf(0, 2, 4, 5, 7, 9, 11, 12, 11, 9, 7, 5, 4, 2, 0)),
    MINOR(listOf(0, 2, 3, 5, 7, 8, 10, 12, 10, 8, 7, 5, 3, 2, 0)),
    BLUES(listOf(0, 3, 5, 6, 7, 10, 12, 10, 7, 6, 5, 3)),
    ARPEGGIO(listOf(0, 4, 7, 4, 7, 12, 7, 12, 16, 12, 16, 19, 19, 16, 12, 16, 12, 7, 12, 7, 4, 7, 4, 0)),
    ALBERTI(listOf(0, 7, 4, 7, 0, 7, 4, 7, 0, 9, 5, 9, -1, 7, 2, 7, 0))
}