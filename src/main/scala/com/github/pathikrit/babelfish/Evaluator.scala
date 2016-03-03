package com.github.pathikrit.babelfish

class Evaluator(engine: Evaluator.Engine, protected val thiz: Option[AnyRef] = None) extends Dynamic {
  def this(extension: String) = this(new javax.script.ScriptEngineManager().getEngineByExtension(extension).asInstanceOf[Evaluator.Engine])

  def as[A](code: String): A = engine.eval(code).asInstanceOf[A]

  def apply(code: String): Evaluator = new Evaluator(engine, Option(as[AnyRef](code)))

  def file(path: String): Evaluator = new Evaluator(engine, Option(engine.eval(new java.io.FileReader(path))))

  def as[A](implicit ct: reflect.ClassTag[A]): A = (thiz match {
    case Some(t) => engine.getInterface(t, ct.runtimeClass)
    case _ => engine.getInterface(ct.runtimeClass)
  }).asInstanceOf[A]

  def applyDynamic[A](method: String)(args: Any*): A = call(method)(args.map(_.asInstanceOf[AnyRef])).asInstanceOf[A]

  private[this] def call(method: String)(args: Seq[AnyRef]) = thiz match {
    case Some(t) => engine.invokeMethod(t, method, args: _*)
    case _ => engine.invokeFunction(method, args: _*)
  }

  def selectDynamic(field: String) = engine.getFactory.getLanguageName match {
    case "ECMAScript" =>  //See: http://stackoverflow.com/questions/35742484
      val obj = thiz.getOrElse(throw new NoSuchFieldException(field)).asInstanceOf[jdk.nashorn.api.scripting.JSObject]
      obj.getMember(field)
    case lang => throw new UnsupportedOperationException(s"Field selection is unsupported for $lang")
  }
}

object Evaluator {
  private[Evaluator] type Engine = javax.script.ScriptEngine with javax.script.Invocable
  class JavaScript extends Evaluator("js")
  //class Shell extends Evaluator("sh")
}
