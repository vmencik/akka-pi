package cz.mencik.tutor.akka.pi

import akka.actor._
import akka.remote.RemoteClientShutdown

class MasterListener extends Actor {
  def receive = {
    case rse: RemoteClientShutdown =>
      println("Cannot live without the Master: " + rse.toString)
      context.system.shutdown()
  }
}

object Slave extends App {
  val system = ActorSystem("PiSystem")
  val lsnr = system.actorOf(Props[MasterListener], "masterListener")
  
  system.eventStream.subscribe(lsnr, classOf[RemoteClientShutdown])
}