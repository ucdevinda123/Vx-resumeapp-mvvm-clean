# Vx Resume Builder App  MVVM + Clean Architecture (Fully Offline)
This  Android application built using MVVM + Clean Architecture architecture approach and is written 100% in Kotlin.
App is capable of working fully offline.
App will be using my html template powered By VUE3.JS

# Architecture behind the app:

Presentation Layer :   UI (Activity,Fragment) and ViewModel
Domain Layer :  UseCases and Abstract repository interface
DI : Hilt for all the dependency management
Data : Offline ( RoomDb + Dao) and  DTO and Repository Implementation (Application works fully offline)
Common : Resource Class

### 1.)Used Libraries and Architecture
- **Architecture**
    * [MVVM  + Clean Architecture](https://developer.android.com/jetpack/guide?gclid=Cj0KCQjwxdSHBhCdARIsAG6zhlVhsDIRhgPzGSshbH7BPyXgzTI9zPLZgxXP5V5ol3KFyCp-gFKZf4oaAgYOEALw_wcB&gclsrc=aw.ds)
    * [Android architecture components](https://developer.android.com/topic/libraries/architecture/index.html)
    * [Jetpack Architecture Guide](https://developer.android.com/jetpack/guide)

- **Dependency Injection**
    * [Hilt](https://dagger.dev/hilt/)
    
- **Offline**
    * [Room](https://square.github.io/retrofit/)

- **Async Communication and data caching**
    * [Kotlin Coroutines](https://developer.android.com/kotlin/coroutines?gclid=Cj0KCQjwxdSHBhCdARIsAG6zhlVAkTBk3eW_R4YZYvyGqNlX3PFEtQWBY0yjmGj74Flk5ZW6UDnu1V4aAsLeEALw_wcB&gclsrc=aw.ds)
    * [Kotlin Flow](https://developer.android.com/kotlin/flow)
  
- **Navigation**
    * [Navigation Component](https://developer.android.com/guide/navigation/navigation-getting-started)

- **Web Technology (under android_asset folder)**
    * [Vue3 js](https://vuejs.org/)
    * [Bootstrap ](https://getbootstrap.com/docs/5.1/getting-started/download/)

### 2.)Screens
      Home Screen - Create new resume
                    Generate list of resume already created
                    Edit resume
                    Remove resume
     Resume creation - 
                    Resume infor dialog
                         Enter resume information
                    Persoanl Infor 
                        Edit Avatar image
                        Add new information save it
                        Update existing info
                        View existing info
                    Education
                        List of education details
                        Add new education details
                              Add new education
                        Edit education details
                              Education details screen
                        Remove education

                    Experience
                        List of experience details
                        Add new experience details
                        Add new experience
                        Edit experience details
                        Experience details screen
                        Remove experience

                    Project details
                        List of project details
                        Add new project details
                        Add new project
                        Edit project details
                        Experience project screen
                        Remove project

                    Resume Viewer
                            View resume in html format
                            Generate resume PDF format



### 3.)Improvements : Needs to implement an API to have online support and add more resume templates people to use

### 4.)Check List
- **k-lint Formatter** - Done
- **Ui Implementation** - Done
- **Offline first support** - Done
- **SOLID implementation** - Done MVVM with Clean Architecture
- **Latest Android Studio** - Done Android Studio Arctic Fox |2020.3.1 Patch 4
- **Offline Support** - Done
- **Resume Viewer and generator** - Partially done (Some of the data still needs to pass in to VUE 3)


