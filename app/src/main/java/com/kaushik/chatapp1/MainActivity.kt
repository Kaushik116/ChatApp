package com.kaushik.chatapp1

import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.kaushik.chatapp1.R
import com.kaushik.chatapp1.Message
import com.kaushik.chatapp1.Constants.OPEN_GOOGLE
import com.kaushik.chatapp1.Constants.OPEN_SEARCH
import com.kaushik.chatapp1.Time
import kotlinx.coroutines.*
import androidx.recyclerview.widget.RecyclerView
import com.kaushik.chatapp1.Constants.RECEIVE_ID
import com.kaushik.chatapp1.Constants.SEND_ID
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var etMessage: EditText
    private lateinit var rvMessage: RecyclerView
    private lateinit var btnSend: Button
    private val TAG = "MainActivity"
    private val RQ_SPEECH_REC = 102
    private lateinit var mmic: ImageView
    private lateinit var image: ImageView


    var messagesList = mutableListOf<Message>()

    private lateinit var adapter: MessagingAdapter
    private val botList = listOf("harshitha","harshitha","harshitha","harshitha")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etMessage = findViewById(R.id.et_message)
        rvMessage = findViewById(R.id.rv_messages)
        btnSend = findViewById(R.id.btn_send)
        mmic = findViewById(R.id.mic)
        image = findViewById(R.id.backimage)

        mmic.setOnClickListener {
            askspeechInput()
        }

        recyclerView()

        clickEvents()

//        val random = (0..3).random()
//        customBotMessage("hello ${botList[random]}")
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if(requestCode == RQ_SPEECH_REC && resultCode == Activity.RESULT_OK){
//            val result = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
//            etMessage.setText(result?.get(0).toString())
//        }
//    }

    private fun askspeechInput(){
        if(!SpeechRecognizer.isRecognitionAvailable(this)){
            Toast.makeText(this,"Speech recognition is not available", Toast.LENGTH_SHORT).show()
        }else{
            val i = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            i.putExtra(RecognizerIntent.EXTRA_PROMPT,"Say Something!")
            startActivityForResult(i,RQ_SPEECH_REC)
        }
    }


    private fun clickEvents() {

        //Send a message
        btnSend.setOnClickListener {
            sendMessage()
        }

        //Scroll back to correct position when user clicks on text view
        etMessage.setOnClickListener {
            GlobalScope.launch {
                delay(100)

                withContext(Dispatchers.Main) {
                    rvMessage.scrollToPosition(adapter.itemCount - 1)

                }
            }
        }
    }

    private fun recyclerView() {
        adapter = MessagingAdapter()
        rvMessage.adapter = adapter
        rvMessage.layoutManager = LinearLayoutManager(applicationContext)

    }

    override fun onStart() {
        super.onStart()

        GlobalScope.launch {
            delay(100)
            withContext(Dispatchers.Main) {
                rvMessage.scrollToPosition(adapter.itemCount - 1)
            }
        }
    }

    private fun sendMessage() {
        val message = etMessage.text.toString()
        val timeStamp = Time.timeStamp()

        if (message.isNotEmpty()) {
            //Adds it to our local list
            messagesList.add(Message(message, SEND_ID, timeStamp))
            etMessage.setText("")

            adapter.insertMessage(Message(message, SEND_ID, timeStamp))
            rvMessage.scrollToPosition(adapter.itemCount - 1)

            botResponse(message)
        }
    }

    private fun botResponse(message: String) {
        val timeStamp = Time.timeStamp()

        GlobalScope.launch {
            //Fake response delay
            delay(4000)

            withContext(Dispatchers.Main) {
                //Gets the response
                val response = BotResponse.basicResponses(message)

                //Adds it to our local list
                messagesList.add(Message(response, RECEIVE_ID, timeStamp))

                //Inserts our message into the adapter
                adapter.insertMessage(Message(response, RECEIVE_ID, timeStamp))

                //Scrolls us to the position of the latest message
                rvMessage.scrollToPosition(adapter.itemCount - 1)

                //Starts Google
                when (response) {
                    OPEN_GOOGLE -> {
                        val site = Intent(Intent.ACTION_VIEW)
                        site.data = Uri.parse("https://www.google.com/")
                        startActivity(site)
                    }
                    OPEN_SEARCH -> {
                        val site = Intent(Intent.ACTION_VIEW)
                        val searchTerm: String? = message.substringAfterLast("search")
                        site.data = Uri.parse("https://www.google.com/search?&q=$searchTerm")
                        startActivity(site)
                    }

                }
            }
        }
    }

    private fun customBotMessage(message: String) {

        GlobalScope.launch {
            delay(1000)
            withContext(Dispatchers.Main) {
                val timeStamp = Time.timeStamp()
                messagesList.add(Message(message, RECEIVE_ID, timeStamp))
                adapter.insertMessage(Message(message, RECEIVE_ID, timeStamp))

                rvMessage.scrollToPosition(adapter.itemCount - 1)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.wallpaper -> {
                val i = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(i, 1)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null) {
//            val selectedImage: Uri? = data.data
//            val filePath = arrayOf(MediaStore.Images.Media.DATA)
//
//            val cursor: Cursor? = contentResolver.query(selectedImage!!, filePath, null, null, null)
//            cursor?.use {
//                it.moveToFirst()
//                val columnIndex: Int = it.getColumnIndex(filePath[0])
//                val picturePath: String = it.getString(columnIndex)
//                it.close()
//
//                image.setImageBitmap(BitmapFactory.decodeFile(picturePath))
//            }
//        }
//    }
override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)

    when {
        requestCode == 1 && resultCode == Activity.RESULT_OK && data != null -> {
            // Handling image selection
            val selectedImage: Uri? = data.data
            val filePath = arrayOf(MediaStore.Images.Media.DATA)

            val cursor: Cursor? = contentResolver.query(selectedImage!!, filePath, null, null, null)
            cursor?.use {
                it.moveToFirst()
                val columnIndex: Int = it.getColumnIndex(filePath[0])
                val picturePath: String = it.getString(columnIndex)
                it.close()

                image.setImageBitmap(BitmapFactory.decodeFile(picturePath))
            }
        }
        requestCode == RQ_SPEECH_REC && resultCode == Activity.RESULT_OK -> {
            // Handling speech recognition result
            val result = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            etMessage.setText(result?.get(0).toString())
        }
    }
}

}