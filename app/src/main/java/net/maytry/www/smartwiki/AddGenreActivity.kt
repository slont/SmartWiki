package net.maytry.www.smartwiki

import android.app.AlertDialog
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.EditText
import net.maytry.www.smartwiki.databinding.ActivityAddGenreBinding
import net.maytry.www.smartwiki.db.GenreTableAdapter
import net.maytry.www.smartwiki.fragment.AddGenreFragment
import net.maytry.www.smartwiki.model.Genre

/**
 * Created by slont on 8/6/16.
 */
class AddGenreActivity : AppCompatActivity(), AddGenreFragment.OnFragmentInteractionListener {

    private val mGenreTableAdapter: GenreTableAdapter

    init {
        mGenreTableAdapter = GenreTableAdapter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityAddGenreBinding>(this@AddGenreActivity, R.layout.activity_add_genre)

        setSupportActionBar(binding.toolbar)

        val fragment = AddGenreFragment.newInstance()
        supportFragmentManager.beginTransaction().add(R.id.content_add_genre, fragment).commit()
    }

    override fun onClickCancelBtn() {
        setResult(RESULT_CANCELED, Intent())
        finish()
    }

    override fun onClickCreateBtn() {
        val name = (findViewById(R.id.genre_name_edit) as EditText).text.toString()
        if (name.isNullOrBlank()) {
            // TODO:切り出し
            AlertDialog.Builder(this)
                    .setTitle("エラー")
                    .setMessage("タイトルが空です")
                    .setPositiveButton("OK") { dialog, which -> }
                    .create().show()
        } else {
            val id = addGenre(name)
            val intent = Intent()
            intent.putExtra("_id", id)
            setResult(RESULT_OK, intent)
            finish()
        }
    }

    private fun addGenre(name: String): Long {
        mGenreTableAdapter.open()
        val genre = Genre(name = name)
        val id = mGenreTableAdapter.insert(genre)
        mGenreTableAdapter.close()
        return id
    }
}
