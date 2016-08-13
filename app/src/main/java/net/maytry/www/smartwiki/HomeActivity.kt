package net.maytry.www.smartwiki

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ListView
import net.maytry.www.smartwiki.fragment.GenreListFragment
import net.maytry.www.smartwiki.model.Genre
import net.maytry.www.smartwiki.viewmodel.GenreAdapter
import java.io.Serializable

/**
 * Created by slont on 8/6/16.
 */
class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, GenreListFragment.OnFragmentInteractionListener {

    private val mGenreList: MutableList<Genre> = mutableListOf()
    private val genreList: List<Genre> = mGenreList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        val fab = findViewById(R.id.add_genre_fab) as FloatingActionButton
        fab.setOnClickListener(OnClickFloatingActionButton())

        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.setDrawerListener(toggle)
        toggle.syncState()

        val navigationView = findViewById(R.id.nav_view) as NavigationView
        navigationView.setNavigationItemSelectedListener(this)
        Log.d("onAct", "4")

        mGenreList.add(Genre("test2"))
        val fragment = GenreListFragment.newInstance(genreList)
        supportFragmentManager.beginTransaction().add(R.id.content_home, fragment).commit()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        Log.d("onAct11", genreList.hashCode().toString())

        outState.putSerializable("KEY", genreList as Serializable)
        super.onSaveInstanceState(outState)
    }

    override fun onBackPressed() {
        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        //noinspection SimplifiableIfStatement
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

    private inner class OnClickFavoriteButton : View.OnClickListener {
        override fun onClick(v: View) {
//            onChangeFavorite()
            Log.d("OnClickGenre", v.toString())
        }
    }

    override fun onChangeFavorite(data: Intent) {
        Log.d("onChangeFavorite cb", data.toString())
//        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            1001 -> {
                when (resultCode) {
                    RESULT_OK -> {
                        mGenreList.add(Genre(data.getStringExtra("genreName")))
                        val genreListView: ListView = findViewById(R.id.genre_list_view) as ListView
                        (genreListView.adapter as GenreAdapter).notifyDataSetChanged()
//                        (mGenreList.adapter as GenreAdapter).notifyDataSetChanged()
                    }
                    RESULT_CANCELED -> {}
                }
            }
            else -> {
            }
        }
    }

    private inner class OnClickFloatingActionButton : View.OnClickListener {
        override fun onClick(v: View) {
            val intent = Intent(this@HomeActivity, AddGenreActivity::class.java)
            startActivityForResult(intent, 1001)
        }
    }
}
