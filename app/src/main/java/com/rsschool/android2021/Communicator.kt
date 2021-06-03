package com.rsschool.android2021

interface Communicator {
    fun sendDataFromSecondToFirstFragment(previousNumber: Int)
    fun sendDataFromFirstToSecondFragment(min: Int, max: Int)
}