# SPlanner

<!-- DESCRIPTION -->
## Description
SPlanner is a planner for students to plan and track their lessons, their tasks for the day,
and help them improve their subjects where they are weak at. Also allows students to listen to music while doing their
work.

<!-- Features -->
## Pages/Features
### Login & Register
### Forgot Password
### Home
### Profile

<!-- TEAM -->
## Team members

| Member      | Student ID   |
|-------------|--------------|
| Justin      | S10227943J   |
| Jason       | S10222947K   |
| Guo Heng    | S10223608C   |
| Yo Ming     | S10223100F   |
| Natalie     | S10227870K   |

<!-- CONTRIBUTION -->
## Contributions

* Natalie
* UI/UX
  * Did the layout of the timetable. I have learned how to combined both horizontal scroll view and recycler view in one page. Sorted the list according to timing, added a delete swipe motion as well. 

* Database
  * Did the journal. Learned how to add new table in the database. Learned how to edit the database, and also did swipe to delete motion as well. **(\*New\*)**
  
* Justin
  * Did the database of the whole application
  
* Guo heng
  * Did the layout of to do list and subject tracker 
  * Made a music player feature that asks for permission to access storage then getting all the songs downloaded in the device then displaying it. Uses media player
  to play each song, there are also pause, play, previous song, next song and repeat song functions. Music can continue
  playing even being outside of the app for example at home screen or in play store. Uses seek bar to track the progress
  of the song, users can adjust the seek bar to go to the part of the song that they like. After a song has finished
  playing it will automatically play the next song just like a playlist. **(\*New\*)**
  * Improvements of the previous two features:
  When adding task in to do list, the due date now uses date picker, When adding grade in subject tracker, the grade now
  uses a dropdown list, also added a remove button for each of the subjects, also added back the weakest subject feature,
  and made the UI look nicer for the subject tracker. 
   
* Yo ming
  * Did the layout, part of the database of homepage, the functions for Timer and The navigation bar to move between fragment activities 
  * New: Improved UI/UX of timer, Expanded Timer to make records, Added a nested fragment view pager for Timer, named 'Timer' and 'Records' **(\*New\*)**

* Jason
* UI/UX
  * Layout for profile page, login and sign up page
  * Implementation of [Circular ImageView][1] to make profile pictures circular when displayed.

* Database
  * Setting up of FirebaseStorage to store profile picture images
  * Implementation of [Picasso][2] to download images from FirebaseStorage and cache it in the app to allow for easy displaying.
  
[1]: https://github.com/lopspower/CircularImageView "Circular ImageView"
[2]: https://github.com/square/picasso "Picasso"

# User guides:

1. Sign up for the application
2. Navigate the application using the fragment bar
3. CLick on timetable icon
4. Enter your timetable for each day
5. Click on To Do List icon
6. Enter in your tasks
7. If task is completed, you can click completed
8. Click on Timer icon
9. Enter in your timing
10. Click start
11. Click Finish
12. Enter Record
13. Swipe left, view records
14. Click on the Exam Tracker icon
15. Enter your grades for your subjects
