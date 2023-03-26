import 'package:flutter/material.dart';
import 'MainPage.dart';
import 'MountainsPage.dart';
import 'Trip.dart';

void main() => runApp(MyApp());

class MyApp extends StatelessWidget {
  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Trail Finder',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
//      home: MainPage(),

      // Declare routes
      routes: {
        // Main initial route
        '/': (context) => MainPage(),
        // Second route
        '/mountains': (context) => MountainsPage(),
        '/trip': (context) => Trip()
      },
      initialRoute: '/',
    );
  }
}
