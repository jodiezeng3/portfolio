import 'package:flutter/material.dart';

Map selected_mountain = {
  "name": "Please Select a Resort!",
  "state": "N/A",
  "height": 0,
  "num_trails": 0,
  "rating": 0,
  "image": "https://media.istockphoto.com/photos/winter-mountains-panorama-with-ski-slopes-and-ski-lifts-picture-id909307378?k=20&m=909307378&s=612x612&w=0&h=sh9Wxy62L1bLb53Eh9F5k-cSDUtyLsRT1Xj34yc7d5Y="
};

var day_str = [
  "1",
  "2",
  "3",
  "4",
  "5",
  "6",
  "7"
];

var pass_str = [
  "1",
  "2",
  "3",
  "4"
];

TextStyle title_style = TextStyle(color: Colors.black, fontWeight: FontWeight.bold, fontSize: 24);
TextStyle body_style = TextStyle(color: Colors.black, fontSize: 12);
TextStyle subtitle_style = TextStyle(color: Colors.black, fontSize: 20);

List mountains = [
  {
    "name": "Vail",
    "state": "CO",
    "height": 11570,
    "num_trails": 274,
    "rating": 4,
    "image": "https://scene7.vailresorts.com/is/image/vailresorts/20070304_VL_Affleck_003_3840x1200?wid=412&fit=vfit,1&resMode=sharp2&hei=309&dpr=on,2.625",
  },
  {
    "name": "Bever Creek",
    "state": "CO",
    "height": 11440,
    "num_trails": 150,
    "rating": 5,
    "image": "https://scene7.vailresorts.com/is/image/vailresorts/20190102_BC_Bowlin_003_1200x825?wid=412&fit=constrain,1&resMode=sharp2&dpr=on,2.625",
  },
  {
    "name": "Breckenridge",
    "state": "CO",
    "height": 12998,
    "num_trails": 187,
    "rating": 5,
    "image": "https://scene7.vailresorts.com/is/image/vailresorts/Breckenridge-Mountain-Info-Heros-2?wid=412&fit=vfit,1&resMode=sharp2&hei=309&dpr=on,2.625",
  },
  {
    "name": "Park City",
    "state": "UT",
    "height": 10026,
    "num_trails": 341.0,
    "rating": 5,
    "image": "https://cdn.vox-cdn.com/thumbor/JyvwlPraEVaQGbB-_zFgdPIMBcw=/0x0:1200x682/1200x800/filters:focal(504x245:696x437)/cdn.vox-cdn.com/uploads/chorus_image/image/64519670/1350600.0.jpg",
  },
  {
    "name": "Keystone",
    "state": "CO",
    "height": 12408,
    "num_trails": 135,
    "rating": 4,
    "image": "https://scene7.vailresorts.com/is/image/vailresorts/Key%20About%20the%20Mountain%20Mountain%20Info%20Mountain%20Page%20Body?wid=412&fit=constrain,1&resMode=sharp2&dpr=on,2.625",
  },
  {
    "name": "Crested Butte",
    "state": "CO",
    "height": 12162,
    "num_trails": 121,
    "rating": 5,
    "image": "http://www.powderhounds.com/site/DefaultSite/filesystem/images/USA/CrestedButte/LiftsTerrain/01.jpg",
  },
  {
    "name": "Stowe",
    "state": "VT",
    "height": 3719,
    "num_trails": 116,
    "rating": 4,
    "image": "https://thumbor.forbes.com/thumbor/960x0/https%3A%2F%2Fblogs-images.forbes.com%2Fkatiechang%2Ffiles%2F2016%2F11%2FStowe_Lodge_Ext_Winter_BaseofMtn_Signature1.jpg",
  },
  {
    "name": "Okemo",
    "state": "VT",
    "height": 3344,
    "num_trails": 120,
    "rating": 4,
    "image": "https://scene7.vailresorts.com/is/image/vailresorts/Mountain%20Info%20ft1?wid=392&fit=constrain,1&resMode=sharp2&dpr=on,2.625",
  },
  {
    "name": "Mount Snow",
    "state": "VT",
    "height": 3586,
    "num_trails": 86,
    "rating": 4,
    "image": "https://urbansherpatravel.com/images/photos/mount_snow1.jpg",
  },
  {
    "name": "Hunter",
    "state": "NY",
    "height": 4039,
    "num_trails": 67,
    "rating": 4,
    "image": "https://urbansherpatravel.com/images/photos/hunter2.jpg",
  },
  {
    "name": "Attitash",
    "state": "NH",
    "height": 2350,
    "num_trails": 67,
    "rating": 4,
    "image": "https://upload.wikimedia.org/wikipedia/commons/5/5e/Attitash_aerial.jpg",
  },
  {
    "name": "Wildcat",
    "state": "NH",
    "height": 4062,
    "num_trails": 48,
    "rating": 4,
    "image": "https://www.newenglandskihistory.com/NewHampshire/wildcatmtnprofile.jpg",
  },
  {
    "name": "Mount Sunapee",
    "state": "NH",
    "height": 2726,
    "num_trails": 66,
    "rating": 3,
    "image": "https://www.nhvacationideas.com/wp-content/uploads/2021/11/About_MS_Hero.jpg",
  },
  {
    "name": "Crotched",
    "state": "NH",
    "height": 2063,
    "num_trails": 26,
    "rating": 3,
    "image": "https://d1shwc4yijf729.cloudfront.net/resized/1280x640/assets/2020/04/08/23762011761-01621d5d9d-z_26_5e8de8132f1c1.webp",
  },
  {
    "name": "Liberty",
    "state": "PA",
    "height": 1190,
    "num_trails": 22,
    "rating": 4,
    "image": "https://img2.onthesnow.com/image/xl/52/liberty_mountain_525799.jpg",
  },
  {
    "name": "Roundtop",
    "state": "PA",
    "height": 1335,
    "num_trails": 21,
    "rating": 3,
    "image": "https://www.dcski.com/images/resort_photos/1602988979_vosiurzvzvtf.jpg",
  },
  {
    "name": "Whitetail",
    "state": "PA",
    "height": 1800,
    "num_trails": 22,
    "rating": 4,
    "image": "https://snowbrains.com/wp-content/uploads/2019/12/whitetail-main-min.jpg",
  },
  {
    "name": "Jack Frost",
    "state": "PA",
    "height": 2000,
    "num_trails": 19,
    "rating": 4,
    "image": "https://www.discovernepa.com/wp-content/uploads/2018/02/DN-TTD-LUZ-JackFrostSkirResort-01.jpg",
  },
  {
    "name": "Big Boulder",
    "state": "PA",
    "height": 2175,
    "num_trails": 15,
    "rating": 3,
    "image": "https://www.dcski.com/images/resort_photos/1602894976_uxpetkegejbg.jpg",
  },
  {
    "name": "Camelback",
    "state": "PA",
    "height": 2133,
    "num_trails": 35,
    "rating": 2,
    "image": "https://snowbrains.com/wp-content/uploads/2018/11/013-18_RES-EMAIL-GRAPHICS-Newsletter_685x500_OpeningDayPR-min.jpg",
  },
];
