import 'package:flutter/material.dart';
import 'mountains.dart';
import 'AppDrawer.dart';

var mountains_used = mountains;
double _sliderTrails = 13.0;
int _radioRating = 0;
int _radioSort = 0;

class MountainsPage extends StatelessWidget {
  @override
  Widget build(BuildContext context) => Scaffold(
      appBar: AppBar(
        title: Text('Trail Finder'),
      ),
      body: Container(child: mountains_widgets()),
      drawer: AppDrawer());
}

class mountains_widgets extends StatefulWidget {
  State createState() => _mwState();
}

class _mwState extends State {
  @override
  void update() {
    if (_radioRating == 0)
      mountains_used = mountains.where((mountain) => mountain["num_trails"] > _sliderTrails).toList();
    else
      mountains_used = mountains.where((mountain) => mountain["num_trails"] > _sliderTrails && mountain["rating"] == _radioRating).toList();

    if (_radioSort == 0)
      mountains_used.sort((a, b) => a["name"].compareTo(b["name"]));
    else if (_radioSort == 1)
      mountains_used.sort((a, b) => a["state"].compareTo(b["state"]));
    else if (_radioSort == 2)
      mountains_used.sort((b, a) => a["height"].compareTo(b["height"]));
    else if (_radioSort == 3) mountains_used.sort((b, a) => a["num_trails"].compareTo(b["num_trails"]));
  }

  Widget build(BuildContext context) {
    GlobalKey<FormState> _formKey = GlobalKey<FormState>();

    return Column(children: [
      Form(
          key: _formKey,
          child: Column(
            children: [
              Text("Sort by: ", style: body_style),
              Row(children: [
                Text("Name ", style: body_style),
                Radio(
                    value: 0,
                    groupValue: _radioSort,
                    onChanged: (value) {
                      setState(() {
                        _radioSort = value;
                        update();
                      });
                    }),
                Text(" | State ", style: body_style),
                Radio(
                    value: 1,
                    groupValue: _radioSort,
                    onChanged: (value) {
                      setState(() {
                        _radioSort = value;
                        update();
                      });
                    }),
                Text(" | Elevation ", style: body_style),
                Radio(
                    value: 2,
                    groupValue: _radioSort,
                    onChanged: (value) {
                      setState(() {
                        _radioSort = value;
                        update();
                      });
                    }),
                Text(" | # Trails ", style: body_style),
                Radio(
                    value: 3,
                    groupValue: _radioSort,
                    onChanged: (value) {
                      setState(() {
                        _radioSort = value;
                        update();
                      });
                    })
              ]),
              Text("\nFilter by: ", style: body_style),
              Text("Number of Trails", style: body_style),
              Slider(
                  value: _sliderTrails,
                  min: 13,
                  max: 300,
                  divisions: 10,
                  label: _sliderTrails.toInt().toString(),
                  onChanged: (value) {
                    setState(() {
                      _sliderTrails = value;
                      update();
                    });
                  }),
              Text("Rating", style: body_style),
              Row(children: [
                Text("All ", style: body_style),
                Radio(
                    value: 0,
                    groupValue: _radioRating,
                    onChanged: (value) {
                      setState(() {
                        _radioRating = value;
                        update();
                      });
                    }),
                Text(" | 1 ", style: body_style),
                Radio(
                    value: 1,
                    groupValue: _radioRating,
                    onChanged: (value) {
                      setState(() {
                        _radioRating = value;
                        update();
                      });
                    }),
                Text(" | 2 ", style: body_style),
                Radio(
                    value: 2,
                    groupValue: _radioRating,
                    onChanged: (value) {
                      setState(() {
                        _radioRating = value;
                        update();
                      });
                    }),
                Text(" | 3 ", style: body_style),
                Radio(
                    value: 3,
                    groupValue: _radioRating,
                    onChanged: (value) {
                      setState(() {
                        _radioRating = value;
                        update();
                      });
                    }),
                Text(" | 4 ", style: body_style),
                Radio(
                    value: 4,
                    groupValue: _radioRating,
                    onChanged: (value) {
                      setState(() {
                        _radioRating = value;
                        update();
                      });
                    }),
                Text(" | 5 ", style: body_style),
                Radio(
                    value: 5,
                    groupValue: _radioRating,
                    onChanged: (value) {
                      setState(() {
                        _radioRating = value;
                        update();
                      });
                    })
              ]),
            ],
          )),
      Expanded(
          flex: 1,
          child: ListView.builder(
              itemCount: mountains_used.length,
              itemBuilder: (context, index) {
                return Card(
                  child: ListTile(
                      title: Text(mountains_used[index]["name"] + ", " + mountains_used[index]["state"], style: title_style),
                      subtitle: Text("# of trails: ${mountains[index]["num_trails"]} | rating: ${mountains[index]["rating"]} | peak elevation: ${mountains[index]["height"]} ft", style: body_style),
                      leading: Icon(Icons.filter_hdr),
                      onTap: () {
                        selected_mountain = mountains_used[index];
                        //switch to trip page
                        Navigator.pushNamed(context, '/trip');
                      }),
                );
              }))
    ]);
  }
}

List<Widget> mountainsPage_widgets() {
  List<Widget> widgets = new List();

  for (int i = 0; i < mountains_used.length; i++) {
    Column text = new Column(children: [
      Text(mountains[i]["name"] + ", " + mountains[i]["state"], style: title_style),
      Text("# of trails: ${mountains[i]["num_trails"]} | rating: ${mountains[i]["rating"]}", style: body_style),
      Text("peak elevation: ${mountains[i]["height"]} ft", style: body_style)
    ]);

    Image image = new Image.network(mountains[i]["image"]);

    Row row = new Row(children: [
      new Expanded(flex: 1, child: new ConstrainedBox(child: image, constraints: BoxConstraints(maxHeight: 200.0))),
      new Expanded(flex: 1, child: new ConstrainedBox(child: text, constraints: BoxConstraints(maxHeight: 100.0)))
    ], mainAxisAlignment: MainAxisAlignment.spaceAround);

    Container blockContainer = new Container(child: row, margin: EdgeInsets.only(bottom: 10.0));

    widgets.add(blockContainer);
  }

  return widgets;
}
