package net.maytry.www.smartwiki

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import net.maytry.www.smartwiki.databinding.ActivityGenreItemBinding
import net.maytry.www.smartwiki.db.GenreItemInfoTableAdapter
import net.maytry.www.smartwiki.enums.GenreItemInfoType
import net.maytry.www.smartwiki.fragment.EditGenreItemInfoDialogFragment
import net.maytry.www.smartwiki.fragment.GenreItemContentFragment
import net.maytry.www.smartwiki.layout.AnimatingRelativeLayout
import net.maytry.www.smartwiki.model.GenreItem
import net.maytry.www.smartwiki.model.GenreItemInfo

/**
 * Created by slont on 9/8/16.
 *
 * GenreItem画面のアクティビティ
 * メインコンテンツではGenreItemInfoの管理を行う
 */
class GenreItemActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, GenreItemContentFragment.OnFragmentInteractionListener, EditGenreItemInfoDialogFragment.OnFragmentInteractionListener {

    private lateinit var mItem: GenreItem
    lateinit var infoList: List<GenreItemInfo>

    private val infoTableAdapter: GenreItemInfoTableAdapter

    private lateinit var fragment: GenreItemContentFragment
    private lateinit var dialog: EditGenreItemInfoDialogFragment

    init {
        infoTableAdapter = GenreItemInfoTableAdapter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityGenreItemBinding>(this@GenreItemActivity, R.layout.activity_genre_item)

        mItem = intent.getSerializableExtra("item") as GenreItem
        title = mItem.name

        val toolbar = binding.appBarGenreItem.toolbar
        setSupportActionBar(toolbar)

        val drawer = binding.drawerLayout
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.setDrawerListener(toggle)
        toggle.syncState()

        binding.navView.setNavigationItemSelectedListener(this)
        val animatingLayout: AnimatingRelativeLayout = binding.appBarGenreItem.contentGenreItem.menuAddGenreItemInfo.menuButtonLayout
        binding.appBarGenreItem.contentGenreItem.menuAddGenreItemInfo.showInfoMenuButton.setOnClickListener { animatingLayout.show() }
        binding.appBarGenreItem.contentGenreItem.menuAddGenreItemInfo.hideInfoMenuButton.setOnClickListener { animatingLayout.hide() }
        binding.appBarGenreItem.contentGenreItem.menuAddGenreItemInfo.onClickInfoMenuButton = OnClickInfoMenuButton()

        fragment = GenreItemContentFragment.newInstance(mItem.infoList)
        supportFragmentManager.beginTransaction().add(R.id.content_genre_item, fragment).commit()
    }

    /**
     * On click info item interface from @link{GenreItemContentFragment}
     */
    override fun onClickInfoListItem(parent: AdapterView<*>?, position: Int) {
        val info = parent?.getItemAtPosition(position) as? GenreItemInfo
        dialog = EditGenreItemInfoDialogFragment.newInstance(info!!)
        dialog.show(fragmentManager, "dialog")
    }

    override fun onClickAddContentButton(position: Int) {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onClickUpdateInfoButton(info: GenreItemInfo) {
        if (GenreItemInfoType.TAG == info.type) {
            dialog.notifyDataSetChanged()
        }
        infoTableAdapter.open()
        val id = infoTableAdapter.update(info)
        if (-1 != id) {
            val list = infoTableAdapter.select("parent_id=${mItem.id}")
            mItem.infoList.clear()
            mItem.infoList.addAll(list)
            fragment.notifyDataSetChanged()
        } else {
            Log.d(this.toString(), "failed update")
        }
        infoTableAdapter.close()
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

    private inner class OnClickInfoMenuButton() : View.OnClickListener {
        override fun onClick(v: View) {
            val type = GenreItemInfoType.strToEnum((v as Button).text.toString())
            if (null != type) {
                mItem.infoList.add(GenreItemInfo(name = type.name, type = type))
                fragment.notifyDataSetChanged()
            }
        }
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
     * Load info data interface from @link{GenreItemContentFragment}
     */
    override fun loadData() {
        infoTableAdapter.open()
        val list = infoTableAdapter.select("parent_id=${mItem.id}")
        infoTableAdapter.close()
        mItem.infoList.clear()
        mItem.infoList.addAll(list)
    }
}
