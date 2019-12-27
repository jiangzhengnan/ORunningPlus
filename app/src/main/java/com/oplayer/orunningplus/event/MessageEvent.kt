package com.oplayer.orunningplus.event


class MessageEvent {

    private var message: Any? = null
    private var messageType: Any? = null

    constructor( messageType: Any,message: Any) {
        this.messageType = messageType
        this.message = message
    }

    fun getMessage(): Any? {
        return message
    }

    fun setMessage(message: Any) {
        this.message = message
    }

    fun getMessageType(): Any? {
        return messageType
    }

    fun setMessageType(messageType: Any?) {
        this.messageType = messageType
    }

    override fun toString(): String {
        return "MessageEvent( messageType=$messageType  ,\n message=   $message,)"
    }


}