package rbm.processing

/**
 * Created with IntelliJ IDEA.
 * User: barrett
 * Date: 1/15/13
 * Time: 4:02 PM
 * To change this template use File | Settings | File Templates.
 */

import scala.util.Random._
import rbm.signals._

case class ExcitationWeight(signal : SynapticTransmitter, weight : Double)
object  ExcitationWeight
{
  implicit  def excitationWeight2Float(ew : ExcitationWeight) : Double = ew.weight * ew.signal
}


trait ComputesNeuronalSignalLike {
  def GenerateSignal(s : List[ExcitationWeight]) : SynapseToNeuronSignal
}

object ComputeNeuronalSignal   extends  ComputesNeuronalSignalLike
{


  private def compute0(s : List[ExcitationWeight], acc:Double) : Double = {

    s match
    {
        case Nil => acc
        case head :: tail => compute0(tail, acc + head )
    }
  }





    def GenerateSignal(s : List[ExcitationWeight]) : SynapseToNeuronSignal = {

      val sum = compute0(s,0)

      nextDouble() match
      {

        case r if r < sum =>   ExciteNeuron
        case _ => InhibitNeuron

      }





    }





}




