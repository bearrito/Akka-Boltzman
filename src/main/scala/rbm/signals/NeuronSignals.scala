package rbm.signals

/**
 * Created with IntelliJ IDEA.
 * User: barrett
 * Date: 1/15/13
 * Time: 4:09 PM
 * To change this template use File | Settings | File Templates.
 */
sealed trait SynapseToNeuronSignal
case object ExciteNeuron   extends  SynapseToNeuronSignal
case object InhibitNeuron  extends  SynapseToNeuronSignal
