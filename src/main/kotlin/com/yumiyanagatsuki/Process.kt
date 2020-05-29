package com.yumiyanagatsuki

import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder
import com.amazonaws.services.simpleemail.model.*
import com.amazonaws.services.sns.message.*
import java.io.InputStream

class Process {

    fun send(useSpecial: Boolean?): Boolean {
        val client = AmazonSimpleEmailServiceClientBuilder.standard().build()

        // Create the subject and body of the message.
        val subjectParam = Content().withData(if (useSpecial == true) Env.SUBJECT_WITH_SPECIAL else Env.SUBJECT_WITHOUT_SPECIAL)
        val textBody = Content().withData(Env.BODY)
        val bodyParam = Body().withText(textBody)

        // Create a message with the specified subject and body.
        val message = Message().withSubject(subjectParam).withBody(bodyParam)

        // Sends email
        client.sendEmail(
            SendEmailRequest()
                .withSource(Env.FROM)
                .withDestination(Destination().withToAddresses(Env.TO))
                .withMessage(message)
                .withConfigurationSetName(Env.CONFIGURATION_SET)
        )

        return true
    }

    fun handle(inputStream: InputStream): Boolean {
        var success = false

        val handler = object : SnsMessageHandler() {
            override fun handle(message: SnsNotification?) {
                success = true
            }

            override fun handle(message: SnsSubscriptionConfirmation?) {
                message?.confirmSubscription()
                success = true
            }

            override fun handle(message: SnsUnsubscribeConfirmation?) {
                success = true
            }

            override fun handle(message: SnsUnknownMessage?) {
                success = true
            }
        }

        SnsMessageManager().handleMessage(inputStream, handler)
        return success
    }

}