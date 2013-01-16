/**
 * Created with IntelliJ IDEA.
 * User: barrett
 * Date: 1/14/13
 * Time: 10:25 PM
 * To change this template use File | Settings | File Templates.
 */

import akka.actor._
import akka.routing.BroadcastRouter
import scala.collection.mutable._
import barrett.rbm.nodes._
import barrett.rbm.signals._


val s = ExciteSynapse(1) * 1.0f
val numInputNodes = 4
val numHiddenNodes = 8
val system = ActorSystem("graph")
var inputNodes =  ArrayBuffer.empty[ActorRef]
var hiddenNodes =  ArrayBuffer.empty[ActorRef]

var inputToHiddenRouters = ArrayBuffer.empty[ActorRef]
var hiddenToInputRouters = ArrayBuffer.empty[ActorRef]


for (i <- 1 to numInputNodes){
  inputNodes +=   system.actorOf(Props(new InSynapse()),name = i.toString())
}


for(i <- 1 to numHiddenNodes){
  hiddenNodes += system.actorOf(Props(new InSynapse()),name = i.toString())
}

for(node <- hiddenNodes){
  inputToHiddenRouters += system.actorOf(Props().withRouter(BroadcastRouter(routees = inputNodes)),name = "router" + i.toString())
}

for(node <- inputNodes){
 hiddenToInputRouters += system.actorOf(Props().withRouter(BroadcastRouter(routees = hiddenNodes)),name = "router" + i.toString())
}




