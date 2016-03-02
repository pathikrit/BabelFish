package com.github.pathikrit.babelfish

import javax.script.{ScriptEngine, ScriptEngineManager, Invocable}


import scala.reflect.ClassTag

class Evaluator(engine: Evaluator.Engine, protected val thiz: Option[AnyRef] = None) extends Dynamic {
  def this(extension: String) = this(new ScriptEngineManager().getEngineByExtension(extension).asInstanceOf[Evaluator.Engine])

  def as[A](code: String): A = engine.eval(code).asInstanceOf[A]

  def apply(code: String): Evaluator = new Evaluator(engine, Some(as[AnyRef](code)))

  def as[A: ClassTag]: A = {
    val clazz = implicitly[ClassTag[A]].runtimeClass
    val result = thiz match {
      case Some(t) => engine.getInterface(t, clazz)
      case _ => engine.getInterface(clazz)
    }
    result.asInstanceOf[A]
  }

  def applyDynamic[A](method: String)(args: Any*): A = {
    val _args = args.map(_.asInstanceOf[AnyRef])
    val result = thiz match {
      case Some(t) => engine.invokeMethod(t, method, _args: _*)
      case _ => engine.invokeFunction(method, _args: _*)
    }
    result.asInstanceOf[A]
  }

  def selectDynamic(field: String) = engine.getFactory.getLanguageName match {
    case "ECMAScript" =>  //See: http://stackoverflow.com/questions/35742484
      import jdk.nashorn.api.scripting.JSObject
      val obj = thiz.getOrElse(throw new NoSuchFieldException(field)).asInstanceOf[JSObject]
      obj.getMember(field)
    case lang => throw new UnsupportedOperationException(s"Field selection is unsupported for $lang")
  }
}

object Evaluator {
  type Engine = ScriptEngine with Invocable
  class JavaScript extends Evaluator("js")
  //class Shell extends Evaluator("sh")
}
