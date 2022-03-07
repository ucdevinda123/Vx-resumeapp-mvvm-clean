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


## Screenshots
Home |CreateResume | Home List
--- | --- | --- 
<img width="270"  src="https://user-images.githubusercontent.com/4921099/156979884-76e19a6a-15c4-49e9-b00e-de5d35e44184.png"> | <img width="270"  src="https://user-images.githubusercontent.com/4921099/156980141-bb710353-53cc-4a37-a9ab-8a4e1b2e960f.png"> | <img width="270"  src="https://user-images.githubusercontent.com/4921099/156980540-df7a52b1-9786-4f0a-b62a-2e2edb79a76a.png"> 

Experience List |New Experience | Experience Validator
--- | --- | --- 
<img width="270"  src="https://user-images.githubusercontent.com/4921099/156952960-8dfce6df-41e8-4e10-b515-5a80db1309aa.png"> | <img width="270"  src="https://user-images.githubusercontent.com/4921099/156952969-e1600909-abee-490f-b8e3-77c03c754424.png"> | <img width="270"  src="https://user-images.githubusercontent.com/4921099/156981587-be71a063-0065-439e-9276-07c2cbdedf2d.png"> 


Personal Information |Personal Info Avatar | Personal infor Validator
--- | --- | --- 
<img width="270"  src="https://user-images.githubusercontent.com/4921099/156983201-c593a946-25eb-41b7-8660-b7ee879b34f9.png"> | <img width="270"  src="https://user-images.githubusercontent.com/4921099/156983272-90459483-d151-4cb9-a46d-2507e50daec2.png"> | <img width="270"  src="https://user-images.githubusercontent.com/4921099/156983326-5aafd6fc-f4df-4986-af9c-821bc8f31daa.png"> 



