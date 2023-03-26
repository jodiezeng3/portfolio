import 'package:flutter/material.dart';
import 'mountains.dart';
import 'AppDrawer.dart';

class Trip extends StatelessWidget {
  @override
  Widget build(BuildContext context) => Scaffold(
      appBar: AppBar(
        title: Text('Trail Finder'),
      ),
      body: Container(child: trip_widgets()),
      drawer: AppDrawer());
}

class trip_widgets extends StatefulWidget {
  State createState() => _tState();
}

class _tState extends State {
  @override
  String first_name = "";
  String last_name = "";
  int days = 0;
  int passes = 0;
  String email = "";
  String _dropdownDay = "1";
  String _dropdownPasses = "1";

  Widget build(BuildContext context) {
    GlobalKey<FormState> _formKey = GlobalKey<FormState>();
    return Column(children: [
      Image.network(selected_mountain["image"]),
      SizedBox(height: 10),
      Text(selected_mountain["name"], style: title_style),
      Text("# of trails: ${selected_mountain["num_trails"]} | rating: ${selected_mountain["rating"]} | peak elevation: ${selected_mountain["height"]} ft", style: body_style),
      SizedBox(height: 5),
      Expanded(
          flex: 1,
          child: ListView(children: [
            Form(
                key: _formKey,
                child: Column(children: [
                  TextFormField(
                    decoration: InputDecoration(icon: Icon(Icons.person), hintText: 'First Name*', labelText: 'Enter your first name'),
                    validator: (String value) {
                      if (value.length > 0) {
                        first_name = value;
                        return null;
                      }

                      return "Enter your first name!";
                    },
                  ),
                  SizedBox(height: 5),
                  TextFormField(
                    decoration: InputDecoration(icon: Icon(Icons.person), hintText: 'Last Name*', labelText: 'Enter your last name'),
                    validator: (String value) {
                      if (value.length > 0) {
                        last_name = value;
                        return null;
                      }

                      return "Enter your last name!";
                    },
                  ),
                  SizedBox(height: 5),
                  Row(children: [
                    Text("Number of days:   ", style: body_style),
                    DropdownButton(
                        value: _dropdownDay,
                        onChanged: (value) {
                          setState(() {
                            _dropdownDay = value;
                            days = int.parse(value);
                          });
                        },
                        items: day_str.map((String value) => DropdownMenuItem(value: value, child: Text(value))).toList()),
                  ]),
                  SizedBox(height: 5),
                  Row(children: [
                    Text("Number of passes:   ", style: body_style),
                    DropdownButton(
                        value: _dropdownPasses,
                        onChanged: (value) {
                          setState(() {
                            _dropdownPasses = value;
                            passes = int.parse(value);
                          });
                        },
                        items: pass_str.map((String value) => DropdownMenuItem(value: value, child: Text(value))).toList()),
                  ]),
                  SizedBox(height: 5),
                  TextFormField(
                    decoration: InputDecoration(icon: Icon(Icons.mail), hintText: 'Email*', labelText: 'Enter your email'),
                    validator: (String value) {
                      if (value.length <= 2 || !value.contains('@')) return "Enter your email!";
                      email = value;
                      return null;
                    },
                  ),
                  SizedBox(height: 5),
                  ElevatedButton(
                      onPressed: () {
                        if (_formKey.currentState.validate() && selected_mountain["name"] != "Please Select a Resort!") setState(() {});
                      },
                      child: Text('Submit')),
                  SizedBox(height: 5),
                  Text("Resot visting: " + selected_mountain["name"] + ", " + selected_mountain["state"], style: subtitle_style),
                  SizedBox(height: 5),
                  Text("Name: $first_name $last_name", style: subtitle_style),
                  SizedBox(height: 5),
                  Text("Length of visit: $days days", style: subtitle_style),
                  SizedBox(height: 5),
                  Text("Number of passes: $passes", style: subtitle_style)
                ])),
          ]))
    ]);
  }
}
