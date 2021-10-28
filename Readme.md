### Collection Widget Example

This widget displays a list of 5-10 random names.


### Content updates

Content is updated in multiple ways:

1. Via the App.kt class. The application observes the data repository emissions, and requests the system
   `AppWidgetManager` to update the widget. 
   
   Note: This type of update can only occur if the app's process is alive (i.e the user has recently interacted with an Activity, or a service is running.) Thus, it should not be relied upon as the primary update trigger.
   
2. Via the interval specified in `res/xml/appwidget_provider.xml` `updatePeriodMillis`
   
   Note: I _think_ the minimum update interval is 30 minutes. This means potentially stale data.
   

A real world application that requires more frequent or timely updates could rely on Job Scheduling to trigger updates. I think the minimum threshold for recurring jobs is 15 minutes, but this could be overcome by scheduling one-shot jobs, which could then schedule another one-shot job after completion.

Alternatively, the widget could host a button which triggers updates when clicked.


### How it works:

`CollectionWidgetProvider` is the entry point of the widget. This class receives an Intent from the system `AppWidgetManager`, when the widget is first added to the screen (or whenever the system decides the widget needs updating, like after boot).

We can also send an Intent to trigger this to update our widget manually (but for updating our _collection_ data, we make a call to `appWidgetManager.notifyAppWidgetViewDataChanged`)

For the collection-y stuff (ListView), we call `setRemoteAdapter()` from our `WidgetProvider` class. This then ties the `CollectionWidgetService` to the widget. That service then reaches out to `CollectionRemoteViewsFactory`, which acts like a `ListAdapter` for our RemoteViews list. This adapter then creates and binds the `widget_item.xml` layout

The aforementioned `notifyAppWidgetViewDataChanged()` call triggers the `onDataSetChanged()` callback in `CollectionRemoteViewsFactory`, which is our opportunity to fetch the latest data.


### Things to note:

The widget grid size, minimum width/height, and whether the widget is resizeable can be configured in `res/xml/appwidget_provider.xml`

You can also choose to display a preview image that shows what the widget will look like.

Widgets are limited to utilising only 'RemoteViews'. This means lots of boring Linear/Relative/Frame Layouts, and no fun stuff like ConstraintLayout. We're also limited to TextView, ImageView and only a few other view types.
