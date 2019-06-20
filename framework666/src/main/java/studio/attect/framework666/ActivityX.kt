package studio.attect.framework666

import studio.attect.framework666.activity.PerceptionActivity
import studio.attect.framework666.viewModel.CommonEventViewModel
import studio.attect.framework666.viewModel.SignalViewModel
import studio.attect.framework666.viewModel.WindowInsetsViewModel

/**
 * 使用本框架
 * Activity就应该继承此类！
 *
 * 此类的父类会随着开发和变更而变更，不可通过反射获取准确类型
 * @author Attect
 */
abstract class ActivityX : PerceptionActivity() {

}