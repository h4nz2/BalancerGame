exports.findAll = function(req, res) {
    res.send([
        {name:'player1', score: 0, date: "Jun 01 1987 00:00:00"}, 
        {name:'player2', score: 0, date: "Jun 02 1988 00:00:00"}, 
        {name:'player3', score: 0, date: "Jun 03 1989 00:00:00"}
    ]);
};

exports.findByName = function(req, res) {
    res.send({name:req.params.name, name: "MoonWalker78", score: 500, date: "Mar 20 2015 19:28:31"});
};