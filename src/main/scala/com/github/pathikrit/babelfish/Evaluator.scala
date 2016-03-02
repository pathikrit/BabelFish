package com.github.pathikrit.babelfish

class Evaluator(extension: String) {
  protected[this] val engine = new javax.script.ScriptEngineManager().getEngineByExtension(extension)
  def apply[A](code: String): A = engine.eval(code).asInstanceOf[A]
}

class DynamicEvaluator(extension: String) extends Evaluator(extension: String) with Dynamic {
  private[this] val invoker = engine.asInstanceOf[javax.script.Invocable]
  def as[A](implicit ct: scala.reflect.ClassTag[A]): A = invoker.getInterface(ct.runtimeClass).asInstanceOf[A]
  def applyDynamic[A](method: String)(args: Any*) = invoker.invokeFunction(method, args.map(_.asInstanceOf[AnyRef]) : _*).asInstanceOf[A]
}

object Evaluator {
  class JavaScript extends DynamicEvaluator("js")
  class Shell extends DynamicEvaluator("sh")
}
