# RectangularsApp
### Documentation:
  Simple app with 2 rectangles shown on screen fetched remotely(but really from the usecase :D) and you can move them around and their location will be stored locally for 10 minutes (measured fron the moment you'v elastly changes a rectangle's position) after that they're fetched remotely again.
### Tools:
  Used RxJava<br/>
  Used Dagger<br/>
  Used JetPack components<br/>
  Used retrofit for networking<br/>
  Used Room DB for storing the rectangles locations<br/>
  Used mockitokotlin2 alongside with JUnit for unit testing<br/>
  Used shared preferences for storing the timne of storing the latest updates<br/>
### Architecture:
  Used MVVM
