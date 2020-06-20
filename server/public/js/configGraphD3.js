
function runGraphD3(arg){

console.log(arg)

//graph = {
//      'nodes': [
//        {'id': 'A','x': 469,'y': 410},
//        {'id': 'B','x': 493,'y': 364},
//        {'id': 'C','x': 442,'y': 365},
//        {'id': 'D','x': 467,'y': 314},
//        {
//          'id': 'E',
//          'x': 477,
//          'y': 248
//        }, {
//          'id': 'F',
//          'x': 425,
//          'y': 207
//        }, {
//          'id': 'G',
//          'x': 402,
//          'y': 155
//        }, {
//          'id': 'H',
//          'x': 369,
//          'y': 196
//        }, {
//          'id': 'I',
//          'x': 350,
//          'y': 148
//        }, {
//          'id': 'J',
//          'x': 539,
//          'y': 222
//        }, {
//          'id': 'K',
//          'x': 594,
//          'y': 235
//        }, {
//          'id': 'L',
//          'x': 582,
//          'y': 185
//        }, {
//          'id': 'M',
//          'x': 633,
//          'y': 200
//        }
//      ],
//      'links': [
//        {
//          'source': 'A',
//          'target': 'B'
//        }, {
//          'source': 'B',
//          'target': 'C'
//        }, {
//          'source': 'C',
//          'target': 'A'
//        }, {
//          'source': 'B',
//          'target': 'D'
//        }, {
//          'source': 'D',
//          'target': 'C'
//        }, {
//          'source': 'D',
//          'target': 'H'
//        }, {
//          'source': 'E',
//          'target': 'F'
//        }, {
//          'source': 'F',
//          'target': 'G'
//        }, {
//          'source': 'F',
//          'target': 'H'
//        }, {
//          'source': 'G',
//          'target': 'H'
//        }, {
//          'source': 'G',
//          'target': 'I'
//        }, {
//          'source': 'H',
//          'target': 'I'
//        }, {
//          'source': 'J',
//          'target': 'E'
//        }, {
//          'source': 'J',
//          'target': 'L'
//        }, {
//          'source': 'J',
//          'target': 'K'
//        }, {
//          'source': 'K',
//          'target': 'L'
//        }, {
//          'source': 'L',
//          'target': 'M'
//        }, {
//          'source': 'M',
//          'target': 'K'
//        }
//      ]
//    };

var graph = arg
    /* resolve node IDs (not optimized at all!)
    */
    _ref = graph.links;
    for (_i = 0, _len = _ref.length; _i < _len; _i++) {
      l = _ref[_i];
      _ref2 = graph.nodes;
      for (_j = 0, _len2 = _ref2.length; _j < _len2; _j++) {
        n = _ref2[_j];
        if (l.source === n.id) {
          l.source = n;
          continue;
        }
        if (l.target === n.id) {
          l.target = n;
          continue;
        }
      }
    }

//    ### resolve node IDs (not optimized at all!) ###
//    for l in graph.links
//        for n in graph.nodes
//            if l.source is n.id
//                l.source = n
//                continue
//
//            if l.target is n.id
//                l.target = n
//                continue

//var data = {
//  "nodes": [
//    {"x": 469, "y": 410},
//    {"x": 493, "y": 364},
//    {"x": 442, "y": 365},
//    {"x": 467, "y": 314},
//    {"x": 477, "y": 248},
//    {"x": 425, "y": 207},
//    {"x": 402, "y": 155},
//    {"x": 369, "y": 196},
//    {"x": 350, "y": 148},
//    {"x": 539, "y": 222},
//    {"x": 594, "y": 235},
//    {"x": 582, "y": 185},
//    {"x": 633, "y": 200}
//  ],
//  "links": [
//    {"source":  0, "target":  1},
//    {"source":  1, "target":  2},
//    {"source":  2, "target":  0},
//    {"source":  1, "target":  3},
//    {"source":  3, "target":  2},
//    {"source":  3, "target":  4},
//    {"source":  4, "target":  5},
//    {"source":  5, "target":  6},
//    {"source":  5, "target":  7},
//    {"source":  6, "target":  7},
//    {"source":  6, "target":  8},
//    {"source":  7, "target":  8},
//    {"source":  9, "target":  4},
//    {"source":  9, "target": 11},
//    {"source":  9, "target": 10},
//    {"source": 10, "target": 11},
//    {"source": 11, "target": 12},
//    {"source": 12, "target": 10}
//  ]
//}
var width = 960,
    height = 500;

var force = d3.layout.force()
    .size([width, height])
    .charge(-400)
    .linkDistance(90)
    .on("tick", tick);

var drag = force.drag()
    .on("dragstart", dragstart);

var svg = d3.select("body").append("svg")
    .attr("width", width)
    .attr("height", height);

var link = svg.selectAll(".link"),
    node = svg.selectAll(".node");

//d3.json("graph.json", function(error, graph) {
  //if (error) throw error;
//var graph = data
  force
      .nodes(graph.nodes)
      .links(graph.links)
      .start();

      console.log(force)

  link = link.data(graph.links)
    .enter().append("line")
      .attr("class", "link");

  node = node.data(graph.nodes)
    .enter().append("circle")
      .attr("class", "node")
      .attr("r", 12)
      .on("dblclick", dblclick)
      .call(drag);
//});

function tick() {
  link.attr("x1", function(d) { return d.source.x; })
      .attr("y1", function(d) { return d.source.y; })
      .attr("x2", function(d) { return d.target.x; })
      .attr("y2", function(d) { return d.target.y; });

  node.attr("cx", function(d) { return d.x; })
      .attr("cy", function(d) { return d.y; });
}

function dblclick(d) {
  d3.select(this).classed("fixed", d.fixed = false);
}

function dragstart(d) {
  d3.select(this).classed("fixed", d.fixed = true);
}

}