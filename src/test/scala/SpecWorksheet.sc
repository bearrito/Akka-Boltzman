/**
 * Created with IntelliJ IDEA.
 * User: barrett
 * Date: 1/18/13
 * Time: 12:41 PM
 * To change this template use File | Settings | File Templates.
 */

import akka.actor._
import rbm.nodes._
import rbm.signals._

val system = ActorSystem("mysis")
val synapse = system.actorOf(Props(new InSynapse("s",1,"n")),"synapse")
val test = system.actorOf(Props(new NamedTestNeuron()),"n")

synapse ! SynapticTransmitter(true,1)


