package com.ittybittyapps.collection.widget.widget

import android.content.Context
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.ittybittyapps.collection.widget.R
import com.ittybittyapps.collection.widget.data.DataRepository

class CollectionRemoteViewsFactory(
    private val context: Context,
    private val repository: DataRepository
) : RemoteViewsService.RemoteViewsFactory {

    data class WidgetItem(val text: String)

    private var widgetItems: List<WidgetItem> = emptyList()

    override fun onCreate() {

    }

    override fun onDestroy() {

    }

    override fun onDataSetChanged() {
        widgetItems = repository.data.value.map { WidgetItem(it.name) }
    }

    override fun getCount(): Int {
        return widgetItems.size
    }

    override fun getViewAt(position: Int): RemoteViews {
        // Construct a remote views item based on the widget item XML file,
        // and set the text based on the position.
        return RemoteViews(context.packageName, R.layout.widget_item).apply {
            setTextViewText(R.id.textView, widgetItems[position].text)
        }
    }

    override fun getLoadingView(): RemoteViews? {
        return null
    }

    override fun getViewTypeCount(): Int {
        return 1
    }

    override fun getItemId(position: Int): Long {
        return widgetItems[position].hashCode().toLong()
    }

    override fun hasStableIds(): Boolean {
        return true
    }

}
