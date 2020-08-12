# **gce-common**

**gce-common** would be the core library required by all GCE micro-services.
This library consists of code that is common across all services.  



## Steps to integrate gce-common locally

1. Build the code with **./gradlew clean build publishToMavenLocal**

2. Add gce-common dependency in build.gradle of the service.
        
        implementation 'com.szells.gce:gce-common:0.0.1'

## Steps to publish gce-common on nexus

1. mention credentials in gradle.properties file.

2. publish the code with **./gradlew publish**

## API List
| API	| Type	| Url					| Controller|
| ----	| ---	| --					| ----------|
| Save Personal Detail API| POST| /auth/users| UserController|
| Forgot ID API| POST| /auth/forgotemail| AuthController|
| Login| POST| /auth/login| AuthController|
| Forgot Password API| POST| /auth/forgotpassword| AuthController|
| Logout| POST| /auth/invalidate| LogoutController|
| Change Password after login| POST| /auth/changepassword| AuthController|
| token validate| POST| /auth/introspect| AuthController|
| Password reset from mail| POST| auth/resetpassword| AuthController|
| Create user in keycloak| POST| /auth/Create| AuthController|

## Steps to Run an application
1. Take the latest copy of the code from the repository
2. Open project.
3. build the project using Gradle-><<Project>>->Tasks->build->build.
4. Once Project get build successfully, press Shift+F10 to run it.


