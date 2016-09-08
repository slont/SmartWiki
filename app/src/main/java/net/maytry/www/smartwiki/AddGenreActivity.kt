package net.maytry.www.smartwiki

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.EditText
import net.maytry.www.smartwiki.databinding.ActivityAddGenreBinding
import net.maytry.www.smartwiki.fragment.AddGenreContentFragment

/**
 * Created by slont on 8/6/16.
 */
class AddGenreActivity : AppCompatActivity(), AddGenreContentFragment.OnFragmentInteractionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityAddGenreBinding>(this@AddGenreActivity, R.layout.activity_add_genre)

        setSupportActionBar(binding.toolbar)

        val fragment = AddGenreContentFragment.newInstance()
        supportFragmentManager.beginTransaction().add(R.id.content_add_genre, fragment).commit()
    }

    override fun onClickCancelButton(v: View) {
        setResult(RESULT_CANCELED, Intent())
        finish()
    }

    override fun onClickCreateButton(v: View) {
        val genreName = findViewById(R.id.genre_name_edit) as EditText
        val intent = Intent()
        intent.putExtra("genreName", genreName.text.toString())
        setResult(RESULT_OK, intent)
        finish()
    }
}
