package rbm.nodes



import akka.actor._
import akka.actor.FSM._

import rbm.signals._

trait NamedNode
{
  def Name : String

}


sealed trait SignalNodeState
case object Off extends  SignalNodeState
case object On extends SignalNodeState
case object Uninitialized extends  SignalNodeState





class SignalNode(val Name : String) extends Actor with FSM[SignalNodeState,SignalNodeState]  with NamedNode
{
    def State : Boolean = true



    startWith(Uninitialized,Uninitialized)

   when(Uninitialized){

     case Event(ExciteNeuron,_) =>  goto(On) using On



   }





}

