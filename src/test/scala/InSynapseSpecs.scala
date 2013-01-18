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

  test("Is pending on construction")
  {

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

    val fsm = TestFSMRef(new InSynapse("synapse",2,"n"))
    val testActor = TestFSMRef(new NamedTestNeuron(),"n")
    val data = InSynapseData(List(SynapticTransmitter(true,1)),Map(1 -> 1.0,2 -> 3.0))
    fsm.setState(Pending,data)

    assert(fsm.stateName == Pending)
    assert(fsm.stateData.receivedTransmitters.length == 1)
    val expected = SynapticTransmitter(true,2)
    within(2 seconds)
    {

       fsm ! expected
       val td = testActor.stateName
       assert(td == SomethingReceived)


    }


  }





}



