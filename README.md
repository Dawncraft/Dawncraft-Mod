# Dawncraft Mod For Minecraft 1.8

----------

##简介

我的世界曙光工艺Mod 版本1.8

曙光工艺采用[GPLv3协议](https://github.com/Wdawning/Dawn-Craft-Mod/blob/master/LICENSE)开源

欢迎[加入我们](http://shang.qq.com/wpa/qunwpa?idkey=61928f37c251f45b49f652efc2d90857f24b38b2d5d69859c8d3ae6241479a02) QQ群:**287307326**

##目录

[开发Mod](#Mod开发)

[开发者规范](#开发者规范)

[Mod目录作用](#Mod目录)

[未来展望](#未来展望)

[更新日志](#更新日志)

[特别致谢](#特别致谢)

##Mod开发

*在您开始我们的开发工作前，我希望您能耐心地看下去，谢谢。*

###准备工作

想要参加曙光工艺Mod的开发工作，你需要：

1. 一个聪慧的大脑和一双巧手
2. 对计算机和Java的基本理解
3. 能熟练地游玩我的世界(Minecraft)
4. 了解MCP，Forge和FML
3. 安装[JDK](http://www.oracle.com/technetwork/java/javase/downloads/index-jsp-138363.html)并且拥有一个IDE([Eclipse](http://www.eclipse.org/)或[Idea](https://www.jetbrains.com/idea/))
4. 会使用[Github](https://github.com/)
5. 下载[本Mod源码](https://github.com/Wdawning/Dawn-Craft-Mod/archive/master.zip)至本地

###配置工作环境
1. 将本mod打包下载并解压到文件夹(这里以D:\wc\Dawn-Craft-Mod为例)

2. 进入该目录并在该目录下执行命令
   
    （在目录下按住Shift键并右键可以快速在当前目录打开命令行）

    如果你使用的是Microsoft Windows，则运行：

	    gradlew.bat setupDecompWorkspace

    如果你使用的是Linux或者Mac OS X，在该目录下运行命令：

	    ./gradlew setupDecompWorkspace

    PS：*因为资源大多在国外，所以可能要等待一段漫长的时间。建议在网络状况好的地方运行此命令，以配置开发环境，并获取反编译过的Minecraft源代码。如果有条件，建议使用国外的代理。*

    如果使用Eclipse作为IDE，请在上面的命令运行完成后运行：

	    gradlew.bat或./gradlew eclipse

    如果使用IntellijIDEA：

	    gradlew.bat或./gradlew idea

3. 打开IDE，将工程目录切换到这个目录，如果配置成功，IDE会注意到这个目录存在一个工程，并自动找到刚刚配置的这个Mod工程的源代码和资源文件的位置。

    Mod工程的源代码在目录`src/main/java`下，Mod工程的资源文件（如图片、模型等）在目录`src/main/resources`下。

    **(摘自ustc-zzzz的教程)**

###构建Mod

构建Mod请运行下面的命令：

	gradlew.bat或./gradlew build

构建完成后的Mod可以在根目录下的`build/libs/`里找到。

###开发Mod

如果你想要开发Mod,却没有开发Mod的基础.

我推荐你研究官方Doc和其他人的教程:

* [**Forge官方文档**](http://mcforge-cn.readthedocs.io/zh/latest/)
* [**manageryzy的MC开发wiki**](https://mcdev-wiki.org/)
* [**ustc-zzzz的教程**](https://ustc-zzzz.gitbooks.io/fmltutor/content/)
* [**szszss的教程**](http://blog.hakugyokurou.net/?page_id=126)
*(貌似被墙了？)*

##开发者规范

如果你想为我们的项目贡献代码

那么请遵守**规范**:

1. 所有代码和文本文件都使用**UTF-8(无BOM)**编码，末尾要加**空行**。

2. 缩进请使用**四个空格**或**一个制表符**，但是每一个文件中都尽量使用同一种缩进。

3. 大括号隔行**随意**，不过最好**统一**。

4. 请使用**正确**的Java语法，并且对自己的代码**负责**。

5. 类名尽量符合**Forge命名规范**，例如物品类应该是`ItemWuPin`

6. 常量名称**大写**，变量、方法的名字请使用**小写驼峰**式。

7. 请好好利用**注释**，在重要和难以理解的地方加**注释**。

8. 请在**gradle.properties**中更改Mod信息

9. 尽量**简化**代码结构，过长的代码要分成**多行**。

10. 请勿**恶意上传**大文件。

11. 写代码请注意**规范**,例如:

    在`com.github.wdawning.dawncraft.item.ItemLoader`中注册物品

    而不是在`com.github.wdawning.dawncraft.common.CommonProxy`的`preInit`阶段中注册

12. 还有其他的就不一一列举了

## Mod目录

>Dawn-Craft-Mod/ 根目录

>>gradle/ gradle目录

>>libs/ 依赖库

>>src/main/ 源码和资源文件

>>>java/com/github/wdawning/dawncraft/ Mod源码

>>>>achievement/ 成就

>>>>block/ 方块

>>>>client/ 客户端

>>>>command/ 命令

>>>>common/ 服务端

>>>>crafting/ 合成配方

>>>>creativetab/ 创造模式物品栏

>>>>enchantment/ 附魔

>>>>entity/ 实体

>>>>fluid/ 流体

>>>>gui/ 界面

>>>>item/ 物品

>>>>magic/ 魔法

>>>>network/ 网络

>>>>potion/ 药水效果

>>>>tileentity/ 不知道怎么翻译*(我一般都翻译成方块实体)*

>>>>worldgen/ 世界生成

>>>>dawncraft.java Mod主类

>>>resources/ 资源文件

>>>>assets/dawncraft/

>>>>>blockstates/ 方块状态

>>>>>lang/ 语言文件

>>>>>lua/ lua脚本文件

>>>>>models/ 模型文件

>>>>>>block/ 方块模型

>>>>>>item/ 物品模型

>>>>>sounds/ 声音文件

>>>>>textures/ 材质文件

>>>>>>blocks/ 方块材质

>>>>>>entity/ 实体材质

>>>>>>fluid/ 流体材质

>>>>>>gui/ 界面材质

>>>>>>items/ 物品材质

>>>>>>models/armor/ 护甲材质

>>>>>>wdawning_logo.png 工作室LOGO

>>>>>sounds.json 声音引用文件

>>>>mcmod.info mod信息

>LICENSE Mod协议

>LICENSE-fml fml协议

>LICENSE-forge forge协议

>README.md 本文件

>README.txt 官方配置工作环境指导

>build.gradle gradle配置文件

>gradle.properties gradle配置文件2

>gradlew gradlew脚本

>gradlew.bat gradlew脚本

>updatelog.txt Mod更新日志

>version.json 版本文件

## 未来展望

等待更新。

## 更新日志

敬请参阅根目录下的`updatelog.txt`文件。

## 特别致谢

Mojang为我们带来的Minecraft，MCP和Forge团队的Forge/FML.

NeoTech的管道模型文件。

#### &copy; 2016 Wdawning Studio ####
