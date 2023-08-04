package com.example.contacts

interface OnContactItemClickListener {
    fun onClick(contact : Contact, position : Int)
}