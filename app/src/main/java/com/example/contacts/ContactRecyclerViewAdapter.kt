package com.example.contacts

import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.android.material.textfield.TextInputEditText

class ContactRecyclerViewAdapter(private val contacts : List<Contact>?) : Adapter<ContactRecyclerViewAdapter.ContactViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
          val view = LayoutInflater.from(parent.context).inflate(R.layout.contact_item,parent,false)
        return ContactViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contactItem = contacts!![position]
        holder.contactName.text =   contactItem.name
        holder.contactNumber.text = contactItem.number
        holder.contactImage.setImageResource(contactItem.image)
        holder.itemView.setOnClickListener { onContactItemClickListener?.onClick(contactItem,position) }
        holder.contactIcon.setOnClickListener { onCallIconClickListener?.onClick(contactItem,position) }
    }

    override fun getItemCount(): Int {
        return contacts?.size ?: 0
    }
    private var onContactItemClickListener :OnContactItemClickListener? = null
    private var onCallIconClickListener :OnContactItemClickListener? = null

     fun setOnContactItemClickListener(onContactItemClickListener: OnContactItemClickListener){
        this.onContactItemClickListener = onContactItemClickListener
    }

    fun setOnCallIconClickListener(OnCallIconClickListener: OnContactItemClickListener){
        this.onCallIconClickListener = OnCallIconClickListener
    }
    class ContactViewHolder(view : View) : ViewHolder(view){
         var contactName : TextView
         var contactNumber : TextView
         var contactImage : ImageView
         var contactIcon : ImageView

        init {
            contactName  = view.findViewById(R.id.contact_name)
            contactNumber  = view.findViewById(R.id.contact_number)
            contactImage = view.findViewById(R.id.contact_image)
            contactIcon = view.findViewById(R.id.contact_icon)
        }
    }

}