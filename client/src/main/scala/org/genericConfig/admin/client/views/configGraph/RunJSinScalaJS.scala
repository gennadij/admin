package org.genericConfig.admin.client.views.configGraph

import org.singlespaced.d3js.d3

import scala.scalajs.js.Dynamic.{global => g}

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 *
 * Created by Gennadi Heimann 
 */
object RunJSinScalaJS {

  def runD3(): Unit = {

//    g.runFromScalaJS("{'test' : 'test'}")
    g.runGraphD3("{'test' : 'test'}")
//    val dataString : String = """{
//      "nodes": [
//        {"x": 469, "y": 410},
//        {"x": 493, "y": 364},
//        {"x": 442, "y": 365},
//        {"x": 467, "y": 314},
//        {"x": 477, "y": 248},
//        {"x": 425, "y": 207},
//        {"x": 402, "y": 155},
//        {"x": 369, "y": 196},
//        {"x": 350, "y": 148},
//        {"x": 539, "y": 222},
//        {"x": 594, "y": 235},
//        {"x": 582, "y": 185},
//        {"x": 633, "y": 200}
//        ],
//        "links": [
//        {"source":  0, "target":  1},
//        {"source":  1, "target":  2},
//        {"source":  2, "target":  0},
//        {"source":  1, "target":  3},
//        {"source":  3, "target":  2},
//        {"source":  3, "target":  4},
//        {"source":  4, "target":  5},
//        {"source":  5, "target":  6},
//        {"source":  5, "target":  7},
//        {"source":  6, "target":  7},
//        {"source":  6, "target":  8},
//        {"source":  7, "target":  8},
//        {"source":  9, "target":  4},
//        {"source":  9, "target": 11},
//        {"source":  9, "target": 10},
//        {"source": 10, "target": 11},
//        {"source": 11, "target": 12},
//        {"source": 12, "target": 10}
//      ]
//    }"""
//
////    val data2 : Dynamic = JSON.parse(dataString, )
//
//
////    def tick() =  {
////      link.attr("x1", (d : JsValue) => { return d.source.x; })
////        .attr("y1", function(d) { return d.source.y; })
////        .attr("x2", function(d) { return d.target.x; })
////        .attr("y2", function(d) { return d.target.y; });
////
////      node.attr("cx", function(d) { return d.x; })
////        .attr("cy", function(d) { return d.y; });
////    }
//
//
//    val width : Double= 960
//    val height : Double= 500
//    val force = d3.layout.force().size(width, height).charge(-400).linkDistance(40)//.on("tick", tick())

//    val drag = force.drag().on("dragstart", dragstart);
//
//    var svg = d3.select("body").append("svg")
//      .attr("width", width)
//      .attr("height", height);
//
//    var link = svg.selectAll(".link")
//    var node = svg.selectAll(".node")

    /**
     * Adapted from http://thecodingtutorials.blogspot.ch/2012/07/introduction-to-d3.html
     */
//    val graphHeight = 450
//
//    //The width of each bar.
//    val barWidth = 80
//
//    //The distance between each bar.
//    val barSeparation = 10
//
//    //The maximum value of the data.
//    val maxData = 50
//
//    //The actual horizontal distance from drawing one bar rectangle to drawing the next.
//    val horizontalBarDistance = barWidth + barSeparation
//
//    //The value to multiply each bar's value by to get its height.
//    val barHeightMultiplier = graphHeight / maxData;
//
//    //Color for start
//    val c = d3.rgb("DarkSlateBlue")
//
//    val rectXFun = (d: Int, i: Int) => i * horizontalBarDistance
//    val rectYFun = (d: Int) => graphHeight - d * barHeightMultiplier
//    val rectHeightFun = (d: Int) => d * barHeightMultiplier
//    val rectColorFun = (d: Int, i: Int) => c.brighter(i * 0.5).toString
//
//    val svg = d3.select("body").append("svg").attr("width", "100%").attr("height", "450px")
//    val sel = svg.selectAll("rect").data(js.Array(8, 22, 31, 36, 48, 17, 25))
//    sel.enter()
//      .append("rect")
//      .attr("x", rectXFun)
//      .attr("y", rectYFun)
//      .attr("width", barWidth)
//      .attr("height", rectHeightFun)
//      .style("fill", rectColorFun)
//
  }
}
