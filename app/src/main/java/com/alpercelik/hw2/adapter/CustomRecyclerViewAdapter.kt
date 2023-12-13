package com.alpercelik.hw2.adapter

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.alpercelik.hw2.R
import com.alpercelik.hw2.databinding.DialogBinding
import com.alpercelik.hw2.db.Music


class CustomRecyclerViewAdapter( private val context: Context, private val recyclerItemValues: MutableList<Music>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface RecyclerAdapterInterface{
        fun displayItem(sc: Music)
    }
    lateinit var recyclerAdapterInterface: RecyclerAdapterInterface

    init {
        recyclerAdapterInterface = context as RecyclerAdapterInterface
    }

    companion object {
        const val TYPE_PREMIUM_ITEM = 100
        const val TYPE_NORMAL_ITEM = 200
    }

    override fun getItemViewType(position: Int): Int {
        return if (position % 2 == 0) {
            TYPE_PREMIUM_ITEM
        } else {
            TYPE_NORMAL_ITEM
        }
    }
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView: View
        val inflator = LayoutInflater.from(viewGroup.context)

        return if (viewType == TYPE_PREMIUM_ITEM) {

            itemView = inflator.inflate(R.layout.recycler_premium_item, viewGroup, false)
            PremiumCustomRecyclerViewItemHolder(itemView)
        } else {

            itemView = inflator.inflate(R.layout.recycler_normal_item, viewGroup, false)
            NormalCustomRecyclerViewItemHolder(itemView)
        }
    }

    private fun showCustomDialog(music: Music) {
        val dialog = Dialog(context)
        val bindingDialog: DialogBinding = DialogBinding.inflate(LayoutInflater.from(context))
        val view = bindingDialog.root
        dialog.setContentView(view)

        bindingDialog.tvAlbum.text = music.album.toString()
        bindingDialog.tvDuration.text = music.duration.toString()
        bindingDialog.imgg.setImageResource(music.imgID)
        bindingDialog.btnClose.setOnClickListener { dialog.dismiss() }

        dialog.show()
    }

    private fun handlePremiumCheckboxChecked(isChecked: Boolean, music: Music) {
        if (isChecked) {
            // Handle the premium checkbox checked state
            Toast.makeText(context, "Premium music added to favorites", Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleNormalCheckboxChecked(isChecked: Boolean, music: Music) {
        if (isChecked) {
            // Handle the normal checkbox checked state
            Toast.makeText(context, "Normal music added to favorites", Toast.LENGTH_SHORT).show()
        }
    }
    override fun onBindViewHolder(myRecyclerViewItemHolder: RecyclerView.ViewHolder, position: Int) {
        val currentItem = recyclerItemValues[position]

        if (getItemViewType(position) == TYPE_PREMIUM_ITEM) {

            val itemView = myRecyclerViewItemHolder as PremiumCustomRecyclerViewItemHolder
            itemView.textPremiumName.text = currentItem.name
            //itemView.textPremiumAlbum.text = currentItem.album
            //itemView.textPremiumDuration.text = currentItem.duration
            itemView.imgPremium.setImageResource(currentItem.imgID)

            itemView.itemPremiumConstraintLayout.setOnClickListener {
                recyclerAdapterInterface.displayItem(currentItem)
                showCustomDialog(currentItem)
            }

            itemView.checkBoxFavorite.setOnCheckedChangeListener { _, isChecked ->
                handlePremiumCheckboxChecked(isChecked, currentItem)
            }
        } else if (getItemViewType(position) == TYPE_NORMAL_ITEM) {
            val itemView = myRecyclerViewItemHolder as NormalCustomRecyclerViewItemHolder
            itemView.textNormalName.text = currentItem.name
            //itemView.textNormalAlbum.text = currentItem.album
            //itemView.textNormalDuration.text = currentItem.duration
            itemView.imgNormal.setImageResource(currentItem.imgID)

            itemView.itemNormalConstraintLayout.setOnClickListener {
                recyclerAdapterInterface.displayItem(currentItem)
                showCustomDialog(currentItem)
            }

            itemView.checkBoxNormal.setOnCheckedChangeListener { _, isChecked ->
                handleNormalCheckboxChecked(isChecked, currentItem)
            }
        }

    }

    override fun getItemCount(): Int {
        return recyclerItemValues.size
    }


    internal inner class PremiumCustomRecyclerViewItemHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var textPremiumName: TextView
        //var textPremiumAlbum: TextView
        //var textPremiumDuration: TextView
        var checkBoxFavorite : CheckBox
        var imgPremium: ImageView
        var itemPremiumConstraintLayout: ConstraintLayout

        init {
            textPremiumName = itemView.findViewById<TextView>(R.id.textPremiumName)
            //textPremiumAlbum = itemView.findViewById<TextView>(R.id.textPremiumAlbum)
            //textPremiumDuration = itemView.findViewById<TextView>(R.id.textPremiumDuration)
            checkBoxFavorite = itemView.findViewById(R.id.checkBoxFavorite)
            imgPremium = itemView.findViewById<ImageView>(R.id.imgPremium)
            itemPremiumConstraintLayout = itemView.findViewById<ConstraintLayout>(R.id.itemPremiumConstraintLayout)
        }
    }


    internal inner class NormalCustomRecyclerViewItemHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var textNormalName: TextView
        //var textNormalAlbum: TextView
        //var textNormalDuration: TextView
        var checkBoxNormal : CheckBox
        var imgNormal: ImageView
        var itemNormalConstraintLayout: ConstraintLayout

        init {
            textNormalName = itemView.findViewById<TextView>(R.id.textNormalName)
            //textNormalAlbum = itemView.findViewById<TextView>(R.id.textNormalAlbum)
            //textNormalDuration = itemView.findViewById<TextView>(R.id.textNormalDuration)
            checkBoxNormal = itemView.findViewById(R.id.checkBoxNormal)
            imgNormal = itemView.findViewById<ImageView>(R.id.imgNormal)
            itemNormalConstraintLayout = itemView.findViewById<ConstraintLayout>(R.id.itemNormalConstraintLayout)
        }
    }
}

