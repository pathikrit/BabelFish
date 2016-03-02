package com.github.pathikrit.babelfish

import javax.script.{Invocable, ScriptEngineManager}

class Evaluator(extension: String) {
  protected[this] val engine = new ScriptEngineManager().getEngineByExtension(extension)
  def apply[A](code: String): A = engine.eval(code).asInstanceOf[A]
}

class DynamicEvaluator(extension: String) extends Evaluator(extension: String) with Dynamic {
  private[this] val invoker = engine.asInstanceOf[Invocable]
  //def as[A]: A = invoker.getInterface(classOf[A])
  def applyDynamic[A](method: String)(args: Any*) = invoker.invokeFunction(method, args.map(_.asInstanceOf[AnyRef]) : _*).asInstanceOf[A]
}

object Evaluator {
  class JavaScript extends DynamicEvaluator("js")
  class Shell extends DynamicEvaluator("sh")
}
