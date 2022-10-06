// ECMAScriptSaiAddDynamicRoutes.js

function touchTime(value) {
    // Create nodes directly in the parent scene
    var scene = Browser.currentScene;

    var shape = scene.createNode('Shape');
    var box   = scene.createNode('Box');
    var touchSensor = scene.createNode('TouchSensor');
    shape.geometry = box;

    // Create a Group to hold the nodes
    var group = scene.createNode('Group');

    // Add the shape and sensor to the group
    group.children = new MFNode(shape, touchSensor);

    // Add the nodes to the scene
    scene.RootNodes[0] = group;

    // Add a route from the touchSensor to this script
    scene.addRoute(touchSensor, 'touchTime', Script, 'touchTime');
}
