### Collection Widget Example

This widget displays a list of 5-10 random names.


### Android Widgets

The Widget API is designed to prevent developers from displaying information on the screen in a manner that would drain battery or load the cpu unnecessarily - since the widget is potentially always displayed.

This is achieved by limiting the available Views to a subset, called 'RemoteViews'. These include Linear/Relative & FrameLayout, TextView, ImageView and a limited few others. 

Updates to these views are made by sending an intent to the AppWidgetManager, which in turn requests the widget to update itself, at which point it fetches any new data and re-renders the set of RemoteViews.


### Triggering List Content updates

Content is updated in multiple ways:

1. Via the App.kt class. The application observes the data repository emissions, and requests the system
   `AppWidgetManager` to update the widget
   
   Note: This type of update can only occur if the app's process is alive (i.e the user has recently interacted with an Activity, or a service is running.) Thus, it should not be relied upon as the primary update trigger.
   
2. Via the interval specified in `res/xml/appwidget_provider.xml` `updatePeriodMillis`
   
   Note: I _think_ the minimum update interval is 30 minutes. This means potentially stale data.
   

A real world application that requires more frequent or timely updates could rely on Job Scheduling to trigger updates. I think the minimum threshold for recurring jobs is 15 minutes, but this could be overcome by scheduling one-shot jobs, which could then schedule another one-shot job after completion.

Alternatively, the widget could host a button which triggers updates when clicked.


### How it works:

`CollectionWidgetProvider` is the entry point of the widget. This class receives an Intent from the system `AppWidgetManager`, when the widget is first added to the screen (or whenever the system decides the widget needs updating, like after boot).

We can also send an Intent to trigger this to update our widget manually.

For the collection-y stuff (ListView), we call `setRemoteAdapter()` from our `WidgetProvider` class. This then ties the `CollectionWidgetService` to the widget. That service then reaches out to `CollectionRemoteViewsFactory`, which acts like a `ListAdapter` for our RemoteViews list. This adapter then creates and binds the `widget_item.xml` layout

To update our _collection_ data, we make a call to `appWidgetManager.notifyAppWidgetViewDataChanged`, which is a bit like calling `noitfyDataSetChanged()` on an adapter.

The `notifyAppWidgetViewDataChanged()` call triggers the `onDataSetChanged()` callback in `CollectionRemoteViewsFactory`, which is our opportunity to fetch the latest data from our repo, and re-bind the widget's collection view cells.


### Things to note:

The widget grid size, minimum width/height, and whether the widget is resizeable can be configured in `res/xml/appwidget_provider.xml`

You can also choose to display a preview image that shows what the widget will look like.

Widgets are limited to utilising only 'RemoteViews'. This means lots of boring Linear/Relative/Frame Layouts, and no fun stuff like ConstraintLayout. We're also limited to TextView, ImageView and only a few other view types.


### Further reading

Documentation:
https://developer.android.com/guide/topics/appwidgets

Widget collections (displaying a list, grid or stack):
https://developer.android.com/guide/topics/appwidgets/collections

Google gave Widgets a little bit of love in Android 12:
https://developer.android.com/about/versions/12/features/widgets

