package net.maytry.www.smartwiki

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.AdapterView
import net.maytry.www.smartwiki.databinding.ActivityGenreItemBinding
import net.maytry.www.smartwiki.db.GenreItemInfoTableAdapter
import net.maytry.www.smartwiki.fragment.GenreItemContentFragment
import net.maytry.www.smartwiki.model.GenreItem
import net.maytry.www.smartwiki.model.GenreItemInfo

/**
 * Created by slont on 9/8/16.
 *
 * GenreItem画面のアクティビティ
 * メインコンテンツではGenreItemInfoの管理を行う
 */
class GenreItemActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, GenreItemContentFragment.OnFragmentInteractionListener {

    private lateinit var mItem: GenreItem
    lateinit var infoList: List<GenreItemInfo>

    private val infoTableAdapter: GenreItemInfoTableAdapter

    private lateinit var fragment: GenreItemContentFragment

    init {
        infoTableAdapter = GenreItemInfoTableAdapter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityGenreItemBinding>(this@GenreItemActivity, R.layout.activity_genre_item)

        mItem = intent.getSerializableExtra("item") as GenreItem
        title = mItem.name

        val toolbar = binding.appBar.toolbar
        setSupportActionBar(toolbar)

        val drawer = binding.drawerLayout
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.setDrawerListener(toggle)
        toggle.syncState()

        binding.navView.setNavigationItemSelectedListener(this)

        fragment = GenreItemContentFragment.newInstance(mItem.infoList)
        supportFragmentManager.beginTransaction().add(R.id.content_genre_item, fragment).commit()
    }

    override fun onClickInfoListItem(parent: AdapterView<*>?, position: Int) {
//        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
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
        menuInflater.inflate(R.menu.add_genre_item_info, menu)
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

    override fun loadData() {
        infoTableAdapter.open()
        mItem.infoList.addAll(infoTableAdapter.select("parent_id=${mItem.id}"))
        infoTableAdapter.close()
    }
}
