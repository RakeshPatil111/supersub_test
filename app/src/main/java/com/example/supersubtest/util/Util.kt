package com.example.supersubtest.util

import android.view.View

object Util {

    /**
     * This method is used for getting video id from Url
     * Pass youtube video link as argument to method
     * */
    fun getVideoIdFromUrl(videoUrl : String) = videoUrl.split('=', limit = 2)[1]

    /**
     * This method returns thumbnail url of video
     * */

    fun getThumbnailForVideo(videoUrl : String?) = "https://img.youtube.com/vi/"+ getVideoIdFromUrl(videoUrl!!)+"/0.jpg"

    /**
     * This method is used for hiding view
     * */
    fun hideView(view : View) {
        view.visibility = View.GONE
    }

    // Show view
    fun showView(view : View) {
        view.visibility = View.GONE
    }

    // Get time
    fun getTimeForDrillFormat(duration : Int) : String {
        var hours = duration / 60;
        var minutes = duration % 60;
        return "$hours:$minutes"
    }
}