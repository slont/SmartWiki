package net.maytry.www.smartwiki

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import net.maytry.www.smartwiki.fragment.EditGenreItemContentFragment
import net.maytry.www.smartwiki.fragment.GenreContentFragment
import net.maytry.www.smartwiki.model.Genre
import net.maytry.www.smartwiki.model.GenreItem

/**
 * Created by slont on 8/15/16.
 */
class EditGenreItemActivity : AppCompatActivity(), EditGenreItemContentFragment.OnFragmentInteractionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_genre_item)

        val item = intent.getSerializableExtra("item") as GenreItem

        val fragment = EditGenreItemContentFragment.newInstance(item)
        supportFragmentManager.beginTransaction().add(R.id.content_edit_genre_item, fragment).commit()
    }

    override fun onClickGenreItemListItem(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
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
        Log.d("tag", genreName.text.toString())
        finish()
    }
}
