package barrett.rbm.nodes

/**
 * Created with IntelliJ IDEA.
 * User: barrett
 * Date: 1/15/13
 * Time: 2:08 PM
 * To change this template use File | Settings | File Templates.
 */

import akka.actor._
import collection.immutable._
import akka.actor.FSM._

import rbm.signals._

trait InSynapseLike
trait OutSynapseLike

sealed trait InSynapseState
case object Pending  extends  InSynapseState



case class InSynapseData(receivedSignals : List[SynapticTransmitter],weights :Map[Int,Float] )

class InSynapse(nodeName : String,inputCount : Int) extends Actor with FSM[InSynapseState,InSynapseData]
{



  when(Pending) {

    case Event(SynapticTransmitter(b,n),data)  =>  {

      data.receivedSignals.length match {
        case cnt if cnt == inputCount => {
            goto(Pending)

        }
        case _ =>  goto(Pending)   using InSynapseData((SynapticTransmitter(b,n) :: data.receivedSignals),data.weights  )




      }


    }

  }



}

class OutSynapse extends Actor with FSM[Int,Int]
{



}
