
function runGraphD3(arg){

    var data_2 = JSON.parse(arg)

    /* resolve node IDs (not optimized at all!) */
    _ref = data_2.links;
    for (_i = 0, _len = _ref.length; _i < _len; _i++) {
      l = _ref[_i];
      _ref2 = data_2.nodes;
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

    var data = JSON.parse(arg)

    console.log(data)
    var graph = data_2
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
          .attr("id", function(d) {return d.id})
          .attr("r", 12)
          .on("dblclick", dblclick)
          .on("click", click)
          .call(drag);

    function tick() {
      link.attr("x1", function(d) { return d.source.x; })
          .attr("y1", function(d) { return d.source.y; })
          .attr("x2", function(d) { return d.target.x; })
          .attr("y2", function(d) { return d.target.y; });

      node.attr("cx", function(d) { return d.x; })
          .attr("cy", function(d) { return d.y; });
    }

    function dblclick(d) {
      console.log("dblclick")
      d3.select(this).classed("fixed", d.fixed = false);
    }

    function dragstart(d) {
      d3.select(this).classed("fixed", d.fixed = true);
    }

    function click(d) {
        console.log("click")
        console.log(d)
    }

}