
package uk.co.smartii.alexa

import com.amazon.speech.slu.Intent
import com.amazon.speech.speechlet.{IntentRequest, LaunchRequest, Session, SessionEndedRequest, SessionStartedRequest, Speechlet, SpeechletRequest}
import uk.co.smartii.alexa.speech.ui.{PlainTextOutputSpeech, SsmlOutputSpeech}
import uk.co.smartii.alexa.speech.speechlet.SpeechletResponse
import uk.co.smartii.alexa.speech.ui.{PlainTextOutputSpeech, SsmlOutputSpeech}

class SmartiiAlexaSpeechlet extends Speechlet {
  import SmartiiAlexaSpeechlet._

  override def onSessionStarted(request: SessionStartedRequest, session: Session): Unit = {
    logInvocation("onSessionStarted", request, session)
  }

  override def onLaunch(request: LaunchRequest, session: Session): SpeechletResponse = {
    logInvocation("onLaunch", request, session)

    val userName = System.getenv("USERNAME")
    val outputSpeech = new SsmlOutputSpeech(s"""<speak>What do you want to know<break time="50ms"/> $userName?</speak>""")

    new SpeechletResponse(outputSpeech)
  }

  override def onIntent(request: IntentRequest, session: Session): SpeechletResponse = {
    logInvocation("onIntent", request, session)

    val answer = SmartiiAlexa.ask()
    val outputSpeech = new PlainTextOutputSpeech(answer)

    new SpeechletResponse(outputSpeech)
  }

  override def onSessionEnded(request: SessionEndedRequest, session: Session): Unit = {
    logInvocation("onSessionEnded", request, session)
  }
}

object SmartiiAlexaSpeechlet {
  private def logInvocation(name: String, request: SpeechletRequest, session: Session): Unit = {
    val requestId = request.getRequestId
    val sessionId = session.getSessionId
    println(s"$name requestId=$requestId sessionId=$sessionId")
  }
}
