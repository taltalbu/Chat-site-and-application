# Chat-site-and-application
React,node.js, mongodb and android studio 


# ChatYTY: Web and Android Chat App
ChatYTY is a versatile and user-friendly chat application that allows seamless communication across both web and Android platforms. ChatYTY provides a convenient and efficient way to exchange messages in real-time.

## Features
- **Cross-Platform Compatibility**: With ChatYTY, you can enjoy a consistent chatting experience on both web browsers and Android devices. Stay connected, no matter which platform you prefer.
- **Real-Time Messaging**: Experience the thrill of instant communication with ChatYTY's real-time messaging feature. Send and receive messages instantly, enabling fluid conversations with your contacts.
- **User-Friendly Interface**: ChatYTY boasts a sleek and intuitive interface designed to enhance your chatting experience. Seamlessly navigate through conversations, manage contacts, and personalize your chat settings with ease.
## Requirements
To run the Android chat app and connect it with Firebase and MongoDB, you need the following:

- **Android Studio**: The official integrated development environment (IDE) for Android development.
- **Firebase Account**: Create a Firebase project in the Firebase console and configure the necessary services, Firebase Cloud Messaging.
- **Google Play Services**: Integrate Google Play Services into your Android project to utilize Firebase services.
- **MongoDB Server**: Set up a MongoDB server and configure it to store chat messages.

## Getting start
first, clone the projet to desire folder:
```
clone https://github.com/taltalbu/chatYTY-android
```

Must initialize the web-client to initialize the server
### web client
1. Install client dependencies:
- Go to the public (client) directory:
  ```
  cd public
  ```
- Install dependencies:
  ```
  npm install
  ```
2. Build the project:
```
npm run build
``` 
### Server
To run the server, follow these steps:
1. Install server dependencies:
   - Go to the main directory(if you in the public):
     ```
     cd ..
     ```
   - Go to the server directory:
     ```
     cd server
     ```
   - Install dependencies:
     ```
     npm install
     ```
2. Start the server:
```
npm start
``` 
clients will be accessible in browsers at `http://localhost:5000`.<br>

## The login screen
<img width="960" alt="image" src="https://github.com/taltalbu/Chat-site-and-application/assets/117980942/d80b2edd-fb39-480e-bc9d-96c9974e51f1">

## The register screen
<img width="960" alt="image" src="https://github.com/taltalbu/Chat-site-and-application/assets/117980942/d82256ea-cab0-4182-816f-1bd1d3cdcfdb">

## The chat screen
<img width="960" alt="image" src="https://github.com/taltalbu/Chat-site-and-application/assets/117980942/c066821b-b2d7-4609-9d7b-521a9af5df4f">


### Android Version: 
First, you must initialize the server as described above.
Clone the repository:
```
https://github.com/taltalbu/chatYTY-android/tree/main/MyApplication
``` 
Open the project in Android Studio.
<br/>Build and run the Android app on your device or emulator.


The ChatYTY app follows the following development methodologies:

- **MVC on the Server**: The server-side architecture is based on the Model-View-Controller (MVC) design pattern, which separates the application logic into three interconnected components: the model (data), the view (user interface), and the controller (handles requests and updates the model and view).
- **Use of RecyclerView and Adapter**: In the Android app, the RecyclerView and Adapter components are used to efficiently display lists of chat messages, contacts, and other data. The RecyclerView provides a flexible and optimized way of recycling and reusing views, while the Adapter manages the data binding and item rendering.
- **ViewModel and LiveData**: The Android ViewModel and LiveData components are utilized to implement a separation of concerns between the UI and data layers. The ViewModel holds and manages the UI-related data, while LiveData provides observable data objects that notify the UI when changes occur.
- **Repository Pattern**: The app uses the repository pattern to abstract the data sources and provide a consistent interface for accessing data from both the local Room database and the server.


