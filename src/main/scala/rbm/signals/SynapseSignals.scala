package rbm.signals

import sun.font.TrueTypeFont

/**
 * Created with IntelliJ IDEA.
 * User: barrett
 * Date: 1/15/13
 * Time: 2:43 PM
 * To change this template use File | Settings | File Templates.
 */

case class SynapticTransmitter(excite : Boolean, id :Int)
object SynapticTransmitter
{

  implicit def synapticTransmitter2Double(e : SynapticTransmitter) : Double = if(e.excite)  1.0 else 0.0


}




