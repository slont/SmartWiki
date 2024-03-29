package net.maytry.www.smartwiki

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import net.maytry.www.smartwiki.databinding.ActivityGenreBinding
import net.maytry.www.smartwiki.db.GenreTableAdapter
import net.maytry.www.smartwiki.fragment.GenreFragment
import net.maytry.www.smartwiki.model.Genre
import java.io.Serializable

/**
 * Created by slont on 8/6/16.
 *
 * Home画面のアクティビティ
 * メインコンテンツではGenreリストの管理を行う
 */
class GenreActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, GenreFragment.OnFragmentInteractionListener {

    companion object {
        private const val ADD_GENRE_REQ_CODE = 100
        private const val LAYERED_REQ_CODE = 200
        private const val LAYOUT_RES = R.layout.activity_genre
    }

    private val mGenreList = mutableListOf<Genre>()
    val genreList: List<Genre> = mGenreList

    private val mGenreTableAdapter = GenreTableAdapter(this)

    private val mFragment = GenreFragment.newInstance(genreList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivityGenreBinding>(this, LAYOUT_RES).run {
            val toolbar = appBarGenre.toolbar
            setSupportActionBar(toolbar)

            appBarGenre.onClickAddGenreFab = OnClickAddGenreFab()
            val drawer = drawerLayout
            val toggle = ActionBarDrawerToggle(
                    this@GenreActivity, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
            drawer.setDrawerListener(toggle)
            toggle.syncState()

            navView.setNavigationItemSelectedListener(this@GenreActivity)
        }
        supportFragmentManager.beginTransaction().add(R.id.content_genre, mFragment).commit()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putSerializable("KEY", genreList as Serializable)
        super.onSaveInstanceState(outState)
    }

    override fun onBackPressed() {
        (findViewById(R.id.drawer_layout) as DrawerLayout).run {
            if (isDrawerOpen(GravityCompat.START)) {
                closeDrawer(GravityCompat.START)
            } else {
                super.onBackPressed()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.genre, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.action_settings) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    @SuppressWarnings("StatementWithEmptyBody")
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val id = item.itemId

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onClickGenre(parent: AdapterView<*>?, position: Int) {
        val intent = Intent(this, GenreItemActivity::class.java).apply {
            putExtra("genre", parent!!.getItemAtPosition(position) as Genre)
        }
        startActivity(intent)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            ADD_GENRE_REQ_CODE -> {
                when (resultCode) {
                    RESULT_OK -> {
                        val id = data.getLongExtra("_id", -1)
                        mGenreTableAdapter.open()
                        val genre = mGenreTableAdapter.findOne(id)
                        mGenreTableAdapter.close()
                        if (null != genre) {
                            mGenreList.add(genre)
                            mFragment.notifyDataSetChanged()
                        }
                    }
                    RESULT_CANCELED -> {}
                }
            }
            LAYERED_REQ_CODE -> {
                when (resultCode) {
                    RESULT_OK -> {

                    }
                    RESULT_CANCELED -> {}
                }
            }
            else -> {
            }
        }
    }

    override fun loadData() {
        mGenreTableAdapter.run {
            open()
            val list = findAll()
            close()
            mGenreList.clear()
            mGenreList.addAll(list)
        }
    }

    private inner class OnClickAddGenreFab : View.OnClickListener {
        override fun onClick(v: View) {
            val intent = Intent(this@GenreActivity, AddGenreActivity::class.java)
            startActivityForResult(intent, ADD_GENRE_REQ_CODE)
        }
    }
}
