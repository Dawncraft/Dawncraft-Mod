print('Hello World!');
var LogLoader = Java.type('io.github.dawncraft.config.LogLoader');
LogLoader.logger().info("This is an example for javascript.");
print(__FILE__, __LINE__, __DIR__);
var ScriptHelper = Java.type('io.github.dawncraft.util.ScriptHelper');
ScriptHelper.showEngineList();
//var imports = new JavaImporter('io.github.dawncraft.util');
//with (imports) {}
