/**
 * Created with IntelliJ IDEA.
 * User: barrett
 * Date: 1/18/13
 * Time: 5:28 PM
 * To change this template use File | Settings | File Templates.
 */

import akka.actor._
class TestActorProxy(val testActor : ActorRef) extends Actor {

  def receive = {
    case msg => testActor ! msg



  }
}


