package rbm.nodes

/**
 * Created with IntelliJ IDEA.
 * User: barrett
 * Date: 1/15/13
 * Time: 2:08 PM
 * To change this template use File | Settings | File Templates.
 */

import collection.immutable._
import akka.actor._
import akka.actor.FSM._



import rbm.signals._
import rbm.processing._

trait InSynapseLike
trait OutSynapseLike

sealed trait InSynapseState
case object Pending  extends  InSynapseState



case class InSynapseData(receivedTransmitters : List[SynapticTransmitter],weights :Map[Int,Double])

class InSynapse(nodeName : String,inputCount : Int, correspondingNeuron :String = "defaultneuron") extends Actor with FSM[InSynapseState,InSynapseData]
{

  val neuron = correspondingNeuron match {

    case "defaultneuron" => context.actorFor("/user/" + nodeName + "-neuron")
    case _ => {
      val l = context.actorFor("/user/" + correspondingNeuron)
      println("using non default")
      l
    }

  }


  startWith(Pending,InSynapseData(List.empty[SynapticTransmitter],Map.empty[Int,Double]))
  when(Pending) {

    case Event(SynapticTransmitter(b,n),data)  =>  {

      data.receivedTransmitters.length match {
        case cnt if cnt + 1 == inputCount  => {

          val joins = for (weight <- data.weights; transmitter <- data.receivedTransmitters; if(weight._1 == transmitter.id)) yield ExcitationWeight(transmitter,weight._2)
          val signal = ComputeNeuronalSignal.GenerateSignal(joins)
          neuron !  signal
          goto(Pending)  using data

        }
        case _ =>  goto(Pending)   using InSynapseData((SynapticTransmitter(b,n) :: data.receivedTransmitters),data.weights  )




      }


    }

  }



}

class OutSynapse extends Actor with FSM[Int,Int]
{



}
