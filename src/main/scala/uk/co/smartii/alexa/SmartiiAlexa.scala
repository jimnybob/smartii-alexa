/*
 * Copyright (c) 2016 Ryan Moeller <ryan@freqlabs.com>
 *
 * Permission to use, copy, modify, and/or distribute this software for any
 * purpose with or without fee is hereby granted, provided that the above
 * copyright notice and this permission notice appear in all copies.
 *
 * THE SOFTWARE IS PROVIDED “AS IS” AND THE AUTHOR DISCLAIMS ALL WARRANTIES
 * WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR
 * ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
 * WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
 * ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF
 * OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 */

package uk.co.smartii.alexa

import scala.io.Source
import scala.util.Random
import play.api.libs.ws.ning.NingWSClient

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import play.api.libs.json._

object SmartiiAlexa {

  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()
  val wsClient = NingWSClient()

  def ask(): String = {

    val futureResponse = wsClient.url(System.getenv("RESPONSE_URL")).get()
    // Has to be synchronous :(
    val response = Await.result(futureResponse, 2 seconds)

    response.json.as[String]
  }
}
