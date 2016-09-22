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
import net.maytry.www.smartwiki.databinding.ActivityHomeBinding
import net.maytry.www.smartwiki.db.GenreTableAdapter
import net.maytry.www.smartwiki.fragment.HomeContentFragment
import net.maytry.www.smartwiki.model.Genre
import java.io.Serializable

/**
 * Created by slont on 8/6/16.
 *
 * Home画面のアクティビティ
 * メインコンテンツではGenreリストの管理を行う
 */
class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, HomeContentFragment.OnFragmentInteractionListener {

    companion object {
        private val ADD_GENRE_REQ_CODE = 100
        private val LAYERED_REQ_CODE = 200
    }

    private val mGenreList: MutableList<Genre> = mutableListOf()
    val genreList: List<Genre> = mGenreList

    private val genreTableAdapter: GenreTableAdapter

    private val fragment: HomeContentFragment

    init {
        genreTableAdapter = GenreTableAdapter(this)
        fragment = HomeContentFragment.newInstance(genreList)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityHomeBinding>(this@HomeActivity, R.layout.activity_home)

        val toolbar = binding.appBarHome.toolbar
        setSupportActionBar(toolbar)

        binding.appBarHome.onClickAddGenreFab = OnClickAddGenreFab()

        val drawer = binding.drawerLayout
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.setDrawerListener(toggle)
        toggle.syncState()

        binding.navView.setNavigationItemSelectedListener(this)

        supportFragmentManager.beginTransaction().add(R.id.content_home, fragment).commit()
    }

    override fun onSaveInstanceState(outState: Bundle) {
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

    override fun onClickGenreListItem(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val intent = Intent(this@HomeActivity, GenreActivity::class.java)
        intent.putExtra("genre", parent!!.getItemAtPosition(position) as Genre)
        startActivity(intent)
        //        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            ADD_GENRE_REQ_CODE -> {
                when (resultCode) {
                    RESULT_OK -> {
                        val id = data.getLongExtra("_id", -1)
                        genreTableAdapter.open()
                        val genre = genreTableAdapter.selectByID(id)
                        genreTableAdapter.close()
                        if (null != genre) {
                            mGenreList.add(genre)
                            fragment.notifyDataSetChanged()
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
        genreTableAdapter.open()
        mGenreList.addAll(genreTableAdapter.selectAll())
        genreTableAdapter.close()
    }

    private inner class OnClickAddGenreFab : View.OnClickListener {
        override fun onClick(v: View) {
            val intent = Intent(this@HomeActivity, AddGenreActivity::class.java)
            startActivityForResult(intent, ADD_GENRE_REQ_CODE)
        }
    }
}
