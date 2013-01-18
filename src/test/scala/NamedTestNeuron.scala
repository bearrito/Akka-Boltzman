/**
 * Created with IntelliJ IDEA.
 * User: barrett
 * Date: 1/18/13
 * Time: 11:41 AM
 * To change this template use File | Settings | File Templates.
 */

import akka.actor._
import akka.actor.FSM

import rbm.signals._
import rbm.nodes._


sealed trait NamedTestNeuronState
case object SomethingReceived  extends  NamedTestNeuronState
case object NothingReceived  extends   NamedTestNeuronState

sealed trait NamedTestNeuronData
case object Nothing extends NamedTestNeuronData
case class SomeData(transmitters : List[SynapseToNeuronSignal])     extends NamedTestNeuronData

class NamedTestNeuron extends Actor with FSM[NamedTestNeuronState,NamedTestNeuronData]{

  startWith(NothingReceived,Nothing)

  when(NothingReceived)
  {
    case Event(ExciteNeuron,Nothing) => {
      println("got")
      goto(SomethingReceived) using  SomeData(List(ExciteNeuron))
    }
    case Event(InhibitNeuron,Nothing) => goto(SomethingReceived) using  SomeData(List(ExciteNeuron))

  }

  when(SomethingReceived)
  {

    case Event(ExciteNeuron,SomeData(d)) => goto(SomethingReceived) using  SomeData( ExciteNeuron :: d)
    case Event(InhibitNeuron,SomeData(d)) => goto(SomethingReceived) using  SomeData( ExciteNeuron :: d)

  }


}
