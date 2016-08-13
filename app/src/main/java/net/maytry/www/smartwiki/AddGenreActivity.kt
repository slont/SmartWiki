package net.maytry.www.smartwiki

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText

/**
 * Created by slont on 8/6/16.
 */
class AddGenreActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_genre)

        val cancelButton = findViewById(R.id.cancel_button) as Button
        val createButton = findViewById(R.id.create_button) as Button
        cancelButton.setOnClickListener(OnClickCancelButton())
        createButton.setOnClickListener(OnClickCreateButton())
    }

    private inner class OnClickCancelButton : View.OnClickListener {
        override fun onClick(v: View) {
            setResult(RESULT_CANCELED, Intent())
            finish()
        }
    }

    private inner class OnClickCreateButton : View.OnClickListener {
        override fun onClick(v: View) {
            val genreName = findViewById(R.id.genre_name_edit) as EditText
            val intent = Intent()
            intent.putExtra("genreName", genreName.text.toString())
            setResult(RESULT_OK, intent)
            Log.d("tag", genreName.text.toString())
            finish()
        }
    }
}
