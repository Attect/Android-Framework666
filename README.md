# Android-Framework666
个性鲜明的Android开发框架 Kotlin/MVVM

这是我根据个人实际Android开发经验，为了解决开发Android程序时，各种项目都会大量重复需要的结构和操作而封装的一套开发框架。

此框架并非严格约束，部分功能和效果你可以绕过。

一些更详细的说明，请参阅Wiki或Demo代码

## 主要成分

框架以来了我的或我修改的其他开源库，例如[StaticViewModelStore](https://github.com/Attect/StaticViewModelStore)、[WebSocketService](https://github.com/Attect/WebSocketService)等，它们的特点不在此描述，有兴趣请点击链接查看。

- ContainerX 容器与组件：将各整屏部分界面抽象为组建（Component）层，用于在Activity/Framgent/ViewPager等“容器”中方便切换内部主要内容，同时做了一个Fragment参数传递可选约束，支持Component间通过Android Navigation进行导航切换。提供全局组件列表，可直接作为入口调试，提供默认Componet避免开发或部署时tag错误导致的非友好体验。
- DataXOffice：数据存档及传输的最小化利器！档案X办公室将会把你的数据丢弃key后用MessagePack将所有value打包，并提供逆向操作。可以用于减小储存数据占用的磁盘空间，或者减小网络传输所带来的开销。它的序列化和反序列化操作，以及结果，均比JSON/GSON优秀，仅在复杂内部类（内部类是支持的）及复杂List/Map的操作上不及GSON。如果你将它用于网络传输，我还提供了一个效果一样只是名称不一样的Java库[EasyMessagePack](https://github.com/Attect/EasyMessagePack)，可用于Spring Boot等项目进行双方对等序列化、反序列化操作。
- CacheX 缓存：直接缓存Bean/POJO/数据类等对象到App缓存空间中，附带versionCode检查、缓存类型设置、缓存tag、缓存有效期指定功能。提供高效的缓存有效性检测功能，兼容高版本安卓系统的空间预申请操作，拥有避免彻底写满用户储存空间策略。读写均为异步操作，无论超多数量还是超大文件均不会导致界面卡顿。提供自动清理调用，失效检测触发可自定义策略执行。
- 安全的文件写入：提供异步文件写入操作，写入前会向系统提前申请空间（高版本安卓SDK），计算预留空间。写入前、时、后均有回调，方便获取各种状态。
- Activity、Fragment基本布局约束：此框架做了一个可选的布局约束，你可以完全遵守或部分遵守，以获得全部或部分自动配置效果。例如若完全遵守此框架的布局约束，可轻松自动适配缺口屏（刘海、水滴屏）的状态栏高度，且可自适应在缺口屏下的屏幕旋转行为。
- 全局信号：优雅的退出所有的Activity，或者接收全局异常信号，亦可自定义值。
- 阻止崩溃弹窗：框架对主线程Handler做了处理，主线程和子线程中，部分崩溃将会被忽略，App将得以继续运行。发生未处理的异常时，会向所有已经正在运行的Activity、Fragment、Service发送信号告知有严重错误发生。同时可以处理掉全局异常，以便自定义崩溃效果。同时依然兼容各类第三方Bug反馈框架回传崩溃信息。
- 事件传递：用于一个Activity内，使用多个Fragment进行同一个可观察的数据进行操作，在一个Fragment或Activity变更数据或进行操作后，需通知Activity和其它Fragment同时进行对应行为。例如情景：一个业务操作分为多步操作，数据在Activity层可观察，每一步分为一个Fragment，每个Fragment中都有取消和保存，而取消和保存的逻辑由Activity实现，当Fragment做数据变更后，用户点击了取消，Activity均可观察到最新数据以及用户发生了消极行为。
- Logcat改进：超长的输出将会被自动分行以便在调试时能看到全部内容，同时提供接口注册自定义Logcat打印器，实现Logcat同时输出到多处功能。
- 屏幕安全距离可观察化：你可以在Activity、Fragment中（均至少含有一个View）的任意生命周期开始观察屏幕安全距离的变更，由截获WindowInsets实现，轻松应对缺口屏的旋转、折叠屏的开合以及环绕屏的前后切换导致边缘布局必要的调整。
- 返回事件队列：原本Google自己实现过但总是大改动且使用很受限，因此自己实现了。你可以添加和注销返回事件处理器，并可控制是否跳过向上冒泡。通常用于“点按两次返回键退出”和点击返回按钮关闭或收起界面中的某一部分内容。
- 残疾辅助、醒目提醒功能：对Activity和Fragment均提供了很方便的TTS语音读出功能，以及震动马达的调用、产生一个提示音、顶部盖住Appbar进行醒目提示。使用TTS时仅需传递一个字符串，引擎的初始化及播报队列会自动为你搞定。
- 特殊状态栏色彩切换兼容：整合了Flyme和MIUI的状态栏图标黑白风格切换。
- 提供运行时BuildConfig：辅助框架获得你的App的BuildConfig
- 极方便的RecyclerViewAdapter：提供SimpleRecyclerViewAdapter，将ViewHolder与Adapter解耦，提供独特简易的数据操作方法，轻松对列表进行增删改差，支持批量操作，且均支持多种ViewHolder，支持假操作预测数据变更效果。使用此Adpater你只需要关心你的数据对应哪个Layout，以及数据如何绑定到Layout上。还兼容部分其它开源Adapter结合使用（如[android-advancedrecyclerview](https://github.com/h6ah4i/android-advancedrecyclerview) 一起作出分组、拖拽排序等效果）
- 繁杂操作扩展：针对安卓开发一些非常绕的写法，例如需要经过各种Compat包做兼容的调用的一些方法和属性，做了Kotlin扩展封装。

