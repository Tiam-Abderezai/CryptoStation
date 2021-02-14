package com.example.cryptostation.fragments

// Used to communicate between fragments
interface Callback {
    fun openFragment(fragment: String)
}