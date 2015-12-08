package net.hotelling.harold.trydust

import scala.io.Source
import javax.servlet.http.{HttpServletRequest, HttpServletResponse}
import org.eclipse.jetty.server.{Request, Server}
import org.eclipse.jetty.server.handler.AbstractHandler

class Handler extends AbstractHandler {

  def sendJson(source: Source, response: HttpServletResponse): Unit = {
    response.setContentType("application/javascript; charset=utf-8")
    response.setStatus(HttpServletResponse.SC_OK)
    val writer = response.getWriter()
    source.foreach { writer.append(_) }
  }

  def sendHtmlFile(filename: String, response: HttpServletResponse): Unit = {
    response.setContentType("text/html; charset=utf-8")
    response.setStatus(HttpServletResponse.SC_OK)
    val writer = response.getWriter()
    Source.fromFile(filename, "UTF-8").foreach { writer.append(_) }
  }

  override def handle(target: String, baseRequest: Request, request: HttpServletRequest, response: HttpServletResponse): Unit = {
    println(s"Handling request for $target")
    target match {
      case "/try_dust.html" => sendHtmlFile("try_dust.html", response)
      case "/dust.js" => sendJson(Source.fromFile("../dust.js", "UTF-8"), response)
      case "/jquery.min.js" => {
        Thread.sleep(5000)
        sendJson(Source.fromURL("https://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"), response)
      }
      case "/templates/intro.tl" => sendHtmlFile("../templates/intro.tl", response)
      case _ => response.setStatus(HttpServletResponse.SC_NOT_FOUND)
    }
    baseRequest.setHandled(true)
  }

}

object App {

  def main(args: Array[String]): Unit = {
    val server = new Server(5432)
    server.setHandler(new Handler())
    server.start()
    server.join()
  }

}