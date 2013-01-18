/**
 * Created with IntelliJ IDEA.
 * User: barrett
 * Date: 1/18/13
 * Time: 1:30 AM
 * To change this template use File | Settings | File Templates.
 */

import akka.actor._
import akka.actor.ActorSystem
import akka.actor.Actor
import akka.actor.Props
import akka.testkit.TestKit

import org.scalatest.FunSuite
import scala.concurrent.duration._

import org.scalatest.BeforeAndAfterAll

import akka.testkit._



import rbm.signals._
import rbm.nodes._

//ActorSystem("test")
class InSynapseSpecs(_system : ActorSystem)  extends TestKit(_system) with FunSuite  with BeforeAndAfterAll
{

  def this() = this(ActorSystem("InSynapseSpecs"))



  override def afterAll {
    system.shutdown()
  }

  test("Is pending on construction"){
       val fsm = TestFSMRef(new InSynapse("synapse",3))
       assert(fsm.stateName == Pending)
  }


  test("Has not received any transmitters on construction"){
   val fsm = TestFSMRef(new InSynapse("synapse",3))
   assert(fsm.stateData.receivedTransmitters.length == 0)
  }

  test("Stores transmitter on reception and stays pending"){

    val fsm = TestFSMRef(new InSynapse("synapse",3))
    val expected =  SynapticTransmitter(true,1)
    fsm !  expected

    assert(fsm.stateName == Pending)
    val actual = fsm.stateData.receivedTransmitters.head
    assert(actual == expected )

  }


  test("When enough transmitters received  neuron signal is sent")
  {

    val proxy = TestActorRef(new TestActorProxy(testActor),"n")
    val fsm = TestFSMRef(new InSynapse("synapse",1,"n"),"synapse")

    val expected = SynapticTransmitter(true,2)
    within(2 seconds)
    {
       fsm ! expected
       expectMsg(InhibitNeuron)
    }


  }





}



