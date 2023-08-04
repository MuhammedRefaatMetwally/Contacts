package com.example.contacts

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.contacts.databinding.ActivityMainBinding
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var contactNameInput: TextInputEditText
    lateinit var contactNumberInput: TextInputEditText
    lateinit var contactDescriptionInput: TextInputEditText
    lateinit var addContact: Button
    lateinit var recylcerview: RecyclerView
    lateinit var contacts: MutableList<Contact>
    private lateinit var adapter: ContactRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        addContact()
    }

    private fun init() {
        contacts = mutableListOf()
        contactNameInput = binding.editTextName
        contactNumberInput = binding.editTextNumber
        contactDescriptionInput = binding.editTextDescription
        addContact = binding.addContactBtn
        recylcerview = binding.recyclerView
        adapter = ContactRecyclerViewAdapter(contacts)
        onContactItemClick()
        onCall()
        recylcerview.adapter = adapter
    }


    private fun addContact() {
        addContact.setOnClickListener {
            if (contactNameInput.text!!.isNotEmpty() && contactNumberInput.text!!.isNotEmpty()) {
                contacts.add(
                    Contact(
                        contactNameInput.text.toString(),
                        contactNumberInput.text.toString(),
                        R.drawable.contact_image,
                        contactDescriptionInput.text.toString()
                    )
                )
                adapter.notifyItemInserted(contacts.size - 1)
            } else {
                Toast.makeText(
                    this@MainActivity,
                    "Please Enter Name and Number!",
                    Toast.LENGTH_LONG
                ).show()
            }
            clearText()
        }

    }

    private fun onContactItemClick() {
        adapter.setOnContactItemClickListener(object : OnContactItemClickListener {
            override fun onClick(contact: Contact, position: Int) {
                val intent = Intent(this@MainActivity, ContactDetailsActivity::class.java)
                intent.putExtra(
                    Constant.CONTACT_DESCRIPTION,
                    "${contact.name} - Description:\n" + contact.description
                )
                startActivity(intent)
            }
        })
    }

    private fun onCall() {
        adapter.setOnCallIconClickListener(object : OnContactItemClickListener {
            override fun onClick(contact: Contact, position: Int) {
                val dialIntent = Intent(Intent.ACTION_DIAL)
                dialIntent.data = Uri.parse("tel:" + contact.number)
                try {
                    startActivity(dialIntent)
                } catch (e: ActivityNotFoundException) {
                    Toast.makeText(this@MainActivity, "Could not call", Toast.LENGTH_LONG).show()
                }

            }

        })
    }

    private fun clearText() {
        contactNameInput.text?.clear()
        contactNumberInput.text?.clear()
        contactDescriptionInput.text?.clear()
    }

}