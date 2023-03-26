import 'package:flutter/material.dart';

class AppDrawer extends StatelessWidget {
  Widget build(BuildContext context) {
    return Drawer(
        child: ListView(children: [
      DrawerHeader(decoration: BoxDecoration(color: Colors.blue), child: Text('')),
      ListTile(
          leading: Icon(Icons.home),
          title: Text('Home Screen'),
          onTap: () {
            Navigator.pushNamed(context, '/');
          }),
      ListTile(
          leading: Icon(Icons.filter_hdr_outlined),
          title: Text('Mountains'),
          onTap: () {
            Navigator.pushNamed(context, '/mountains');
          }),
      ListTile(
          leading: Icon(Icons.calendar_today),
          title: Text('My Trip'),
          onTap: () {
            Navigator.pushNamed(context, '/trip');
          })
    ]));
  }
}
