function runGraphD3_2(arg){

	var data = JSON.parse(arg)
	console.log(data.properties)
	var c10 = d3.scale.category10();
	var svg = d3.select("body")
    .append("svg")
    .attr("width", data.properties.svgWidth)
    .attr("height", data.properties.svgHeight);

//  var drag = d3.behavior.drag()
//    .on("drag", function(d, i) {
//      console.log(this)
//      d.x += d3.event.dx
//      d.y += d3.event.dy
//      d3.select(this).attr("cx", d.x).attr("cy", d.y);
//      links.each(function(l, li) {
//        if (l.source == d.id) {
//          d3.select(this).attr("x1", d.x).attr("y1", d.y);
//        } else if (l.target == d.id) {
//          d3.select(this).attr("x2", d.x).attr("y2", d.y);
//        }
//      });
//    });

  var links = svg.selectAll("link")
    .data(data.links)
    .enter()
    .append("line")
    .attr("class", "link")
    .attr("x1", function(l) {
      var sourceNode = data.nodes.filter(function(d, i) {
        return d.id == l.source
      })[0];
      d3.select(this).attr("y1", sourceNode.y);
      return sourceNode.x
    })
    .attr("x2", function(l) {
      var targetNode = data.nodes.filter(function(d, i) {
        return d.id == l.target
      })[0];
      d3.select(this).attr("y2", targetNode.y);
      return targetNode.x
    })
    .attr("fill", "none")
    .attr("stroke", "white");

  var nodes = svg.selectAll("g")
    .data(data.nodes)
    .enter()
    .append("g");

  var circle = nodes.append("ellipse")
		.attr("class", "node")
    .attr("cx", function(d) {return d.x})
    .attr("cy", function(d) {return d.y})
    .attr("rx", 60)
    .attr("ry", 30)
    .attr("fill", function(d, i) {return c10(i);})
    .attr("id", function(d) {return d.id});
//    .call(drag);


  var label = nodes.append("text")
    .attr("dx", function(d){return d.x - 50})
    .attr("dy", function(d){return d.y})
    .text(function(d){return d.nameToShow.substring(0, 10)});
}