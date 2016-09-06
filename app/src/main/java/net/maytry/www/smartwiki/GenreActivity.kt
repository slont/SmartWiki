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
import net.maytry.www.smartwiki.fragment.GenreContentFragment
import net.maytry.www.smartwiki.model.Genre
import net.maytry.www.smartwiki.model.GenreItem

/**
 * Created by slont on 8/29/16.
 *
 * Genre画面のアクティビティ
 * メインコンテンツではGenreItemの管理を行う
 */
class GenreActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, GenreContentFragment.OnFragmentInteractionListener {

    companion object {
        private val ADD_GENRE_REQ_CODE = 100
        private val LAYERED_REQ_CODE = 200
    }

    private var mItemList: MutableList<GenreItem> = mutableListOf()
    val itemList: List<GenreItem> = mItemList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityGenreBinding>(this@GenreActivity, R.layout.activity_genre)

        val genre = intent.getSerializableExtra("genre") as Genre
        title = genre.name
        mItemList.addAll(genre.itemMap.values)

        val toolbar = binding.appBarGenre.toolbar
        setSupportActionBar(toolbar)

        val drawer = binding.drawerLayout
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.setDrawerListener(toggle)
        toggle.syncState()

        binding.appBarGenre.onClickEditGenreItemFab = OnClickEditGenreItemFab()
        binding.navView.setNavigationItemSelectedListener(this)

        val fragment = GenreContentFragment.newInstance(itemList)
        supportFragmentManager.beginTransaction().add(R.id.content_genre, fragment).commit()
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
        menuInflater.inflate(R.menu.layered, menu)
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

    /**
     * LayeredItemのセレクトイベント
     * LayeredContentが設定されていればさらに潜る
     * ItemContentが設定されていれば、ページを表示する
     */
    override fun onClickGenreItemListItem(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val intent = Intent(this@GenreActivity, EditGenreItemActivity::class.java)
        intent.putExtra("item", parent!!.getItemAtPosition(position) as GenreItem)
        startActivityForResult(intent, GenreActivity.ADD_GENRE_REQ_CODE)
    }

    /**
     * EditGenreItemのクリックイベント
     */
    private inner class OnClickEditGenreItemFab : View.OnClickListener {
        override fun onClick(v: View) {
            val intent = Intent(this@GenreActivity, EditGenreItemActivity::class.java)
            intent.putExtra("item",  GenreItem(""))
            startActivityForResult(intent, ADD_GENRE_REQ_CODE)
        }
    }
}
