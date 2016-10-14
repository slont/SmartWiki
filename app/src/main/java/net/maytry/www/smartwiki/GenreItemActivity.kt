package net.maytry.www.smartwiki

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import net.maytry.www.smartwiki.databinding.ActivityGenreItemBinding
import net.maytry.www.smartwiki.db.GenreItemTableAdapter
import net.maytry.www.smartwiki.enums.EditType
import net.maytry.www.smartwiki.fragment.GenreItemFragment
import net.maytry.www.smartwiki.model.Genre
import net.maytry.www.smartwiki.model.GenreItem
import net.maytry.www.smartwiki.viewmodel.GenreItemAdapter

/**
 * Created by slont on 8/29/16.
 *
 * Genre画面のアクティビティ
 * メインコンテンツではGenreItemの管理を行う
 */
class GenreItemActivity : AppCompatActivity(), GenreItemFragment.OnFragmentInteractionListener {

    companion object {
        private val CREATE_ITEM_REQ_CODE = 100
        private val UPDATE_ITEM_REQ_CODE = 200
    }

    private lateinit var mGenre: Genre
    private val mItemList: MutableList<GenreItem> = mutableListOf()
    val itemList: List<GenreItem> = mItemList

    private val mItemTableAdapter: GenreItemTableAdapter

    private val mFragment: GenreItemFragment

    init {
        mItemTableAdapter = GenreItemTableAdapter(this)
        mFragment = GenreItemFragment.newInstance(itemList)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityGenreItemBinding>(this@GenreItemActivity, R.layout.activity_genre_item)

        mGenre = intent.getSerializableExtra("genre") as Genre
        title = mGenre.name
        mItemList.addAll(mGenre.itemList)

        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_menu_back)
        toolbar.setNavigationOnClickListener { onBackPressed() }

        binding.onClickAddGenreItemFab = OnClickAddGenreItemFab()

        supportFragmentManager.beginTransaction().add(R.id.content_genre_item, mFragment).commit()
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.genre_item, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.action_settings) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    /**
     * LayeredItemのセレクトイベント
     * LayeredContentが設定されていればさらに潜る
     * ItemContentが設定されていれば、ページを表示する
     */
    override fun onClickItem(parent: AdapterView<*>?, position: Int) {
        val intent = Intent(this@GenreItemActivity, GenreItemInfoActivity::class.java)
        intent.putExtra("item", parent!!.getItemAtPosition(position) as GenreItem)
        startActivity(intent)
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
                        val id = data.getLongExtra("_id", -1)
                        mItemTableAdapter.open()
                        val item = mItemTableAdapter.selectByID(id)
                        mItemTableAdapter.close()
                        if (null != item) {
                            mItemList.add(item)
                            mFragment.notifyDataSetChanged()
                        }
                    }
                    RESULT_CANCELED -> {}
                }
            }
            else -> {
            }
        }
    }

    override fun loadData() {
        mItemTableAdapter.open()
        val list = mItemTableAdapter.select("parent_id=${mGenre.id}")
        mItemTableAdapter.close()
        mItemList.clear()
        mItemList.addAll(list)
    }

    /**
     * AddGenreItemのクリックイベント
     */
    private inner class OnClickAddGenreItemFab : View.OnClickListener {
        override fun onClick(v: View) {
            val genre = mGenre
            val intent = Intent(this@GenreItemActivity, AddGenreItemActivity::class.java)
            intent.putExtra("item",  GenreItem(name = "", parentId= genre.id!!))
            intent.putExtra("type", EditType.CREATE)
            startActivityForResult(intent, CREATE_ITEM_REQ_CODE)
        }
    }
}
