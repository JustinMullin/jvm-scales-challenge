package com.nerdery.jvm

import javax.sound.midi.MidiSystem
import javax.sound.midi.Sequence

/**
 * @author Josh Klun (jklun@nerdery.com)
 * @author Justin Mullin (jmullin@nerdery.com)
 */
class ScalesChallenge {
    fun buildScale(note: Note, scaleType: ScaleType): List<RelativeNote> {
        return scaleType.offsets.map { offset ->
            val noteValue = note.cDistance + offset
            RelativeNote(valueToNote[wrapValue(noteValue)] ?: Note.C, valueOctave(noteValue))
        }
    }

    /**
     * Wraps a global note value to a relative note value
     */
    fun wrapValue(value: Int) = (value + 3) % 12 - 3

    /**
     * Calculates the octave for a global note value
     */
    fun valueOctave(value: Int) = (value + 3) / 12

    fun convertToMidi(notes: List<RelativeNote>): Sequence = NotesMidiGenerator(notes).generateSong()

    fun playMidi(sequence: Sequence): Unit {
        val sequencer = MidiSystem.getSequencer()
        sequencer.sequence = sequence
        sequencer.open()
        Thread.sleep(300L)
        sequencer.start()
        while(sequencer.isRunning) {
            Thread.sleep(100)
        }
        Thread.sleep(500)
        sequencer.stop()
        sequencer.close()
    }

    companion object {
        /**
         * Lookup table for relative note values to notes.
         */
        val valueToNote = mapOf(
            Pair(0, Note.C), Pair(1, Note.C_SHARP),
            Pair(2, Note.D), Pair(3, Note.D_SHARP),
            Pair(4, Note.E),
            Pair(5, Note.F), Pair(6, Note.F_SHARP),
            Pair(7, Note.G), Pair(8, Note.G_SHARP),
            Pair(-3, Note.A), Pair(-2, Note.A_SHARP),
            Pair(-1, Note.B)
        )
    }
}

val usageMessage = "Enter a key signature (for example: C_FLAT, C, C_SHARP) and a scale/run type (one of: MAJOR, MINOR, BLUES, ARPEGGIO, ALBERTI)"

fun main(args: Array<String>) = println(when (args.size) {
    0, 1 -> usageMessage
    else -> try {
        val challenge = ScalesChallenge()
        val scale = challenge.buildScale(Note.valueOf(args.first()), ScaleType.valueOf(args[1]))
        val sequence = challenge.convertToMidi(scale)
        challenge.playMidi(sequence)
        scale.joinToString()
    } catch (e: IllegalArgumentException) {
        usageMessage
    }
})
