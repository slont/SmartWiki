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
import android.widget.ListView
import net.maytry.www.smartwiki.databinding.ActivityGenreBinding
import net.maytry.www.smartwiki.enums.EditType
import net.maytry.www.smartwiki.fragment.GenreContentFragment
import net.maytry.www.smartwiki.model.Genre
import net.maytry.www.smartwiki.model.GenreItem
import net.maytry.www.smartwiki.viewmodel.GenreItemAdapter

/**
 * Created by slont on 8/29/16.
 *
 * Genre画面のアクティビティ
 * メインコンテンツではGenreItemの管理を行う
 */
class GenreActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, GenreContentFragment.OnFragmentInteractionListener {

    companion object {
        private val CREATE_ITEM_REQ_CODE = 100
        private val UPDATE_ITEM_REQ_CODE = 200
    }

    private var genre: Genre = Genre(name = "")
    private var mItemList: MutableList<GenreItem> = mutableListOf()
    val itemList: List<GenreItem> = mItemList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityGenreBinding>(this@GenreActivity, R.layout.activity_genre)

        genre = intent.getSerializableExtra("genre") as Genre
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
        intent.putExtra("type", EditType.UPDATE)
        startActivityForResult(intent, UPDATE_ITEM_REQ_CODE)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            UPDATE_ITEM_REQ_CODE -> {
                when (resultCode) {
                    RESULT_OK -> {
                        val itemListView = findViewById(R.id.item_list_view) as ListView
                        (itemListView.adapter as GenreItemAdapter).notifyDataSetChanged()
                    }
                    RESULT_CANCELED -> {}
                }
            }
            CREATE_ITEM_REQ_CODE -> {
                when (resultCode) {
                    RESULT_OK -> {
                        mItemList.add(data.getSerializableExtra("item") as GenreItem)
                        val itemListView = findViewById(R.id.item_list_view) as ListView
                        (itemListView.adapter as GenreItemAdapter).notifyDataSetChanged()
                    }
                    RESULT_CANCELED -> {}
                }
            }
            else -> {
            }
        }
    }

    /**
     * EditGenreItemのクリックイベント
     */
    private inner class OnClickEditGenreItemFab : View.OnClickListener {
        override fun onClick(v: View) {
            val intent = Intent(this@GenreActivity, EditGenreItemActivity::class.java)
            intent.putExtra("item",  GenreItem(name = "", parentID = genre.id ?: -1))
            intent.putExtra("type", EditType.CREATE)
            startActivityForResult(intent, CREATE_ITEM_REQ_CODE)
        }
    }
}
