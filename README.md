# COWORKING-SPACE M233
## Description
### This application was made for a Coworking-Space. You should be able to create a User and then you can login with your User and depending on your role you will get an Admin or User token. The tokens are needed to access the other Endpoints that are protected since a normal visitor shouldn't be able to see that data.

## Set-up
### To set-up the project all you need to do is clone the Repo from the [Github](https://github.com/jonlanda/m233-Project). You'll need Docker to be able to run the application and if you use Visual Studio Code I highly recommend using the [Quarkus](https://marketplace.visualstudio.com/items?itemName=redhat.vscode-quarkus) and [Dev Container](https://marketplace.visualstudio.com/items?itemName=ms-vscode-remote.remote-containers) extensions.

## Starting the Project
### To start the project you just need to open it in the container (a window will pop up for that if you're using the vs-code extension) and the run the command ```./mvnw quarkus:dev``` in your terminal. If you use the Quarkus extension you can also just use Ctrl + Shift + P and the type 'Quarkus: Debug current Quarkus project'

## Starting with Test-Data
### To start the project with test data you need to open the project in the container just like normal (if you already have it open in the container you don't need to do it again). But then you need to add something to your ```./mvnw quarkus:dev``` command. We need to tell Quarkus, that we want to open the project on our test profile so just type ```./mvnw quarkus:dev -Dquarkus.profile=testwithdata```. To edit the test data go to ```src/main/resources/init-dev.sql```.
