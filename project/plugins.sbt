resolvers += "jgit-repo" at "http://download.eclipse.org/jgit/maven"

addSbtPlugin("com.jsuereth"       %   "sbt-pgp"         % "1.0.0")
addSbtPlugin("com.github.gseitz"  %   "sbt-release"     % "1.0.0")
addSbtPlugin("com.timushev.sbt"   %   "sbt-updates"     % "0.1.9")
addSbtPlugin("org.scoverage"      %   "sbt-scoverage"   % "1.1.0")
addSbtPlugin("org.xerial.sbt"     %   "sbt-sonatype"    % "0.5.1")
