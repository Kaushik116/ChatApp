package com.kaushik.chatapp1

import com.kaushik.chatapp1.Constants.OPEN_GOOGLE
import com.kaushik.chatapp1.Constants.OPEN_SEARCH
import java.sql.Date
import java.sql.Timestamp
import java.text.SimpleDateFormat

object BotResponse {

    fun basicResponses(_message: String): String {

        val random = (0..2).random()
        val message =_message.toLowerCase()

        return when {

            //Flips a coin
            message.contains("flip") && message.contains("coin") -> {
                val r = (0..1).random()
                val result = if (r == 0) "heads" else "tails"

                "I flipped a coin and it landed on $result"
            }

            //Kaushik point of view
            message.contains("hello harshitha") -> {
                when (random) {
                    0 -> "hello kaushik"
                    1 -> "hello kaushik"
                    else -> "hello kaushik" }
            }
            message.contains("did you watch the world cup final?") -> {
                when (random) {
                    0 -> "yeah tough loss for our team"
                    1 -> "yeah. tough loss for our team"
                    else -> "yeah. tough loss for our team" }
            }
            message.contains("thought they had it in their bag") -> {
                when (random) {
                    0 -> "it happens. sports can be brutal"
                    1 -> "it happens. sports can be brutal"
                    else -> "it happens. sports can be brutal" }
            }
            message.contains("it's just frustrating. they were so close") -> {
                when (random) {
                    0 -> "that's the beauty of sports. unpredictable till the end"
                    1 -> "that's the beauty of sports. unpredictable till the end"
                    else -> "that's the beauty of sports. unpredictable till the end" }
            }
            message.contains("i know but it still stings") -> {
                when (random) {
                    0 -> "emotions run high. it will fade"
                    1 -> "emotions run high. it will fade"
                    else -> "emotions run high. it will fade" }
            }
            message.contains("i guess you're right we gave it our all") -> {
                when (random) {
                    0 -> "exactly, no regrets. until next season, bye"
                    1 -> "exactly, no regrets. until next season, bye"
                    else -> "exactly, no regrets. until next season, bye" }
            }
            //How are you?
            message.contains("how are you") -> {
                when (random) {
                    0 -> "I am doing fine"
                    1 -> "I am doing fine"
                    else -> "I am doing fine"
                }
            }
            message.contains("bye") -> {
                when (random) {
                    0 -> ":)"
                    1 -> ":)"
                    else -> ":)"
                }
            }


            //harshitha point of view
//            message.contains("hello kaushik") -> {
//                when (random) {
//                    0 -> "how are you"
//                    1 -> "how are you"
//                    else -> "how are you" }
//            }
//            message.contains("i am doing fine") -> {
//                when (random) {
//                    0 -> "did you watch the world cup final?"
//                    1 -> "did you watch the world cup final?"
//                    else -> "did you watch the world cup final?"
//                }
//            }
//            message.contains("yeah tough loss for our team") -> {
//                when (random) {
//                    0 -> "thought they had it in their bag"
//                    1 -> "thought they had it in their bag"
//                    else -> "thought they had it in their bag" }
//            }
//            message.contains("it happens. sports can be brutal") -> {
//                when (random) {
//                    0 -> "it's just frustrating. they were so close :(:("
//                    1 -> "it's just frustrating. they were so close :(:("
//                    else -> "it's just frustrating. they were so close :(:(" }
//            }
//            message.contains("that's the beauty of sports. unpredictable till the end") -> {
//                when (random) {
//                    0 -> "i know but it still stings"
//                    1 -> "i know but it still stings"
//                    else -> "i know but it still stings" }
//            }
//            message.contains("emotions run high. it will fade") -> {
//                when (random) {
//                    0 -> "i guess you're right we gave it our all"
//                    1 -> "i guess you're right we gave it our all"
//                    else -> "i guess you're right we gave it our all" }
//            }
//            message.contains("exactly, no regrets. until next season, bye") -> {
//                when (random) {
//                    0 -> "bye"
//                    1 -> "bye"
//                    else -> "bye" }
//            }
//            //How are you?
//            message.contains(":)") -> {
//                when (random) {
//                    0 -> ""
//                    1 -> ""
//                    else -> ""
//                }
//            }


            //What time is it?
            message.contains("time") && message.contains("?")-> {
                val timeStamp = Timestamp(System.currentTimeMillis())
                val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm")
                val date = sdf.format(Date(timeStamp.time))

                date.toString()
            }

            //Open Google
            message.contains("open") && message.contains("google")-> {
                OPEN_GOOGLE
            }

            //Search on the internet
            message.contains("search")-> {
                OPEN_SEARCH
            }

            //When the programme doesn't understand...
            else -> {
                when (random) {
                    0 -> "I don't understand..."
                    1 -> "Try asking me something different"
                    2 -> "Idk"
                    else -> "error"
                }
            }
        }
    }
}