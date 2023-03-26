import 'package:flutter/material.dart';
import 'mountains.dart';
import 'AppDrawer.dart';

class MainPage extends StatelessWidget {
  @override
  Widget build(BuildContext context) => Scaffold(
      appBar: AppBar(
        title: Text('Trail Finder'),
      ),
      body: Container(
        child: Column(
          children: <Widget>[
            Expanded(flex: 1, child: ListView(children: mainPage_widgets(context))),
          ],
        ),
      ),
      drawer: AppDrawer());
}

List<Widget> mainPage_widgets(BuildContext context) {
  List<Widget> widgets = new List();

  widgets.add(Text("Find your trail", style: title_style));
  widgets.add(Image.network("https://leisurelylifestyle.com/wp-content/uploads/2020/11/Snowy-Range-Ski-Area-1440x533.jpg"));
  widgets.add(Text("Whether you shread or ski, find the best place for you to find fresh pow. Just find your dream resort on the mountains page and select it, then fill out your information on the trip page.\n", style: body_style));
  widgets.add(Text("Popular Spots\n", style: title_style));

  mountains.sort((b, a) => a["rating"].compareTo(b["rating"]));

  for (int i = 0; i < 3; i++) {
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

    widgets.add(ElevatedButton(
      onPressed: () {
        selected_mountain = mountains[i];
        Navigator.pushNamed(context, '/trip');
      },
      child: const Text('Book Now!'),
    ));

    widgets.add(SizedBox(height: 30));
  }

  return widgets;
}
