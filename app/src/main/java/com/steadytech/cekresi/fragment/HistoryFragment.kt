package com.steadytech.cekresi.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.steadytech.cekresi.R
import com.steadytech.cekresi.adapter.AwbHistoryAdapter
import com.steadytech.cekresi.constant.Constant
import com.steadytech.cekresi.helper.FontsHelper
import com.steadytech.cekresi.model.realm.AWBLocal
import io.realm.Realm
import io.realm.RealmResults
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.fragment_check_awb_bottom_sheet.*

class HistoryFragment : Fragment(), View.OnClickListener, TextWatcher {

    lateinit var textTotalSize : TextView

    lateinit var inputSearch : EditText

    lateinit var imageSearch : ImageView
    lateinit var imageClose : ImageView

    lateinit var layoutSearch : LinearLayout

    lateinit var fabDelete : FloatingActionButton

    lateinit var recyclerHistory : RecyclerView

    lateinit var layoutManager: LinearLayoutManager

    lateinit var awbHistoryAdapter: AwbHistoryAdapter

    lateinit var realm : Realm

    lateinit var awbHistory : RealmResults<AWBLocal>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    companion object{
        fun newInstance(): HistoryFragment = HistoryFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.init(view)

        this.deleteSingleItem()
    }

    private fun deleteSingleItem() {
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val awb = this@HistoryFragment.awbHistory[viewHolder.adapterPosition]
                val awbLocal = this@HistoryFragment.realm.where<AWBLocal>().equalTo("awbNumber", awb!!.awbNumber).findFirst()
                Toast.makeText(this@HistoryFragment.activity!!, "Berhasil Menghapus Item!", Toast.LENGTH_SHORT).show()

                this@HistoryFragment.realm.executeTransaction{ i ->
                    awbLocal!!.isDelete = true
                    i.copyToRealmOrUpdate(awbLocal)
                }

                this@HistoryFragment.recyclerHistory.adapter!!.notifyDataSetChanged()
            }
        }

        val itemTouchHelper  = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(recyclerHistory)
    }

    @SuppressLint("SetTextI18n")
    private fun init(view: View) {
        this.realm = Realm.getDefaultInstance()

        this.imageSearch = view.findViewById(R.id.imageSearch)
        this.imageClose = view.findViewById(R.id.imageClose)

        this.layoutSearch = view.findViewById(R.id.layoutSearch)

        this.inputSearch = view.findViewById(R.id.inputSearch)
        this.inputSearch.typeface = FontsHelper.JOST.regular(this.activity!!)

        this.fabDelete = view.findViewById(R.id.fabDelete)

        this.textTotalSize = view.findViewById(R.id.textSizeList)
        this.textTotalSize.typeface = FontsHelper.JOST.medium(this.activity!!)

        this.recyclerHistory = view.findViewById(R.id.recyclerHistory)

        this.awbHistory = realm.where<AWBLocal>().equalTo("isDelete", false).findAll()

        this.recyclerHistory.layoutManager = LinearLayoutManager(this.activity!!, LinearLayoutManager.VERTICAL, false)
        this.recyclerHistory.adapter = AwbHistoryAdapter(awbHistory, this.activity!! as AppCompatActivity, true)

        this.textTotalSize.text = awbHistory.size.toString() + " Hasil "

        this.inputSearch.addTextChangedListener(this)

        this.fabDelete.setOnClickListener(this)
        this.imageSearch.setOnClickListener(this)
        this.imageClose.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (v == this.fabDelete){
            this.deleteAll()
        }else if (v == this.imageClose){
            this.closeSearch()
        }else if (v == this.imageSearch){
            this.openSearch()
        }
    }

    private fun openSearch() {
        this.layoutSearch.visibility = View.VISIBLE
        this.imageSearch.visibility = View.GONE
        this.inputSearch.requestFocus()
    }

    private fun closeSearch() {
        this.layoutSearch.visibility = View.GONE
        this.imageSearch.visibility = View.VISIBLE

        this.awbHistory = this.realm.where<AWBLocal>().equalTo("isDelete", false).findAll()
        this.recyclerHistory.adapter = AwbHistoryAdapter(this.awbHistory, this.activity!! as AppCompatActivity, false)
        this.textTotalSize.text = this.awbHistory.size.toString() + " Hasil"
    }

    @SuppressLint("SetTextI18n")
    private fun deleteAll() {
        for (awb in awbHistory){
            this.realm.executeTransaction{transaction ->
                val awbLocal = transaction.where<AWBLocal>().equalTo("awbNumber", awb.awbNumber).findFirst()
                awbLocal!!.isDelete = true
                transaction.copyToRealmOrUpdate(awbLocal)
                this.recyclerHistory.adapter!!.notifyDataSetChanged()

            }
        }

        Toast.makeText(this.activity!!, "Berhasil Menghapus Semua Data!", Toast.LENGTH_SHORT).show()

        this.textTotalSize.text = awbHistory.size.toString() + " Hasil"
    }

    override fun afterTextChanged(s: Editable?) {
        if (s!!.isNotEmpty()){
            this.awbHistory = this.realm.where<AWBLocal>().contains("awbNumber", s.toString()).equalTo("isDelete", false).findAll()
            this.recyclerHistory.adapter = AwbHistoryAdapter(this.awbHistory, this.activity!! as AppCompatActivity, false)
            this.textTotalSize.text = this.awbHistory.size.toString() + " Hasil"
        }else{
            this.awbHistory = this.realm.where<AWBLocal>().equalTo("isDelete", false).findAll()
            this.recyclerHistory.adapter = AwbHistoryAdapter(this.awbHistory, this.activity!! as AppCompatActivity, false)
            this.textTotalSize.text = this.awbHistory.size.toString() + " Hasil"
        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

    }
}