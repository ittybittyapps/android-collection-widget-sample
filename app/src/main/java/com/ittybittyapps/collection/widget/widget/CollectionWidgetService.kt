package com.ittybittyapps.collection.widget.widget

import android.content.Intent
import android.widget.RemoteViewsService
import com.ittybittyapps.collection.widget.App

class CollectionWidgetService : RemoteViewsService() {

    private val container by lazy { (applicationContext as App).serviceContainer }

    override fun onGetViewFactory(intent: Intent): RemoteViewsFactory {
        return CollectionRemoteViewsFactory(applicationContext, container.repository)
    }
}
